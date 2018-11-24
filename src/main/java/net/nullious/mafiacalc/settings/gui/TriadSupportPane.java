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

public class TriadSupportPane extends JPanel {
	private JCheckBox chckbxExcludeAdministrator;
	private JCheckBox chckbxExcludeInterrogator;
	private JCheckBox chckbxExcludeVanguard;
	private JCheckBox chckbxExcludeSilencer;
	private JCheckBox chckbxExcludeLiaison;

	/**
	 * Create the panel.
	 */
	public TriadSupportPane() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		chckbxExcludeSilencer = new JCheckBox("Exclude Silencer");
		add(chckbxExcludeSilencer);
		
		chckbxExcludeInterrogator = new JCheckBox("Exclude Interrogator");
		add(chckbxExcludeInterrogator);
		
		chckbxExcludeLiaison = new JCheckBox("Exclude Liaison");
		add(chckbxExcludeLiaison);
		
		chckbxExcludeAdministrator = new JCheckBox("Exclude Administrator");
		add(chckbxExcludeAdministrator);
		
		chckbxExcludeVanguard = new JCheckBox("Exclude Vanguard");
		add(chckbxExcludeVanguard);

	}
	
	public RoleDecider getDecider() {
		Set<Tag> include = EnumSet.of(Tag.TRIAD, Tag.SUPPORT);
		Set<Role> exclude = EnumSet.noneOf(Role.class);
		
		if (this.chckbxExcludeSilencer.isSelected()) {
			exclude.add(Role.SILENCER);
		}
		if (this.chckbxExcludeInterrogator.isSelected()) {
			exclude.add(Role.INTERROGATOR);
		}
		if (this.chckbxExcludeLiaison.isSelected()) {
			exclude.add(Role.LIAISON);
		}
		if (this.chckbxExcludeAdministrator.isSelected()) {
			exclude.add(Role.ADMINISTRATOR);
		}
		if (this.chckbxExcludeVanguard.isSelected()) {
			exclude.add(Role.VANGUARD);
		}
		
		return StaticDecider.byTagRole(include, exclude);
	}
}
