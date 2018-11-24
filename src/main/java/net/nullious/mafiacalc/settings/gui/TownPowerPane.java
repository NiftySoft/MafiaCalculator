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

public class TownPowerPane extends JPanel {
	private JCheckBox chckbxExcludeVeteran;
	private JCheckBox chckbxExcludeSpy;
	private JCheckBox chckbxExcludeJailor;
	private JCheckBox chckbxExcludeBusDriver;

	/**
	 * Create the panel.
	 */
	public TownPowerPane() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		chckbxExcludeVeteran = new JCheckBox("Exclude Veteran");
		add(chckbxExcludeVeteran);
		
		chckbxExcludeSpy = new JCheckBox("Exclude Spy");
		add(chckbxExcludeSpy);
		
		chckbxExcludeBusDriver = new JCheckBox("Exclude Bus Driver");
		add(chckbxExcludeBusDriver);
		
		chckbxExcludeJailor = new JCheckBox("Exclude Jailor");
		add(chckbxExcludeJailor);

	}
	
	public RoleDecider getDecider() {
		Set<Tag> include = EnumSet.of(Tag.TOWN, Tag.POWER);
		Set<Role> exclude = EnumSet.noneOf(Role.class);

		if (this.chckbxExcludeVeteran.isSelected()) {
			exclude.add(Role.VETERAN);
		}
		if (this.chckbxExcludeSpy.isSelected()) {
			exclude.add(Role.SPY);
		}
		if (this.chckbxExcludeBusDriver.isSelected()) {
			exclude.add(Role.BUS_DRIVER);
		}
		if (this.chckbxExcludeJailor.isSelected()) {
			exclude.add(Role.JAILOR);
		}
		
		return StaticDecider.byTagRole(include, exclude);
	}
}
