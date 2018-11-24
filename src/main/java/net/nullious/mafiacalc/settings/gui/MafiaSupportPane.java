/**
 * This file is part of MafiaCalculator.
 *
 * MafiaCalculator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MafiaCalculator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MafiaCalculator.  If not, see <https://www.gnu.org/licenses/>.
 */
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
