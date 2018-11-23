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

public class MafiaSupportPane extends JPanel {
	private JCheckBox chckbxExcludeConsigliere;
	private JCheckBox chckbxExcludeConsort;
	private JCheckBox chckbxExcludeBlackmailer;
	private JCheckBox chckbxExcludeKidnapper;
	private JCheckBox chckbxExcludeAgent;

	/**
	 * Create the panel.
	 */
	public MafiaSupportPane() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		chckbxExcludeBlackmailer = new JCheckBox("Exclude Blackmailer");
		add(chckbxExcludeBlackmailer);
		
		chckbxExcludeKidnapper = new JCheckBox("Exclude Kidnapper");
		add(chckbxExcludeKidnapper);
		
		chckbxExcludeConsort = new JCheckBox("Exclude Consort");
		add(chckbxExcludeConsort);
		
		chckbxExcludeConsigliere = new JCheckBox("Exclude Consigliere");
		add(chckbxExcludeConsigliere);
		
		chckbxExcludeAgent = new JCheckBox("Exclude Agent");
		add(chckbxExcludeAgent);

	}
	
	public RoleDecider getDecider() {
		Set<Tag> include = EnumSet.of(Tag.MAFIA, Tag.SUPPORT);
		Set<Role> exclude = EnumSet.noneOf(Role.class);
		
		if (this.chckbxExcludeBlackmailer.isSelected()) {
			exclude.add(Role.BLACKMAILER);
		}
		if (this.chckbxExcludeKidnapper.isSelected()) {
			exclude.add(Role.KIDNAPPER);
		}
		if (this.chckbxExcludeConsort.isSelected()) {
			exclude.add(Role.CONSORT);
		}
		if (this.chckbxExcludeConsigliere.isSelected()) {
			exclude.add(Role.CONSIGLIERE);
		}
		if (this.chckbxExcludeAgent.isSelected()) {
			exclude.add(Role.AGENT);
		}
		
		return StaticDecider.byTagRole(include, exclude);
	}
}
