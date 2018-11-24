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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import net.nullious.mafiacalc.settings.RoleDecider;
import net.nullious.mafiacalc.simulation.DemoSimulation;
import net.nullious.mafiacalc.simulation.RelationalSimulation;
import net.nullious.mafiacalc.simulation.Simulation;
import javax.swing.JTree;

@SuppressWarnings("serial")
public class SimulationFrame extends JFrame {
	
	private final Map<Role, RoleDecider> settings;
	private final List<Role> saveRoles;

	private JPanel contentPane;
	private JList<Role> saveRoleList;
	private JList<Role> confirmedList;
	private JList<Role> ignoredList;
	private RoleTabbedListPane roleTabbedListPane;
	private JTree possibleLivingTree;
	private JTree suspectAnalysisTree;
	private JTree possibleRemainingTree;
	private JList<Role> suspectedList;

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
		gbl_contentPane.rowHeights = new int[]{0, 230, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		
		JLabel lblSuspectedRoles = new JLabel("Suspected Roles");
		GridBagConstraints gbc_lblSuspectedRoles = new GridBagConstraints();
		gbc_lblSuspectedRoles.insets = new Insets(0, 0, 5, 5);
		gbc_lblSuspectedRoles.gridx = 2;
		gbc_lblSuspectedRoles.gridy = 0;
		contentPane.add(lblSuspectedRoles, gbc_lblSuspectedRoles);
		
		JLabel lblIgnoredRoles = new JLabel("Ignored Roles");
		GridBagConstraints gbc_lblIgnoredRoles = new GridBagConstraints();
		gbc_lblIgnoredRoles.insets = new Insets(0, 0, 5, 5);
		gbc_lblIgnoredRoles.gridx = 3;
		gbc_lblIgnoredRoles.gridy = 0;
		contentPane.add(lblIgnoredRoles, gbc_lblIgnoredRoles);
		
		JLabel lblPossibleLivingRoles = new JLabel("Possible Living Roles");
		GridBagConstraints gbc_lblPossibleLivingRoles = new GridBagConstraints();
		gbc_lblPossibleLivingRoles.insets = new Insets(0, 0, 5, 5);
		gbc_lblPossibleLivingRoles.gridx = 4;
		gbc_lblPossibleLivingRoles.gridy = 0;
		contentPane.add(lblPossibleLivingRoles, gbc_lblPossibleLivingRoles);
		
		JLabel lblPossibleRemainingRoles = new JLabel("Possible Remaining Roles");
		GridBagConstraints gbc_lblPossibleRemainingRoles = new GridBagConstraints();
		gbc_lblPossibleRemainingRoles.insets = new Insets(0, 0, 5, 5);
		gbc_lblPossibleRemainingRoles.gridx = 5;
		gbc_lblPossibleRemainingRoles.gridy = 0;
		contentPane.add(lblPossibleRemainingRoles, gbc_lblPossibleRemainingRoles);
		
		JLabel lblSuspectAnalysis = new JLabel("Suspect Analysis");
		GridBagConstraints gbc_lblSuspectAnalysis = new GridBagConstraints();
		gbc_lblSuspectAnalysis.insets = new Insets(0, 0, 5, 5);
		gbc_lblSuspectAnalysis.gridx = 6;
		gbc_lblSuspectAnalysis.gridy = 0;
		contentPane.add(lblSuspectAnalysis, gbc_lblSuspectAnalysis);
		
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
		
		JScrollPane suspectedScrollPane = new JScrollPane();
		GridBagConstraints gbc_suspectedScrollPane = new GridBagConstraints();
		gbc_suspectedScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_suspectedScrollPane.fill = GridBagConstraints.BOTH;
		gbc_suspectedScrollPane.gridx = 2;
		gbc_suspectedScrollPane.gridy = 1;
		contentPane.add(suspectedScrollPane, gbc_suspectedScrollPane);
		
		suspectedList = new JList<Role>();
		suspectedScrollPane.setViewportView(suspectedList);
		suspectedList.setModel(new DefaultListModel<Role>());
		
		JScrollPane ignoredScrollPane = new JScrollPane();
		GridBagConstraints gbc_ignoredScrollPane = new GridBagConstraints();
		gbc_ignoredScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_ignoredScrollPane.fill = GridBagConstraints.BOTH;
		gbc_ignoredScrollPane.gridx = 3;
		gbc_ignoredScrollPane.gridy = 1;
		contentPane.add(ignoredScrollPane, gbc_ignoredScrollPane);
		
		ignoredList = new JList<>();
		ignoredScrollPane.setViewportView(ignoredList);
		ignoredList.setModel(new DefaultListModel<Role>());
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 4;
		gbc_scrollPane_1.gridy = 1;
		contentPane.add(scrollPane_1, gbc_scrollPane_1);
		
		possibleLivingTree = new JTree();
		possibleLivingTree.setRootVisible(false);
		scrollPane_1.setViewportView(possibleLivingTree);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 5;
		gbc_scrollPane_2.gridy = 1;
		contentPane.add(scrollPane_2, gbc_scrollPane_2);
		
		possibleRemainingTree = new JTree();
		possibleRemainingTree.setRootVisible(false);
		scrollPane_2.setViewportView(possibleRemainingTree);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_3 = new GridBagConstraints();
		gbc_scrollPane_3.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_3.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_3.gridx = 6;
		gbc_scrollPane_3.gridy = 1;
		contentPane.add(scrollPane_3, gbc_scrollPane_3);
		
		suspectAnalysisTree = new JTree();
		suspectAnalysisTree.setRootVisible(false);
		scrollPane_3.setViewportView(suspectAnalysisTree);
		
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
		
		JButton btnUnsuspect = new JButton("Unsuspect");
		btnUnsuspect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel<Role> model = (DefaultListModel<Role>) suspectedList.getModel();
				if (suspectedList.getSelectedIndex() == -1) {
					model.removeElementAt(model.size()-1);
				} else {
					model.removeElementAt(suspectedList.getSelectedIndex());
				}
				recalculateRoles();
			}
		});
		GridBagConstraints gbc_btnUnsuspect = new GridBagConstraints();
		gbc_btnUnsuspect.fill = GridBagConstraints.BOTH;
		gbc_btnUnsuspect.insets = new Insets(0, 0, 5, 5);
		gbc_btnUnsuspect.gridx = 2;
		gbc_btnUnsuspect.gridy = 2;
		contentPane.add(btnUnsuspect, gbc_btnUnsuspect);
		
		JButton btnUnignore = new JButton("Unignore");
		btnUnignore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel<Role> model = (DefaultListModel<Role>) ignoredList.getModel();
				if (ignoredList.getSelectedIndex() == -1) {
					model.removeElementAt(model.size()-1);
				} else {
					model.removeElementAt(ignoredList.getSelectedIndex());
				}
				recalculateRoles();
			}
		});
		GridBagConstraints gbc_btnUnignore = new GridBagConstraints();
		gbc_btnUnignore.fill = GridBagConstraints.BOTH;
		gbc_btnUnignore.insets = new Insets(0, 0, 5, 5);
		gbc_btnUnignore.gridx = 3;
		gbc_btnUnignore.gridy = 2;
		contentPane.add(btnUnignore, gbc_btnUnignore);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Role role = roleTabbedListPane.getSelectedRole();
				
				DefaultListModel<Role> model = (DefaultListModel<Role>) confirmedList.getModel();
				
				if (role != null && !role.hasTag(Tag.META)) {
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
		
		JButton btnSuspect = new JButton("Suspect");
		btnSuspect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Role role = roleTabbedListPane.getSelectedRole();
				
				DefaultListModel<Role> model = (DefaultListModel<Role>) suspectedList.getModel();
				
				if (role != null && !role.hasTag(Tag.META)) {
					model.addElement(role);
					recalculateRoles();
				}
			}
		});
		GridBagConstraints gbc_btnSuspect = new GridBagConstraints();
		gbc_btnSuspect.fill = GridBagConstraints.BOTH;
		gbc_btnSuspect.insets = new Insets(0, 0, 5, 0);
		gbc_btnSuspect.gridx = 7;
		gbc_btnSuspect.gridy = 3;
		contentPane.add(btnSuspect, gbc_btnSuspect);
		
		JButton btnIgnore = new JButton("Ignore");
		btnIgnore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Role role = roleTabbedListPane.getSelectedRole();
				
				DefaultListModel<Role> model = (DefaultListModel<Role>) ignoredList.getModel();
				
				if (role != null && !role.hasTag(Tag.META) && !model.contains(role)) {
					model.addElement(role);
					recalculateRoles();
				}
			}
		});
		GridBagConstraints gbc_btnIgnore = new GridBagConstraints();
		gbc_btnIgnore.fill = GridBagConstraints.BOTH;
		gbc_btnIgnore.gridx = 7;
		gbc_btnIgnore.gridy = 4;
		contentPane.add(btnIgnore, gbc_btnIgnore);
		
		ArrayList<Role> temp = new ArrayList<Role>();
		for (Role r : roles) {
			temp.add(r);
		}
		saveRoles = Collections.unmodifiableList(temp);
		recalculateRoles();
	}
	
	private void recalculateRoles() {
		List<Role> confirmed = new ArrayList<>();
		List<Role> suspected = new ArrayList<>();
		Set<Role> ignored = EnumSet.noneOf(Role.class);
		
		DefaultListModel<Role> cnfmodel = (DefaultListModel<Role>) confirmedList.getModel();
		DefaultListModel<Role> susmodel = (DefaultListModel<Role>) suspectedList.getModel();
		DefaultListModel<Role> ignmodel = (DefaultListModel<Role>) ignoredList.getModel();
		
		for (int x = 0; x < cnfmodel.size(); x++) {
			assert(!cnfmodel.getElementAt(x).hasTag(Tag.META));
			confirmed.add(cnfmodel.getElementAt(x));
		}
		
		for (int x = 0; x < susmodel.size(); x++) {
			assert(!susmodel.getElementAt(x).hasTag(Tag.META));
			suspected.add(susmodel.getElementAt(x));
		}
		
		for (int x = 0; x < ignmodel.size(); x++) {
			ignored.add(ignmodel.getElementAt(x));
		}

		//Simulation sim = new RelationalSimulation(this.settings, this.saveRoles, ignored, confirmed, suspected);
		Simulation sim = new DemoSimulation(this.settings, this.saveRoles, ignored, confirmed, suspected);
		
		sim.simulate();
		
		populatePossibleLivingTree(sim);
		populatePossibleRemainingTree(sim);
		populateSuspectAnalysisTree(sim);
	}

	private void populatePossibleLivingTree(Simulation sim) {
		Role[] roles = sim.getPossiblyLivingRoles().toArray(new Role[0]);
		Map<Role, Integer> minmap = sim.getMinPossibleCount();
		Map<Role, Integer> maxmap = sim.getMaxPossibleCount();
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		
		for (Role r : roles) {
			DefaultMutableTreeNode min = new DefaultMutableTreeNode("Min Count: " + minmap.get(r), false);
			DefaultMutableTreeNode max = new DefaultMutableTreeNode("Max Count: " + maxmap.get(r), false);
			
			DefaultMutableTreeNode rnode = new DefaultMutableTreeNode(r);
			rnode.add(min);
			rnode.add(max);
			
			root.add(rnode);
		}
		
		TreeModel model = new DefaultTreeModel(root, true);
		this.possibleLivingTree.setModel(model);
	}

	private void populatePossibleRemainingTree(Simulation sim) {
		List<Role> roles = sim.getPossiblyLivingInitialConfig();
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		
		for (Role r : roles) {
			root.add(new DefaultMutableTreeNode(r,false));
		}
		
		TreeModel model = new DefaultTreeModel(root, true);
		this.possibleRemainingTree.setModel(model);
	}

	private void populateSuspectAnalysisTree(Simulation sim) {
		Object analysis = sim.getSuspectAnalysis();
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		
		root.add(new DefaultMutableTreeNode(analysis, false));
		
		TreeModel model = new DefaultTreeModel(root, true);
		this.suspectAnalysisTree.setModel(model);
	}
}
