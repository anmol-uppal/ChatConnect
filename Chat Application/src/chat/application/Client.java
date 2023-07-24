package chat.application;

import javax.swing.*; //JFrame is present indide swing package which is further present inside the extended package of java
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;//for the EmptyBorder class
import java.util.*;//for calender class
import java.text.*;//for SimpleDateFormat
import java.net.*;//for serversocket class
import java.io.*; //for input output of msg

public class Client implements ActionListener {

    JTextField text; //globally deaclaring bcz data has to be fetched and used outside the constructor
    static JPanel a1; //declared globally since it has to be used in another function also
    static Box vertical = Box.createVerticalBox(); //messages would be shown in vertical manner
    static DataOutputStream dout;
    static JFrame f = new JFrame();

    Client() {
        f.setLayout(null); //setting layout as null signifies that user is not going to use any of the swing layouts but is going to create their own

        JPanel p1 = new JPanel(); //JPanel helps in the setting of component above the frame
        p1.setBackground(new Color(7, 94, 84)); //objet of Color class is created using 'new' keyword so that default GREEN is not shown
        //numbers for rgb has been provided precisely
        p1.setBounds(0, 0, 450, 70);
        p1.setLayout(null); //it's null so that image can call it's setBounds function
        f.add(p1); //add function is used to add p1 above the frame

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png")); //getting image from the directory
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT); //Image class is present inside the awt package
        ImageIcon i3 = new ImageIcon(i2); //i2 is converted to ImageIcon
        JLabel back = new JLabel(i3); //i2 cannot be directly passed here so i3 that is ImageIcon is passed
        back.setBounds(5, 20, 25, 25);
        p1.add(back); //add function has to be invoked by p1 since we want to add ther image on the panel(p1) and not on the frame

        //here we have to perform action on the clicking of the 'back' arrow
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ae) {
                System.exit(0); //instead of it 'setVisible(false);' can also be used
            }
        });

        //creating profile image 
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/p2.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 10, 50, 50);
        p1.add(profile);

        //creating video call icon
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 30, 30);
        p1.add(video);

        //creating the phone icon
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360, 20, 35, 30);
        p1.add(phone);

        //creating morevert icon
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(420, 20, 10, 25);
        p1.add(morevert);

        //now creating the name of the person1 chatting
        JLabel name = new JLabel("Shreya"); //JLabel used to display text on the panel
        name.setBounds(110, 15, 100, 20);
        name.setForeground(Color.WHITE); //setting font color to white
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18)); //object of class 'Font' takes 3 args type,(bold,underline,italic), and font size
        p1.add(name);

        //for the status of the person1
        JLabel status = new JLabel("Active now"); //JLabel used to display text on the panel
        status.setBounds(110, 35, 100, 10);
        status.setForeground(Color.WHITE); //setting font color to white
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 12)); //object of class 'Font' takes 3 args type,(bold,underline,italic), and font size
        p1.add(status);

        //for tyhe texting area under panel1
        a1 = new JPanel();
        a1.setBounds(5, 75, 440, 570);
        f.add(a1);

        //for creating textfield where the user can type the message
        text = new JTextField();
        text.setBounds(5, 655, 310, 40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(text);

        //creating a 'SEND' button that would sent the message to person2
        JButton send = new JButton("Send");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(new Color(7, 94, 84)); //setting bg color of the button
        send.setForeground(Color.WHITE); //setting olor of text 'Send' on the button
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 17)); //settings for 'Send' font on button
        send.addActionListener(this);
        f.add(send);

        //setSize is the function presnt inside the JFrame class used for setting the length and width of the frame
        f.setSize(450, 700);
        //the frame will be successfully created here but it won't be shown in output as such only successful completion of frame would be shown
        //the visibility of frame is by deafault is HIDDEN

        f.setLocation(800, 50); //this will set new location for the frame which would be 800 from y-axis and 50 from x-axis
        f.setUndecorated(true); //to remove the header of the frame
        f.getContentPane().setBackground(Color.WHITE);

        f.setVisible(true); //this function is used to umhide the frame by setting it's property to true
    }
//overridding the abtract method od JFrame class

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {

            String out = text.getText();

            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout()); //message will be aligned to the right side
            right.add(p2, BorderLayout.LINE_END); //takes panel as arg not string
            vertical.add(right);//multiple meassges will be aligned in vertical manner
            vertical.revalidate();
            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical, BorderLayout.PAGE_START);
            dout.writeUTF(out);

            text.setText(""); //this is done to remove msg once it's sent

            f.repaint(); //thses three funtions are used to rekload the frame oncw the 'send' cick event is performed
            f.invalidate();
            f.validate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //for displaying mesgges in a formatted way
    public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        //JLabel output=new JLabel(out); //it will show non uniform dimensions for all lengths of msg
        JLabel output = new JLabel("<html><p style=\"width:150px\">" + out + "</p></html>");//dimensions of any length of msgs would be same
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50)); //import border package that is inside child package of swing 

        panel.add(output);

        //for time setting under the message
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();//JLabel helps to include anything on the frame
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);

        return panel; //panel for formatting msg has to be returned at the end

    }

    public static void main(String[] args) {
        new Client(); //this is the anonymous object of class

        try {
            Socket s = new Socket("127.0.0.1", 6001);//local host and port 
            DataInputStream din = new DataInputStream(s.getInputStream()); //for getting msgs
            dout = new DataOutputStream(s.getOutputStream()); //for sending msgs. DataOutputStream has een declared globally and statically

            while (true) {
                a1.setLayout(new BorderLayout());
                String msg = din.readUTF();
                JPanel panel = formatLabel(msg); //msg that should be displayed and formattted

                //showing msg on left side of the panel
                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START); //msg to be shown where the line begins

                //vertically aligning the multiple msgs
                vertical.add(left);

                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);

                //after getting t6he msg the frame has to be relaoded
                f.validate();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
