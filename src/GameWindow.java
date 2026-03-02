import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameWindow extends JFrame {

    private gameLogic action; //reference to game logic

    private JButton hitButton;
    private JButton standButton;
    private JLabel dealerSayings;
    private JLabel playerScore;
    private JLabel dealerScore;
    private JPanel controlPanel;
    private JPanel playerPanel;
    private JPanel dealerPanel;
    private JPanel dialoguePanel;
    private JPanel gamePanel;
    private JPanel buttonGroup;
    private JPanel pCard;
    private JPanel dCard;
    private JPanel endGame;
    private JButton againButton;

    public GameWindow(String playerName) {

        //initialize panels
        controlPanel = new JPanel();
        playerPanel = new JPanel();
        dealerPanel = new JPanel();
        dialoguePanel = new JPanel();
        gamePanel = new JPanel();

        Color tableColor = new Color(54,94,59);

        //core game components
        Player player = new Player(100);
        Dealer dealer = new Dealer();
        Deck deck = new Deck();
        this.action = new gameLogic(player, dealer, deck, this);

        //set window
        setBounds(160,20, 1200,800);
        setResizable(false);
        setTitle("BlackJack");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //set buttons and listeners
        hitButton = new JButton("Hit");
        hitButton.addActionListener(e -> {
            action.handleHit();
        });
        hitButton.setFocusable(false);
        standButton = new JButton("STAND");
        standButton.addActionListener(e -> {
            action.handleStand();
        });
        standButton.setFocusable(false);

        //set labels
        //--PLAYER--
        JLabel playerNameLabel = new JLabel(playerName);
        playerScore =  new JLabel("Score: 0");
        playerNameLabel.setForeground(Color.WHITE);
        playerScore.setForeground(Color.WHITE);

        //--DEALER--
        dealerScore = new JLabel("Dealer Score: 0");
        dealerScore.setForeground(Color.WHITE);

        //--TEXT--
        dealerSayings = new JLabel("Take a good look at that hand. It's the best you'll have all night");

        //assemble panels
        //--BUTTONS--
        buttonGroup = new JPanel();
        buttonGroup.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonGroup.setBackground(Color.BLACK);
        buttonGroup.add(hitButton);
        buttonGroup.add(standButton);

        //--CONTROL--
        controlPanel.setLayout(new BorderLayout());
        controlPanel.setBackground(Color.BLACK);
        controlPanel.setPreferredSize(new Dimension(200, 200));
        controlPanel.setLayout(new GridBagLayout());
        controlPanel.add(buttonGroup, new GridBagConstraints());

        //--CARDS--
        pCard = new JPanel(new FlowLayout(FlowLayout.CENTER, 10,10));
        pCard.setBackground(tableColor);
        dCard = new JPanel(new FlowLayout(FlowLayout.CENTER, 10,10));
        dCard.setBackground(tableColor);

        //--PLAYER--
        JPanel playerHeader = new JPanel();
        playerHeader.add(playerNameLabel);
        playerHeader.add(playerScore);
        playerHeader.setBackground(tableColor);

        playerPanel.setLayout(new BorderLayout());
        playerPanel.setBackground(tableColor);
        playerPanel.add(playerHeader, BorderLayout.NORTH);
        playerPanel.add(pCard, BorderLayout.CENTER);
        playerPanel.setBounds(0,300,1000,300);

        //--DEALER--
        JPanel dealerHeader = new JPanel();
        dealerHeader.add(dealerScore);
        dealerHeader.setBackground(tableColor);

        dealerPanel.setLayout(new BorderLayout());
        dealerPanel.setBackground(tableColor);
        dealerPanel.add(dealerHeader, BorderLayout.NORTH);
        dealerPanel.add(dCard, BorderLayout.CENTER);
        dealerPanel.setBounds(0,0,1000,300);

        //--END OF GAME--
        endGame = new JPanel();
        endGame.setLayout(new GridBagLayout());
        endGame.setBackground(new Color(0,0,0,160));
        endGame.setBounds(300, 190,400,150);
        endGame.setVisible(false);

        //--play again button--
        againButton = new JButton("Play Again?");
        againButton.setFont(new Font("Serif", Font.BOLD, 20));
        againButton.setPreferredSize(new Dimension(180, 55));
        againButton.addActionListener(e -> action.newGame());
        endGame.add(againButton);

        //--GAME--
        gamePanel.setLayout(null);
        gamePanel.add(dealerPanel);
        gamePanel.add(playerPanel);
        gamePanel.add(endGame);

        //--DIALOGUE--
        dialoguePanel.setLayout(new BorderLayout());
        dialoguePanel.setBackground(Color.BLACK);
        dialoguePanel.setPreferredSize(new Dimension(600,165));
        dialoguePanel.add(dealerSayings, BorderLayout.CENTER);

        //add to JFrame
        add(gamePanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);
        add(dialoguePanel, BorderLayout.SOUTH);

        setVisible(true);

        action.startGame();
    }

    public void updateDisplay(){
        int playerScore = action.getPlayerScore();
        int dealerScore = action.getDealerScore();

        pCard.removeAll();
        dCard.removeAll();

        updateScore(playerScore, dealerScore);

        //drawing logic to show hands
        ArrayList<Cards> pHand = action.getPHand();
        ArrayList<Cards> dHand = action.getDHand();

        //draw Player's cards
        for (Cards card : pHand) {
            ImageIcon cardIcon = loadImage(card);
            JLabel cardLabel = new JLabel(cardIcon);
            pCard.add(cardLabel);
        }

        //draw Dealer's cards
        for (Cards card : dHand) {
            ImageIcon cardIcon = loadImage(card);
            JLabel cardLabel = new JLabel(cardIcon);
            dCard.add(cardLabel);
        }

        pCard.revalidate();
        pCard.repaint();
        dCard.revalidate();
        dCard.repaint();
    }

    public void showMessage(String message)
    {
       dealerSayings.setText(message);
    }

    public void enableActionButtons(boolean enable)
    {
        hitButton.setEnabled(enable);
        standButton.setEnabled(enable);
    }

    public void clearGame() {
        pCard.removeAll();
        dCard.removeAll();

        //repaint to reflect the cleared panels
        pCard.revalidate();
        pCard.repaint();
        dCard.revalidate();
        dCard.repaint();
    }

    public void updateScore(int pScore, int dScore)
    {
        playerScore.setText("Score: " + pScore);
        dealerScore.setText("Dealer Score: " + dScore);

        this.revalidate();
        this.repaint();
    }

    private ImageIcon loadImage(Cards card)
    {
        String path;
        if(!card.faceUp)
        {
            path = "cards/card_pngs/card_backs/purple.png";
        }
        else
        {
            String filename = card.rank + card.suit + ".png";
            path = "cards/card_pngs/card_faces/" + filename;
        }

        ImageIcon originalIcon = new ImageIcon(path);
        Image image = originalIcon.getImage();
        Image scaled = image.getScaledInstance(110, 154, Image.SCALE_SMOOTH);

        return new ImageIcon(scaled);
     }

     public void endGameUI()
     {
         hitButton.setEnabled(false);
         standButton.setEnabled(false);
         endGame.setVisible(true);
         endGame.revalidate();
         endGame.repaint();
     }

     public void resetUI()
     {
         hitButton.setEnabled(true);
         standButton.setEnabled(true);
         endGame.setVisible(false);
         dealerSayings.setText("Take a good look at that hand. It's the best you'll have all night");
     }
}
