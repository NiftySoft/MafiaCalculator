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

public class TriadDeceptionPane extends JPanel {
	private JCheckBox chckbxExcludeInformant;
	private JCheckBox chckbxExcludeDeceiver;
	private JCheckBox chckbxExcludeIncenseMaster;
	private JCheckBox chckbxExcludeForger;

	/**
	 * Create the panel.
	 */
	public TriadDeceptionPane() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		chckbxExcludeInformant = new JCheckBox("Exclude Informant");
		add(chckbxExcludeInformant);
		
		chckbxExcludeForger = new JCheckBox("Exclude Forger");
		add(chckbxExcludeForger);
		
		chckbxExcludeIncenseMaster = new JCheckBox("Exclude Incense Master");
		add(chckbxExcludeIncenseMaster);
		
		chckbxExcludeDeceiver = new JCheckBox("Exclude Deceiver");
		add(chckbxExcludeDeceiver);

	}
	
	public RoleDecider getDecider() {
		Set<Tag> include = EnumSet.of(Tag.TRIAD, Tag.DECEPTION);
		Set<Role> exclude = EnumSet.noneOf(Role.class);
		
		if (this.chckbxExcludeInformant.isSelected()) {
			exclude.add(Role.INFORMANT);
		}
		if (this.chckbxExcludeForger.isSelected()) {
			exclude.add(Role.FORGER);
		}
		if (this.chckbxExcludeIncenseMaster.isSelected()) {
			exclude.add(Role.INCENSE_MASTER);
		}
		if (this.chckbxExcludeDeceiver.isSelected()) {
			exclude.add(Role.DECEIVER);
		}
		
		return StaticDecider.byTagRole(include, exclude);
	}
}
