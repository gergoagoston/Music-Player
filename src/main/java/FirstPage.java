import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class FirstPage {

    public FirstPage(){
        JFrame frame = new JFrame("FirstPage");
        frame.setBounds(100,100,500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        //frame.setVisible(true);

        try {
            frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("C:\\Users\\Koppany\\Egyetem\\Java\\NRDBproject\\pictures\\5.jpeg")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //frame.pack();
        JButton but1 = new JButton("Login");
        but1.setSize(100, 25);
        but1.setLocation(20, 100);
        frame.add(but1);
        but1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                LoginPage mp = new LoginPage();
            }
        });

        JButton but2 = new JButton("Register");
        but2.setSize(150, 25);
        but1.setLocation(20, 200);
        frame.add(but2);

        frame.setVisible(true);
        but2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                RegisterPage mp = new RegisterPage();
            }
        });
    }
}
