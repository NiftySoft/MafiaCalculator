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
