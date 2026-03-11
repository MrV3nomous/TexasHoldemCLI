████████╗███████╗██╗  ██╗ █████╗ ███████╗
╚══██╔══╝██╔════╝██║  ██║██╔══██╗██╔════╝
   ██║   █████╗  ███████║███████║███████╗
   ██║   ██╔══╝  ██╔══██║██╔══██║╚════██║
   ██║   ███████╗██║  ██║██║  ██║███████║
   ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝

![Build](https://img.shields.io/badge/build-passing-brightgreen) ![Java](https://img.shields.io/badge/Java-17-blue) ![License](https://img.shields.io/badge/license-MIT-green) ![Code size](https://img.shields.io/github/languages/code-size/MrV3nomous/TexasHoldemCLI)

# 🎲 Texas Hold'em CLI Poker

 │*Ultimate Interactive Poker CLI* │  

Welcome to **Texas Hold'em CLI**, a fully interactive, realistic, and engaging command-line poker game written in **Java**. Experience the thrill of **multiplayer poker** with AI opponents featuring **unique personalities**, risk strategies, and dynamic decision-making.

---

## 🃏 Features

#### - **Realistic Texas Hold’em rules**
  - Pre-flop, flop, turn, river, and showdown implemented.

#### - **Dynamic AI opponents**
  - Each AI has a **risk tolerance**, plays differently, folds or raises intelligently.

#### - **Interactive betting**
  - Small blind, big blind, call, raise, fold, all-in mechanics.
  - Realistic pot and call tracking throughout the rounds.

#### - **ASCII card visuals**
  - Hole cards and community cards displayed in **colorful ASCII art**.

#### - **Multi-round gameplay**
  - Continue rounds seamlessly; chips carry over.

#### - **Showdown display**
  - Community cards revealed with each player’s hand for dramatic effect.


---

## ⚡ How It Works

1. **Setup**: Choose number of players (2–6), you play against AI.  
2. **Dealing**: Hole cards are dealt to each player.  
3. **Betting Rounds**: Small blind and big blind posted, betting rounds executed.  
4. **Community Cards**: Flop → Turn → River, displayed with ASCII art.  
5. **Showdown**: Winner determined automatically with hand evaluation.  
6. **Repeat**: Play multiple rounds, chips accumulate.


---


## ⚙️ Installation & Running

```bash
# Clone the repo
git clone https://github.com/MrV3nomous/TexasHoldemCLI.git
cd TexasHoldemCLI
```

### Compile all Java files

```bash
javac -d bin poker/*.java
```

### Run the game

```bash
java -cp ../bin poker.Main
```

---


## 💡 Design Highlights


### - AI Logic:
  - Evaluates hand strength, draw potential, and pot pressure.
  - Makes dynamic decisions: fold, call, raise.
  - Individual personality with risk tolerance for realism.

 
### - Betting System:
  - Proper small/big blinds, calls, raises, all-in logic.
  - Call amount persists across betting rounds.
  - Ensures smooth turn progression with accurate pot tracking.


### - Visual Rendering:
  - ASCII-based card display for community and player hands.
  - Folded and all-in states clearly displayed.


### - Robust Game Flow:
  - Multiple rounds, pot accumulation, early-win detection, and showdown evaluation.


---


## 🎯 Future Features

- Multiplayer over network
- Chip betting strategies and bluffing AI
- Customizable table and themes
- Stats and leaderboard system


---


## 🚀 Highlights

- Immersive CLI experience – feels like playing real poker.
- AI opponents – challenging and unpredictable.
- Clean codebase – modular, extensible, and fully documented.



---

**Play, strategize, and dominate the table.**

💥 Every hand is a chance to test your skill against intelligent opponents!


---


**MIT LICENSE ©️ Soumik Halder • 2026**





