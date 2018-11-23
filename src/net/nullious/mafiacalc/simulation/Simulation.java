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
	protected final List<Role> save;
	// User-provided set of every Role that is impossible in the current game.
	protected final Set<Role> ignored;
	// User-provided list of every role that has died so far.
	protected final List<Role> dead;

	public Simulation(Map<Role, RoleDecider> settings, List<Role> save, Set<Role> ignored, List<Role> dead) {
		this.settings = Collections.unmodifiableMap(new EnumMap<>(settings));
		this.save = Collections.unmodifiableList(new ArrayList<>(save));
		this.ignored = Collections.unmodifiableSet(EnumSet.copyOf(ignored));
		this.dead = Collections.unmodifiableList(new ArrayList<>(dead));
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
