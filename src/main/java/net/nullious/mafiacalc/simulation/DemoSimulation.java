package net.nullious.mafiacalc.simulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.nullious.mafiacalc.Role;
import net.nullious.mafiacalc.settings.RoleDecider;

public class DemoSimulation extends Simulation {

	public DemoSimulation(Map<Role, RoleDecider> settings, List<Role> configured, Set<Role> ignored, List<Role> confirmedDead, List<Role> suspected) {
		super(settings, configured, ignored, confirmedDead, suspected);
	}

	@Override
	public void simulate() {
		this.result_possibly_living_roles = EnumSet.noneOf(Role.class);
		this.result_max_possible_count = new HashMap<>();
		this.result_min_possible_count = new HashMap<>();
		this.result_possibly_living_initial_config = new ArrayList<>();
		this.result_suspect_analysis = new Object();
		
		this.result_possibly_living_roles.addAll(Arrays.asList(new Role[] {
			Role.GODFATHER,
			Role.CITIZEN,
		}));
		
		this.result_max_possible_count.put(Role.GODFATHER, 1);
		this.result_max_possible_count.put(Role.CITIZEN, 1);
		
		this.result_min_possible_count.put(Role.GODFATHER, 1);
		this.result_min_possible_count.put(Role.CITIZEN, 1);
		
		this.result_possibly_living_initial_config.addAll(Arrays.asList(new Role[] {
			Role.GODFATHER,
			Role.CITIZEN,
		}));
	}

}
