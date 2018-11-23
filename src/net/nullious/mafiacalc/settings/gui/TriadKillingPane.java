package net.nullious.mafiacalc.settings.gui;

import javax.swing.JPanel;

import net.nullious.mafiacalc.Role;
import net.nullious.mafiacalc.Tag;
import net.nullious.mafiacalc.settings.RoleDecider;
import net.nullious.mafiacalc.settings.StaticDecider;

import javax.swing.JCheckBox;

import java.util.EnumSet;
import java.util.Set;

import javax.swing.BoxLayout;

public class TriadKillingPane extends JPanel {
	private JCheckBox chckbxExcludeInformant;
	private JCheckBox chckbxExcludeDragonHead;
	private JCheckBox chckbxExcludeEnforcer;
	private JCheckBox chckbxExcludeInterrogator;

	/**
	 * Create the panel.
	 */
	public TriadKillingPane() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		chckbxExcludeInformant = new JCheckBox("Exclude Informant");
		add(chckbxExcludeInformant);
		
		chckbxExcludeInterrogator = new JCheckBox("Exclude Interrogator");
		add(chckbxExcludeInterrogator);
		
		chckbxExcludeEnforcer = new JCheckBox("Exclude Enforcer");
		add(chckbxExcludeEnforcer);
		
		chckbxExcludeDragonHead = new JCheckBox("Exclude Dragon Head");
		add(chckbxExcludeDragonHead);

	}
	
	public RoleDecider getDecider() {
		Set<Tag> include = EnumSet.of(Tag.TRIAD, Tag.KILLING);
		Set<Role> exclude = EnumSet.noneOf(Role.class);
		
		if (this.chckbxExcludeInformant.isSelected()) {
			exclude.add(Role.INFORMANT);
		}
		if (this.chckbxExcludeInterrogator.isSelected()) {
			exclude.add(Role.INTERROGATOR);
		}
		if (this.chckbxExcludeEnforcer.isSelected()) {
			exclude.add(Role.ENFORCER);
		}
		if (this.chckbxExcludeDragonHead.isSelected()) {
			exclude.add(Role.DRAGON_HEAD);
		}
		
		return StaticDecider.byTagRole(include, exclude);
	}
}
