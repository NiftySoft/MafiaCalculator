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
import java.awt.GridBagLayout;
import java.util.EnumSet;
import java.util.Set;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import net.nullious.mafiacalc.Role;
import net.nullious.mafiacalc.Tag;
import net.nullious.mafiacalc.settings.RoleDecider;
import net.nullious.mafiacalc.settings.StaticDecider;

import java.awt.FlowLayout;
import javax.swing.JCheckBox;
import javax.swing.BoxLayout;

public class TownRandomPane extends JPanel {
	private JCheckBox chckbxExcludeGovernment;
	private JCheckBox chckbxExcludeKilling;
	private JCheckBox chckbxExcludeInvestigative;
	private JCheckBox chckbxExcludePower;
	private JCheckBox chckbxExcludeProtective;

	/**
	 * Create the panel.
	 */
	public TownRandomPane() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		chckbxExcludeKilling = new JCheckBox("Exclude Killing");
		add(chckbxExcludeKilling);
		
		chckbxExcludeGovernment = new JCheckBox("Exclude Government");
		add(chckbxExcludeGovernment);
		
		chckbxExcludeInvestigative = new JCheckBox("Exclude Investigative");
		add(chckbxExcludeInvestigative);
		
		chckbxExcludeProtective = new JCheckBox("Exclude Protective");
		add(chckbxExcludeProtective);
		
		chckbxExcludePower = new JCheckBox("Exclude Power");
		add(chckbxExcludePower);

	}
	
	public RoleDecider getDecider() {
		Set<Tag> include = EnumSet.of(Tag.TOWN);
		Set<Tag> exclude = EnumSet.noneOf(Tag.class);
		
		if (this.chckbxExcludeKilling.isSelected()) {
			exclude.add(Tag.KILLING);
		}
		if (this.chckbxExcludeGovernment.isSelected()) {
			exclude.add(Tag.GOVERNMENT);
		}
		if (this.chckbxExcludeInvestigative.isSelected()) {
			exclude.add(Tag.INVESTIGATIVE);
		}
		if (this.chckbxExcludeProtective.isSelected()) {
			exclude.add(Tag.PROTECTIVE);
		}
		if (this.chckbxExcludePower.isSelected()) {
			exclude.add(Tag.POWER);
		}
		
		return StaticDecider.byTagTag(include, exclude);
	}
}
