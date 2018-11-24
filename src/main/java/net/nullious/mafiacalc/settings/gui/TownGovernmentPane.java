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

public class TownGovernmentPane extends JPanel {
	private JCheckBox chckbxExcludeCitizen;
	private JCheckBox chckbxExcludeCrier;
	private JCheckBox chckbxExcludeMason;
	private JCheckBox chckbxExcludeMayormarshall;
	private JCheckBox chckbxExcludeMasonLeader;

	/**
	 * Create the panel.
	 */
	public TownGovernmentPane() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		chckbxExcludeCitizen = new JCheckBox("Exclude Citizen");
		add(chckbxExcludeCitizen);
		
		chckbxExcludeMason = new JCheckBox("Exclude Mason");
		add(chckbxExcludeMason);
		
		chckbxExcludeMayormarshall = new JCheckBox("Exclude Mayor/Marshall");
		add(chckbxExcludeMayormarshall);
		
		chckbxExcludeMasonLeader = new JCheckBox("Exclude Mason Leader");
		add(chckbxExcludeMasonLeader);
		
		chckbxExcludeCrier = new JCheckBox("Exclude Crier");
		add(chckbxExcludeCrier);

	}
	
	public RoleDecider getDecider() {
		Set<Tag> include = EnumSet.of(Tag.TOWN, Tag.GOVERNMENT);
		Set<Role> exclude = EnumSet.noneOf(Role.class);
		
		if (this.chckbxExcludeCitizen.isSelected()) {
			exclude.add(Role.CITIZEN);
		}
		if (this.chckbxExcludeMason.isSelected()) {
			exclude.add(Role.MASON);
		}
		if (this.chckbxExcludeMayormarshall.isSelected()) {
			exclude.add(Role.MAYOR);
			exclude.add(Role.MARSHALL);
		}
		if (this.chckbxExcludeMasonLeader.isSelected()) {
			exclude.add(Role.MASON_LEADER);
		}
		if (this.chckbxExcludeCrier.isSelected()) {
			exclude.add(Role.CRIER);
		}
		
		return StaticDecider.byTagRole(include, exclude);
	}
}
