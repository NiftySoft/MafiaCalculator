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

public class MafiaDeceptionPane extends JPanel {
	private JCheckBox chckbxExcludeFramer;
	private JCheckBox chckbxExcludeBeguiler;
	private JCheckBox chckbxExcludeJanitor;
	private JCheckBox chckbxExcludeDisguiser;

	/**
	 * Create the panel.
	 */
	public MafiaDeceptionPane() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		chckbxExcludeDisguiser = new JCheckBox("Exclude Disguiser");
		add(chckbxExcludeDisguiser);
		
		chckbxExcludeFramer = new JCheckBox("Exclude Framer");
		add(chckbxExcludeFramer);
		
		chckbxExcludeJanitor = new JCheckBox("Exclude Janitor");
		add(chckbxExcludeJanitor);
		
		chckbxExcludeBeguiler = new JCheckBox("Exclude Beguiler");
		add(chckbxExcludeBeguiler);

	}
	
	public RoleDecider getDecider() {
		Set<Tag> include = EnumSet.of(Tag.MAFIA, Tag.DECEPTION);
		Set<Role> exclude = EnumSet.noneOf(Role.class);
		
		if (this.chckbxExcludeDisguiser.isSelected()) {
			exclude.add(Role.DISGUISER);
		}
		if (this.chckbxExcludeFramer.isSelected()) {
			exclude.add(Role.FRAMER);
		}
		if (this.chckbxExcludeJanitor.isSelected()) {
			exclude.add(Role.JANITOR);
		}
		if (this.chckbxExcludeBeguiler.isSelected()) {
			exclude.add(Role.BEGUILER);
		}
		
		return StaticDecider.byTagRole(include, exclude);
	}
}
