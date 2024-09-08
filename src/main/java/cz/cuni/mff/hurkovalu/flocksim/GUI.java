/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim;

import cz.cuni.mff.hurkovalu.flocksim.descriptors.ComboBoxDescriptor;
import cz.cuni.mff.hurkovalu.flocksim.descriptors.Descriptor;
import cz.cuni.mff.hurkovalu.flocksim.descriptors.IntFieldDescriptor;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.Timer;
import javax.swing.ToolTipManager;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Class representing the graphical user interface of the FlockSim.
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class GUI {

    private JFrame frame;
    private FlockGraphics fGraphics;
    private int sizeX;
    private int sizeY;
    private String name;
    private final int x;
    private Flock simulation;
    private int steps = 1000;
    private State state = State.FRONT_PAGE;
    private List<FlockModel> plugins = new ArrayList<>();
    private Map<FlockModel, JMenuItem> runItems = new IdentityHashMap<>();
    private Map<FlockModel, JMenuItem> removeItems = new IdentityHashMap<>();
    private Timer timer;
    private Settings settings;
    JFileChooser fc;
    
    private CardLayout cardLayout;
    private JPanel cardsPanel;
    private JPanel simulationPanel;
    private static final String FRONT_PAGE_PANEL = "front page panel";
    private static final String SIM_PANEL = "simulation panel";
    
    private JMenuItem stopItem;
    private JMenuItem continueItem;
    private JMenuItem pauseItem;
    private JMenu runItemMenu;
    private JMenuItem frontPageItem;
    private JMenu removeItemMenu;
    
    private JButton stopButton;
    private JButton pauseButton;
    private JButton continueButton;
    private JButton startButton;
    private JButton settingsButton;
    private JComboBox<String> pluginSelector;
    private static final String EMPTY_SELECTOR = "No plug-in loaded";
    private static final String PLUGIN_PATH = "plugin path";
    
    private IntFieldDescriptor stepsDescriptor;
    private IntFieldDescriptor agentsDescriptor;
    private ComboBoxDescriptor colorDescriptor;
    private ComboBoxDescriptor sizeDescriptor;

    /**
     * Creates a new instance of the {@link GUI} of specified window size.
     * @param sizeX
     * @param sizeY
     * @param x
     * @param name 
     */
    public GUI(int sizeX, int sizeY, int x, String name) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.x = x;
        this.name = name;
        stepsDescriptor = new IntFieldDescriptor("Number of steps", 0, 5000, 500);
        colorDescriptor = new ComboBoxDescriptor("Color", FlockGraphics.AgentColor.displayNames(), 0);
        agentsDescriptor = new IntFieldDescriptor("Number of birds", 0, 200, 50);
        sizeDescriptor = new ComboBoxDescriptor("Size", FlockGraphics.AgentSize.displayNames(), 1);
    }

    /**
     * Initializes all the graphical components in the {@link GUI}.
     */
    public void createGUI() {
        //System.setProperty("apple.laf.useScreenMenuBar", "true");
        ToolTipManager.sharedInstance().setInitialDelay(100);
        ToolTipManager.sharedInstance().setDismissDelay(20000);
        frame = new JFrame(name);
        frame.setLocation(x, 0);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                
        timer = new Timer(100, e -> paintSimulation());
        fc = new JFileChooser();
        
        JToolBar toolBar = new JToolBar("Tool Bar");
        
        JPanel buttonMenu = new JPanel();
        buttonMenu.setLayout(new BoxLayout(buttonMenu, BoxLayout.X_AXIS));
        
        settingsButton = new JButton("Settings");
        Font buttonFont = new Font("Lucida Grande", Font.PLAIN, 13);
        settingsButton.setFont(buttonFont);
        settingsButton.addActionListener(e -> showSettings());
        startButton = new JButton("Start");
        startButton.setFont(buttonFont);
        startButton.addActionListener(e -> runSimulationWithButton());
        pauseButton = new JButton("Pause");
        pauseButton.setFont(buttonFont);
        pauseButton.addActionListener(e -> pauseSimulation());
        continueButton = new JButton("Continue");
        continueButton.setFont(buttonFont);
        continueButton.addActionListener(e -> continueSimulation());
        stopButton = new JButton("Stop");
        stopButton.setFont(buttonFont);
        stopButton.addActionListener(e -> stopSimulation());
        pluginSelector = new JComboBox<>();
        pluginSelector.addItem(EMPTY_SELECTOR);
        
        toolBar.add(settingsButton);
        toolBar.addSeparator();
        toolBar.add(pluginSelector);
        toolBar.add(startButton);
        toolBar.add(pauseButton);
        toolBar.add(continueButton);
        toolBar.add(stopButton);
        
        createMenu();
        
        fGraphics = new FlockGraphics();
        fGraphics.setPreferredSize(new Dimension(sizeX, sizeY));
        simulationPanel = new JPanel(true);
        simulationPanel.setLayout(new BoxLayout(simulationPanel, BoxLayout.Y_AXIS));
        simulationPanel.add(fGraphics);
        simulationPanel.setBackground(Color.WHITE);
        JPanel frontPage = new JPanel();
        frontPage.setPreferredSize(new Dimension(sizeX, sizeY));
        JLabel label = new JLabel("Front Page");
        frontPage.add(label);
        
        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);
        cardsPanel.add(frontPage, FRONT_PAGE_PANEL);
        cardsPanel.add(simulationPanel, SIM_PANEL);
        cardLayout.show(cardsPanel, FRONT_PAGE_PANEL);
        
        settings = new Settings(frame, new Descriptor[] {stepsDescriptor,
            agentsDescriptor, colorDescriptor, sizeDescriptor});        
        
        frame.getContentPane().add(toolBar, BorderLayout.NORTH);
        frame.getContentPane().add(cardsPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
        
    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu applicationMenu = new JMenu("FlockSim");
        JMenu fileMenu = new JMenu("File");
        JMenu runMenu = new JMenu("Run");
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(applicationMenu);
        menuBar.add(fileMenu);
        menuBar.add(runMenu);
        menuBar.add(helpMenu);
        
        JMenuItem aboutItem = new JMenuItem("About FlockSim");
        JMenuItem settingsItem = new JMenuItem("Settings");
        settingsItem.addActionListener(e -> showSettings());
        JMenuItem quitItem = new JMenuItem("Quit FlockSim");
        quitItem.addActionListener(e -> frame.dispose());
        applicationMenu.add(aboutItem);
        applicationMenu.addSeparator();
        applicationMenu.add(settingsItem);
        applicationMenu.addSeparator();
        applicationMenu.add(quitItem);
        
        JMenuItem pluginItem = new JMenuItem("Load plug-in");
        pluginItem.addActionListener(e -> loadPlugin());
        removeItemMenu = new JMenu("Remove plug-in");
        if (plugins.isEmpty()) removeItemMenu.setEnabled(false);
        fileMenu.add(pluginItem);
        fileMenu.add(removeItemMenu);
       
        runItemMenu = new JMenu("Run simulation");
        runItemMenu.addPropertyChangeListener("enabled", e -> changeEnabledProperty(startButton, e));
        runItemMenu.addPropertyChangeListener("enabled", e -> changeEnabledProperty(pluginSelector, e));
        if (plugins.isEmpty()) runItemMenu.setEnabled(false);
        pauseItem = new JMenuItem("Pause simulation");
        pauseItem.addActionListener(e -> pauseSimulation());
        pauseItem.addPropertyChangeListener("enabled", e -> changeEnabledProperty(pauseButton, e));
        pauseItem.setEnabled(false);
        continueItem = new JMenuItem("Continue simulation");
        continueItem.addActionListener(e -> continueSimulation());
        continueItem.addPropertyChangeListener("enabled", e -> changeEnabledProperty(continueButton, e));
        continueItem.setEnabled(false);
        stopItem = new JMenuItem("Stop simulation");
        stopItem.addActionListener(e -> stopSimulation());
        stopItem.addPropertyChangeListener("enabled", e -> changeEnabledProperty(stopButton, e));
        stopItem.setEnabled(false);
        runMenu.add(runItemMenu);
        runMenu.add(pauseItem);
        runMenu.add(continueItem);
        runMenu.add(stopItem);
        
        frontPageItem = new JMenuItem("Show front page");
        frontPageItem.addActionListener(e -> showFrontPage());
        frontPageItem.setEnabled(false);
        helpMenu.add(frontPageItem);
        
        frame.setJMenuBar(menuBar);
    }
    
    private void showSettings() {
        settings.setLocationRelativeTo(frame);
        settings.setVisible(true);
    }
    
    private void runSimulationWithButton() {
        int index = pluginSelector.getSelectedIndex();
        runSimulation(plugins.get(index));
    }
       
    private void runSimulation(FlockModel flockModel) {
        Parameters params = settings.getSimulationParams();
        steps = params.getInteger(stepsDescriptor);
        int count = params.getInteger(agentsDescriptor);
        String color = params.getString(colorDescriptor);
        String size = params.getString(sizeDescriptor);
        fGraphics.setColor(FlockGraphics.AgentColor.valueForName(color));
        fGraphics.setSize(FlockGraphics.AgentSize.valueForName(size));
        pluginSelector.setSelectedIndex(plugins.indexOf(flockModel));
        Parameters pluginParams = settings.getPluginParams(flockModel);
        try {
            simulation = new Flock(simulationPanel.getHeight(),
                simulationPanel.getWidth(), count, flockModel, pluginParams);
            changeState(State.RUNNING);
        } catch (Exception e) {
            JOptionPane.showMessageDialog
        (frame, "Exeption in plug-in occured\nMessage: "+e.getMessage(), "Exeption", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, "Exeption in plug-in", e);
            changeState(State.STOPPED);
            removePlugin(flockModel);
        }
    }
    
    private void changeEnabledProperty(JComponent component, PropertyChangeEvent e) {
        component.setEnabled((boolean)e.getNewValue());
    }
    
    private void pauseSimulation() {
        changeState(State.PAUSED);
    }
    
    private void continueSimulation() {
        changeState(State.RUNNING);
    }
    
    private void stopSimulation() {
        changeState(State.STOPPED);
    }
    
    private void loadPlugin() {
        Preferences pref = Preferences.userNodeForPackage(getClass());
        String prevPath = pref.get(PLUGIN_PATH, ".");
        fc.addChoosableFileFilter(new FileNameExtensionFilter("JAR file", "jar") );
        fc.setAcceptAllFileFilterUsed(false);
        fc.setSelectedFile(new File(prevPath));
        int returnVal = fc.showOpenDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fc.getSelectedFile();
                pref.put(PLUGIN_PATH, file.getAbsolutePath());
                URL jarPath = file.toURI().toURL();
                URLClassLoader clsLoader = new URLClassLoader(new URL[]{jarPath});
                ServiceLoader<FlockModel> sv = ServiceLoader.load(FlockModel.class, clsLoader);
                if (sv.findFirst().isEmpty()) {
                    JOptionPane.showMessageDialog(frame,
                    "Plug-in does not contain correct FlockModel class", "Invalid plug-in", JOptionPane.ERROR_MESSAGE);

                }
                FlockModel flockModel = sv.findFirst().get();
                plugins.add(flockModel);
                settings.addPlugin(flockModel);
                addPluginToMenu(flockModel);
            } catch (MalformedURLException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void addPluginToMenu(FlockModel flockModel) {
        JMenuItem pluginItem = new JMenuItem(flockModel.getName());
        pluginItem.addActionListener(e -> runSimulation(flockModel));
        runItems.put(flockModel, pluginItem);
        runItemMenu.add(pluginItem);
        runItemMenu.setEnabled(true);
        if (pluginSelector.getItemCount() == 1
            && EMPTY_SELECTOR.equals(pluginSelector.getItemAt(0))) {
            pluginSelector.removeAllItems();
        }
        pluginSelector.addItem(flockModel.getName());
        JMenuItem removeItem = new JMenuItem(flockModel.getName());
        removeItem.addActionListener(e -> removePlugin(flockModel));
        removeItems.put(flockModel, removeItem);
        removeItemMenu.add(removeItem);
        removeItemMenu.setEnabled(true);
    }
    
    private void removePlugin(FlockModel flockModel) {
        int index = plugins.indexOf(flockModel);
        pluginSelector.removeItemAt(index);
        runItemMenu.remove(runItems.get(flockModel));
        removeItemMenu.remove(removeItems.get(flockModel));
        settings.removePlugin(flockModel);
        plugins.remove(index);
        if (plugins.isEmpty()) {
            runItemMenu.setEnabled(false);
            removeItemMenu.setEnabled(false);
            pluginSelector.addItem(EMPTY_SELECTOR);
        }
    }
    
    private void paintSimulation() {
        if (steps-- > 0) {
            assert state == State.RUNNING;
            try {
            List<AgentInfo> agents = simulation.doStep();
            fGraphics.setAgents(agents);
            simulationPanel.repaint();
            } catch (Exception e) {
                JOptionPane.showMessageDialog
        (frame, "Exeption in plug-in occured\nMessage: "+e.getMessage(), "Exeption", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, "Exeption in plug-in", e);
                changeState(State.STOPPED);
                removePlugin(simulation.getFlockModel());
            }
        } else {
            changeState(State.STOPPED);
        }
    }
    
    private void showFrontPage() {
        changeState(State.FRONT_PAGE);
    }
    
    private void changeState(State newState) {
        switch (newState) {
            case RUNNING:
                if (null == state) {throw new IllegalStateException();}
                else switch (state) {
                    case PAUSED:
                        runItemMenu.setEnabled(false);
                        pauseItem.setEnabled(true);
                        continueItem.setEnabled(false);
                        timer.start();
                        break;
                    case STOPPED:
                    case FRONT_PAGE:
                        cardLayout.show(cardsPanel, SIM_PANEL);
                        runItemMenu.setEnabled(false);
                        pauseItem.setEnabled(true);
                        stopItem.setEnabled(true);
                        frontPageItem.setEnabled(false);
                        frame.setResizable(false);
                        timer.start();
                        break;
                    default:
                        throw new IllegalStateException();
                }
                state = State.RUNNING;
                break;

            case PAUSED:
                if (state == State.RUNNING) {
                    timer.stop();
                    pauseItem.setEnabled(false);
                    continueItem.setEnabled(true);
                } else {throw new IllegalStateException();}
                state = State.PAUSED;
                break;
            case STOPPED:
                if (state == State.RUNNING || state == State.PAUSED) {
                    timer.stop();
                    pauseItem.setEnabled(false);
                    continueItem.setEnabled(false);
                    stopItem.setEnabled(false);
                    runItemMenu.setEnabled(true);
                    frontPageItem.setEnabled(true);
                    frame.setResizable(true);
                } else {throw new IllegalStateException();}
                state = State.STOPPED;
                break;
            case FRONT_PAGE:
                cardLayout.show(cardsPanel, FRONT_PAGE_PANEL);
                frontPageItem.setEnabled(false);
                if (!plugins.isEmpty()) {
                    pauseItem.setEnabled(false);
                    continueItem.setEnabled(false);
                    runItemMenu.setEnabled(true);
                }
                frame.setResizable(true);
                state = State.FRONT_PAGE;
                break;
            default:
                throw new AssertionError();
        }
    }
    
    private enum State {
        RUNNING,
        PAUSED,
        STOPPED,
        FRONT_PAGE
    }

}
