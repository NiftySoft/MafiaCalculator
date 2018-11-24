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

public class TownPowerPane extends JPanel {
	private JCheckBox chckbxExcludeVeteran;
	private JCheckBox chckbxExcludeSpy;
	private JCheckBox chckbxExcludeJailor;
	private JCheckBox chckbxExcludeBusDriver;

	/**
	 * Create the panel.
	 */
	public TownPowerPane() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		chckbxExcludeVeteran = new JCheckBox("Exclude Veteran");
		add(chckbxExcludeVeteran);
		
		chckbxExcludeSpy = new JCheckBox("Exclude Spy");
		add(chckbxExcludeSpy);
		
		chckbxExcludeBusDriver = new JCheckBox("Exclude Bus Driver");
		add(chckbxExcludeBusDriver);
		
		chckbxExcludeJailor = new JCheckBox("Exclude Jailor");
		add(chckbxExcludeJailor);

	}
	
	public RoleDecider getDecider() {
		Set<Tag> include = EnumSet.of(Tag.TOWN, Tag.POWER);
		Set<Role> exclude = EnumSet.noneOf(Role.class);

		if (this.chckbxExcludeVeteran.isSelected()) {
			exclude.add(Role.VETERAN);
		}
		if (this.chckbxExcludeSpy.isSelected()) {
			exclude.add(Role.SPY);
		}
		if (this.chckbxExcludeBusDriver.isSelected()) {
			exclude.add(Role.BUS_DRIVER);
		}
		if (this.chckbxExcludeJailor.isSelected()) {
			exclude.add(Role.JAILOR);
		}
		
		return StaticDecider.byTagRole(include, exclude);
	}
}
