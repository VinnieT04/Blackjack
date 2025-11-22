//STATE MACHINE

import java.util.ArrayList;
import java.util.Scanner;

public class Action
{
    //needs to access the hands, have an input form
    private Player player;
    private Dealer dealer;
    private Deck deck;
    private Scanner scan;
    private double currentBet;

    public Action(Player player, Dealer dealer, Deck deck, Scanner scan)
    {
        this.player = player;
        this.dealer = dealer;
        this.deck = deck;
        this.scan = scan;
    }

    public void playerMove()
    {
        int playerScore = calculateHand(player.getHand());

        if(playerScore == 21 && player.getHand().size() == 2)
        {
            System.out.println("--BLACKJACK! You win--");
            player.winBet(true);
            return;
        }

        //ask for hit or stand 
        while(playerScore < 21)
        {
            System.out.println("Player's score: " + playerScore);
            System.out.println("\n--> 'HIT' or 'STAND'");
            String decision = scan.nextLine();

            //HIT -> add new card, add value
            if(decision.equalsIgnoreCase("HIT"))
            {
                Cards newCard = deck.dealCard();
                player.addCard(newCard);
                player.showHand();
                playerScore = calculateHand(player.getHand());

                if(playerScore > 21)
                {
                    System.out.println("You busted! You loose you bet...");
                    System.out.println("Your balance is: " +  player.getBalance());
                    return;
                }
                else if(playerScore == 21)
                {
                    System.out.println("Hard 21, waiting for dealer...");
                    return;
                }
            }
            //STAND -> end turn, end game, compare scores
            else if (decision.equalsIgnoreCase("STAND"))
            {
                System.out.println("\nPlayer stands with: " + playerScore);
                break;
            }
            else {
                System.out.println("--> please type 'HIT' or 'STAND' <--");
            }

        }

        dealerMove();
        compareScores();
    }

    public void dealerMove()
    {
        System.out.println("\n-> Dealer reveals the hidden card...");
        dealer.revealHiddenCard();

        int dealerScore = calculateHand(dealer.getHand());
        System.out.println("Dealer's score: " + dealerScore);

        while(dealerScore < 17)
        {
            System.out.println("\nDealer hits...");
            Cards newCard = deck.dealCard();
            dealer.addCard(newCard);

            dealer.showHand();

            dealerScore = calculateHand(dealer.getHand());
            System.out.println("Dealer's new score: " + dealerScore);
        }

        if(dealerScore > 21)
        {
            System.out.println("Dealer bust!");
            player.winBet(false);
            return;
        }

        //stand after 17
        System.out.println("Dealer stands with " + dealerScore + "\n");
    }

    //gets the value of the ranks
    public int calculateHand(ArrayList<Cards> cards)
    {
        int value = 0;
        int ace = 0; //ace count to later choose 11 or 1

        for(int i =0; i < cards.size(); i++)
        {
            value += cards.get(i).getValue();

            if(cards.get(i).getValue() == 11)
            {
                ace++; //increase ace count
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
        System.out.println("Calculating results...");
        int playerScore = calculateHand(player.getHand());
        int dealerScore = calculateHand(dealer.getHand());

        boolean isBlackjack = (playerScore == 21 && player.getHand().size() == 2);

        if(playerScore > dealerScore && playerScore <= 21)
        {
            System.out.println("--You win!--");
            player.winBet(isBlackjack);
        }
        else if (dealerScore == playerScore)
        {
            System.out.println("--It's a tie!--");
            player.pushBet();
        }
        else if (dealerScore > playerScore && dealerScore <= 21)
        {
            System.out.println("--Dealer wins!--");
        }

        System.out.println("Your balance is : " + player.getBalance());
    }
}