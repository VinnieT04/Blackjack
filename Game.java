//class that deals with the game logic

import java.util.Scanner;

public class Game {
    private Player player;
    private Dealer dealer;
    private Deck deck;
    private Scanner scan;
    private Action action;

    //constructor
    public Game() {
        scan = new Scanner(System.in);
        player = new Player(100);
        dealer = new Dealer();
        deck = new Deck();
        action = new Action(player, dealer, deck, scan);
    }

    public void gameStart() {
        System.out.println("Your initial bet is: " + player.getBalance());

        playStart();
    }
    public void playStart() {
        while(!player.placeBet(scan))
        {

        }

        System.out.println();
        //initial cards face down
        player.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());
        player.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());

        //face up
        player.getHand().get(0).faceUp = true;
        player.getHand().get(1).faceUp = true;
        dealer.getHand().get(0).faceUp = true;

        //show initial hand
        //player
        player.showHand();
        //dealer
        dealer.showInitialHand();

        System.out.println();

        // Player moves
        action.playerMove();

        //check balance
        if(player.getBalance() <= 0)
        {
            System.out.println("\nYou're broke\n GAME OVER");
            return;
        }

        // Ask to play again
        while (true)
        {
            System.out.println();
            System.out.println("-> Play again? [yes/no]");
            if(!scan.nextLine().equalsIgnoreCase("yes"))
            {
                if(player.balance <= 0 )
                {
                    System.out.println("--YOU LOOSE--");
                }
                System.out.println("GAME OVER!");
                break;
            }

            //reset cards
            player.clearHand();
            dealer.clearHand();
            deck = new Deck();
            action = new Action(player, dealer, deck, scan);
            playStart();

            return;
        }

    }

}