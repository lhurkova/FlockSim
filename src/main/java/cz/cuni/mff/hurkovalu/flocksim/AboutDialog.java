/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.cuni.mff.hurkovalu.flocksim;

import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Lucie Hurkova <hurkova.lucie@email.cz>
 */
public class AboutDialog extends JDialog {

    public AboutDialog(Frame owner) {
        super(owner, "About FlockSim", true);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        URL resource = getClass().getResource("icon.png");
        Image image = Toolkit.getDefaultToolkit().getImage(resource);
        ImageIcon icon = new ImageIcon(image.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        JLabel label = new JLabel();
        label.setIcon(icon);
        JPanel namePanel = new JPanel(new GridBagLayout());
        JLabel name = new JLabel("FlockSim");
        Font f = name.getFont();
        Font newFont = new Font(f.getFontName(), Font.BOLD, f.getSize()+2);
        name.setFont(newFont);
        namePanel.add(name);
        JPanel versionPanel = new JPanel(new GridBagLayout());
        JLabel version = new JLabel("Version: 1.0");
        versionPanel.add(version);
        panel.add(label);
        panel.add(namePanel);
        panel.add(versionPanel);
        getContentPane().add(panel);
        setSize(200, 200);
        setResizable(false);
    }


}
