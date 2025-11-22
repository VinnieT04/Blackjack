//class that deals with the dealer's actions

import java.util.ArrayList;

public class Dealer {
    ArrayList<Cards> cards = new ArrayList<>(); //dealer's cards

    //method to add cards to the dealer's card array
    public void addCard(Cards card)
    {
        cards.add(card);
    }

    public void revealHiddenCard()
    {
        // Find and flip the face-down card
        for(int i = 0; i < cards.size(); i++)
        {
            Cards card = cards.get(i);
            if (!card.faceUp) {
                card.faceUp = true;
            }
        }

        // Display the revealed hand
        String cardDisplay = "";
        for (int i = 0; i < cards.size(); i++)
        {
            Cards card = cards.get(i);
            cardDisplay += card.toString() + " ";
        }
        System.out.println(cardDisplay.trim());
    }

    //show all cards
    public void showInitialHand()
    {
        System.out.print("-> Dealer: ");
        for(int i = 0; i < cards.size(); i++)
        {
            Cards card = cards.get(i);
            if(card.faceUp)
            {
                System.out.print(card.toString() + " ");
            }
            else
            {
                System.out.print("[Hidden Card]");
            }
        }

        System.out.println();
    }

    public void showHand()
    {
        System.out.print("-> Dealer: ");
        for(int i = 0; i < cards.size(); i++)
        {
            Cards card = cards.get(i);
            System.out.print(card.toString() + " ");
        }

        System.out.println();
    }

    //returns the dealer's cards
    public ArrayList<Cards> getHand()
    {
        return cards;
    }

    //for a new round
    public void clearHand()
    {
        cards.clear();
    }
}