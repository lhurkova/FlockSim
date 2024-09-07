/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim;

import cz.cuni.mff.hurkovalu.flocksim.settings.SettingsItem;
import cz.cuni.mff.hurkovalu.flocksim.descriptors.Descriptor;
import cz.cuni.mff.hurkovalu.flocksim.settings.Savable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 * Class representing settings window in graphical user interface.
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class Settings extends JDialog {
    
    private Map<FlockModel, List<SettingsItem>> pluginsSettings = new IdentityHashMap<>();
    private Map<FlockModel, Parameters> pluginsParams = new IdentityHashMap<>();
    private JTabbedPane mainTabbedPane;
    private JPanel simulationSettingsPanel;
    private JTabbedPane pluginsTabbedPane;
    private JPanel pluginsSettingsPanel;
    private JTextField stepsTextField;
    private JTextField agentsTextField;
    private Parameters simulationParams;
    private List<SettingsItem> simulationSettings;
    private Frame owner;
    
    /**
     * Creates a new {@link Settings} dialog with specified owner and main simulation
     * parameters.
     * @param owner owner frame
     * @param mainParams main simulation parameters
     */
    public Settings(Frame owner, Descriptor[] mainParams) {
        super(owner, "Settings", true);
        this.owner = owner;
        
        addWindowListener(new SavingListener());
        
        simulationParams = new Parameters();
        
        setPreferredSize(new Dimension(500, 300));
        setBackground(Color.white);
        mainTabbedPane = new JTabbedPane();
        mainTabbedPane.setTabPlacement(JTabbedPane.LEFT);
        mainTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        
        simulationSettingsPanel = new JPanel();
        simulationSettingsPanel.setLayout(new BoxLayout(simulationSettingsPanel, BoxLayout.Y_AXIS));
        
        simulationSettings = new ArrayList<>();
        for (Descriptor param: mainParams) {
            SettingsItem row = SettingsItem.createCorrectSettingsItem(param);
            simulationSettings.add(row);
            simulationSettingsPanel.add(row);
        }
        
        mainTabbedPane.add("Simulation", simulationSettingsPanel);
        
        
        pluginsSettingsPanel = new JPanel();
        pluginsSettingsPanel.setLayout(new BoxLayout(pluginsSettingsPanel, BoxLayout.Y_AXIS));
//        mainTabbedPane.addTab("Plug-ins", pluginsSettingsPanel);
        pluginsTabbedPane = new JTabbedPane();
        JScrollPane scroll = new JScrollPane(pluginsSettingsPanel);
        mainTabbedPane.addTab("Plug-ins", scroll);
        pluginsSettingsPanel.add(pluginsTabbedPane);
        getContentPane().add(mainTabbedPane, BorderLayout.CENTER);
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {this.setVisible(false);
                                            saveSettings();});
        getContentPane().add(closeButton, BorderLayout.SOUTH);
        pack();
    }
    
    /**
     * Gets values of the main simulation parameters.
     * @return values of the main simulation parameters
     */
    public Parameters getSimulationParams() {
        for (SettingsItem row: simulationSettings) {
            simulationParams.put(row.getDescriptor(), row.getResultAsObject());
        }
        return simulationParams;
    }
    
    /**
     * Gets values of parameters for the specified plugin.
     * @param plugin required plugin
     * @return values of the plugin parameters
     */
    public Parameters getPluginParams(FlockModel plugin) {
        List<SettingsItem> pluginSettings = pluginsSettings.get(plugin);
        Parameters pluginParams = pluginsParams.get(plugin);
        if (pluginSettings != null && pluginParams != null) {
            for (SettingsItem i: pluginSettings) {
                pluginParams.put(i.getDescriptor(), i.getResultAsObject());
            }
        }
        return pluginParams;
    }
    
    /**
     * Registers a new plugin and creates settings tab for the plugin parameters.
     * @param plugin new plugin
     */
    public void addPlugin(FlockModel plugin) {
        List<SettingsItem> pluginSettings = createPluginSettings(plugin);
        pluginsSettings.put(plugin, pluginSettings);
        pluginsParams.put(plugin, new Parameters());
    }
    
    private List<SettingsItem> createPluginSettings(FlockModel plugin) {
        JPanel pluginPanel = new JPanel();
        pluginPanel.setLayout(new BoxLayout(pluginPanel, BoxLayout.Y_AXIS));
        List<Descriptor> descriptors = plugin.getDescriptors();
        List<SettingsItem> settingsItems = new ArrayList<>();
        for (Descriptor d: descriptors) {
            SettingsItem item = SettingsItem.createCorrectSettingsItem(d);
            settingsItems.add(item);
            pluginPanel.add(item);
        }
        pluginsTabbedPane.add(plugin.getName(), pluginPanel);
        return settingsItems;
    }
    
    private void saveSettings() {
        for (SettingsItem i: simulationSettings) {
                if (i instanceof Savable savable) {
                    savable.save();
                }
            }
            
            for (List<SettingsItem> items: pluginsSettings.values()) {
                for (SettingsItem i: items) {
                    if (i instanceof Savable savable) {
                        savable.save();
                    }
                }
            }
    }
    
    private class SavingListener extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            saveSettings();
        }
        
        
    }
    
}
