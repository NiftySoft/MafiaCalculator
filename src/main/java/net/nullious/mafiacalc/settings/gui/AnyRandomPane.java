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
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import net.nullious.mafiacalc.Tag;
import net.nullious.mafiacalc.settings.RoleDecider;
import net.nullious.mafiacalc.settings.StaticDecider;

import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.util.EnumSet;
import java.util.Set;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import javax.swing.LayoutStyle.ComponentPlacement;

public class AnyRandomPane extends JPanel {
	private JCheckBox chckbxExcludeNeutralRoles;
	private JCheckBox chckbxExcludeKillingRoles;
	private JCheckBox chckbxExcludeTownRoles;
	private JCheckBox chckbxExcludeMafiaRoles;
	private JCheckBox chckbxExcludeTriadRoles;

	/**
	 * Create the panel.
	 */
	public AnyRandomPane() {
		
		chckbxExcludeKillingRoles = new JCheckBox("Exclude killing roles");
		
		chckbxExcludeMafiaRoles = new JCheckBox("Exclude Mafia roles");
		
		chckbxExcludeTownRoles = new JCheckBox("Exclude Town roles");
		
		chckbxExcludeNeutralRoles = new JCheckBox("Exclude Neutral roles");
		
		chckbxExcludeTriadRoles = new JCheckBox("Exclude Triad roles");
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(chckbxExcludeKillingRoles);
		add(chckbxExcludeMafiaRoles);
		add(chckbxExcludeTownRoles);
		add(chckbxExcludeNeutralRoles);
		add(chckbxExcludeTriadRoles);

	}
	
	public RoleDecider getDecider() {
		Set<Tag> include = null;
		Set<Tag> exclude = EnumSet.noneOf(Tag.class);
		
		if (this.chckbxExcludeKillingRoles.isSelected()) {
			exclude.add(Tag.KILLING);
		}
		if (this.chckbxExcludeMafiaRoles.isSelected()) {
			exclude.add(Tag.MAFIA);
		}
		if (this.chckbxExcludeTownRoles.isSelected()) {
			exclude.add(Tag.TOWN);
		}
		if (this.chckbxExcludeNeutralRoles.isSelected()) {
			exclude.add(Tag.NEUTRAL);
		}
		if (this.chckbxExcludeTriadRoles.isSelected()) {
			exclude.add(Tag.TRIAD);
		}
		
		return StaticDecider.byTagTag(include, exclude);
	}
}
