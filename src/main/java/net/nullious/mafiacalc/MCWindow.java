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
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.nullious.mafiacalc.settings.RoleDecider;
import net.nullious.mafiacalc.settings.gui.SaveSettingsFrame;

public class MCWindow {

	private JFrame frame;
	private SaveSettingsFrame settingsFrame;
	private JList<Role> saveList;
	private JButton btnResetSaveSettings;
	private RoleTabbedListPane roleTabbedListPane;

	/**
	 * Launch the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MCWindow window = new MCWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MCWindow() {
		initialize();

		settingsFrame = new SaveSettingsFrame();
		settingsFrame.setVisible(false);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 623, 952);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{258, 331, 0};
		gridBagLayout.rowHeights = new int[]{29, 789, 29, 29, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JButton btnSimulate = new JButton("Simulate");
		btnSimulate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Map<Role, RoleDecider> settings = settingsFrame.getSaveSettings();
				
				DefaultListModel<Role> model = (DefaultListModel<Role>) saveList.getModel();
				
				Role[] roles = new Role[model.toArray().length];
				
				for (int x = 0; x < roles.length; x++) {
					roles[x] = (Role) model.toArray()[x];
				}
				
				SimulationFrame sim = new SimulationFrame(settings, roles);
				sim.setVisible(true);
			}
		});
		
		JButton btnEditSaveSettings = new JButton("Edit Save Settings");
		btnEditSaveSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				settingsFrame.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnEditSaveSettings = new GridBagConstraints();
		gbc_btnEditSaveSettings.anchor = GridBagConstraints.NORTH;
		gbc_btnEditSaveSettings.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEditSaveSettings.insets = new Insets(0, 0, 5, 5);
		gbc_btnEditSaveSettings.gridx = 0;
		gbc_btnEditSaveSettings.gridy = 0;
		frame.getContentPane().add(btnEditSaveSettings, gbc_btnEditSaveSettings);
		
		btnResetSaveSettings = new JButton("Reset Save Settings");
		btnResetSaveSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				settingsFrame.dispose();
				settingsFrame = new SaveSettingsFrame();
			}
		});
		GridBagConstraints gbc_btnResetSaveSettings = new GridBagConstraints();
		gbc_btnResetSaveSettings.fill = GridBagConstraints.BOTH;
		gbc_btnResetSaveSettings.insets = new Insets(0, 0, 5, 0);
		gbc_btnResetSaveSettings.gridx = 1;
		gbc_btnResetSaveSettings.gridy = 0;
		frame.getContentPane().add(btnResetSaveSettings, gbc_btnResetSaveSettings);
		
		JScrollPane westScrollPane = new JScrollPane();
		
		saveList = new JList<>();
		westScrollPane.setViewportView(saveList);
		DefaultListModel<Role> saveModel = new DefaultListModel<Role>();
		saveModel.addElement(Role.GODFATHER);
		saveModel.addElement(Role.MAFIA_DECEPTION);
		saveModel.addElement(Role.MAFIA_DECEPTION);
		saveModel.addElement(Role.TOWN_INVESTIGATIVE);
		saveModel.addElement(Role.TOWN_INVESTIGATIVE);
		saveModel.addElement(Role.TOWN_KILLING);
		saveModel.addElement(Role.TOWN_PROTECTIVE);
		saveModel.addElement(Role.TOWN_POWER);
		saveModel.addElement(Role.TOWN_RANDOM);
		saveModel.addElement(Role.TOWN_RANDOM);
		saveModel.addElement(Role.NEUTRAL_BENIGN);
		saveModel.addElement(Role.NEUTRAL_BENIGN);
		saveModel.addElement(Role.NEUTRAL_EVIL);
		saveModel.addElement(Role.NEUTRAL_RANDOM);
		saveModel.addElement(Role.ARSONIST);
		saveList.setModel(saveModel);
		GridBagConstraints gbc_westScrollPane = new GridBagConstraints();
		gbc_westScrollPane.fill = GridBagConstraints.BOTH;
		gbc_westScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_westScrollPane.gridx = 0;
		gbc_westScrollPane.gridy = 1;
		frame.getContentPane().add(westScrollPane, gbc_westScrollPane);
		
		
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultListModel<Role> model = (DefaultListModel<Role>) saveList.getModel();

				if (roleTabbedListPane.getSelectedRole() == null) {
					return;
				}
				
				model.addElement(roleTabbedListPane.getSelectedRole());
			}
		});
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel<Role> model = (DefaultListModel<Role>) saveList.getModel();
				if (saveList.getSelectedIndex() == -1) {
					model.removeElementAt(model.size()-1);
				} else {
					model.removeElementAt(saveList.getSelectedIndex());
				}
			}
		});
		
		roleTabbedListPane = new RoleTabbedListPane();
		GridBagConstraints gbc_roleTabbedListPane = new GridBagConstraints();
		gbc_roleTabbedListPane.insets = new Insets(0, 0, 5, 0);
		gbc_roleTabbedListPane.fill = GridBagConstraints.BOTH;
		gbc_roleTabbedListPane.gridx = 1;
		gbc_roleTabbedListPane.gridy = 1;
		frame.getContentPane().add(roleTabbedListPane, gbc_roleTabbedListPane);
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.anchor = GridBagConstraints.NORTH;
		gbc_btnRemove.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRemove.insets = new Insets(0, 0, 5, 5);
		gbc_btnRemove.gridx = 0;
		gbc_btnRemove.gridy = 2;
		frame.getContentPane().add(btnRemove, gbc_btnRemove);
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.anchor = GridBagConstraints.NORTH;
		gbc_btnAdd.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAdd.insets = new Insets(0, 0, 5, 0);
		gbc_btnAdd.gridx = 1;
		gbc_btnAdd.gridy = 2;
		frame.getContentPane().add(btnAdd, gbc_btnAdd);
		GridBagConstraints gbc_btnSimulate = new GridBagConstraints();
		gbc_btnSimulate.anchor = GridBagConstraints.NORTH;
		gbc_btnSimulate.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSimulate.gridwidth = 2;
		gbc_btnSimulate.gridx = 0;
		gbc_btnSimulate.gridy = 3;
		frame.getContentPane().add(btnSimulate, gbc_btnSimulate);
	}
}
