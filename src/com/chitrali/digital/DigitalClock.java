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

public class DigitalClock extends JPanel {
	 static int hour,minute,second;
	 static int alarmhour,alarmminute;
	 static String alarmampm;
	 static String txthour,txtmin,ampm1;
	 static boolean alarm=false;
	 static String ampm;
	 static JPanel p1=new JPanel();
     static JPanel p2=new JPanel();
     static JPanel p3=new JPanel();
     static JTextField hourField=new JTextField(2);;
	 static	JTextField  minuteField = new JTextField(2);
	 static JComboBox amPmBox = new JComboBox();
	 static JLabel a1 = new JLabel("",JLabel.CENTER);
	 static JButton clearalarm=new JButton("STOP ALARM");
	 static JButton button = new JButton(new ImageIcon("alarm.jpeg"));
	 static JLabel colon = new JLabel(":");
	 static JButton setalarm=new JButton("SET ALARM");
	

	public static void main(String[] args) {
		
		JFrame clock= new JFrame("Digital Clock");
         amPmBox.addItem("am");
         amPmBox.addItem("pm");        
         clearalarm.setVisible(false);
		a1.setVisible(false);
		clock.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		clock.setLayout(new GridLayout(3,1));
		clock.setSize(400, 400);  	
		p1.add(new DigitalClock());
		clock.add(p1);
			
		p2.setBackground(Color.BLACK);
		p2.add(button);
		p2.add(a1);
		p3.setBackground(Color.BLACK);
		p3.add(hourField);
		p3.add(colon);
		p3.add(minuteField);
		p3.add(amPmBox);
		p3.add(setalarm);
		p3.add(clearalarm);
		p3.setVisible(false);	
		clock.add(p2);
		clock.add(p3);
		clock.getContentPane();
		clock.pack();
		clock.setVisible(true);	
		
		
		button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                p2.setVisible(false);
            	p3.setVisible(true);
            	hourField.setVisible(true);
                minuteField.setVisible(true);
                colon.setVisible(true);
                setalarm.setVisible(true);
                amPmBox.setVisible(true);
            	
            }
        });
		
		setalarm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				txthour= new String(hourField.getText());
				txtmin=new String(minuteField.getText());
				ampm1=String.valueOf(amPmBox.getSelectedItem());
				alarmhour=Integer.parseInt(txthour);
				alarmminute=Integer.parseInt(txtmin);
				alarm=true;
				p3.setVisible(false);
				p2.setVisible(true);
				button.setVisible(false);
				a1.setVisible(true);
				a1.setText("Alarm is set at "+alarmhour+":"+alarmminute+" "+ampm1);
				a1.setOpaque(true);
				a1.setForeground(Color.WHITE);
				a1.setBackground(Color.BLACK);
				a1.setFont(new Font("Comic Sans Ms", Font.BOLD, 20));
			}
		});
	}
	
	static public void invokealarm(){
		if(hour == alarmhour && minute == alarmminute && ampm.equals(ampm1)){
            p2.setVisible(false);
            p3.setVisible(true);
            hourField.setVisible(false);
            minuteField.setVisible(false);
            colon.setVisible(false);
            setalarm.setVisible(false);
            amPmBox.setVisible(false);
            clearalarm.setVisible(true);
			try{
            	File file=new File("sound.wav");
            	AudioInputStream audioInputStream=AudioSystem.getAudioInputStream(file);
            	Clip clip=AudioSystem.getClip();
            	clip.open(audioInputStream);
            	clip.start();
            
            	Color d = new Color((float) Math.random(), (float) Math.random(),(float) Math.random());
            
                p3.setBackground(d);
                
                clearalarm.addActionListener(new ActionListener(){
        			public void actionPerformed(ActionEvent e){
        				clip.stop();
        				p2.setBackground(Color.BLACK);
        				p2.setVisible(true);
        				button.setVisible(true);
        				a1.setVisible(false);
        				hourField.setText("");
                        minuteField.setText("");
                        amPmBox.setSelectedItem("");
                        alarm=false;
                        p3.setVisible(false);
                        clearalarm.setVisible(false);
                        
        			}
        		});
			}catch(Exception e){
            	System.out.println(e);
            }
                
                
                
        }
		
	}
	public DigitalClock(){
		setPreferredSize(new Dimension(370,200));
		repeat();
	}
	public void paintComponent(Graphics g)
    {   
        g.setColor(Color.BLACK);
       
        
        g.fillRect(0, 0,getWidth(),getHeight());
        g.setColor(Color.green);
        
        
        Calendar time = Calendar.getInstance();
         hour = time.get(Calendar.HOUR_OF_DAY);
         minute = time.get(Calendar.MINUTE);
         second = time.get(Calendar.SECOND);
        
        if(hour<12)
        	ampm="am";
        else ampm="pm";
        
        if(hour>12)
        	hour-=12;
        else if(hour==0)
        	hour=12;
        	
        
        g.setFont (new Font("DS-DIGITAL", Font.BOLD, 50));
        g.drawString((hour==0)?"00":hour+":"+minute/10+minute%10+":"+second/10+second%10+" "+ampm , 60, 60);
        Date date=new Date();
        SimpleDateFormat sdate=new SimpleDateFormat("EEEE  MMMM d, YYYY");
        g.setFont (new Font("Comic Sans Ms", Font.BOLD, 20));
        g.drawString(sdate.format(date), 60, 100);
        g.setFont (new Font("DS-DIGITAL", Font.BOLD, 50));
        g.drawString(IntegerToRoman(hour) + ":" + IntegerToRoman(minute) + ":" + IntegerToRoman(second) + " " + ampm, 60, 150);
        
    }
	
	public void repeat(){
		ActionListener action = new ActionListener() {
            public void actionPerformed(ActionEvent evt) 
            {
                repaint();
                checkForQuarter();
                if(alarm)
                	invokealarm();
            }
        };
        new Timer(1000, action).start();
	}

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
	public static String IntegerToRoman(int n){
		String roman="";
		int repeat;
		int magnitude[]={1000,900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
		String symbol[]={"M","CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
		repeat=n/1;
		for(int x=0; n>0; x++){
		repeat=n/magnitude[x];
		for(int i=1; i<=repeat; i++){
		roman=roman + symbol[x];
		}
			n=n%magnitude[x];
		}
		return roman;
	}
	
	
}
