package com.alex.breaktime;

import sun.audio.*;

import javax.swing.JFrame;

import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.DropMode;
import java.awt.Component;

import javax.sound.sampled.*;
import javax.swing.JSlider;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 * Ring-tone source: http://www.tones7.com/
 * 
 * Purpose of this Java Application is to provide the users with the time reminder app.
 * After specific time period the application will play ring-tones to remind 
 * that it is time to take a break from a computer :).
 * 
 * @author Olexandr Matveyev
 * Date: 9/20/2015
 *
 */
public class WindowBreakTime
{

	// GUI Components
	// ---------------------------------------------------------- //
	private Timer timer = null;
	private InputStream in = null;
	private AudioStream audioStream = null;
	private AudioInputStream audioInputStream = null;
	private Clip clip = null;
	private FloatControl gainControl = null;
	
	private JFrame frmBreakTime = null;
	private JComboBox cHours = null;
	private JComboBox cMinutes = null;
	private JComboBox cRingtones = null;
	private JLabel lbTimeLeft = null;
	private JButton bStart = null;
	private JButton bStop = null;
	private JSlider sVolume = null;
	// ---------------------------------------------------------- //
	
	// In these variables will be stored values from drop down boxes
	// ---------------------------------------------------------- //
	private String hSelected = "0";
	private String mSelected = "0";
	private String rSelected = "";
	// ---------------------------------------------------------- //
	
	// Default ring-tone name
	private String fileName = "crazy_chicken.wav";
	
	// Time variables
	// ---------------------------------------------------------- //
	private int hours = 0;
	private int minutes = 0;
	private int seconds = 0;
	private int hoursRemaining = 0;
	private int minutesRemaining = 0;
	private int secondsRemaining = 0;
	private int totalTimeInMinutes = 0;
	private int totalTimeInSeconds = 0;
	// ---------------------------------------------------------- //
	
	// Volume
	private float volumeValue = 0.0F;

	/**
	 * Create application
	 */
	public WindowBreakTime() 
	{
		initialize();
	}

