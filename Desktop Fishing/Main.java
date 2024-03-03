import javax.print.DocFlavor.READER;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Inet4Address;
import java.nio.file.Paths;
import java.security.KeyException;
import java.awt.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Main {
    public static void main(String[] args) {
        Visuals visuals = new Visuals();
    }
}

class Visuals extends JPanel {

    JFrame frame;
    int screenWidth = 500;
    int screenHeight = 300;
    int fishingScreenWidth = 300;
    int fishingScreenHeight = 500;
    int panelXloc;
    int panelYloc;
    String location = "menu";
    int timer;
    int offset = 0;
    ArrayList<UsedBait> usedBaits = new ArrayList<UsedBait>();
    boolean clicking = false;
    int baitSelection;
    ArrayList<JFrame> baitsOnScreen = new ArrayList<JFrame>();

    int currentFrame = 0;


    boolean overPlayButton = false;

    public Visuals() {
        
        Component comp = this;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        panelXloc = (int)screenSize.getWidth()/2 - screenWidth/2;
        panelYloc = (int)screenSize.getHeight()/2 - screenHeight/2;
        frame = new JFrame();		
        frame.setSize (screenWidth, screenHeight + 24);
        frame.setLocation(panelXloc, panelYloc);
        frame.add(comp);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.setVisible (true);
        frame.setResizable(false);
        frame.setTitle("Desktop Fishing!");

        frame.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                //System.out.println(e.getX() + "," + e.getY());
                clicking = true;
                if (overPlayButton) {
                    location = "fishing";
                    panelXloc = (int)screenSize.getWidth() - 300;
                    panelYloc = 50;
                    frame.add(comp);	
                    frame.setSize (fishingScreenWidth, fishingScreenHeight + 24);
                    frame.setLocation(panelXloc, panelYloc);
                    frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
                    frame.setVisible (true);
                    frame.setResizable(false);
                    frame.setTitle("Fishing Kit");
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {      
                clicking = true;
                if (location == "fishing") {
                    UsedBait b = new UsedBait();
                    baitsOnScreen.add(new JFrame());
                    baitSelection = baitsOnScreen.size() - 1;
                    baitsOnScreen.get(baitSelection).setLocation(MouseInfo.getPointerInfo().getLocation().x - 50, MouseInfo.getPointerInfo().getLocation().y - 50);
                    baitsOnScreen.get(baitSelection).setSize(100, 100);
                    baitsOnScreen.get(baitSelection).setUndecorated(true);
                    baitsOnScreen.get(baitSelection).setBackground(new Color(1.0f,1.0f,1.0f,1.0f));
                    baitsOnScreen.get(baitSelection).setVisible (true);
                    baitsOnScreen.get(baitSelection).setResizable(false);
                    baitsOnScreen.get(baitSelection).setTitle("test");
                } 
            }
            @Override
            public void mouseReleased(MouseEvent e) {   
                clicking = false;  
                baitSelection = -1;          
            }
            @Override
            public void mouseEntered(MouseEvent e) { 
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        }); 

        frame.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (clicking) {
                    baitsOnScreen.get(baitSelection).setLocation(MouseInfo.getPointerInfo().getLocation().x - 50, MouseInfo.getPointerInfo().getLocation().y - 50);
                } 
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                if (e.getX() > 207 && e.getX() < 307 && e.getY() > 170 && e.getY() < 209 && location == "menu") overPlayButton = true; 
                else overPlayButton = false;
            }
        });

        while(true) {
            
            timer ++;
            if (timer > 400) timer = 0;
            if (timer % 40 == 0) offset ++;
            if (offset > screenWidth * 2) {
                offset = 0;
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            currentFrame = 0;
            frame.repaint();
            
            for (JFrame j: baitsOnScreen) {
                currentFrame++;
                j.repaint();
            }
        }
    }
    public void paintComponent(Graphics g) {
        
        if (location == "menu") { 
            g.drawImage(FindImage("menu bg 1").getImage(), offset, 0, screenWidth, screenHeight, this);
            g.drawImage(FindImage("menu bg 2").getImage(), offset - screenWidth, 0, screenWidth, screenHeight, this);
            g.drawImage(FindImage("title").getImage(), screenWidth/2 - 180, 20, 360, 120, this);
            if (!overPlayButton) g.drawImage(FindImage("play button").getImage(), screenWidth/2 - 50, screenHeight/2 - 10, 100, 40, this);
            else g.drawImage(FindImage("play button").getImage(), screenWidth/2 - 50 - 2, screenHeight/2 - 10 - 2, 104, 44, this);
        }
        if (baitsOnScreen.size() > 0) { 
            g.fillRect(baitsOnScreen.get(0).getLocation().x, baitsOnScreen.get(0).getLocation().y, 50, 50);
        }
    }

    private ImageIcon FindImage(String filePath) {
        File imageFile = new File("./images/" + filePath + ".png");

        return new ImageIcon(imageFile.toString());
    }
}

class UsedBait extends JPanel {
    public UsedBait() {
        JFrame frame;
        Component comp = this;
        
        frame = new JFrame();		
        frame.setSize (50, 50);
        frame.setLocation(100, 100);
        frame.add(comp);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setVisible (true);
        frame.setResizable(false);
        frame.setTitle("Bait");
    }

    public void paintComponent(Graphics g) {
        g.fillRect(0, 0, 100, 100);
    }
}
