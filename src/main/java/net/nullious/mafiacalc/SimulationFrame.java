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
package net.nullious.mafiacalc;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.nullious.mafiacalc.settings.RoleDecider;
import net.nullious.mafiacalc.simulation.LogicalSimulation;
import net.nullious.mafiacalc.simulation.Simulation;
import net.nullious.mafiacalc.simulation.StackSimulation;

import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class SimulationFrame extends JFrame {
	
	private final Map<Role, RoleDecider> settings;
	private final List<Role> saveRoles;

	private JPanel contentPane;
	private JList<Role> saveRoleList;
	private JList<Role> confirmedList;
	private JList<Role> ignoredList;
	private JList<Role> possibleList;
	private RoleTabbedListPane roleTabbedListPane;
	private JList<Role> calculatedConfirmedList;
	private JList<Role> unmatchedRolesList;
	private JList<Role> unmatchedMetasList;

	/**
	 * Create the frame.
	 */
	public SimulationFrame() {
		this(new EnumMap<Role, RoleDecider>(Role.class), new Role[0]);
	}
	
	public SimulationFrame(Map<Role, RoleDecider> settings, Role[] roles) {
		this.settings = settings;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1285, 822);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {115, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 230, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblSaveConfiguration = new JLabel("Save Configuration");
		GridBagConstraints gbc_lblSaveConfiguration = new GridBagConstraints();
		gbc_lblSaveConfiguration.insets = new Insets(0, 0, 5, 5);
		gbc_lblSaveConfiguration.gridx = 0;
		gbc_lblSaveConfiguration.gridy = 0;
		contentPane.add(lblSaveConfiguration, gbc_lblSaveConfiguration);
		
		JLabel lblConfirmedRoles = new JLabel("Confirmed Roles");
		GridBagConstraints gbc_lblConfirmedRoles = new GridBagConstraints();
		gbc_lblConfirmedRoles.insets = new Insets(0, 0, 5, 5);
		gbc_lblConfirmedRoles.gridx = 1;
		gbc_lblConfirmedRoles.gridy = 0;
		contentPane.add(lblConfirmedRoles, gbc_lblConfirmedRoles);
		
		JLabel lblIgnoredRoles = new JLabel("Ignored Roles");
		GridBagConstraints gbc_lblIgnoredRoles = new GridBagConstraints();
		gbc_lblIgnoredRoles.insets = new Insets(0, 0, 5, 5);
		gbc_lblIgnoredRoles.gridx = 2;
		gbc_lblIgnoredRoles.gridy = 0;
		contentPane.add(lblIgnoredRoles, gbc_lblIgnoredRoles);
		
		JLabel lblCalculatedConfirmed = new JLabel("Calculated Confirmed");
		GridBagConstraints gbc_lblCalculatedConfirmed = new GridBagConstraints();
		gbc_lblCalculatedConfirmed.insets = new Insets(0, 0, 5, 5);
		gbc_lblCalculatedConfirmed.gridx = 3;
		gbc_lblCalculatedConfirmed.gridy = 0;
		contentPane.add(lblCalculatedConfirmed, gbc_lblCalculatedConfirmed);
		
		JLabel lblUnmatchedMetas = new JLabel("Unmatched Metas");
		GridBagConstraints gbc_lblUnmatchedMetas = new GridBagConstraints();
		gbc_lblUnmatchedMetas.insets = new Insets(0, 0, 5, 5);
		gbc_lblUnmatchedMetas.gridx = 4;
		gbc_lblUnmatchedMetas.gridy = 0;
		contentPane.add(lblUnmatchedMetas, gbc_lblUnmatchedMetas);
		
		JLabel lblUnmatchedRoles = new JLabel("Unmatched Roles");
		GridBagConstraints gbc_lblUnmatchedRoles = new GridBagConstraints();
		gbc_lblUnmatchedRoles.insets = new Insets(0, 0, 5, 5);
		gbc_lblUnmatchedRoles.gridx = 5;
		gbc_lblUnmatchedRoles.gridy = 0;
		contentPane.add(lblUnmatchedRoles, gbc_lblUnmatchedRoles);
		
		JLabel lblPossibleRoles = new JLabel("Possible Roles");
		GridBagConstraints gbc_lblPossibleRoles = new GridBagConstraints();
		gbc_lblPossibleRoles.insets = new Insets(0, 0, 5, 5);
		gbc_lblPossibleRoles.gridx = 6;
		gbc_lblPossibleRoles.gridy = 0;
		contentPane.add(lblPossibleRoles, gbc_lblPossibleRoles);
		
		JLabel lblOptions = new JLabel("Options");
		GridBagConstraints gbc_lblOptions = new GridBagConstraints();
		gbc_lblOptions.insets = new Insets(0, 0, 5, 0);
		gbc_lblOptions.gridx = 7;
		gbc_lblOptions.gridy = 0;
		contentPane.add(lblOptions, gbc_lblOptions);
		
		JScrollPane saveRoleScrollPane = new JScrollPane();
		
		saveRoleList = new JList<>();
		saveRoleList.setEnabled(false);
		saveRoleScrollPane.setViewportView(saveRoleList);
		saveRoleList.setModel(new AbstractListModel<Role>() {
			private Role[] values = roles;
			
			public int getSize() {
				return values.length;
			}
			public Role getElementAt(int index) {
				return values[index];
			}
		});
		GridBagConstraints gbc_saveRoleScrollPane = new GridBagConstraints();
		gbc_saveRoleScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_saveRoleScrollPane.fill = GridBagConstraints.BOTH;
		gbc_saveRoleScrollPane.gridx = 0;
		gbc_saveRoleScrollPane.gridy = 1;
		contentPane.add(saveRoleScrollPane, gbc_saveRoleScrollPane);
		
		JScrollPane confirmedScrollPane = new JScrollPane();
		GridBagConstraints gbc_confirmedScrollPane = new GridBagConstraints();
		gbc_confirmedScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_confirmedScrollPane.fill = GridBagConstraints.BOTH;
		gbc_confirmedScrollPane.gridx = 1;
		gbc_confirmedScrollPane.gridy = 1;
		contentPane.add(confirmedScrollPane, gbc_confirmedScrollPane);
		
		confirmedList = new JList<>();
		confirmedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		confirmedScrollPane.setViewportView(confirmedList);
		confirmedList.setModel(new DefaultListModel<Role>());
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 1;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		ignoredList = new JList<>();
		scrollPane.setViewportView(ignoredList);
		ignoredList.setModel(new DefaultListModel<Role>());
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 3;
		gbc_scrollPane_1.gridy = 1;
		contentPane.add(scrollPane_1, gbc_scrollPane_1);
		
		calculatedConfirmedList = new JList();
		scrollPane_1.setViewportView(calculatedConfirmedList);
		calculatedConfirmedList.setModel(new DefaultListModel<Role>());
		
		JScrollPane scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 4;
		gbc_scrollPane_2.gridy = 1;
		contentPane.add(scrollPane_2, gbc_scrollPane_2);
		
		unmatchedMetasList = new JList();
		scrollPane_2.setViewportView(unmatchedMetasList);
		unmatchedMetasList.setModel(new DefaultListModel<Role>());
		
		JScrollPane scrollPane_3 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_3 = new GridBagConstraints();
		gbc_scrollPane_3.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_3.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_3.gridx = 5;
		gbc_scrollPane_3.gridy = 1;
		contentPane.add(scrollPane_3, gbc_scrollPane_3);
		
		unmatchedRolesList = new JList();
		scrollPane_3.setViewportView(unmatchedRolesList);
		unmatchedRolesList.setModel(new DefaultListModel<Role>());
		
		JScrollPane possibleScrollPane = new JScrollPane();
		GridBagConstraints gbc_possibleScrollPane = new GridBagConstraints();
		gbc_possibleScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_possibleScrollPane.fill = GridBagConstraints.BOTH;
		gbc_possibleScrollPane.gridx = 6;
		gbc_possibleScrollPane.gridy = 1;
		contentPane.add(possibleScrollPane, gbc_possibleScrollPane);
		
		possibleList = new JList<>();
		possibleScrollPane.setViewportView(possibleList);
		possibleList.setModel(new DefaultListModel<Role>());
		
		roleTabbedListPane = new RoleTabbedListPane();
		GridBagConstraints gbc_roleTabbedListPane = new GridBagConstraints();
		gbc_roleTabbedListPane.insets = new Insets(0, 0, 5, 0);
		gbc_roleTabbedListPane.fill = GridBagConstraints.BOTH;
		gbc_roleTabbedListPane.gridx = 7;
		gbc_roleTabbedListPane.gridy = 1;
		contentPane.add(roleTabbedListPane, gbc_roleTabbedListPane);
		
		JButton btnUnconfirm = new JButton("Unconfirm");
		btnUnconfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel<Role> model = (DefaultListModel<Role>) confirmedList.getModel();
				if (confirmedList.getSelectedIndex() == -1) {
					model.removeElementAt(model.size()-1);
				} else {
					model.removeElementAt(confirmedList.getSelectedIndex());
				}
				recalculateRoles();
			}
		});
		GridBagConstraints gbc_btnUnconfirm = new GridBagConstraints();
		gbc_btnUnconfirm.fill = GridBagConstraints.BOTH;
		gbc_btnUnconfirm.insets = new Insets(0, 0, 5, 5);
		gbc_btnUnconfirm.gridx = 1;
		gbc_btnUnconfirm.gridy = 2;
		contentPane.add(btnUnconfirm, gbc_btnUnconfirm);
		
		JButton btnUnignore = new JButton("Unignore");
		btnUnignore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel<Role> model = (DefaultListModel<Role>) confirmedList.getModel();
				if (confirmedList.getSelectedIndex() == -1) {
					model.removeElementAt(model.size()-1);
				} else {
					model.removeElementAt(confirmedList.getSelectedIndex());
				}
				recalculateRoles();
			}
		});
		GridBagConstraints gbc_btnUnignore = new GridBagConstraints();
		gbc_btnUnignore.fill = GridBagConstraints.BOTH;
		gbc_btnUnignore.insets = new Insets(0, 0, 5, 5);
		gbc_btnUnignore.gridx = 2;
		gbc_btnUnignore.gridy = 2;
		contentPane.add(btnUnignore, gbc_btnUnignore);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Role role = roleTabbedListPane.getSelectedRole();
				
				DefaultListModel<Role> model = (DefaultListModel<Role>) confirmedList.getModel();
				
				if (role != null) {
					model.addElement(role);
					recalculateRoles();
				}
			}
		});
		GridBagConstraints gbc_btnConfirm = new GridBagConstraints();
		gbc_btnConfirm.insets = new Insets(0, 0, 5, 0);
		gbc_btnConfirm.fill = GridBagConstraints.BOTH;
		gbc_btnConfirm.gridx = 7;
		gbc_btnConfirm.gridy = 2;
		contentPane.add(btnConfirm, gbc_btnConfirm);
		
		JButton btnIgnore = new JButton("Ignore");
		btnIgnore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Role role = roleTabbedListPane.getSelectedRole();
				
				DefaultListModel<Role> model = (DefaultListModel<Role>) ignoredList.getModel();
				
				if (role != null) {
					model.addElement(role);
					recalculateRoles();
				}
			}
		});
		GridBagConstraints gbc_btnIgnore = new GridBagConstraints();
		gbc_btnIgnore.fill = GridBagConstraints.BOTH;
		gbc_btnIgnore.gridx = 7;
		gbc_btnIgnore.gridy = 3;
		contentPane.add(btnIgnore, gbc_btnIgnore);
		
		ArrayList<Role> temp = new ArrayList<Role>();
		for (Role r : roles) {
			temp.add(r);
		}
		saveRoles = Collections.unmodifiableList(temp);
		recalculateRoles();
	}
	
	private void recalculateRoles() {
		List<Role> possible = new ArrayList<>();
		Set<Role> ignored = EnumSet.noneOf(Role.class);
		List<Role> confirmed = new ArrayList<>();
		
		DefaultListModel<Role> ignmodel = (DefaultListModel<Role>) ignoredList.getModel();
		DefaultListModel<Role> cnfmodel = (DefaultListModel<Role>) confirmedList.getModel();
		
		for (int x = 0; x < ignmodel.size(); x++) {
			ignored.add(ignmodel.getElementAt(x));
		}
		
		for (int x = 0; x < cnfmodel.size(); x++) {
			confirmed.add(cnfmodel.getElementAt(x));
		}
		
		Simulation sim = new StackSimulation(this.settings, this.saveRoles, ignored, confirmed);
		
		sim.simulate();
		
		fillList(this.calculatedConfirmedList, sim.getResult_confirmed());
		fillList(this.unmatchedMetasList, sim.getResult_unmatched_metas());
		fillList(this.unmatchedRolesList, sim.getResult_unmatched_roles());
		fillList(this.possibleList, sim.getResult_possible_roles());
		
		/*for (Role r : saveRoles) {
			
			if (!r.hasTag(Tag.META)) {
				// Put explicitly configured roles in "possible"
				possible.add(r);
			} else if (settings.containsKey(r)) {
				// Expand Meta roles to all their possibilities
				for (Role mr : settings.get(r).getRoleSet()) {
					// ... UNLESS they are ignored.
					if (!ignored.contains(mr)) {
						possible.add(mr);
					}
				}
			} else {
				System.err.println("WARN: '" + r + "' is META, but does not have any associated Settings!");
			}
		}
		
		// Cull over-limit roles
		for (Role r : Role.values()) {
			if (r.getLimit() > -1) {
				while (Collections.frequency(possible, r) > r.getLimit()) {
					possible.remove(r);
				}
			}
		}
		
		// Sort everything
		Collections.sort(possible, new Comparator<Role>() {
			@Override
			public int compare(Role a, Role b) {
				return a.toString().compareTo(b.toString());
			}
		});
		
		// Update the lists
		DefaultListModel<Role> model = new DefaultListModel<>();
		for (Role r : possible) {
			model.addElement(r);
		}
		possibleList.setModel(model);*/
	}

	private void fillList(JList<Role> jlist, Collection<Role> content) {
		DefaultListModel<Role> model = (DefaultListModel<Role>) jlist.getModel();
		model.clear();
		for (Role r : content) {
			model.addElement(r);
		}
	}
}
