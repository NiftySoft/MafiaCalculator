package net.nullious.mafiacalc;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public enum Role {
	// Town
	CITIZEN(Tag.TOWN, Tag.GOVERNMENT),
	SHERIFF(Tag.TOWN, Tag.INVESTIGATIVE),
	INVESTIGATOR(Tag.TOWN, Tag.INVESTIGATIVE),
	DETECTIVE(Tag.TOWN, Tag.INVESTIGATIVE),
	LOOKOUT(Tag.TOWN, Tag.INVESTIGATIVE),
	DOCTOR(Tag.TOWN, Tag.PROTECTIVE),
	ESCORT(Tag.TOWN, Tag.PROTECTIVE),
	JAILOR(Tag.TOWN, Tag.KILLING, Tag.POWER),
	VIGILANTE(Tag.TOWN, Tag.KILLING),
	MASON(Tag.TOWN, Tag.GOVERNMENT),
	MASON_LEADER(Tag.TOWN, Tag.GOVERNMENT),
	SPY(Tag.TOWN, Tag.POWER),
	BUS_DRIVER(Tag.TOWN, Tag.PROTECTIVE, Tag.POWER),
	CORONER(Tag.TOWN, Tag.INVESTIGATIVE),
	BODYGUARD(Tag.TOWN, Tag.PROTECTIVE, Tag.KILLING),
	VETERAN(Tag.TOWN, Tag.KILLING, Tag.POWER),
	MAYOR(1, Tag.TOWN, Tag.GOVERNMENT),
	MARSHALL(1, Tag.TOWN, Tag.GOVERNMENT),
	CRIER(Tag.TOWN, Tag.GOVERNMENT),
	
	// Mafia
	MAFIOSO(Tag.MAFIA, Tag.KILLING),
	CONSIGLIERE(Tag.MAFIA, Tag.SUPPORT),
	GODFATHER(Tag.MAFIA, Tag.KILLING),
	DISGUISER(Tag.MAFIA, Tag.KILLING, Tag.DECEPTION),
	CONSORT(Tag.MAFIA, Tag.SUPPORT),
	FRAMER(Tag.MAFIA, Tag.DECEPTION),
	JANITOR(Tag.MAFIA, Tag.DECEPTION),
	BLACKMAILER(Tag.MAFIA, Tag.SUPPORT),
	KIDNAPPER(Tag.MAFIA, Tag.KILLING, Tag.SUPPORT),
	AGENT(Tag.MAFIA, Tag.SUPPORT),
	BEGUILER(Tag.MAFIA, Tag.DECEPTION),
	
	// Triad
	ENFORCER(Tag.TRIAD, Tag.KILLING),
	ADMINISTRATOR(Tag.TRIAD, Tag.SUPPORT),
	DRAGON_HEAD(Tag.TRIAD, Tag.KILLING),
	INFORMANT(Tag.TRIAD, Tag.KILLING, Tag.DECEPTION),
	LIAISON(Tag.TRIAD, Tag.SUPPORT),
	FORGER(Tag.TRIAD, Tag.DECEPTION),
	INCENSE_MASTER(Tag.TRIAD, Tag.DECEPTION),
	SILENCER(Tag.TRIAD, Tag.SUPPORT),
	INTERROGATOR(Tag.TRIAD, Tag.KILLING, Tag.SUPPORT),
	VANGUARD(Tag.TRIAD, Tag.SUPPORT),
	DECEIVER(Tag.TRIAD, Tag.DECEPTION),
	
	// Neutral
	SERIAL_KILLER(Tag.NEUTRAL, Tag.KILLING, Tag.EVIL),
	ARSONIST(Tag.NEUTRAL, Tag.KILLING, Tag.EVIL),
	MASS_MURDERER(Tag.NEUTRAL, Tag.KILLING, Tag.EVIL),
	WITCH(Tag.NEUTRAL, Tag.EVIL),
	AUDITOR(Tag.NEUTRAL, Tag.EVIL),
	JUDGE(Tag.NEUTRAL, Tag.EVIL),
	SURVIVOR(Tag.NEUTRAL, Tag.BENIGN),
	JESTER(Tag.NEUTRAL, Tag.BENIGN),
	EXECUTIONER(Tag.NEUTRAL, Tag.BENIGN),
	CULTIST(Tag.NEUTRAL, Tag.EVIL),
	WITCH_DOCTOR(Tag.NEUTRAL, Tag.EVIL),
	AMNESIAC(Tag.NEUTRAL, Tag.BENIGN),
	
	// Meta
	TOWN_RANDOM(Tag.META),
	TOWN_GOVERNMENT(Tag.META),
	TOWN_INVESTIGATIVE(Tag.META),
	TOWN_PROTECTIVE(Tag.META),
	TOWN_KILLING(Tag.META),
	TOWN_POWER(Tag.META),
	MAFIA_RANDOM(Tag.META),
	MAFIA_KILLING(Tag.META),
	MAFIA_SUPPORT(Tag.META),
	MAFIA_DECEPTION(Tag.META),
	TRIAD_RANDOM(Tag.META),
	TRIAD_KILLING(Tag.META),
	TRIAD_SUPPORT(Tag.META),
	TRIAD_DECEPTION(Tag.META),
	NEUTRAL_RANDOM(Tag.META),
	NEUTRAL_KILLING(Tag.META),
	NEUTRAL_EVIL(Tag.META),
	NEUTRAL_BENIGN(Tag.META),
	ANY_RANDOM(Tag.META);
	
	private final int limit;
	private final EnumSet<Tag> tags = EnumSet.noneOf(Tag.class);
	
	private Role(int limit, Tag... tags) {
		this.limit = limit;
		for (Tag t : tags) {
			this.tags.add(t);
		}
	}
	
	private Role(Tag... tags) {
		this(-1, tags);
	}
	
	public int getLimit() {
		return this.limit;
	}
	
	public Set<Tag> getTags() {
		return Collections.unmodifiableSet(this.tags);
	}
	
	public boolean hasTag(Tag t) {
		return this.tags.contains(t);
	}
	
	public static Set<Role> getNormalSet() {
		return Role.withAnyTag(Tag.TOWN, Tag.MAFIA, Tag.TRIAD, Tag.NEUTRAL);
	}
	
	public static Set<Role> withAnyTag(Tag... ts) {
		EnumSet<Role> rs = EnumSet.noneOf(Role.class);
		
		for (Role r : Role.values()) {
			for (Tag t : ts) {
				if (r.hasTag(t)) {
					rs.add(r);
					break;
				}
			}
		}
		
		return Collections.unmodifiableSet(rs);
	}
	
	public static Set<Role> withAnyTag(Set<Tag> ts) {
		return Role.withAnyTag(ts.toArray(new Tag[0]));
	}
	
	public static Set<Role> withAllTags(Tag... ts) {
		EnumSet<Role> rs = EnumSet.noneOf(Role.class);
		
		for (Role r : Role.values()) {
			boolean has_all = true;
			for (Tag t : ts) {
				if (!r.hasTag(t)) {
					has_all = false;
					break;
				}
			}
			if (has_all) {
				rs.add(r);
			}
		}
		
		return Collections.unmodifiableSet(rs);
	}
	
	public static Set<Role> withAllTags(Set<Tag> ts) {
		return Role.withAllTags(ts.toArray(new Tag[0]));
	}
}
