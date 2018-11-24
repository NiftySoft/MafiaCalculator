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

import net.nullious.mafiacalc.Role;
import net.nullious.mafiacalc.Tag;
import net.nullious.mafiacalc.settings.RoleDecider;

import java.util.*;
import java.util.stream.Collectors;

/**
 * RelationalSimulation just uses the relation between meta and non-meta Roles which is present in the settings to
 * determine the truths about which we can be certain.
 */
public class RelationalSimulation extends Simulation {
    // The role relation, in its current state. This relation maps each role to its set of possible roles.
    protected final Map<Role, Set<Role>> roleRelation;

    // Maximum number of each role that could possibly still be alive.
    protected final Map<Role, Long> maxRolesPossiblyAlive;
    // isSaveStillAlive.get(i) == true iff it is possible that configured role in configured.get(i) could still be alive.
    protected final List<Boolean> isConfiguredStillAlive;

    // imageOfConfigured is the image of the configured list under the roleRelation.
    protected final List<Set<Role>> imageOfConfigured;

    public RelationalSimulation(Map<Role, RoleDecider> settings, List<Role> configured, Set<Role> ignored, List<Role> dead,
                                List<Role> suspected) {
        super(settings, configured, ignored, dead, suspected);

        roleRelation = new HashMap<>();
        maxRolesPossiblyAlive = new HashMap<>();
        isConfiguredStillAlive = new ArrayList<>(configured.size());

        imageOfConfigured = new ArrayList<>();

        precompute();
    }

    public void precompute() {
        // Create the role relation as it is presently understood.
        for (Role r : Role.values()) {
            if(!ignored.contains(r)) {
                if (!r.getTags().contains(Tag.META)) {
                    // Non-meta roles are always mapped to the set containing just themselves.
                    roleRelation.put(r, Collections.singleton(r));
                } else {
                    // Meta roles could be any non-ignored role configured in the settings.
                    roleRelation.put(r, settings.get(r).getRoleSet().stream()
                            .filter(x -> ignored.contains(x))
                            .collect(Collectors.toSet()));
                }
            }
        }
    }

    @Override
    public void simulate() {
        computeMaxPossiblyAlive();
        computeIsConfiguredStillAlive();
    }

    private void computeMaxPossiblyAlive() {
        // Create the image of the configured list after the roleRelation is applied.
        for (int i = 0; i < configured.size(); ++i) {
            imageOfConfigured.set(i, roleRelation.get(configured.get(i)));

            // Add an empty key to the maxRolesPossiblyAlive map for each role in imageOfConfigured.
            for (Role possibleRole : imageOfConfigured.get(i)) {
                maxRolesPossiblyAlive.put(possibleRole, 0L);
            }
        }

        // Compute the maximum number of roles possibly alive for each possible role that could
        // still be alive. This is just the count of the sets in the imageOfConfigured map which contain
        // the possibleRole.
        maxRolesPossiblyAlive.replaceAll((role, zero) -> imageOfConfigured.stream()
                .filter(roles -> roles.contains(role))
                .count());
    }

    private void computeIsConfiguredStillAlive() {
        // Everyone is alive until they have been proven dead.
        Collections.fill(isConfiguredStillAlive, true);

        // Try to prove each role is dead in sequence.
        for (int i = 0; i < configured.size(); ++i) {
            Role r = configured.get(i);

            // Find all of the dead roles that this role could possibly be.
            List<Role> possibleDeadRoles = confirmedDead.stream()
                     .filter(deadRole -> !roleRelation.get(r).contains(deadRole))
                     .collect(Collectors.toList());

            // Find all of the configured roles which the possibleDeadRoles could possibly be.
            List<Role> possiblyDeadConfiguredRoles = configured.stream()
                    .filter(configuredRole -> {
                        Set<Role> intersectedWithDeadRoles = EnumSet.copyOf(roleRelation.get(configured));
                        intersectedWithDeadRoles.retainAll(possibleDeadRoles);
                        return intersectedWithDeadRoles.isEmpty();
                    })
                    .collect(Collectors.toList());

            // If there are only as many possiblyDeadConfiguredRoles as there are possibleDeadRoles then
            // whoever was assigned role r is most certainly dead (by the pigeonhole principle).
            if (possibleDeadRoles.size() == possiblyDeadConfiguredRoles.size()) {
                isConfiguredStillAlive.set(i, false);
            }
        }
    }
}
