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

public class NeutralEvilPane extends JPanel {
	private JCheckBox chckbxExcludeAuditor;
	private JCheckBox chckbxExcludeWitch;
	private JCheckBox chckbxExcludeJudge;
	private JCheckBox chckbxExcludeKilling;
	private JCheckBox chckbxExcludeCult;

	/**
	 * Create the panel.
	 */
	public NeutralEvilPane() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		chckbxExcludeKilling = new JCheckBox("Exclude Killing");
		add(chckbxExcludeKilling);
		
		chckbxExcludeCult = new JCheckBox("Exclude Cult");
		add(chckbxExcludeCult);
		
		chckbxExcludeJudge = new JCheckBox("Exclude Judge");
		add(chckbxExcludeJudge);
		
		chckbxExcludeWitch = new JCheckBox("Exclude Witch");
		add(chckbxExcludeWitch);
		
		chckbxExcludeAuditor = new JCheckBox("Exclude Auditor");
		add(chckbxExcludeAuditor);

	}
	
	public RoleDecider getDecider() {
		Set<Tag> include = EnumSet.of(Tag.NEUTRAL, Tag.EVIL);
		Set<Tag> excludeTag = EnumSet.noneOf(Tag.class);
		Set<Role> exclude = EnumSet.noneOf(Role.class);
		
		if (this.chckbxExcludeKilling.isSelected()) {
			excludeTag.add(Tag.KILLING);
		}
		if (this.chckbxExcludeCult.isSelected()) {
			exclude.add(Role.CULTIST);
			exclude.add(Role.WITCH_DOCTOR);
		}
		if (this.chckbxExcludeJudge.isSelected()) {
			exclude.add(Role.JUDGE);
		}
		if (this.chckbxExcludeWitch.isSelected()) {
			exclude.add(Role.WITCH);
		}
		if (this.chckbxExcludeAuditor.isSelected()) {
			exclude.add(Role.AUDITOR);
		}
		
		return StaticDecider.byTagTagRole(include, excludeTag, exclude);
	}
}
