package net.nullious.mafiacalc.settings.gui;

import javax.swing.JPanel;

import net.nullious.mafiacalc.Tag;
import net.nullious.mafiacalc.settings.RoleDecider;
import net.nullious.mafiacalc.settings.StaticDecider;

import javax.swing.JCheckBox;

import java.util.EnumSet;
import java.util.Set;

import javax.swing.BoxLayout;

public class TriadRandomPane extends JPanel {
	private JCheckBox chckbxExcludeKilling;

	/**
	 * Create the panel.
	 */
	public TriadRandomPane() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		chckbxExcludeKilling = new JCheckBox("Exclude Killing");
		add(chckbxExcludeKilling);

	}
	
	public RoleDecider getDecider() {
		Set<Tag> include = EnumSet.of(Tag.TRIAD);
		Set<Tag> exclude = EnumSet.noneOf(Tag.class);
		
		if (this.chckbxExcludeKilling.isSelected()) {
			exclude.add(Tag.KILLING);
		}
		
		return StaticDecider.byTagTag(include, exclude);
	}
}
