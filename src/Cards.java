//class that deals with individual cards

public class Cards {
    String suit;
    String rank;
    int value;
    boolean faceUp;

    //constructor
    public Cards(String suit, String rank, boolean  faceUp) {
        this.suit = suit;
        this.rank = rank;
        this.value = calculateValue();
        this.faceUp = faceUp;
    }

    //converts the cards values to strings
    @Override
    public String toString() {
        return rank + suit;
    }

    //returns cards values
    public int getValue() {
        return value;
    }

    //calculates how much each card values depending on the rank
    private int calculateValue()
    {
        if ("AJQK".contains(rank)) { //A J Q K return 10
            if (rank.equals("A")) {
                return 11;
            }
            return 10;
        }
        return Integer.parseInt(rank); //1-9 return their individual value
    }
}