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

public class TownProtectivePane extends JPanel {
	private JCheckBox chckbxExcludeEscort;
	private JCheckBox chckbxExcludeDoctor;
	private JCheckBox chckbxExcludeBusDriver;
	private JCheckBox chckbxExcludeBodyguard;

	/**
	 * Create the panel.
	 */
	public TownProtectivePane() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		chckbxExcludeBusDriver = new JCheckBox("Exclude Bus Driver");
		add(chckbxExcludeBusDriver);
		
		chckbxExcludeBodyguard = new JCheckBox("Exclude Bodyguard");
		add(chckbxExcludeBodyguard);
		
		chckbxExcludeDoctor = new JCheckBox("Exclude Doctor");
		add(chckbxExcludeDoctor);
		
		chckbxExcludeEscort = new JCheckBox("Exclude Escort");
		add(chckbxExcludeEscort);

	}
	
	public RoleDecider getDecider() {
		Set<Tag> include = EnumSet.of(Tag.TOWN, Tag.PROTECTIVE);
		Set<Role> exclude = EnumSet.noneOf(Role.class);
		
		if (this.chckbxExcludeBusDriver.isSelected()) {
			exclude.add(Role.BUS_DRIVER);
		}
		if (this.chckbxExcludeBodyguard.isSelected()) {
			exclude.add(Role.BODYGUARD);
		}
		if (this.chckbxExcludeDoctor.isSelected()) {
			exclude.add(Role.DOCTOR);
		}
		if (this.chckbxExcludeEscort.isSelected()) {
			exclude.add(Role.ESCORT);
		}
		
		return StaticDecider.byTagRole(include, exclude);
	}
}
