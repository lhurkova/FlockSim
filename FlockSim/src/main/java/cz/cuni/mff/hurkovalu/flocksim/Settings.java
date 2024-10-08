/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim;

import cz.cuni.mff.hurkovalu.flocksim.spi.Parameters;
import cz.cuni.mff.hurkovalu.flocksim.spi.FlockModel;
import cz.cuni.mff.hurkovalu.flocksim.settings.SettingsItem;
import cz.cuni.mff.hurkovalu.flocksim.spi.descriptors.Descriptor;
import cz.cuni.mff.hurkovalu.flocksim.settings.Savable;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 * Class representing settings window in graphical user interface.
 * @author Lucie Hurkova
 */
public class Settings extends JDialog {
    
    private Map<FlockModel, List<SettingsItem>> pluginsSettings = new IdentityHashMap<>();
    private Map<FlockModel, Parameters> pluginsParams = new IdentityHashMap<>();
    private List<FlockModel> sortedPlugins = new ArrayList<>();
    private JTabbedPane mainTabbedPane;
    private JPanel simulationSettingsPanel;
    private JTabbedPane pluginsTabbedPane;
    private JPanel pluginsSettingsPanel;
    private JTextField stepsTextField;
    private JTextField agentsTextField;
    private Parameters simulationParams;
    private List<SettingsItem> simulationSettings;
    private Frame owner;
    private JPanel emptyPanel;
    private CardLayout cardLayout;
    private JPanel cardsPanel;
    private static final String EMPTY_SETTINGS = "No plug-ins loaded";
    private static final String PLUGINS_TABS = "Plugins tabbed pane";
    
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
        
        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);
        emptyPanel = new JPanel(new GridBagLayout());
        emptyPanel.add(new JLabel(EMPTY_SETTINGS));
        
        pluginsSettingsPanel = new JPanel();
        pluginsSettingsPanel.setLayout(new BoxLayout(pluginsSettingsPanel, BoxLayout.Y_AXIS));
//        mainTabbedPane.addTab("Plug-ins", pluginsSettingsPanel);
        pluginsTabbedPane = new JTabbedPane();
        JScrollPane scroll = new JScrollPane(pluginsSettingsPanel);
        
        cardsPanel.add(scroll, PLUGINS_TABS);
        cardsPanel.add(emptyPanel, EMPTY_SETTINGS);
        cardLayout.show(cardsPanel, EMPTY_SETTINGS);
        
        mainTabbedPane.addTab("Plug-ins", cardsPanel);
        pluginsSettingsPanel.add(pluginsTabbedPane);
        getContentPane().add(mainTabbedPane, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {this.setVisible(false);
                                            saveSettings();});
        buttonPanel.add(closeButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
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
        cardLayout.show(cardsPanel, PLUGINS_TABS);
    }
    
    /**
     * Removes the specified plugin from the settings.
     * @param plugin plugin to be removed
     */
    public void removePlugin(FlockModel plugin) {
        pluginsTabbedPane.removeTabAt(sortedPlugins.indexOf(plugin));
        sortedPlugins.remove(plugin);
        pluginsParams.remove(plugin);
        pluginsSettings.remove(plugin);
        if (sortedPlugins.isEmpty()) {
            cardLayout.show(cardsPanel, EMPTY_SETTINGS);
        }
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
        pluginsTabbedPane.addTab(plugin.getName(), pluginPanel);
        sortedPlugins.add(plugin);
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
