package net.nullious.mafiacalc.test;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import net.nullious.mafiacalc.Role;
import net.nullious.mafiacalc.settings.RoleDecider;
import net.nullious.mafiacalc.settings.gui.SaveSettingsPane;
import net.nullious.mafiacalc.simulation.RelationalSimulation;
import net.nullious.mafiacalc.simulation.Simulation;

class SimulationTest {
	
	private static final Class<?>[] constructor_signature = new Class<?>[] { Map.class, List.class, Set.class, List.class, List.class };

	@ParameterizedTest
	@ValueSource(classes = { RelationalSimulation.class })
	void test(Class<?> sim_class) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Map<Role, RoleDecider> settings = (new SaveSettingsPane()).getSaveSettings();
		List<Role> save = Arrays.asList(new Role[] {
				Role.GODFATHER,
				Role.CITIZEN,
		});
		List<Role> dead = Arrays.asList(new Role[] {
				Role.GODFATHER,
		});
		List<Role> suspected = Arrays.asList(new Role[] {
		});
		Set<Role> ignored = EnumSet.noneOf(Role.class);
		
		Simulation sim = (Simulation) sim_class.getConstructor(constructor_signature).newInstance(new Object[] { settings, save, ignored, dead, suspected });
		
		sim.simulate();
		
		// TODO(James): Update the Simulation output API.
		
		fail("Test failed.");
	}

}
