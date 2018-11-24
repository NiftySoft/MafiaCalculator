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

public class NeutralBenignPane extends JPanel {
	private JCheckBox chckbxExcludeJester;
	private JCheckBox chckbxExcludeSurvivor;
	private JCheckBox chckbxExcludeExecutioner;
	private JCheckBox chckbxExcludeAmnesiac;

	/**
	 * Create the panel.
	 */
	public NeutralBenignPane() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		chckbxExcludeSurvivor = new JCheckBox("Exclude Survivor");
		add(chckbxExcludeSurvivor);
		
		chckbxExcludeJester = new JCheckBox("Exclude Jester");
		add(chckbxExcludeJester);
		
		chckbxExcludeExecutioner = new JCheckBox("Exclude Executioner");
		add(chckbxExcludeExecutioner);
		
		chckbxExcludeAmnesiac = new JCheckBox("Exclude Amnesiac");
		add(chckbxExcludeAmnesiac);

	}
	
	public RoleDecider getDecider() {
		Set<Tag> include = EnumSet.of(Tag.NEUTRAL, Tag.BENIGN);
		Set<Role> exclude = EnumSet.noneOf(Role.class);
		
		if (this.chckbxExcludeSurvivor.isSelected()) {
			exclude.add(Role.SURVIVOR);
		}
		if (this.chckbxExcludeJester.isSelected()) {
			exclude.add(Role.JESTER);
		}
		if (this.chckbxExcludeExecutioner.isSelected()) {
			exclude.add(Role.EXECUTIONER);
		}
		if (this.chckbxExcludeAmnesiac.isSelected()) {
			exclude.add(Role.AMNESIAC);
		}
		
		return StaticDecider.byTagRole(include, exclude);
	}
}
