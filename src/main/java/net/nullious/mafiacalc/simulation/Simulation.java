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
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.nullious.mafiacalc.Role;
import net.nullious.mafiacalc.settings.RoleDecider;

public abstract class Simulation {
	// META-Role -> Role Decider. Used to lookup what Roles can be picked for "Random Any", etc.
	protected final Map<Role, RoleDecider> settings;
	// An exact list of all Roles (META and non-META) which were configured in the game.
	protected final List<Role> configured;
	// User-provided set of every Role that is impossible in the current game.
	protected final Set<Role> ignored;
	// User-provided list of every role that has died so far.
	protected final List<Role> confirmedDead;
	// User-provided list of suspected roles for each entry in configured.
	protected final List<Role> suspected;

	public Simulation(Map<Role, RoleDecider> settings, List<Role> configured, Set<Role> ignored, List<Role> confirmedDead,
					  List<Role> suspected) {
		this.settings = Collections.unmodifiableMap(new EnumMap<>(settings));
		this.configured = Collections.unmodifiableList(new ArrayList<>(configured));
		this.ignored = Collections.unmodifiableSet(EnumSet.copyOf(ignored));
		this.confirmedDead = Collections.unmodifiableList(new ArrayList<>(confirmedDead));
		this.suspected = Collections.unmodifiableList(new ArrayList<>(suspected));
	}
	
	protected List<Role> result_confirmed;
	protected List<Role> result_unmatched_metas;
	protected List<Role> result_unmatched_roles;
	protected Set<Role> result_possible_roles;
	
	public abstract void simulate();
	
	public List<Role> getResult_confirmed() {
		return result_confirmed;
	}

	public List<Role> getResult_unmatched_metas() {
		return result_unmatched_metas;
	}

	public List<Role> getResult_unmatched_roles() {
		return result_unmatched_roles;
	}
	
	public Set<Role> getResult_possible_roles() {
		return result_possible_roles;
	}
}
