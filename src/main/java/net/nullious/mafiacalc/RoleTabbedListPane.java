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

import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class RoleTabbedListPane extends JPanel {

	private JList<Role> townRoleList;
	private JList<Role> mafiaRoleList;
	private JList<Role> triadRoleList;
	private JList<Role> neutralRoleList;
	private JList<Role> metaRoleList;
	private JTabbedPane roleTabbedPane;
	private JScrollPane townRoleScrollPane;
	private JScrollPane mafiaRoleScrollPane;
	private JScrollPane triadRoleScrollPane;
	private JScrollPane neutralRoleScrollPane;
	private JScrollPane metaRoleScrollPane;
	/**
	 * Create the panel.
	 */
	public RoleTabbedListPane() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		roleTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		townRoleScrollPane = new JScrollPane();
		roleTabbedPane.addTab("Town", null, townRoleScrollPane, null);
		townRoleScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		townRoleScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		townRoleList = new JList<>();
		townRoleScrollPane.setViewportView(townRoleList);
		townRoleList.setModel(new AbstractListModel<Role>() {
			private Role[] values = Role.withAllTags(Tag.TOWN).toArray(new Role[0]);
			
			public int getSize() {
				return values.length;
			}
			public Role getElementAt(int index) {
				return values[index];
			}
		});
		townRoleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		mafiaRoleScrollPane = new JScrollPane();
		mafiaRoleScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		mafiaRoleScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		roleTabbedPane.addTab("Mafia", null, mafiaRoleScrollPane, null);
		
		mafiaRoleList = new JList<Role>();
		mafiaRoleScrollPane.setViewportView(mafiaRoleList);
		mafiaRoleList.setModel(new AbstractListModel<Role>() {
			private Role[] values = Role.withAllTags(Tag.MAFIA).toArray(new Role[0]);
			
			public int getSize() {
				return values.length;
			}
			public Role getElementAt(int index) {
				return values[index];
			}
		});
		mafiaRoleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		triadRoleScrollPane = new JScrollPane();
		triadRoleScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		triadRoleScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		roleTabbedPane.addTab("Triad", null, triadRoleScrollPane, null);
		
		triadRoleList = new JList<Role>();
		triadRoleScrollPane.setViewportView(triadRoleList);
		triadRoleList.setModel(new AbstractListModel<Role>() {
			private Role[] values = Role.withAllTags(Tag.TRIAD).toArray(new Role[0]);
			
			public int getSize() {
				return values.length;
			}
			public Role getElementAt(int index) {
				return values[index];
			}
		});
		triadRoleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		neutralRoleScrollPane = new JScrollPane();
		neutralRoleScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		neutralRoleScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		roleTabbedPane.addTab("Neutral", null, neutralRoleScrollPane, null);
		
		neutralRoleList = new JList<Role>();
		neutralRoleScrollPane.setViewportView(neutralRoleList);
		neutralRoleList.setModel(new AbstractListModel<Role>() {
			private Role[] values = Role.withAllTags(Tag.NEUTRAL).toArray(new Role[0]);
			
			public int getSize() {
				return values.length;
			}
			public Role getElementAt(int index) {
				return values[index];
			}
		});
		neutralRoleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		metaRoleScrollPane = new JScrollPane();
		roleTabbedPane.addTab("Meta", null, metaRoleScrollPane, null);
		
		metaRoleList = new JList<Role>();
		metaRoleScrollPane.setViewportView(metaRoleList);
		metaRoleList.setModel(new AbstractListModel<Role>() {
			private Role[] values = Role.withAllTags(Tag.META).toArray(new Role[0]);
			
			public int getSize() {
				return values.length;
			}
			public Role getElementAt(int index) {
				return values[index];
			}
		});
		metaRoleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.add(roleTabbedPane);
	}
	
	public Role getSelectedRole() {
		JList<Role> list;
		
		if (roleTabbedPane.getSelectedComponent() == townRoleScrollPane) {
			list = townRoleList;
		} else if (roleTabbedPane.getSelectedComponent() == mafiaRoleScrollPane) {
			list = mafiaRoleList;
		} else if (roleTabbedPane.getSelectedComponent() == triadRoleScrollPane) {
			list = triadRoleList;
		} else if (roleTabbedPane.getSelectedComponent() == neutralRoleScrollPane) {
			list = neutralRoleList;
		} else if (roleTabbedPane.getSelectedComponent() == metaRoleScrollPane) {
			list = metaRoleList;
		} else {
			throw new AssertionError();
		}
		
		return list.getSelectedValue();
	}

}
