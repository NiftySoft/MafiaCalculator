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

import net.nullious.mafiacalc.Tag;
import net.nullious.mafiacalc.settings.RoleDecider;
import net.nullious.mafiacalc.settings.StaticDecider;

import javax.swing.JCheckBox;

import java.util.EnumSet;
import java.util.Set;

import javax.swing.BoxLayout;

public class NeutralRandomPane extends JPanel {
	private JCheckBox chckbxExcludeBenign;
	private JCheckBox chckbxExcludeEvil;
	private JCheckBox chckbxExcludeKilling;

	/**
	 * Create the panel.
	 */
	public NeutralRandomPane() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		chckbxExcludeKilling = new JCheckBox("Exclude Killing");
		add(chckbxExcludeKilling);
		
		chckbxExcludeEvil = new JCheckBox("Exclude Evil");
		add(chckbxExcludeEvil);
		
		chckbxExcludeBenign = new JCheckBox("Exclude Benign");
		add(chckbxExcludeBenign);

	}
	
	public RoleDecider getDecider() {
		Set<Tag> include = EnumSet.of(Tag.NEUTRAL);
		Set<Tag> exclude = EnumSet.noneOf(Tag.class);
		
		if (this.chckbxExcludeKilling.isSelected()) {
			exclude.add(Tag.KILLING);
		}
		if (this.chckbxExcludeEvil.isSelected()) {
			exclude.add(Tag.EVIL);
		}
		if (this.chckbxExcludeBenign.isSelected()) {
			exclude.add(Tag.BENIGN);
		}
		
		return StaticDecider.byTagTag(include, exclude);
	}
}
