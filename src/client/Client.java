package client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Client {
    static int port = 9876;
    static InetAddress IPAddress;
    static JLabel labelOne;
    static JLabel labelTwo;
    static JLabel labelAnswer;
    static DatagramSocket clientSocket;

    public static void main(String[] args) {
        clientSocket = null;
        try {
            clientSocket = new DatagramSocket(); //Creating UDP socket
        } catch (SocketException e1) {
            e1.printStackTrace();
        }

        IPAddress = null;
        try {
            IPAddress = InetAddress.getByName("localhost"); //Creating IP Address
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        }

        //Set window frame for calculator
        JFrame frame = new JFrame();
        frame.setBounds(100, 100, 700, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Calculator Assignment" );
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);

        //Set first label (First Number)
        labelOne = new JLabel("");
        labelOne.setBounds(20, 20, 500, 20);
        labelOne.setFont(new Font("Times", Font.BOLD, 16));
        labelOne.setHorizontalAlignment(SwingConstants.LEFT);
        labelOne.setVerticalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(labelOne);
        labelOne.setText("First Number");

        //First Text box
        JTextField textfieldOne = new JTextField("");
        textfieldOne.setFont(new Font("Times", Font.BOLD, 14));
        textfieldOne.setBounds(170, 20, 100, 20);
        frame.getContentPane().add(textfieldOne);

        //Set second label (Second Number);
        labelTwo = new JLabel("");
        labelTwo.setBounds(20, 60, 500, 20);
        labelTwo.setFont(new Font("Times", Font.BOLD, 16));
        labelTwo.setHorizontalAlignment(SwingConstants.LEFT);
        labelTwo.setVerticalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(labelTwo);
        labelTwo.setText("Second Number");

        //Second Text box
        JTextField textfieldTwo= new JTextField("");
        textfieldTwo.setFont(new Font("Times", Font.BOLD, 14));
        textfieldTwo.setBounds(170, 60, 100, 20);
        frame.getContentPane().add(textfieldTwo);

        //Set answer label
        labelAnswer = new JLabel("");
        labelAnswer.setBounds(20, 100, 500, 20);
        labelAnswer.setFont(new Font("Times", Font.BOLD, 16));
        labelAnswer.setHorizontalAlignment(SwingConstants.LEFT);
        labelAnswer.setVerticalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(labelAnswer);
        labelAnswer.setText("Answer: ");

        //UI buttons set up
        JButton plusButton = new JButton("+");
        plusButton.setBounds(20, 140, 60, 23);
        frame.getContentPane().add(plusButton);

        JButton minusButton = new JButton("-");
        minusButton.setBounds(90, 140, 60, 23);
        frame.getContentPane().add(minusButton);

        JButton multiplyButton = new JButton("x");
        multiplyButton.setBounds(160, 140, 60, 23);
        frame.getContentPane().add(multiplyButton);

        JButton divButton = new JButton("÷");
        divButton.setBounds(230, 140, 60, 23);
        frame.getContentPane().add(divButton);

        JButton maxButton = new JButton("MAX");
        maxButton.setBounds(300, 140, 60, 23);
        frame.getContentPane().add(maxButton);

        JButton minButton = new JButton("MIN");
        minButton.setBounds(370, 140, 60, 23);
        frame.getContentPane().add(minButton);

        //When button is clicked, data is passed to sendMessage function which sends data to Server
        plusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String operator = "+";
                String firstNumber = textfieldOne.getText();
                String secondNumber = textfieldTwo.getText();

                if (sendMessage(operator, firstNumber, secondNumber)) {
                    receiveAnswer();// Result is received and displayed by receivedAnswer function
                }
            }
        });

        minusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String operator = "-";
                String firstNumber = textfieldOne.getText();
                String secondNumber = textfieldTwo.getText();

                if (sendMessage(operator, firstNumber, secondNumber)) {
                    receiveAnswer();
                }
            }
        });

        multiplyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String operator = "x";
                String firstNumber = textfieldOne.getText();
                String secondNumber = textfieldTwo.getText();

                if (sendMessage(operator, firstNumber, secondNumber)) {
                    receiveAnswer();
                }
            }
        });

        divButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String operator = "÷";
                String firstNumber = textfieldOne.getText();
                String secondNumber = textfieldTwo.getText();

                if (sendMessage(operator, firstNumber, secondNumber)) {
                    receiveAnswer();
                }
            }
        });

        maxButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String operator = "MAX";
                String firstNumber = textfieldOne.getText();
                String secondNumber = textfieldTwo.getText();

                if (sendMessage(operator, firstNumber, secondNumber)) {
                    receiveAnswer();
                }
            }
        });

        minButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String operator = "MIN";
                String firstNumber = textfieldOne.getText();
                String secondNumber = textfieldTwo.getText();

                if (sendMessage(operator, firstNumber, secondNumber)) {
                    receiveAnswer();
                }
            }
        });

        frame.setVisible(true);
    }
    private static boolean sendMessage(String operator, String firstNumber, String secondNumber) {
        String sendingSentence = operator + "," + firstNumber + "," + secondNumber; //Creating string to send data to server
        byte[] data = sendingSentence.getBytes(); //data extracted in bytes and put in an array of bytes to send to Server
        DatagramPacket sendingPacket = new DatagramPacket(data, data.length, IPAddress, port); //Creating data packet to send to Server
        try {
            clientSocket.send(sendingPacket);//Sending packet with information to server
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    private static void receiveAnswer() {
        byte[] data = new byte[1024]; //Data received will be read through an array of bytes
        DatagramPacket receivedPacket = new DatagramPacket(data, data.length); //Creating packet to read received data
        try {
            clientSocket.receive(receivedPacket);//Receiving data packet from the server, containing the answer
        } catch (IOException e) {
            e.printStackTrace();
        }
        String receivedSentence = new String(receivedPacket.getData());//Reading data received
        labelAnswer.setText(receivedSentence);//Setting label text to the result calculated by Server
    }
}

