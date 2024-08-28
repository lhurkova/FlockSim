/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
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
    private Timer timer;
    
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
    
    private JButton stopButton;
    private JButton pauseButton;
    private JButton continueButton;
    private JButton startButton;
    private JButton settingsButton;
    private JComboBox<String> pluginSelector;
    private static final String EMPTY_SELECTOR = "No plug-in loaded";

    public GUI(int sizeX, int sizeY, int x, String name) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.x = x;
        this.name = name;
    }

    public void createGUI() {
        //System.setProperty("apple.laf.useScreenMenuBar", "true");
        frame = new JFrame(name);
        frame.setLocation(x, 0);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                
        timer = new Timer(100, e -> paintSimulation());
        
        JToolBar toolBar = new JToolBar("Tool Bar");
        
        JPanel buttonMenu = new JPanel();
        buttonMenu.setLayout(new BoxLayout(buttonMenu, BoxLayout.X_AXIS));
                
        settingsButton = new JButton("Settings");
        settingsButton.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
        startButton = new JButton("Start");
        startButton.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
        startButton.addActionListener(e -> runSimulationWithButton());
        pauseButton = new JButton("Pause");
        pauseButton.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
        pauseButton.addActionListener(e -> pauseSimulation());
        continueButton = new JButton("Continue");
        continueButton.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
        continueButton.addActionListener(e -> continueSimulation());
        stopButton = new JButton("Stop");
        stopButton.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
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
        simulationPanel.add(fGraphics);
        simulationPanel.setBackground(Color.WHITE);
//        simulationPanel.add(new JLabel("Simulation panel"));
        JPanel frontPage = new JPanel();
        frontPage.setPreferredSize(new Dimension(sizeX, sizeY));
        JLabel label = new JLabel("Front Page");
        frontPage.add(label);
        
        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);
        cardsPanel.add(frontPage, FRONT_PAGE_PANEL);
        cardsPanel.add(simulationPanel, SIM_PANEL);
        cardLayout.show(cardsPanel, FRONT_PAGE_PANEL);
        
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
        
        JMenuItem aboutItem = new JMenuItem("About");
        JMenuItem settingsItem = new JMenuItem("Settings");
        applicationMenu.add(aboutItem);
        applicationMenu.add(settingsItem);
        
        JMenuItem pluginItem = new JMenuItem("Load plug-in");
        pluginItem.addActionListener(e -> loadPlugin());
        JMenuItem saveItem = new JMenuItem("Save settings");
        JMenuItem loadItem = new JMenuItem("Load settings");
        fileMenu.add(pluginItem);
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
       
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
        helpMenu.add(frontPageItem);
        frontPageItem.addActionListener(e -> showFrontPage());
        
        frame.setJMenuBar(menuBar);
    }
    
    private void runSimulationWithButton() {
        int index = pluginSelector.getSelectedIndex();
        runSimulation(plugins.get(index));
    }
       
    private void runSimulation(FlockModel flockModel) {
        pluginSelector.setSelectedIndex(plugins.indexOf(flockModel));
        simulation = new Flock(sizeY, sizeX, 20, flockModel);
        changeState(State.RUNNING);
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
        JFileChooser fc = new JFileChooser();
        fc.addChoosableFileFilter(new FileNameExtensionFilter("JAR file", "jar") );
        fc.setAcceptAllFileFilterUsed(false);
        int returnVal = fc.showOpenDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fc.getSelectedFile();
                URL jarPath = file.toURI().toURL();
                URLClassLoader clsLoader = new URLClassLoader(new URL[]{jarPath});
                ServiceLoader<FlockModel> sv = ServiceLoader.load(FlockModel.class, clsLoader);
                FlockModel flockModel = sv.findFirst().get();
                plugins.add(flockModel);
                JMenuItem pluginItem = new JMenuItem(flockModel.getName());
                pluginItem.addActionListener(e -> runSimulation(flockModel));
                runItemMenu.add(pluginItem);
                runItemMenu.setEnabled(true);
                if (pluginSelector.getItemCount() == 1
                    && EMPTY_SELECTOR.equals(pluginSelector.getItemAt(0))) {
                    pluginSelector.removeAllItems();
                }
                pluginSelector.addItem(flockModel.getName());
            } catch (MalformedURLException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void paintSimulation() {
        if (steps-- > 0) {
            assert state == State.RUNNING;
            List<AgentInfo> agents = simulation.doStep();
            fGraphics.setAgents(agents);
            simulationPanel.repaint();
        }
    }
    
    public void paintAgents(List<AgentInfo> agents) {
        fGraphics.setAgents(agents);
        frame.repaint();
    }
    
    public void showFrontPage() {
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
                        timer.start();
                        break;
                    case STOPPED:
                    case FRONT_PAGE:
                        cardLayout.show(cardsPanel, SIM_PANEL);
                        runItemMenu.setEnabled(false);
                        pauseItem.setEnabled(true);
                        stopItem.setEnabled(true);
                        frontPageItem.setEnabled(false);
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
                } else {throw new IllegalStateException();}
                state = State.STOPPED;
                break;
            case FRONT_PAGE:
                cardLayout.show(cardsPanel, FRONT_PAGE_PANEL);
                frontPageItem.setEnabled(false);
                pauseItem.setEnabled(false);
                continueItem.setEnabled(false);
                runItemMenu.setEnabled(true);
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
    
    private class StopAction extends AbstractAction {

        public StopAction(String text, ImageIcon icon, String desc) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            changeState(State.STOPPED);
        }
        
    }

}
