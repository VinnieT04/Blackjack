# Blackjack

Text-based Blackjack game built in Java that simulates the classic casino game, supporting the actions of 'Hit' and 'Stand'

## Project Structure
```
Blackjack/
│
├── src/
│   ├── BlackJack.java        # Entry point — launches the game
│   ├── BlackJackGUI.java     # Graphical user interface
│   ├── GameWindow.java       # Main game window and layout
│   ├── gameLogic.java        # Core game rules and logic
│   ├── Deck.java             # Deck creation and shuffling
│   ├── Cards.java            # Card model and values
│   ├── Player.java           # Player state and hand management
│   ├── Dealer.java           # Dealer behaviour
│   └── BlackJack.iml         # IntelliJ IDEA module file
│
└── cards/                    # Card image assets
```

### Prerequisites
- Java JDK 8 or higher
- An IDE like [IntelliJ IDEA](https://www.jetbrains.com/idea/) (recommended — project includes `.iml` file) or any Java-compatible environment

### Running the Game
1. Clone the repository:
   ```bash
   git clone https://github.com/VinnieT04/Blackjack.git
   cd Blackjack
   ```

2. Open the project in IntelliJ IDEA and run `BlackJack.java`, or compile and run manually:
   ```bash
   javac src/*.java
   java -cp src BlackJack
   ```

