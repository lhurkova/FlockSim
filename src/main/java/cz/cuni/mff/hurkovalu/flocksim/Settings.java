/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim;

import cz.cuni.mff.hurkovalu.flocksim.settings.SettingsItem;
import cz.cuni.mff.hurkovalu.flocksim.descriptors.Descriptor;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class Settings extends JDialog {
    
    private List<FlockModel> plugins = new ArrayList<>();
    private JTabbedPane mainTabbedPane;
    private JPanel simulationSettings;
    private JTabbedPane pluginsTabbedPane;
    private JPanel pluginsSettings;
    private JTextField stepsTextField;
    private JTextField agentsTextField;
    private Parameters simulationParameters;
    private List<SettingsItem> simulationRows;
    
    public Settings(Frame owner, Descriptor[] mainParams) {
        super(owner, "Settings", true);
        
        simulationParameters = new Parameters(mainParams);
        
        setPreferredSize(new Dimension(500, 300));
        mainTabbedPane = new JTabbedPane();
        mainTabbedPane.setTabPlacement(JTabbedPane.LEFT);
        mainTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        
        simulationSettings = new JPanel();
        simulationSettings.setLayout(new BoxLayout(simulationSettings, BoxLayout.Y_AXIS));
        
        simulationRows = new ArrayList<>();
        for (Descriptor param: mainParams) {
            SettingsItem row = SettingsItem.createCorrectSettingsRow(param);
            simulationRows.add(row);
            simulationSettings.add(row);
        }
        
        mainTabbedPane.add("Simulation", simulationSettings);
        
        pluginsSettings = new JPanel();
        mainTabbedPane.addTab("Plug-ins", pluginsSettings);
        pluginsTabbedPane = new JTabbedPane();
        pluginsSettings.add(pluginsTabbedPane);
        
        getContentPane().add(mainTabbedPane, BorderLayout.CENTER);
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> this.setVisible(false));
        getContentPane().add(closeButton, BorderLayout.SOUTH);
        pack();
    }
    
    public Parameters getSimulationParams() {
        for (SettingsItem row: simulationRows) {
            simulationParameters.set(row.getDescriptor(), row.getResultAsObject());
        }
        return simulationParameters;
    }
    
    public void addPlugin(FlockModel plugin) {
        plugins.add(plugin);
        JPanel pluginPanel = new JPanel();
        pluginPanel.setPreferredSize(new Dimension(200, 300));
        pluginPanel.add(new JLabel("Plugin Settings"));
        pluginsTabbedPane.add(plugin.getName(), pluginPanel);
    }
    
}
