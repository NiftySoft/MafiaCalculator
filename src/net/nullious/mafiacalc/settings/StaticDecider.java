package net.nullious.mafiacalc.settings;

import java.util.EnumSet;
import java.util.Set;

import net.nullious.mafiacalc.Role;
import net.nullious.mafiacalc.Tag;

public class StaticDecider implements RoleDecider {
	private final Set<Role> roles;
	
	private StaticDecider(Set<Role> rs) {
		this.roles = rs;
	}
	
	public static StaticDecider byTagRole(Set<Tag> include, Set<Role> exclude) {
		Set<Role> rs = EnumSet.noneOf(Role.class);
		
		if (include == null) {
			rs.addAll(Role.getNormalSet());
		} else {
			rs.addAll(Role.withAllTags(include));
		}
		
		rs.removeAll(exclude);
		
		return new StaticDecider(rs);
	}
	
	public static StaticDecider byTagTag(Set<Tag> include, Set<Tag> exclude) {
		Set<Role> rs = EnumSet.noneOf(Role.class);
		
		if (include == null) {
			rs.addAll(Role.getNormalSet());
		} else {
			rs.addAll(Role.withAllTags(include));
		}
		
		rs.removeAll(Role.withAnyTag(exclude));
		
		return new StaticDecider(rs);
	}
	
	public static StaticDecider byTagTagRole(Set<Tag> include, Set<Tag> exclude, Set<Role> excludeRole) {
		Set<Role> rs = EnumSet.noneOf(Role.class);
		
		if (include == null) {
			rs.addAll(Role.getNormalSet());
		} else {
			rs.addAll(Role.withAllTags(include));
		}
		
		rs.removeAll(Role.withAnyTag(exclude));
		rs.removeAll(excludeRole);
		
		return new StaticDecider(rs);
	}
	
	@Override
	public Set<Role> getRoleSet() {
		return this.roles;
	}
}
