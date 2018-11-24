package net.nullious.mafiacalc.simulation;

import net.nullious.mafiacalc.Role;
import net.nullious.mafiacalc.settings.RoleDecider;
import net.nullious.mafiacalc.settings.gui.SaveSettingsPane;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.fail;

class SimulationTest {
	
	private static final Class<?>[] constructor_signature = new Class<?>[] { Map.class, List.class, Set.class,
			List.class, List.class };
			
	private final static Class<?>[] test_targets = new Class<?>[] { 
				RelationalSimulation.class,
	};
	
	private static final class SimulationProvider implements ArgumentsProvider {

		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext arg0) throws Exception {
			return Stream.of(test_targets).map(Arguments::of);
		}
		
	}

	@ParameterizedTest
	@ArgumentsSource(value = SimulationProvider.class)
	void test(Class<?> sim_class) throws Exception {
		// Specify inputs and outputs
		Map<Role, RoleDecider> input_settings = (new SaveSettingsPane()).getSaveSettings();
		List<Role> input_configured = Arrays.asList(new Role[] {
				Role.GODFATHER,
				Role.CITIZEN,
		});
		List<Role> input_dead = Arrays.asList(new Role[] {
				Role.GODFATHER,
		});
		List<Role> input_suspected = Arrays.asList(new Role[] {
		});
		Set<Role> input_ignored = EnumSet.noneOf(Role.class);
		Set<Role> expected_living = EnumSet.of(Role.CITIZEN);
		List<Role> expected_remaining = Arrays.asList(new Role[] { Role.CITIZEN });
		Map<Role, Integer>  expected_minCount = new HashMap<>();
		expected_minCount.put(Role.GODFATHER, 0);
		expected_minCount.put(Role.CITIZEN, 1);
		Map<Role, Integer>  expected_maxCount = new HashMap<>();
		expected_maxCount.put(Role.GODFATHER, 0);
		expected_maxCount.put(Role.CITIZEN, 1);
		
		// Run the test.
		Simulation sim = constructSimulation(sim_class, input_settings, input_configured, input_ignored, input_dead, input_suspected);
		
		sim.simulate();
		
		// Validate the outputs
		if (!isEquivalentSet(expected_living, sim.getPossiblyLivingRoles())) {
			fail("Possible Living Roles does not match expected output.");
		}
		if (!isEquivalentList(expected_remaining, sim.getPossiblyLivingInitialConfig())) {
			fail("Possible Initial Roles does not match expected output.");
		}
		if (!isEquivalentMap(expected_minCount, sim.getMinPossibleCount())) {
			fail("Possible Min Count does not match expected output.");
		}
		if (!isEquivalentMap(expected_maxCount, sim.getMaxPossibleCount())) {
			fail("Possible Max Count does not match expected output.");
		}
	}

	private Simulation constructSimulation(Class<?> sim_class, Map<Role, RoleDecider> settings,
										   List<Role> configured, Set<Role> ignored, List<Role> dead,
										   List<Role> suspected) throws Exception {
		return (Simulation) sim_class.getConstructor(constructor_signature)
				.newInstance(new Object[] { settings, configured, ignored, dead, suspected });
	}

	private static boolean isEquivalentSet(Set<Role> a, Set<Role> b) {
		return (a.containsAll(b) && b.containsAll(a));
	}

	private static boolean isEquivalentList(List<Role> a, List<Role> b) {
		ArrayList<Role> copy_a = new ArrayList<>(a);
		
		for (Role r : b) {
			if (!copy_a.remove(r)) {
				return false;
			}
		}
		
		return copy_a.isEmpty();
	}

	private static boolean isEquivalentMap(Map<Role, Integer> a, Map<Role, Integer> b) {
		HashMap<Role, Integer> copy_a = new HashMap<>(a);
		
		for (Role r : b.keySet()) {
			if (!b.get(r).equals(copy_a.remove(r))) {
				return false;
			}
		}
		
		return copy_a.isEmpty();
	}
}
