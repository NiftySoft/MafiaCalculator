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