	/**
	 * Initialize the frame 'window'
	 */
	private void initialize() 
	{
		setFrame(new JFrame());
		getFrame().setBounds(100, 100, 340, 285);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Getting back JFrame
	 * @return JFrame
	 */
	public JFrame getFrame() 
	{
		return frmBreakTime;
	}

	/**
	 * Initialize all components of the frame
	 * @param frame
	 */
	public void setFrame(JFrame frame) 
	{
		this.frmBreakTime = frame;
		frmBreakTime.setTitle("Break Time");
		frame.setSize(new Dimension(340, 471));
		frame.setPreferredSize(new Dimension(340, 285));
		frame.setMinimumSize(new Dimension(340, 350));
		frame.setMaximumSize(new Dimension(340, 285));
		frame.getContentPane().setMinimumSize(new Dimension(400, 200));
		frame.getContentPane().setSize(new Dimension(400, 200));
		frame.getContentPane().setPreferredSize(new Dimension(400, 200));
		frame.getContentPane().setMaximumSize(new Dimension(400, 200));
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.getContentPane().setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		frmBreakTime.getContentPane().setLayout(null);
		
		cHours = new JComboBox();
		cHours.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
                // Get the source of the component, which is our combo box.
                JComboBox comboBox = (JComboBox) e.getSource();

                Object selected = comboBox.getSelectedItem();
                hSelected = selected.toString();
			}
		});
		
		cHours.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));
		cHours.setMaximumRowCount(5);
		cHours.setFont(new Font("Tahoma", Font.BOLD, 24));
		cHours.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		cHours.setBounds(22, 110, 55, 55);
		frmBreakTime.getContentPane().add(cHours);
		
		JLabel lbHours = new JLabel("H");
		lbHours.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lbHours.setHorizontalAlignment(SwingConstants.CENTER);
		lbHours.setFont(new Font("Tahoma", Font.BOLD, 35));
		lbHours.setBounds(22, 62, 55, 35);
		frmBreakTime.getContentPane().add(lbHours);
		
		cMinutes = new JComboBox();
		cMinutes.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
                // Get the source of the component, which is our combo box.
                JComboBox comboBox = (JComboBox) e.getSource();

                Object selected = comboBox.getSelectedItem();
                mSelected = selected.toString();
			}
		});
		
		cMinutes.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "34", "35", "36", "37", "38", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"}));
		cMinutes.setMaximumRowCount(5);
		cMinutes.setFont(new Font("Tahoma", Font.BOLD, 24));
		cMinutes.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		cMinutes.setBounds(89, 110, 55, 55);
		frmBreakTime.getContentPane().add(cMinutes);
		
		JLabel lbMinutes = new JLabel("M");
		lbMinutes.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lbMinutes.setHorizontalAlignment(SwingConstants.CENTER);
		lbMinutes.setFont(new Font("Tahoma", Font.BOLD, 35));
		lbMinutes.setBounds(89, 62, 55, 35);
		frmBreakTime.getContentPane().add(lbMinutes);
		
		JLabel lbSetTimeBorder = new JLabel("");
		lbSetTimeBorder.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbSetTimeBorder.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		lbSetTimeBorder.setBounds(12, 52, 142, 120);
		frmBreakTime.getContentPane().add(lbSetTimeBorder);
		
		JLabel lbSetTime = new JLabel("Set Time");
		lbSetTime.setHorizontalAlignment(SwingConstants.CENTER);
		lbSetTime.setFont(new Font("Tahoma", Font.BOLD, 24));
		lbSetTime.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lbSetTime.setBounds(12, 13, 142, 36);
		frmBreakTime.getContentPane().add(lbSetTime);
		
		JLabel lbTimeRemaining = new JLabel("Remaining");
		lbTimeRemaining.setHorizontalAlignment(SwingConstants.CENTER);
		lbTimeRemaining.setFont(new Font("Tahoma", Font.BOLD, 24));
		lbTimeRemaining.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lbTimeRemaining.setBounds(156, 13, 150, 36);
		frmBreakTime.getContentPane().add(lbTimeRemaining);
		
		lbTimeLeft = new JLabel("00:00:00");
		lbTimeLeft.setHorizontalAlignment(SwingConstants.CENTER);
		lbTimeLeft.setFont(new Font("Tahoma", Font.BOLD, 21));
		lbTimeLeft.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lbTimeLeft.setBounds(166, 62, 129, 25);
		frmBreakTime.getContentPane().add(lbTimeLeft);
		
		bStop = new JButton("Stop");
		bStop.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				stop();
			}
		});
		
		bStop.setFont(new Font("Tahoma", Font.BOLD, 15));
		bStop.setBounds(166, 140, 129, 25);
		bStop.setEnabled(false);
		frmBreakTime.getContentPane().add(bStop);
		
		cRingtones = new JComboBox();
		cRingtones.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
                // Get the source of the component, which is our combo box.
                JComboBox comboBox = (JComboBox) e.getSource();

                Object selected = comboBox.getSelectedItem();
                rSelected = selected.toString();
                
                rSelected = rSelected.replace(" ", "_");
                fileName = rSelected.toLowerCase() + ".wav";
			}
		});
		cRingtones.setModel(new DefaultComboBoxModel(new String[] {"Crazy Chicken", "Hallowee Theme", "Satisfaction", "Rising Sun", "Tsunami"}));
		cRingtones.setMaximumRowCount(5);
		cRingtones.setFont(new Font("Tahoma", Font.BOLD, 20));
		cRingtones.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		cRingtones.setBounds(22, 185, 273, 35);
		frmBreakTime.getContentPane().add(cRingtones);
		
		bStart = new JButton("Start");
		bStart.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				start();
			}
		});
		
		bStart.setFont(new Font("Tahoma", Font.BOLD, 15));
		bStart.setBounds(166, 110, 129, 25);
		frmBreakTime.getContentPane().add(bStart);
		
		JLabel label_1 = new JLabel("");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		label_1.setBounds(156, 52, 150, 45);
		frmBreakTime.getContentPane().add(label_1);
		
		JLabel label = new JLabel("");
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		label.setBounds(12, 178, 294, 50);
		frmBreakTime.getContentPane().add(label);
		
		JLabel label_2 = new JLabel("");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		label_2.setBounds(156, 100, 150, 72);
		frmBreakTime.getContentPane().add(label_2);
		
		sVolume = new JSlider();
		sVolume.setMinimum(-70);
		sVolume.setMaximum(0);
		sVolume.setValue(-45);
		sVolume.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e) 
			{
				volumeValue = (float) sVolume.getValue();
				soundLevel("State Changed --- " + volumeValue);
			}
		});
		sVolume.setMinorTickSpacing(10);
		sVolume.setBounds(12, 264, 294, 26);
		frmBreakTime.getContentPane().add(sVolume);
		
		JLabel lblNewLabel = new JLabel("Volume Level");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel.setBounds(12, 233, 294, 25);
		frmBreakTime.getContentPane().add(lblNewLabel);
	}
	
	/**
	 * Control volume level
	 * @param str
	 */
	public void soundLevel(String str)
	{
		System.out.println("Slider: " + str);
		
		try
		{
	    	gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	    	
	    	// Reduce volume by volumeValue decibels.
	    	gainControl.setValue(volumeValue);	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	

	/**
	 * Start counting time
	 */
	public void start()
	{	
		// try to convert String values into Integer
		try
		{
			hours = Integer.parseInt(hSelected);
			minutes = Integer.parseInt(mSelected);
			totalTimeInSeconds = minutes * 60;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		lbTimeLeft.setText(String.format("%s : %s : %s", hours, minutes, seconds));
		
		// If hours more than 0 then, convert it to minutes
		// else save only minutes
		if(hours > 0)
		{
			totalTimeInMinutes = hours * 60 + minutes;
		}
		else if(hours == 0 && minutes > 0)
		{
			totalTimeInMinutes = minutes;
		}
		
		// If hours or minutes more than 0 start Timer
		if(hours > 0 || minutes > 0)
		{
			setRemainingTime();
			
			// run timer every second or minute 
			// { in 1 second is 1000 milliseconds}
			
			// every minute
			//timer = new Timer(60 * 1000, new Listener());
			
			// every second
			timer = new Timer(1000, new Listener());
			timer.start();

			// Enable or disable some buttons
			bStart.setEnabled(false);
			bStop.setEnabled(true);
			cRingtones.setEnabled(false);
			
			// Stop playing ring-tone
			stopRingtone();
		}
	}
	
	// This inner class is used to perform time counting every minute
	private class Listener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			// discriminating second
			seconds--;
			
			setRemainingTime();
		}
	}
	
	/**
	 * If Stop button pressed Stop time counting
	 */
	public void stop()
	{
		// reset some fields
		hours = 0;
		minutes = 0;
		seconds = 0;
		lbTimeLeft.setText(String.format("00 : 00 : 00"));
		lbTimeLeft.revalidate();
		
		//Stop timer
		timer.stop();
		
		// Stop ring-tone
		stopRingtone();
		
		// Set enable buttons
		bStart.setEnabled(true);
		bStop.setEnabled(false);
		cRingtones.setEnabled(true);
	}
	
	/**
	 * If time is out Stop time counting
	 */
	public void timeOut()
	{
		// reset some fields
		hours = 0;
		minutes = 0;
		seconds = 0;
		lbTimeLeft.setText(String.format("00 : 00 : 00"));
		lbTimeLeft.revalidate();
		
		//Stop timer
		timer.stop();
		
		// Stop ring-tone
		playRingtone();
		
		// Set enable buttons
		bStart.setEnabled(false);
		bStop.setEnabled(true);
	}
	
	/**
	 * Set time remaining
	 */
	public void setRemainingTime()
	{	
		// Convert minutes into hours and convert each hour into minutes
		if(totalTimeInMinutes > 0)
		{
			hoursRemaining = totalTimeInMinutes / 60;
			minutesRemaining = totalTimeInMinutes - hoursRemaining * 60;
		}
		
		// just set minutesRemaining into 0
		if(totalTimeInMinutes == 0)
		{
			minutesRemaining = 0;
		}
		
		// seconds discriminating in 'private class Listener implements ActionListener'
		if (seconds == 0)
		{
			// Subtract from total minutes 1 minute
			totalTimeInMinutes--;	
			
			// after minutes were discriminated we have to reset seconds count
			seconds = 60;
		}
		
		// Set hours and minutes on TimeLeft label
		lbTimeLeft.setText(String.format("%s : %s : %s", hoursRemaining, minutesRemaining, seconds));
		
		// every time revalidate TimeLeft label
		lbTimeLeft.revalidate();
		
		if(totalTimeInMinutes < 0)
		{
			timeOut();
		}
	}
	
	/**
	 * Play Ring-tone
	 */
	public void playRingtone()
	{	
	    // Try to play sound-track
	    try 
	    {
	    	audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource(fileName));
	    	
	    	clip = AudioSystem.getClip();
	    	clip.open(audioInputStream);
	    	gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	    	
	    	// Reduce volume by 0.1F decibels. 	//-70.0F
	    	gainControl.setValue((float) sVolume.getValue());
	    	clip.start();
	    	
	    	// loop music
	    	clip.addLineListener(new LineListener() 
	    	{	
				@Override
				public void update(LineEvent event) 
				{
					if (event.getFramePosition() == clip.getFrameLength())
					{
						//stop();
						playRingtone();
					}
				}
			});
	    } 
	    catch(Exception ex) 
	    {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}
	
	/**
	 * Stop Ring-tone
	 */
	public void stopRingtone()
	{
	    try 
	    {
	    	clip.stop();
	    } 
	    catch(Exception ex) 
	    {
	        System.out.println("Error with stoping sound.");
	        ex.printStackTrace();
	    }
	}
}