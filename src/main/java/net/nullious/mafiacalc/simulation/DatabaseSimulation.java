package net.nullious.mafiacalc.simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import net.nullious.mafiacalc.Role;
import net.nullious.mafiacalc.Tag;
import net.nullious.mafiacalc.settings.RoleDecider;

public class DatabaseSimulation extends Simulation {
	
	private static enum SimTag {
		DEAD,
		SUSPECTED,
	}
	
	// Variable sin the RoleState class will be "null" if they has not yet been determined.
	// RoleStates will be merged together over the course of the Simulation.
	private static class RoleState {
		// A Meta/Non-Meta Role from the initial configuration
		public Role configured_role = null;
		// A Non-Meta Role which is compatible with this.configured_role
		public Role selected_role = null;
		
		public EnumSet<SimTag> tags = EnumSet.noneOf(SimTag.class);
	}
	
	private static class SimState {
		public ArrayList<RoleState> role_states = new ArrayList<>();
		
	}

	public DatabaseSimulation(Map<Role, RoleDecider> settings, List<Role> configured, Set<Role> ignored, List<Role> confirmedDead, List<Role> suspected) {
		super(settings, configured, ignored, confirmedDead, suspected);
	}

	@Override
	public void simulate() {
		SimState state = new SimState();
		
		initSimState(state);
		
		merge_exact_matches(state);
		
		commit_results(state);
	}

	private void initSimState(SimState state) {
		for (Role r : this.configured) {
			RoleState rstate = new RoleState();
			rstate.configured_role = r;
			state.role_states.add(rstate);
		}
		
		for (Role r : this.confirmedDead) {
			RoleState rstate = new RoleState();
			rstate.selected_role = r;
			rstate.tags.add(SimTag.DEAD);
			state.role_states.add(rstate);
		}
		
		for (Role r : this.suspected) {
			RoleState rstate = new RoleState();
			rstate.selected_role = r;
			rstate.tags.add(SimTag.SUSPECTED);
			state.role_states.add(rstate);
		}
	}

	private void merge_exact_matches(SimState state) {
		List<RoleState> configured_only = state.role_states.stream()
			.filter(r -> r.configured_role != null && r.selected_role == null)
			.collect(Collectors.toList());
		
		List<RoleState> selected_only = state.role_states.stream()
			.filter(r -> r.configured_role == null && r.selected_role != null)
			.collect(Collectors.toList());
		
		for (RoleState cstate : configured_only) {
			Optional<RoleState> match = selected_only.stream()
				.filter(r -> r.selected_role == cstate.configured_role)
				.findFirst();
			
			if (match.isPresent()) {
				RoleState sstate = match.get();
				cstate.selected_role = sstate.selected_role;
				cstate.tags.addAll(sstate.tags);
				state.role_states.remove(sstate);
			}
		}
	}

	private void commit_results(SimState state) {
		this.result_possibly_living_initial_config = Collections.unmodifiableList(state.role_states.stream()
				.filter(r -> r.configured_role != null && !r.tags.contains(SimTag.DEAD))
				.map(r -> r.configured_role)
				.collect(Collectors.toList())
			);
		
		EnumSet<Role> plr_set = EnumSet.noneOf(Role.class);
		
		for (RoleState rs : state.role_states) {
			if (rs.configured_role == null || rs.tags.contains(SimTag.DEAD)) {
				continue;
			}
			if (rs.selected_role != null) {
				plr_set.add(rs.selected_role);
			} else if (!rs.configured_role.hasTag(Tag.META)) {
				plr_set.add(rs.configured_role);
			} else {
				plr_set.addAll(this.settings.get(rs.configured_role).getRoleSet());
			}
		}
		
		this.result_possibly_living_roles = Collections.unmodifiableSet(plr_set);
		
		HashMap<Role, Integer> min_map = new HashMap<>();
		HashMap<Role, Integer> max_map = new HashMap<>();
		
		for (Role r : this.result_possibly_living_roles) {
			min_map.put(r, 0);
			max_map.put(r, 0);
		}
		
		for (RoleState rs : state.role_states) {
			if (rs.configured_role == null || rs.tags.contains(SimTag.DEAD)) {
				continue;
			}
			
			if (rs.selected_role == null) {
				if (!rs.configured_role.hasTag(Tag.META)) {
					min_map.put(rs.configured_role, min_map.get(rs.configured_role)+1);
				}
				// technically there may be some cases where a Meta role can only be
				// one specific Non-Meta role, but I choose to ignore that.
			}
		}
		
		for (RoleState rs : state.role_states) {
			if (rs.configured_role == null || rs.tags.contains(SimTag.DEAD)) {
				continue;
			}
			
			if (rs.selected_role == null) {
				if (!rs.configured_role.hasTag(Tag.META)) {
					max_map.put(rs.configured_role, max_map.get(rs.configured_role)+1);
				} else {
					for (Role r : this.settings.get(rs.configured_role).getRoleSet()) {
						max_map.put(r, max_map.get(r));
					}
				}
				// technically there may be some cases where a Meta role can only be
				// one specific Non-Meta role, but I choose to ignore that.
			} else {
				max_map.put(rs.selected_role, max_map.get(rs.selected_role)+1);
			}
		}

		this.result_min_possible_count = Collections.unmodifiableMap(min_map);
		this.result_max_possible_count = Collections.unmodifiableMap(max_map);
	}

}
