/**
 * This file is part of MafiaCalculator.
 *
 * MafiaCalculator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MafiaCalculator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MafiaCalculator.  If not, see <https://www.gnu.org/licenses/>.
 */
package net.nullious.mafiacalc.simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.nullious.mafiacalc.Role;
import net.nullious.mafiacalc.Tag;
import net.nullious.mafiacalc.settings.RoleDecider;

public class LogicalSimulation extends Simulation {
	
	public LogicalSimulation(Map<Role, RoleDecider> settings, List<Role> save, Set<Role> ignored, List<Role> dead,
							 List<Role> suspected) {
		super(settings, save, ignored, dead, suspected);
	}

	public void simulate() {
		// A list of Non-META Roles which we are currently assuming are in the game
		List<Role> calculated_confirmed = new ArrayList<>();
		// A list of META Roles which we have not yet considered
		List<Role> unmatched_metas = new ArrayList<>();
		// A list of the confirmed confirmedDead Roles which we have not yet considered
		List<Role> rem_dead = new ArrayList<>(this.confirmedDead);
		// A list of roles which are confirmed to be in the game, but we don't know what META Role spawned them yet.
		List<Role> unmatched_roles = new ArrayList<>();
		
		for (Role r : configured) {
			if (r.hasTag(Tag.META)) {
				unmatched_metas.add(r);
			} else {
				calculated_confirmed.add(r);
				if (rem_dead.contains(r)) {
					// We'll assume that the confirmedDead Role was explicitly configured in the game.
					rem_dead.remove(r);
				}
			}
		}
		
		// Add any remaining confirmedDead Roles back into our outstanding work
		unmatched_roles.addAll(rem_dead);
		rem_dead = null; // Don't need this anymore, so let's cause an error if I forget about that.
		
		// **** First Pass ****
		// Let's look for any Roles that have only one META-Role which can spawn them.  
		List<Role> todo = new ArrayList<>(unmatched_roles);
		unmatched_roles.clear();
		
		while (!todo.isEmpty()) {
			Role current_role = todo.remove(0);
			// The Set of META Roles which could spawn the current Non-META Role.
			Set<Role> possible = getPossibleSpawners(current_role, unmatched_metas);
			
			if (possible.isEmpty()) {
				throw new IllegalArgumentException("The current setup is impossible! (probably someone had their role changed)");
			} else if (possible.size() == 1) {
				// We only have one choice, so it must be the correct one.
				calculated_confirmed.add(current_role);
				unmatched_metas.remove(possible.toArray(new Role[0])[0]);
			} else {
				// We aren't sure what this role is quite yet.
				unmatched_roles.add(current_role);
			}
		}
		
		// **** Second Pass ****
		// Now we'll go the other way around -- look for any META-Roles which can only spawn one confirmed role
		todo.addAll(unmatched_metas);
		
		while (!todo.isEmpty()) {
			// Grab the current META-Role
			Role current_meta = todo.remove(0);
			
			// Is the current role a superset of anything?
			boolean proper_superset = false;
			for (Role other : unmatched_metas) {
				if (isProperSuperset(current_meta, other)) {
					// Skip this one for now.
					proper_superset = true;
					break;
				}
			}
			if (proper_superset) {
				continue;
			}
			
			Set<Role> possible_spawns = cullIgnored(this.settings.get(current_meta).getRoleSet());
			
			// Check if any roles fit into this META-Role
			Role target = null;
			for (Role r : unmatched_roles) {
				if (possible_spawns.contains(r)) {
					target = r;
					break;
				}
			}
			if (target != null) {
				calculated_confirmed.add(target);
				unmatched_roles.remove(target);
				unmatched_metas.remove(current_meta);
			}
		}
		
		Set<Role> all_possible_roles = EnumSet.noneOf(Role.class);
		for (Role meta : unmatched_metas) {
			all_possible_roles.addAll(this.settings.get(meta).getRoleSet());
			for (Role t : this.settings.get(meta).getRoleSet()) {
				if (t == Role.GODFATHER) {
					System.out.println(meta + " Contains " + t);
				}
			}
		}
		
		//this.result_confirmed = Collections.unmodifiableList(calculated_confirmed);
		//this.result_unmatched_metas = Collections.unmodifiableList(unmatched_metas);
		//this.result_unmatched_roles = Collections.unmodifiableList(unmatched_roles);
		this.result_possibly_living_roles = Collections.unmodifiableSet(all_possible_roles);
	}
	
	// Remove all entries from 'rs' which the user has told us to ignore.
	private Set<Role> cullIgnored(Set<Role> rs) {
		Set<Role> culled = EnumSet.copyOf(rs);
		culled.removeAll(this.ignored);
		return culled;
	}
	
	private Set<Role> getPossibleSpawners(Role r, List<Role> metas) {
		Set<Role> possible = EnumSet.noneOf(Role.class);
		
		for (Role meta : metas) {
			if (cullIgnored(this.settings.get(meta).getRoleSet()).contains(r)) {
				possible.add(meta);
			}
		}
		
		return possible;
	}
	
	private Set<Role> getPossibleSpawns(Role meta, List<Role> dead) {
		Set<Role> possible = cullIgnored(this.settings.get(meta).getRoleSet());

		possible.retainAll(dead);
		
		return possible;
	}
	
	private Role getImproperSubset(Set<Role> metas) {
		
		for (Role candidate : metas) {
			Set<Role> subset = cullIgnored(this.settings.get(candidate).getRoleSet());
			boolean all_improper = true;
			for (Role meta : metas) {
				Set<Role> superset = cullIgnored(this.settings.get(meta).getRoleSet());
				if (!isImproperSubset(subset, superset)) {
					all_improper = false;
					break;
				}
			}
			if (all_improper) {
				return candidate;
			}
		}
		
		return null;
	}
	
	private boolean isImproperSubset(Set<Role> subset, Set<Role> superset) {
		return superset.containsAll(subset);
	}
	
	private boolean isProperSuperset(Role superset, Role subset) {
		Set<Role> sup = cullIgnored(this.settings.get(superset).getRoleSet());
		Set<Role> sub = cullIgnored(this.settings.get(subset).getRoleSet());
		return sup.containsAll(sub) && !sub.containsAll(sup);
	}
}
