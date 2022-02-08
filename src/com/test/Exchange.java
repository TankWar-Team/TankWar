package com.test;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import org.eclipse.paho.client.mqttv3.MqttClient;

public class Exchange implements ActionListener{
    JFrame f1,f2;
    JPanel p1,p2;
    JButton b1,b2,b3;
    JLabel l1,l2,l3,l4;
    JTextField t1,t2;
    JPasswordField s1,s2;
    MqttClient client;
    Exchange() {
        f1 = new JFrame("µÇÂ½");
        p1 = new JPanel();
        b1 = new JButton("×¢²áÕËºÅ");
        l1 = new JLabel("ÕËºÅ£º");
        t1 = new JTextField(18);
        l2 = new JLabel("ÃÜÂë£º");
        s1 = new JPasswordField(18);
        b3 = new JButton("µÇÂ½");
        f1.add(p1);
        p1.add(l1);
        p1.add(t1);
        p1.add(l2);
        p1.add(s1);
        p1.add(b3);
        p1.add(b1);
        f1.setVisible(true);
        f1.setSize(280,150);
        b1.addActionListener(this);
   
    }
    

    public static void main(String[] args) {
        new Exchange();

    }

    public void actionPerformed(ActionEvent e) {
        f2 = new JFrame("×¢²á");
        p2 = new JPanel();
        b2 = new JButton("È·ÈÏ");
        l3 = new JLabel("ÕËºÅ£º");
        t2 = new JTextField(18);
        l4 = new JLabel("ÃÜÂë£º");
        s2 = new JPasswordField(18);
        f2.add(p2);
        p2.add(l3);
        p2.add(t2);
        p2.add(l4);
        p2.add(s2);
        p2.add(b2);
        f2.setVisible(true);
        f1.setVisible(false);
        f2.setSize(280,150);
    }

}