package net.nullious.mafiacalc.settings.gui;

import java.awt.BorderLayout;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.nullious.mafiacalc.Role;
import net.nullious.mafiacalc.settings.RoleDecider;

@SuppressWarnings("serial")
public class SaveSettingsFrame extends JFrame {

	private JPanel contentPane;
	private SaveSettingsPane saveSettingsPane;

	/**
	 * Create the frame.
	 */
	public SaveSettingsFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		saveSettingsPane = new SaveSettingsPane();
		contentPane.add(saveSettingsPane, BorderLayout.CENTER);
	}
	
	public Map<Role, RoleDecider> getSaveSettings() {
		return saveSettingsPane.getSaveSettings();
	}
}
