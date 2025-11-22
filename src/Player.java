//class that deals with the player's actions

import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    ArrayList<Cards> cards = new ArrayList<>(); //array that holds the player's cards
    double balance;
    double currentBet;

    //asks for the player's name
    public Player(int balance) {
        this.balance = balance;
    }

    //add the cards to the player's hand
    public void addCard(Cards card)
    {
        cards.add(card);
    }

    //shows all player's hand
    public void showHand()
    {
        System.out.print("-> Player: ");
        for(int i = 0; i < cards.size(); i++)
        {
            Cards card = cards.get(i);
            System.out.print(card.toString() + " ");
        }

        System.out.println();
    }

    //returns the player's cards
    public ArrayList<Cards> getHand()
    {
        return cards;
    }

    //for a new round
    public void clearHand()
    {
        cards.clear();
    }

    public boolean placeBet(Scanner scan)
    {
        System.out.println("Place bet: ");
        int bet = scan.nextInt();
        scan.nextLine();

        if(bet > balance || bet < 0)
        {
            System.out.println("You cannot place this bet");
            return false;
        }

        this.balance -= bet;
        currentBet = bet;
        return true;
    }

    public void winBet(boolean isBlackjack)
    {
        if(!isBlackjack)
        {
            balance += currentBet * 2;
            System.out.println("You get " + currentBet*2);
        }
        else
        {
            balance += currentBet * 2.5;
            System.out.println("You get " + currentBet*2.5);
        }
    }

    public void pushBet()
    {
        this.balance += currentBet;
        System.out.println("Bet returned");
    }

    public double getBalance()
    {
        return balance;
    }

}