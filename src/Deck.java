//calss to build and shuffle the deck

import java.util.ArrayList;
import java.util.Random;

public class Deck {
    //store the rank and suits of the cards
    ArrayList<Cards> deck = new ArrayList<>();
    String[] suit = {"C", "D", "H", "S"};
    String[] rank = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

    //constructor
    public Deck()
    {
        buildDeck();
        shuffleDeck();
    }

    //build the deck by combining suits and ranks
    void buildDeck()
    {
        for(int i = 0; i < suit.length; i++)
        {
            for(int j = 0; j < rank.length; j++)
            {
                deck.add(new Cards(suit[i], rank[j], false));
            }
        }
    }

    //shuffle the deck, move cards at a random position
    void shuffleDeck()
    {
        Random rand = new Random();

        for(int i = 0; i < deck.size(); i++)
        {
            int randomPosition = rand.nextInt(deck.size());

            //basic shuffle algorithm
            Cards temp = deck.get(i);
            deck.set(i, deck.get(randomPosition));
            deck.set(randomPosition, temp);
        }
    }

    //give cards to the player and dealer and decrease the number of cards in the deck
    //so no card gets repeated
    Cards dealCard()
    {
        Cards dealtCard = deck.get(0);
        deck.remove(0);
        return dealtCard;
    }
}