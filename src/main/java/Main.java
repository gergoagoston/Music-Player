import com.mongodb.*;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import javazoom.jl.player.Player;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Vector;

public class Main implements ActionListener {

    JFrame frame;
    JLabel songName;
    JButton select,addSong;
    JPanel playerPanel, controlPanel;
    Icon iconPlay, iconPause, iconResume, iconStop;
    JButton play, pause, resume, stop;

    List<String> categories = new ArrayList<String>();

    JFileChooser fileChooser;
    FileInputStream fileInputStream;
    BufferedInputStream bufferedInputStream;
    File myFile = null;
    String filename, filePath;
    long totalLength, pauseLength;
    Player player;
    Thread playThread, resumeThread;

    private JList list;
    private JTextArea jTextArea1;
    private JScrollPane scrollpane;


    public Main(List<String> a) {
        categories = a;
        initUI();
        addActionEvents();
        playThread = new Thread(runnablePlay);
        resumeThread = new Thread(runnableResume);



    }

    public void initUI() {

        songName = new JLabel("", SwingConstants.CENTER);

        select = new JButton("Select Mp3");
        addSong = new JButton("Add Song");


        playerPanel = new JPanel(); //Music Selection Panel
        controlPanel = new JPanel(); //Control Selection Panel

        ImageIcon iconPlay = new ImageIcon("C:\\Users\\Koppany\\Egyetem\\Java\\NRDBproject\\pictures\\1.jpeg");
        Image image = iconPlay.getImage();
        Image newimg = image.getScaledInstance(30,30,java.awt.Image.SCALE_SMOOTH);
        iconPlay = new ImageIcon(newimg);

        ImageIcon iconPause = new ImageIcon("C:\\Users\\Koppany\\Egyetem\\Java\\NRDBproject\\pictures\\2.jpeg");
        Image image1 = iconPause.getImage();
        Image newimg1 = image1.getScaledInstance(30,30,java.awt.Image.SCALE_SMOOTH);
        iconPause = new ImageIcon(newimg1);

        ImageIcon iconResume = new ImageIcon("C:\\Users\\Koppany\\Egyetem\\Java\\NRDBproject\\pictures\\3.png");
        Image image2 = iconResume.getImage();
        Image newimg2 = image2.getScaledInstance(30,30,java.awt.Image.SCALE_SMOOTH);
        iconResume = new ImageIcon(newimg2);

        ImageIcon iconStop = new ImageIcon("C:\\Users\\Koppany\\Egyetem\\Java\\NRDBproject\\pictures\\4.jpeg");
        Image image3 = iconStop.getImage();
        Image newimg3 = image3.getScaledInstance(30,30,java.awt.Image.SCALE_SMOOTH);
        iconStop = new ImageIcon(newimg3);

        play = new JButton(iconPlay);
        pause = new JButton(iconPause);
        resume = new JButton(iconResume);
        stop = new JButton(iconStop);

        playerPanel.setLayout(new GridLayout(3, 1));

        playerPanel.add(select);
        playerPanel.add(addSong);
        playerPanel.add(songName);
        //playerPanel.add(scrollpane);

        controlPanel.setLayout(new GridLayout(1, 4));

        controlPanel.add(play);
        controlPanel.add(pause);
        controlPanel.add(resume);
        controlPanel.add(stop);

        play.setBackground(Color.WHITE);
        pause.setBackground(Color.WHITE);
        resume.setBackground(Color.WHITE);
        stop.setBackground(Color.WHITE);

        frame = new JFrame();

        //Lista elemek


        JList list = new JList(categories.toArray());

        //list = new JList((Vector) categories);
        scrollpane = new JScrollPane(list);

        frame.setTitle("Music Player");

        frame.setBackground(Color.white);
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.add(scrollpane,BorderLayout.CENTER);

        //frame.getContentPane().setLayout(new FlowLayout());

        frame.add(playerPanel, BorderLayout.NORTH);
        frame.add(controlPanel, BorderLayout.SOUTH);



        //Lisa elemek


        list.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });


    }

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {
        //set text on right here
        String s = (String)list.getSelectedValue();
        if (s.equals("Household")) {
            System.out.println("Household");
        }
        if (s.equals("Office")) {
            System.out.println(s);
        }
    }

    public void addActionEvents() {

        select.addActionListener(this);
        addSong.addActionListener(this);
        play.addActionListener(this);
        pause.addActionListener(this);
        resume.addActionListener(this);
        stop.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(select)) {
            fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("C:\\Users"));
            fileChooser.setDialogTitle("Select Mp3");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new FileNameExtensionFilter("Mp3 files", "mp3"));
            if (fileChooser.showOpenDialog(select) == JFileChooser.APPROVE_OPTION) {
                myFile = fileChooser.getSelectedFile();
                filename = fileChooser.getSelectedFile().getName();
                filePath = fileChooser.getSelectedFile().getPath();
                songName.setText("File Selected : " + filename);
            }
        }

        if (e.getSource().equals(addSong)) {
            fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("C:\\Users"));
            fileChooser.setDialogTitle("Select Mp3");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new FileNameExtensionFilter("Mp3 files", "mp3"));
            if (fileChooser.showOpenDialog(select) == JFileChooser.APPROVE_OPTION) {
                myFile = fileChooser.getSelectedFile();
                filename = fileChooser.getSelectedFile().getName();

                categories.add(filename);

                frame.setVisible(false);
                Main mp = new Main(categories);
            }
        }

        if (e.getSource().equals(play)) {
            if (filename != null) {
                playThread.start();
                songName.setText("Now playing : " + filename);
            } else {
                songName.setText("No File was selected!");
            }
        }
        if (e.getSource().equals(pause)) {
            if (player != null && filename != null) {
                try {
                    pauseLength = fileInputStream.available();
                    player.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        if (e.getSource().equals(resume)) {
            if (filename != null) {
                resumeThread.start();
            } else {
                songName.setText("No File was selected!");
            }
        }
        if (e.getSource().equals(stop)) {
            if (player != null) {
                player.close();
                songName.setText("");
            }

        }

    }

    Runnable runnablePlay = new Runnable() {
        @Override
        public void run() {
            try {
                fileInputStream = new FileInputStream(myFile);
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                player = new Player(bufferedInputStream);
                totalLength = fileInputStream.available();
                player.play();//starting music
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    Runnable runnableResume = new Runnable() {
        @Override
        public void run() {
            try {
                fileInputStream = new FileInputStream(myFile);
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                player = new Player(bufferedInputStream);
                fileInputStream.skip(totalLength - pauseLength);
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public static void main(String args[]) {

        MongoClient mongoClient = MongoClients.create("mongodb://127.0.0.1:27017/?directConnection=true&serverSelectionTimeoutMS=2000&appName=mongosh+1.3.0");
        MongoDatabase db = mongoClient.getDatabase("sampleDB");
        MongoCollection col = db.getCollection("sampleCollection");

        //Document sampleDoc = new Document("_id", "3").append("name", "John Smith").append("penis", "20");

        //col.insertOne(sampleDoc);
        List<String> a = new ArrayList<String>();
        a.add("A");
        a.add("B");
        a.add("C");
        a.add("D");
        a.add("E");
        a.add("F");
        a.add("G");
        a.add("H");
        a.add("I");
        a.add("J");
        a.add("K");
        a.add("L");
        a.add("M");
        a.add("N");
        a.add("O");
        a.add("P");
        a.add("Q");
        a.add("R");
        a.add("S");
        a.add("T");
        a.add("U");
        a.add("V");
        a.add("Z");

        Main mp = new Main(a);
    }
}