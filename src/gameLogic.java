import java.util.ArrayList;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gameLogic {
    //needs to access the hands, have an input form
    private Player player;
    private Dealer dealer;
    private Deck deck;
    private GameWindow gui;

    public gameLogic(Player player, Dealer dealer, Deck deck, GameWindow gui)
    {
        this.player = player;
        this.dealer = dealer;
        this.deck = deck;
        this.gui = gui;
    }

    public void startGame()
    {
        //betting system

        player.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());
        player.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());

        //face up
        player.getHand().get(0).faceUp = true;
        player.getHand().get(1).faceUp = true;
        dealer.getHand().get(0).faceUp = true;

        if(getPlayerScore() == 21 && player.getHand().size() == 2)
        {
            gui.showMessage("Beginner's luck, I guess");
            gui.enableActionButtons(false);
            handleStand();
        }

        gui.updateDisplay();
    }

    //getters
    public int getPlayerScore() {
        return calculateHand(player.getHand());
    }

    public int getDealerScore() {
        return calculateHand(dealer.getHand());
    }

    public ArrayList<Cards> getPHand()
    {
        return player.getHand();
    }

    public ArrayList<Cards> getDHand()
    {
        return dealer.getHand();
    }
    //

    public void handleHit()
    {
        Cards newCard = deck.dealCard();
        player.addCard(newCard);
        newCard.faceUp = true;
        int playerScore = getPlayerScore();

        if(playerScore > 21)
        {
            gui.showMessage("Well, that was unsatisfying. Thank you for making my job so easy");
            dealer.getHand().get(1).faceUp = true; // Flips the hidden card face up
            gui.updateDisplay();
            gui.enableActionButtons(false);
            endGame();
            return;
        }
        else if(playerScore == 21)
        {
            gui.showMessage("A perfect hand? How pedestrian. Let's see if my hand can't spoil your little victory");
            handleStand();
        }

        gui.updateDisplay();
    }

    public void handleStand()
    {
        gui.enableActionButtons(false); //disable both buttons

        Timer revealTimer = new Timer(1500, e -> {
            dealer.getHand().get(1).faceUp = true;
            gui.updateDisplay();

            dealerMove();
        });
        revealTimer.setRepeats(false);
        revealTimer.start();

        gui.updateDisplay();
    }

    public void dealerMove()
    {
        dealerHitSequence();
    }

    public void dealerHitSequence()
    {
        if(getDealerScore() < 17)
        {
            Timer hitTimer = new Timer(1000, e -> {
                Cards newCard = deck.dealCard();
                newCard.faceUp = true;
                dealer.addCard(newCard);
                gui.updateDisplay();

                //check if hitting again
                dealerHitSequence();
            });
            hitTimer.setRepeats(false);
            hitTimer.start();

        }
        else
        {
            if(getDealerScore() > 21)
            {
                gui.showMessage("A momentary lapse. Savor this small victory. It won't happen again");
                player.winBet(false);
                endGame();
                return;
            }
            compareScores();
            endGame();
        }
    }

    //gets the value of the ranks
    public int calculateHand(ArrayList<Cards> cards)
    {
        int value = 0;
        int ace = 0; //ace count to later choose 11 or 1

        for(int i =0; i < cards.size(); i++)
        {
            if(cards.get(i).faceUp) {
                value += cards.get(i).getValue();

                if (cards.get(i).getValue() == 11) {
                    ace++; //increase ace count
                }
            }
        }

        while(value > 21 && ace > 0) //can only have one ace card that is 11
        {
            value -= 10; //decrease the value of ace to 1
            ace--;
        }

        return value;
    }

    public void compareScores()
    {
        boolean isBlackjack = (getPlayerScore() == 21 && player.getHand().size() == 2);

        int playerScore = getPlayerScore();
        int dealerScore = getDealerScore();

        if(playerScore > dealerScore && playerScore <= 21)
        {
            gui.showMessage("How lucky...");
            player.winBet(isBlackjack);
        }
        else if (dealerScore == playerScore)
        {
            gui.showMessage("You're good at wasting time. No one gets out for free");
            player.pushBet();
        }
        else if (dealerScore > playerScore && dealerScore<= 21)
        {
            gui.showMessage("Did you think you had a chance? I always win, eventually");
        }
    }

    public void endGame()
    {
        gui.endGameUI();

        if(player.balance <= 0)
        {
            gui.showMessage("You're broke, bud");
        }
    }

    public void newGame()
    {
        player.clearHand();
        dealer.clearHand();
        deck = new Deck();

        gui.clearGame();
        gui.resetUI();

        startGame();

        //gui.enableActionButtons(true);
    }

    public void handleBet()
    {

    }
}
