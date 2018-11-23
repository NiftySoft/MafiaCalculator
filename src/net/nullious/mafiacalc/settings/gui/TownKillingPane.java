package net.nullious.mafiacalc.settings.gui;

import javax.swing.JPanel;

import net.nullious.mafiacalc.Role;
import net.nullious.mafiacalc.Tag;
import net.nullious.mafiacalc.settings.RoleDecider;
import net.nullious.mafiacalc.settings.StaticDecider;

import java.util.EnumSet;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;

public class TownKillingPane extends JPanel {
	private JCheckBox chckbxExcludeBodyguard;
	private JCheckBox chckbxExcludeVeteran;
	private JCheckBox chckbxExcludeJailor;
	private JCheckBox chckbxExcludeVigilante;

	/**
	 * Create the panel.
	 */
	public TownKillingPane() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		chckbxExcludeVeteran = new JCheckBox("Exclude Veteran");
		add(chckbxExcludeVeteran);
		
		chckbxExcludeJailor = new JCheckBox("Exclude Jailor");
		add(chckbxExcludeJailor);
		
		chckbxExcludeBodyguard = new JCheckBox("Exclude Bodyguard");
		add(chckbxExcludeBodyguard);
		
		chckbxExcludeVigilante = new JCheckBox("Exclude Vigilante");
		add(chckbxExcludeVigilante);

	}
	
	public RoleDecider getDecider() {
		Set<Tag> include = EnumSet.of(Tag.TOWN, Tag.KILLING);
		Set<Role> exclude = EnumSet.noneOf(Role.class);
		
		if (this.chckbxExcludeVeteran.isSelected()) {
			exclude.add(Role.VETERAN);
		}
		if (this.chckbxExcludeJailor.isSelected()) {
			exclude.add(Role.JAILOR);
		}
		if (this.chckbxExcludeBodyguard.isSelected()) {
			exclude.add(Role.BODYGUARD);
		}
		if (this.chckbxExcludeVigilante.isSelected()) {
			exclude.add(Role.VIGILANTE);
		}
		
		return StaticDecider.byTagRole(include, exclude);
	}
}
