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

public class NeutralKillingPane extends JPanel {
	private JCheckBox chckbxExcludeSerialKiller;
	private JCheckBox chckbxExcludeMassMurderer;
	private JCheckBox chckbxExcludeArsonist;

	/**
	 * Create the panel.
	 */
	public NeutralKillingPane() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		chckbxExcludeSerialKiller = new JCheckBox("Exclude Serial Killer");
		add(chckbxExcludeSerialKiller);
		
		chckbxExcludeArsonist = new JCheckBox("Exclude Arsonist");
		add(chckbxExcludeArsonist);
		
		chckbxExcludeMassMurderer = new JCheckBox("Exclude Mass Murderer");
		add(chckbxExcludeMassMurderer);

	}
	
	public RoleDecider getDecider() {
		Set<Tag> include = EnumSet.of(Tag.NEUTRAL, Tag.KILLING);
		Set<Role> exclude = EnumSet.noneOf(Role.class);
		
		if (this.chckbxExcludeSerialKiller.isSelected()) {
			exclude.add(Role.SERIAL_KILLER);
		}
		if (this.chckbxExcludeArsonist.isSelected()) {
			exclude.add(Role.ARSONIST);
		}
		if (this.chckbxExcludeMassMurderer.isSelected()) {
			exclude.add(Role.MASS_MURDERER);
		}
		
		return StaticDecider.byTagRole(include, exclude);
	}
}
