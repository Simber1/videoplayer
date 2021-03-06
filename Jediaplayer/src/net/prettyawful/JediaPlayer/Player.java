package net.prettyawful.JediaPlayer;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Scanner;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class Player  {

	private Boolean DEBUG = true;
	
	private JFrame frame;
	private static String song;
	private MediaPlayer mediaPlayer;
	private Media hit;
	private int makeframemaybe;
	
	




	public Player() {
		if(makeframemaybe == 0) {
			makeFrame();
			makeframemaybe++;
		}
		
		
		@SuppressWarnings("unused")
		final JFXPanel fxPanel = new JFXPanel(); 
			
		song = Player.songSelect().toString();
		hit = new Media(new File(song).toURI().toString());  //Changing song into a form that Media can use
		mediaPlayer = new MediaPlayer(hit);
	}


	public static void main(String[] args) {
		Player player = new Player();

		@SuppressWarnings("unused")
		final JFXPanel fxPanel = new JFXPanel();  //Makes JavaFX not die https://stackoverflow.com/questions/14025718/javafx-toolkit-not-initialized-when-trying-to-play-an-mp3-file-through-mediap/43277386#43277386
		
		
		System.out.println("selecting");
		
//		Media hit = new Media(new File(song).toURI().toString());  //Changing song into a form that Media can use
//		MediaPlayer mediaPlayer = new MediaPlayer(hit);  //Starting media player with track
		
		player.mediaPlayer.setVolume(0.05);
		
		player.mediaPlayer.setAutoPlay(true);








	}




	//Function for selecting a song
	public static File songSelect() {
		final JFileChooser fc = new JFileChooser();  //starting Java File chooser    TO REPLACE
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);  //Setting file chooser to files only
		int returnVal = fc.showOpenDialog(fc);  //Opening file chooser, returns 0 on success and 1 on fail

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File song = fc.getSelectedFile();
			System.out.println("Opening: " + song.getName() + ".");
			return song;
		} else {
			System.out.println("Canceled by user");
			return null;
		}
	}


	//Function for setting the volume
	public static double volume() {
		Scanner scan = new Scanner(System.in); //Scanner for volume controls

		System.out.print("Set volume in %:");
		int volumeIn = scan.nextInt(); //taking next int for volume controls

		double volume = volumeIn/100.0;  //changing to 0.xx instead of xx%

		scan.close();
		return volume;
	}

	
	
	
	
	private void quit() {
		System.exit(0);
		
	}
	
	private void start() {
		if(mediaPlayer.getStatus()!=MediaPlayer.Status.PLAYING) {
				mediaPlayer.play();  //Playing media
				
		}else {
				System.out.println("Song is already playing, please stop it before starting another song");
		}
		if(DEBUG == true) {
			System.out.println("starting");
		}
	}
	
	private void pause() {
		if(DEBUG == true) {
			System.out.println(mediaPlayer.getStatus());
		}
		mediaPlayer.pause();
		
		if(DEBUG == true) {
			System.out.println("Pausing");
			System.out.println(mediaPlayer.getStatus());
		}
	}
	
	private void stop() {
		
		if(DEBUG == true) {
			System.out.println(mediaPlayer.getStatus());
		}
		mediaPlayer.stop();
		
		if(DEBUG == true) {
			System.out.println("stopping");
			System.out.println(mediaPlayer.getStatus());
		}
	}
	
	private void select() {
		String oldsong = song;
		song = songSelect().toString();
		
		if(song == null) {
			song = oldsong;
		}
		
		if(DEBUG == true) {
			System.out.println(song);
		}
	}


    private void makeFrame()
    {
        frame = new JFrame("Jedia Player");
        JPanel contentPane = (JPanel)frame.getContentPane();
        contentPane.setBorder(new EmptyBorder(1, 60, 1, 60));

        makeMenuBar(frame);
        
        // Specify the layout manager with nice spacing
        contentPane.setLayout(new BorderLayout(12, 12));
        
        // Create the image pane in the center

      //  Font displayFont = label.getFont().deriveFont(96.0f);
       // label.setFont(displayFont);
        //imagePanel.setBorder(new EtchedBorder());
        //contentPane.add(label, BorderLayout.CENTER);

        // Create the toolbar with the buttons
        JPanel toolbar = new JPanel();
        toolbar.setLayout(new GridLayout(1, 0));
        
        JButton startButton = new JButton("Play");
        startButton.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { start(); }
                           });
        toolbar.add(startButton);
        
        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { pause(); }
                           });
        toolbar.add(pauseButton);
        
        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { stop(); }
                           });
        toolbar.add(stopButton);
        
        JButton selectButton = new JButton("Song select");
        selectButton.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { select(); }
                           });
        toolbar.add(selectButton);



        // Add toolbar into panel with flow layout for spacing
        JPanel flow = new JPanel();
        flow.add(toolbar);
        
        contentPane.add(flow, BorderLayout.SOUTH);
        
        // building is done - arrange the components      
        frame.pack();
        
        // place the frame at the center of the screen and show
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(d.width/2 - frame.getWidth()/2, d.height/2 - frame.getHeight()/2);
        frame.setVisible(true);
    }
    
    /**
     * Create the main frame's menu bar.
     * 
     * @param frame   The frame that the menu bar should be added to.
     */
    private void makeMenuBar(JFrame frame)
    {
        final int SHORTCUT_MASK =
            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        
        JMenu menu;
        JMenuItem item;
        
        // create the File menu
        menu = new JMenu("File");
        menubar.add(menu);
        
        item = new JMenuItem("About Clock...");
            item.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { quit(); }
                           });
        menu.add(item);

        menu.addSeparator();
        
        item = new JMenuItem("Quit");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
            item.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { quit(); }
                           });
        menu.add(item);
    }
}
