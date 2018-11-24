package net.nullious.mafiacalc.simulation;

import net.nullious.mafiacalc.Role;
import net.nullious.mafiacalc.Tag;
import net.nullious.mafiacalc.settings.RoleDecider;

import java.util.*;
import java.util.stream.Collectors;

public class RelationalSimulation extends Simulation {
    protected final Map<Role, Set<Role>> roleRelation;

    // Maximum number of each role that could possibly still be alive.
    protected final Map<Role, Long> maxRolesPossiblyAlive;
    // isSaveStillAlive.get(i) == true iff it is possible that configured role in save.get(i) could still be alive.
    protected final List<Boolean> isSaveStillAlive;

    // Image of the save list under the RoleDecider relation.
    protected final List<Set<Role>> imageOfSave;

    public RelationalSimulation(Map<Role, RoleDecider> settings, List<Role> save, Set<Role> ignored, List<Role> dead) {
        super(settings, save, ignored, dead);

        roleRelation = new HashMap<>();
        maxRolesPossiblyAlive = new HashMap<>();
        isSaveStillAlive = new ArrayList<>(save.size());

        imageOfSave = new ArrayList<>();

        precompute();
    }

    public void precompute() {

        // Create the role relation as it is presently understood.
        for (Role r : Role.values()) {
            if(!ignored.contains(r)) {
                if (!r.getTags().contains(Tag.META)) {
                    // Non-meta roles are always themselves.
                    roleRelation.put(r, Collections.singleton(r));
                } else {
                    // Meta roles could be any non-ignored role configured in the settings.
                    roleRelation.put(r, settings.get(r).getRoleSet().stream()
                            .filter(x -> ignored.contains(x))
                            .collect(Collectors.toSet()));
                }
            }
        }

        // Create the image of the save list after the roleRelation is applied.
        for (int i = 0; i < save.size(); ++i) {
            imageOfSave.set(i, roleRelation.get(save.get(i)));

            // Add an empty key to the maxRolesPossiblyAlive map for each role in imageOfSave.
            for (Role possibleRole : imageOfSave.get(i)) {
                maxRolesPossiblyAlive.put(possibleRole, 0L);
            }
        }

        // Compute the maximum number of roles possibly alive for each possible role that could
        // still be alive. This is just the count of the sets in the imageOfSave map which contain
        // the possibleRole.
        maxRolesPossiblyAlive.replaceAll((role, zero) -> imageOfSave.stream()
                                                                    .filter(roles -> roles.contains(role))
                                                                    .count());
    }

    @Override
    public void simulate() {

    }

}
