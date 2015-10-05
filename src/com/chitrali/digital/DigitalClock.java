package com.chitrali.digital;

import java.awt.*;

import javax.swing.*;
import javax.swing.Timer;

import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.io.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

@SuppressWarnings("serial")
public class DigitalClock extends JPanel {
	static int hour,minute,second,alarmHour,alarmMinute;
	static String textFieldHour,textFieldMinute,comboBoxPeriod,alarmPeriod,clockPeriod;
	static boolean alarmSet=false;
	static JPanel clockPanel=new JPanel();
    static JPanel alarmPanel=new JPanel();
    static JTextField hourField=new JTextField(2);;
	static	JTextField  minuteField = new JTextField(2);
	static JComboBox<String> periodBox = new JComboBox<String>();
	static JLabel alarmLabel = new JLabel("",JLabel.CENTER);
	static JLabel colonLabel = new JLabel(":");
	static JButton clearAlarmButton=new JButton("STOP ALARM");
	static JButton alarmButton = new JButton(new ImageIcon("alarm.jpeg"));
	static JButton setAlarmButton=new JButton("SET ALARM");
	 
	/*
	 * Constructor
	 */
	public DigitalClock(){
		setPreferredSize(new Dimension(370,200));
		repeat();
	}
	
	public static void main(String[] args) {
		//Initializing clock frame and clock panel
		JFrame clock= new JFrame("Digital Clock");
		clock.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		clock.setLayout(new GridLayout(2,1));
		clock.setBackground(Color.BLACK);
		clock.setSize(300, 300); 
		clockPanel.setBackground(Color.BLACK);
		clockPanel.add(new DigitalClock());
		clock.add(clockPanel);
		//Populating period box with am/pm
		periodBox.addItem("am");
        periodBox.addItem("pm");  
        //Set element visibility
        hourField.setVisible(false);
        minuteField.setVisible(false);
        colonLabel.setVisible(false);
        setAlarmButton.setVisible(false);
        periodBox.setVisible(false);
        clearAlarmButton.setVisible(false);
		alarmLabel.setVisible(false);		
		//Initializing alarm panel and adding it to clock frame
		alarmPanel.setBackground(Color.BLACK);
		alarmPanel.add(alarmButton);
		alarmPanel.add(alarmLabel);
		alarmPanel.add(hourField);
		alarmPanel.add(colonLabel);
		alarmPanel.add(minuteField);
		alarmPanel.add(periodBox);
		alarmPanel.add(setAlarmButton);
		alarmPanel.add(clearAlarmButton);	
		clock.add(alarmPanel);
		clock.getContentPane();
		clock.pack();
		clock.setVisible(true);	
		//Adding action event on alarm button
		alarmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                alarmButton.setVisible(false);
            	hourField.setVisible(true);
                minuteField.setVisible(true);
                colonLabel.setVisible(true);
                setAlarmButton.setVisible(true);
                periodBox.setVisible(true);
            }
        });
		//Adding action event to set alarm button
		setAlarmButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Fetching values from alarm fields
				textFieldHour= new String(hourField.getText());
				textFieldMinute=new String(minuteField.getText());
				comboBoxPeriod=String.valueOf(periodBox.getSelectedItem());
				alarmHour=Integer.parseInt(textFieldHour);
				alarmMinute=Integer.parseInt(textFieldMinute);
				alarmSet=true;
				hourField.setVisible(false);
                minuteField.setVisible(false);
                colonLabel.setVisible(false);
                setAlarmButton.setVisible(false);
                periodBox.setVisible(false);
                alarmButton.setVisible(false);	
                //Alarm label presented
				alarmLabel.setVisible(true);
				alarmLabel.setText("Alarm is set for "+alarmHour+":"+alarmMinute+" "+comboBoxPeriod);
				alarmLabel.setOpaque(true);
				alarmLabel.setForeground(Color.WHITE);
				alarmLabel.setBackground(Color.BLACK);
				alarmLabel.setFont(new Font("Comic Sans Ms", Font.BOLD, 20));
			}
		});
	}
	/*
	 * Invokes alarm when condition is met
	 */
	static public void invokealarm(){
		if(hour == alarmHour && minute == alarmMinute && clockPeriod.equals(comboBoxPeriod)){
            hourField.setVisible(false);
            minuteField.setVisible(false);
            colonLabel.setVisible(false);
            setAlarmButton.setVisible(false);
            periodBox.setVisible(false);
            alarmLabel.setVisible(false);
            clearAlarmButton.setVisible(true);
            alarmButton.setVisible(false);
			try{
				//Fetching audio file and playing alarm audio
            	File file=new File("sound.wav");
            	AudioInputStream audioInputStream=AudioSystem.getAudioInputStream(file);
            	Clip clip=AudioSystem.getClip();
            	clip.open(audioInputStream);
            	clip.start();
            	//Changing color of alarm panel with random colors
            	Color d = new Color((float) Math.random(), (float) Math.random(),(float) Math.random());
                alarmPanel.setBackground(d);
                //Add action event to clear alarm button
                clearAlarmButton.addActionListener(new ActionListener(){
        			public void actionPerformed(ActionEvent e){
        				clip.stop();
        				// Reverting to initial alarm panel
        				alarmPanel.setBackground(Color.BLACK);
        				hourField.setText("");
                        minuteField.setText("");
                        periodBox.setSelectedItem("");
                        alarmSet=false;
                        alarmButton.setVisible(true);
                        alarmLabel.setVisible(false);
                        hourField.setVisible(false);
                        minuteField.setVisible(false);
                        colonLabel.setVisible(false);
                        setAlarmButton.setVisible(false);
                        periodBox.setVisible(false);
                        alarmLabel.setVisible(false);
                        clearAlarmButton.setVisible(false);
        			}
        		});
			}catch(Exception e){
            	System.out.println(e);
            }
        }
	}
	/*
	 * Painting alarm panel
	 */
	public void paintComponent(Graphics g)
    {   
        g.setColor(Color.BLACK);
        g.fillRect(0, 0,getWidth(),getHeight());
        g.setColor(Color.green);
        Calendar time = Calendar.getInstance();
        hour = time.get(Calendar.HOUR_OF_DAY);
        minute = time.get(Calendar.MINUTE);
        second = time.get(Calendar.SECOND);
        //Setting to 12 hour clock standard
        if(hour<12) clockPeriod="am";
        else clockPeriod="pm";
        if(hour>12) hour-=12;
        else if(hour==0) hour=12;
        //Printing digital time
        g.setFont (new Font("DS-DIGITAL", Font.BOLD, 50));
        g.drawString((hour==0)?"00":convertToDigitalTime(hour)+":"+convertToDigitalTime(minute)+":"+convertToDigitalTime(second)+" "+clockPeriod , 60, 100);
        //Printing present date, day and year
        Date date=new Date();
        SimpleDateFormat sdate=new SimpleDateFormat("EEEE MMMM d, YYYY");
        g.setFont (new Font("Comic Sans Ms", Font.BOLD, 20));
        g.drawString(sdate.format(date), 60, 40);
        //Printing in Roman Numerals
        g.setFont (new Font("DS-DIGITAL", Font.BOLD, 50));
        g.drawString(IntegerToRoman(hour) + ":" + IntegerToRoman(minute) + ":" + IntegerToRoman(second), 60, 150);
    }
	/*
	 * Converts to digital time with 0 prepended in case of single digits
	 */
	static String convertToDigitalTime(int num)
	{
		return ("" + num/10 + num%10);
	}
	
	/*
	 * Re paints the panel every second.Checks for alarm and quarters
	 */
	public void repeat(){
		ActionListener action = new ActionListener() {
            public void actionPerformed(ActionEvent evt) 
            {
                repaint();
                checkForQuarter();
                if(alarmSet)
                	invokealarm();
            }
        };
        new Timer(1000, action).start();
	}
	/*
	 * Invoke quarter event alarm
	 */
	static void checkForQuarter(){
	if(minute==0||minute==15||minute==30||minute==45){
		try{
			File file=new File("sound.wav");
	    	AudioInputStream audioInputStream=AudioSystem.getAudioInputStream(file);
	    	Clip clip=AudioSystem.getClip();
	    	clip.open(audioInputStream);
	    	clip.start();
		}catch( Exception e){
			System.out.println(e);
		}
	  }
	}
	/*
	 * Calculates and returns the corresponding Roman equivalent
	 */
	static String IntegerToRoman(int number){
		String romanResult="";
		int standards[]={1000,900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
		String romanSymbols[]={"M","CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
		int frequency;
		frequency=number/1;
		for(int x=0; number>0; x++){
			frequency=number/standards[x];
			for(int i=1; i<=frequency; i++){
				romanResult=romanResult + romanSymbols[x];
			}
			number=number%standards[x];
		}
		return romanResult;
	}
}
