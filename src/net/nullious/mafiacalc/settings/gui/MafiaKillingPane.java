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

public class MafiaKillingPane extends JPanel {
	private JCheckBox chckbxExcludeDisguiser;
	private JCheckBox chckbxExcludeGodfather;
	private JCheckBox chckbxExcludeMafioso;
	private JCheckBox chckbxExcludeKidnapper;

	/**
	 * Create the panel.
	 */
	public MafiaKillingPane() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		chckbxExcludeDisguiser = new JCheckBox("Exclude Disguiser");
		add(chckbxExcludeDisguiser);
		
		chckbxExcludeKidnapper = new JCheckBox("Exclude Kidnapper");
		add(chckbxExcludeKidnapper);
		
		chckbxExcludeMafioso = new JCheckBox("Exclude Mafioso");
		add(chckbxExcludeMafioso);
		
		chckbxExcludeGodfather = new JCheckBox("Exclude Godfather");
		add(chckbxExcludeGodfather);

	}
	
	public RoleDecider getDecider() {
		Set<Tag> include = EnumSet.of(Tag.MAFIA, Tag.KILLING);
		Set<Role> exclude = EnumSet.noneOf(Role.class);
		
		if (this.chckbxExcludeDisguiser.isSelected()) {
			exclude.add(Role.DISGUISER);
		}
		if (this.chckbxExcludeKidnapper.isSelected()) {
			exclude.add(Role.KIDNAPPER);
		}
		if (this.chckbxExcludeMafioso.isSelected()) {
			exclude.add(Role.MAFIOSO);
		}
		if (this.chckbxExcludeGodfather.isSelected()) {
			exclude.add(Role.GODFATHER);
		}
		
		return StaticDecider.byTagRole(include, exclude);
	}
}
