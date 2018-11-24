package net.nullious.mafiacalc.simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import net.nullious.mafiacalc.Role;
import net.nullious.mafiacalc.Tag;
import net.nullious.mafiacalc.settings.RoleDecider;

public class StackSimulation extends Simulation {
	private static final ExecutorService executor = Executors.newWorkStealingPool();
	private final Role[] all_roles = Role.getNormalSet().toArray(new Role[0]);
	private List<List<Role>> possibilities;

	public StackSimulation(Map<Role, RoleDecider> settings, List<Role> save, Set<Role> ignored, List<Role> dead) {
		super(settings, save, ignored, dead);
	}

	@Override
	public void simulate() {
		this.possibilities = new ArrayList<>();
		ArrayList<Role> stack = new ArrayList<>();
		
		ConcurrentLinkedQueue<List<Role>> buffer = new ConcurrentLinkedQueue<>();
		AtomicInteger pending_count = new AtomicInteger(0);
		
		long start = System.currentTimeMillis();
		StackWorker worker = new StackWorker(stack, buffer, pending_count, false);
		executor.execute(worker);
		
		printEstimate();
		
		do {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ignored) {}
			System.out.println(pending_count.get());
		} while (pending_count.get() > 0);
		
		System.out.println("Simulation ran in " + (System.currentTimeMillis() - start) + "ms");
		
		
		this.result_confirmed = new ArrayList<>();
		this.result_possible_roles = EnumSet.noneOf(Role.class);
		this.result_unmatched_metas = new ArrayList<>();
		this.result_unmatched_roles = new ArrayList<>();
	}
	
	private void printEstimate() {
		int estimate = 1;
		
		for (Role r : this.save) {
			if (r.hasTag(Tag.META)) {
				estimate *= this.settings.get(r).getRoleSet().size();
			}
		}
		
		System.out.println("Preparing to simulate " + estimate + " possibilites.");
	}
	
	private class StackWorker implements Runnable {
		
		private final List<Role> inherited;
		private final ConcurrentLinkedQueue<List<Role>> output_buffer;
		private final AtomicInteger pending_count;
		
		public StackWorker(List<Role> inherited, ConcurrentLinkedQueue<List<Role>> output_buffer, AtomicInteger pending_count, boolean synchronous) {
			this.inherited = inherited;
			this.output_buffer = output_buffer;
			this.pending_count = pending_count;
			if (!synchronous) {
				this.pending_count.incrementAndGet();
			}
		}

		@Override
		public void run() {
			int slot = this.inherited.size();
			Role next = save.get(slot);
			
			//System.out.println(this.inherited);
			
			if (!next.hasTag(Tag.META)) {
				ArrayList<Role> inheritance = new ArrayList<>(this.inherited);
				inheritance.add(next);
				
				if (isPossible(inheritance)) {
					if (inheritance.size() < save.size()) {
						StackWorker child = new StackWorker(inheritance, this.output_buffer, this.pending_count, true);
						child.run();
					} else {
						recordPossibility(inheritance);
					}
				}
			} else {
				Set<Role> candidates = settings.get(next).getRoleSet();
				for (Role candidate : candidates) {
					ArrayList<Role> inheritance = new ArrayList<>(this.inherited);
					inheritance.add(candidate);
					
					if (isPossible(inheritance)) {
						if (inheritance.size() < save.size()) {
							if (this.pending_count.get() < 64) {
								StackWorker child = new StackWorker(inheritance, this.output_buffer, this.pending_count, false);
								executor.execute(child);
							} else {
								StackWorker child = new StackWorker(inheritance, this.output_buffer, this.pending_count, true);
								child.run();
							}
						} else {
							recordPossibility(inheritance);
						}
					}
				}
			}

			this.pending_count.decrementAndGet();
		}
		
		private void recordPossibility(List<Role> stack) {
			if (stack.size() != save.size()) {
				throw new IllegalArgumentException("Stack size is invalid");
			}
			
			this.output_buffer.add(Collections.unmodifiableList(stack));
		}
		
		private boolean isPossible(List<Role> candidate) {
			if (candidate.size() > save.size()) {
				System.err.println("WARN: isPossible() is checking a too-big candidate!");
				return false;
			}
			
			int x = 0;
			for (Role c : candidate) {
				Role from_save = save.get(x);
				if (from_save.hasTag(Tag.META)) {
					if (!settings.get(from_save).getRoleSet().contains(c)) {
						return false;
					}
				} else {
					if (c != from_save) {
						return false;
					}
				}
				
				x++;
			}
			
			return true;
		}
		
	}

}
