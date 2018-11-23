package net.nullious.mafiacalc.settings;

import java.util.Set;

import net.nullious.mafiacalc.Role;

public interface RoleDecider {
	public Set<Role> getRoleSet();
}
