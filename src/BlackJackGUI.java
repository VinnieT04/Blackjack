import javax.swing.*;
import java.awt.*;

public class BlackJackGUI {
    private float alpha = 0f; // transparency level
    private Timer fadeTimer;

    public void initialWindow()
    {
        Color tableColor = new Color(54,94,59);

        JFrame frame = new JFrame();
        frame.setTitle("BlackJack");
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setBounds(160,20, 1200,800);
        frame.getContentPane().setBackground(tableColor);

        JLabel invitation = new JLabel("Welcome to", SwingConstants.CENTER);
        invitation.setFont(new Font("Serif", Font.PLAIN, 32));
        invitation.setForeground(Color.white);
        invitation.setBounds(0, 250, 1200, 50);
        invitation.setForeground(new Color(225,225,225, 0));

        JLabel title = new JLabel("BlackJack", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 50));
        title.setForeground(Color.white);
        title.setBounds(0, 300, 1200, 100);
        title.setForeground(new Color(225,225,225, 0));

        JButton button = new JButton("Start");
        button.setBounds(550,450,85,50);

        button.addActionListener(e -> {
            gameStart(frame);
        });

        frame.add(button);
        frame.add(invitation);
        frame.add(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        fadeTimer = new Timer(50, e -> {
            alpha += 0.05f;
            if (alpha >= 1f) {
                alpha = 1f;
                fadeTimer.stop();
                button.setVisible(true);
            }
            // Update label transparency
            invitation.setForeground(new Color(255, 255, 255, (int)(alpha * 255)));
            title.setForeground(new Color(255, 255, 255, (int)(alpha * 255)));
        });

        fadeTimer.start();
    }

    private void gameStart(JFrame currentFrame)
    {
        String player = JOptionPane.showInputDialog(
                currentFrame,
                "Enter your name: ",
                JOptionPane.QUESTION_MESSAGE
        );

        if(player != null && !player.isEmpty())
        {
            currentFrame.dispose(); //close current window
            new GameWindow(player);
        }
    }

    public static void  main(String[] args)
    {
        SwingUtilities.invokeLater(() ->  new BlackJackGUI().initialWindow());
    }
}
