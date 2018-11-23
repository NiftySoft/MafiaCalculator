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

public class TownInvestigativePane extends JPanel {
	private JCheckBox chckbxExcludeLookout;
	private JCheckBox chckbxExcludeSheriff;
	private JCheckBox chckbxExcludeCoroner;
	private JCheckBox chckbxExcludeInvestigator;
	private JCheckBox chckbxExcludeDetective;

	/**
	 * Create the panel.
	 */
	public TownInvestigativePane() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		chckbxExcludeCoroner = new JCheckBox("Exclude Coroner");
		add(chckbxExcludeCoroner);
		
		chckbxExcludeSheriff = new JCheckBox("Exclude Sheriff");
		add(chckbxExcludeSheriff);
		
		chckbxExcludeInvestigator = new JCheckBox("Exclude Investigator");
		add(chckbxExcludeInvestigator);
		
		chckbxExcludeDetective = new JCheckBox("Exclude Detective");
		add(chckbxExcludeDetective);
		
		chckbxExcludeLookout = new JCheckBox("Exclude Lookout");
		add(chckbxExcludeLookout);

	}
	
	public RoleDecider getDecider() {
		Set<Tag> include = EnumSet.of(Tag.TOWN, Tag.INVESTIGATIVE);
		Set<Role> exclude = EnumSet.noneOf(Role.class);
		
		if (this.chckbxExcludeCoroner.isSelected()) {
			exclude.add(Role.CORONER);
		}
		if (this.chckbxExcludeSheriff.isSelected()) {
			exclude.add(Role.SHERIFF);
		}
		if (this.chckbxExcludeInvestigator.isSelected()) {
			exclude.add(Role.INVESTIGATOR);
		}
		if (this.chckbxExcludeDetective.isSelected()) {
			exclude.add(Role.DETECTIVE);
		}
		if (this.chckbxExcludeLookout.isSelected()) {
			exclude.add(Role.LOOKOUT);
		}
		
		return StaticDecider.byTagRole(include, exclude);
	}
}
