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
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import net.nullious.mafiacalc.Role;
import net.nullious.mafiacalc.settings.RoleDecider;

public class SaveSettingsPane extends JPanel {
	private AnyRandomPane anyRandomPane;
	private TownPowerPane townPowerPane;
	private TownKillingPane townKillingPane;
	private TownProtectivePane townProtectivePane;
	private TownInvestigativePane townInvestigativePane;
	private TownGovernmentPane townGovernmentPane;
	private TownRandomPane townRandomPane;
	private MafiaRandomPane mafiaRandomPane;
	private MafiaKillingPane mafiaKillingPane;
	private MafiaSupportPane mafiaSupportPane;
	private MafiaDeceptionPane mafiaDeceptionPane;
	private TriadRandomPane triadRandomPane;
	private TriadKillingPane triadKillingPane;
	private TriadSupportPane triadSupportPane;
	private TriadDeceptionPane triadDeceptionPane;
	private NeutralRandomPane neutralRandomPane;
	private NeutralKillingPane neutralKillingPane;
	private NeutralEvilPane neutralEvilPane;
	private NeutralBenignPane neutralBenignPane;

	/**
	 * Create the panel.
	 */
	public SaveSettingsPane() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JTabbedPane alignmentTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(alignmentTabbedPane);
		
		JTabbedPane townTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		alignmentTabbedPane.addTab("Town", null, townTabbedPane, null);
		
		townRandomPane = new TownRandomPane();
		townTabbedPane.addTab("Random", null, townRandomPane, null);
		
		townGovernmentPane = new TownGovernmentPane();
		townTabbedPane.addTab("Government", null, townGovernmentPane, null);
		
		townInvestigativePane = new TownInvestigativePane();
		townTabbedPane.addTab("Investigative", null, townInvestigativePane, null);
		
		townProtectivePane = new TownProtectivePane();
		townTabbedPane.addTab("Protective", null, townProtectivePane, null);
		
		townKillingPane = new TownKillingPane();
		townTabbedPane.addTab("Killing", null, townKillingPane, null);
		
		townPowerPane = new TownPowerPane();
		townTabbedPane.addTab("Power", null, townPowerPane, null);
		
		JTabbedPane mafiaTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		alignmentTabbedPane.addTab("Mafia", null, mafiaTabbedPane, null);
		
		mafiaRandomPane = new MafiaRandomPane();
		mafiaTabbedPane.addTab("Random", null, mafiaRandomPane, null);
		
		mafiaKillingPane = new MafiaKillingPane();
		mafiaTabbedPane.addTab("Killing", null, mafiaKillingPane, null);
		
		mafiaSupportPane = new MafiaSupportPane();
		mafiaTabbedPane.addTab("Support", null, mafiaSupportPane, null);
		
		mafiaDeceptionPane = new MafiaDeceptionPane();
		mafiaTabbedPane.addTab("Deception", null, mafiaDeceptionPane, null);
		
		JTabbedPane triadTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		alignmentTabbedPane.addTab("Triad", null, triadTabbedPane, null);
		
		triadRandomPane = new TriadRandomPane();
		triadTabbedPane.addTab("Random", null, triadRandomPane, null);
		
		triadKillingPane = new TriadKillingPane();
		triadTabbedPane.addTab("Killing", null, triadKillingPane, null);
		
		triadSupportPane = new TriadSupportPane();
		triadTabbedPane.addTab("Support", null, triadSupportPane, null);
		
		triadDeceptionPane = new TriadDeceptionPane();
		triadTabbedPane.addTab("Deception", null, triadDeceptionPane, null);
		
		JTabbedPane neutralTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		alignmentTabbedPane.addTab("Neutral", null, neutralTabbedPane, null);
		
		neutralRandomPane = new NeutralRandomPane();
		neutralTabbedPane.addTab("Random", null, neutralRandomPane, null);
		
		neutralKillingPane = new NeutralKillingPane();
		neutralTabbedPane.addTab("Killing", null, neutralKillingPane, null);
		
		neutralEvilPane = new NeutralEvilPane();
		neutralTabbedPane.addTab("Evil", null, neutralEvilPane, null);
		
		neutralBenignPane = new NeutralBenignPane();
		neutralTabbedPane.addTab("Benign", null, neutralBenignPane, null);
		
		anyRandomPane = new AnyRandomPane();
		alignmentTabbedPane.addTab("Any", null, anyRandomPane, null);

	}
	
	public Map<Role, RoleDecider> getSaveSettings() {
		Map<Role, RoleDecider> map = new EnumMap<>(Role.class);
		
		map.put(Role.TOWN_RANDOM, townRandomPane.getDecider());
		map.put(Role.TOWN_GOVERNMENT, townGovernmentPane.getDecider());
		map.put(Role.TOWN_INVESTIGATIVE, townInvestigativePane.getDecider());
		map.put(Role.TOWN_PROTECTIVE, townProtectivePane.getDecider());
		map.put(Role.TOWN_KILLING, townKillingPane.getDecider());
		map.put(Role.TOWN_POWER, townPowerPane.getDecider());
		
		map.put(Role.MAFIA_RANDOM, mafiaRandomPane.getDecider());
		map.put(Role.MAFIA_KILLING, mafiaKillingPane.getDecider());
		map.put(Role.MAFIA_SUPPORT, mafiaSupportPane.getDecider());
		map.put(Role.MAFIA_DECEPTION, mafiaDeceptionPane.getDecider());
		
		map.put(Role.TRIAD_RANDOM, triadRandomPane.getDecider());
		map.put(Role.TRIAD_KILLING, triadKillingPane.getDecider());
		map.put(Role.TRIAD_SUPPORT, triadSupportPane.getDecider());
		map.put(Role.TRIAD_DECEPTION, triadDeceptionPane.getDecider());
		
		map.put(Role.NEUTRAL_RANDOM, neutralRandomPane.getDecider());
		map.put(Role.NEUTRAL_KILLING, neutralKillingPane.getDecider());
		map.put(Role.NEUTRAL_EVIL, neutralEvilPane.getDecider());
		map.put(Role.NEUTRAL_BENIGN, neutralBenignPane.getDecider());
		
		map.put(Role.ANY_RANDOM, anyRandomPane.getDecider());
		
		return Collections.unmodifiableMap(map);
	}
}
