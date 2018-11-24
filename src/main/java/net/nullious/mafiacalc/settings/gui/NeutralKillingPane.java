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

public class NeutralKillingPane extends JPanel {
	private JCheckBox chckbxExcludeSerialKiller;
	private JCheckBox chckbxExcludeMassMurderer;
	private JCheckBox chckbxExcludeArsonist;

	/**
	 * Create the panel.
	 */
	public NeutralKillingPane() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		chckbxExcludeSerialKiller = new JCheckBox("Exclude Serial Killer");
		add(chckbxExcludeSerialKiller);
		
		chckbxExcludeArsonist = new JCheckBox("Exclude Arsonist");
		add(chckbxExcludeArsonist);
		
		chckbxExcludeMassMurderer = new JCheckBox("Exclude Mass Murderer");
		add(chckbxExcludeMassMurderer);

	}
	
	public RoleDecider getDecider() {
		Set<Tag> include = EnumSet.of(Tag.NEUTRAL, Tag.KILLING);
		Set<Role> exclude = EnumSet.noneOf(Role.class);
		
		if (this.chckbxExcludeSerialKiller.isSelected()) {
			exclude.add(Role.SERIAL_KILLER);
		}
		if (this.chckbxExcludeArsonist.isSelected()) {
			exclude.add(Role.ARSONIST);
		}
		if (this.chckbxExcludeMassMurderer.isSelected()) {
			exclude.add(Role.MASS_MURDERER);
		}
		
		return StaticDecider.byTagRole(include, exclude);
	}
}
