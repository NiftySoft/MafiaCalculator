package net.nullious.mafiacalc.simulation;

import net.nullious.mafiacalc.Role;
import net.nullious.mafiacalc.settings.RoleDecider;
import net.nullious.mafiacalc.settings.gui.SaveSettingsPane;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.fail;

class SimulationTest {
	
	private static final Class<?>[] constructor_signature = new Class<?>[] { Map.class, List.class, Set.class,
			List.class, List.class };

	@ParameterizedTest
	@ValueSource(classes = { RelationalSimulation.class })
	void test(Class<?> sim_class) throws Exception {
		Map<Role, RoleDecider> settings = (new SaveSettingsPane()).getSaveSettings();
		List<Role> configured = Arrays.asList(new Role[] {
				Role.GODFATHER,
				Role.CITIZEN,
		});
		List<Role> dead = Arrays.asList(new Role[] {
				Role.GODFATHER,
		});
		List<Role> suspected = Arrays.asList(new Role[] {
		});
		Set<Role> ignored = EnumSet.noneOf(Role.class);
		
		Simulation sim = constructSimulation(sim_class, settings, configured, ignored, dead, suspected);
		
		sim.simulate();
		
		// TODO(James): Update the Simulation output API.
		
		fail("Test failed.");
	}

	private Simulation constructSimulation(Class<?> sim_class, Map<Role, RoleDecider> settings,
										   List<Role> configured, Set<Role> ignored, List<Role> dead,
										   List<Role> suspected) throws Exception {
		return (Simulation) sim_class.getConstructor(constructor_signature)
				.newInstance(new Object[] { settings, configured, ignored, dead, suspected });
	}
}
