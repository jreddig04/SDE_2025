#  software_design_2025
 
A modular Java console application demonstrating the use of **object-oriented design** and several **software design patterns** from various categories: Creational, Structural, Behavioral.
The project includes several mini games: TicTacToe, Rock–Paper–Scissors, Number Guess and Math Quiz, all of which a user can play through a command line menu.
 
All the dependencies are handled automatically, so there's no need to use any kind of build tool, whether it be Maven or Gradle.
 
---
 
##  Features
 
-  Clean modular architecture (`core`, `games`, `cli`)
-  Multiple design patterns implemented across all categories:
  - Creational: **Factory Method**, **Singleton**
  - Structural: **Decorator**, **Adapter**
  - Behavioural: **Strategy**, **Observer**, **Command**, **Template Method**
-  Fully automated build + test + run process via PowerShell script
-  Integrated **JUnit 5** tests for multiple games
-  Easily extendable with new games thanks to the flexible architecture
 
---
 
##  Run Instructions
 
Use the following command in the terminal to automatically:
 
1. install dependencies (JUnit),
2. compile all Java source files,
3. run all unit tests,
4. and launch the gamescenter application:
 
```powershell
.\scripts\run.ps1
```
 
---
 
##  Architecture Overview
 
The project uses a simple but scalable structure inside `src/main/java`:
 
```
jreddig/gamescenter/
  core
  games
  cli
```
 
### 1. `jreddig/gamescenter/core` — Core Framework
 
This package defines the abstractions that all games depend on:
 
- `Game` — the standard interface for all games
- `GameFactory` — used for Factory Method
- `GameDescriptor` — metadata for the game selection menu
- `Command` — behavioural Command pattern
- `GameState` — immutable state object
- `GameObserver` — used in Observer pattern
- `GameRegistry` — collection of available games
- `AppConfig` — **Singleton** for global configuration
 
This package contains *no* game-specific logic.
 
### 2. `jreddig.gamescenter.games` — Individual Games
 
Each game lies in its own subpackage, for example:
 
```
jreddig.gamescenter/games/ttt/   → TicTacToe
jreddig.gamescenter/games/rps/   → Rock-Paper-Scissors
jreddig.gamescenter/games/guess/ → Number Guess
jreddig.gamescenter/games/quiz/  → Math Quiz
```
 
Each game contains:
 
- its own commands (e.g. `TttPlace`)
- its factory (e.g. `TttFactory`)
- its state objects (e.g. `TttState`)
- its runner in the CLI layer
 
### 3. `jreddig/gamescenter/cli/` — Console User Interface
 
This layer is responsible for:
 
- rendering menus
- reading user input
- connecting the UI with the games
- showing game progress and results
 
Contains:
 
- `Main` (game menu)
- `GameCliRunner` (common interface)
- `TttCliRunner`, `RpsCliRunner`, `GuessCliRunner`, `QuizCliRunner`
 
### 4. Build & Test Flow
 
The PowerShell script in `scripts/run.ps1`:
 
- downloads JUnit if needed
- compiles `src/main/java` and `src/test/java`
- executes all unit tests
- runs the application
 
This makes the project fully portable and independent of Maven/Gradle.
 
---
 
#  Design Patterns by Category
 
## 🟦 Creational Patterns
 
### 1. **Factory Method**
 
Implemented via `GameFactory` and subclasses:
 
- `TttFactory`
- `RpsFactory`
- `GuessFactory`
- `QuizFactory`
 
Each factory creates game instances without exposing their concrete types.  
The CLI only depends on the interface `GameFactory`.
 
### 2. **Singleton**
 
Implemented in `AppConfig`.
 
Example usage inside `NumberGuess`:
 
```java
AppConfig cfg = AppConfig.instance();
int min = cfg.minGuess();
int max = cfg.maxGuess();
```
 
This centralises configuration in one place and ensures a single shared instance.
 
---
 
##  Structural Patterns
 
### 1. **Decorator**
 
Implemented in `LoggingMoveStrategy`.
 
```java
var human = new LoggingMoveStrategy(new HumanMoveStrategy(in), System.out, "Human");
var ai    = new LoggingMoveStrategy(new RandomMoveStrategy(), System.out, "AI");
```
 
This decorator wraps another `MoveStrategy` and adds logging behavior without modifying the original strategy.
 
### 2. **Adapter**
 
Defined by the interface `HighscoreStorage` and implemented through:
 
- `FileHighscoreStorageAdapter`
 
The adapter converts the abstract highscore API into a concrete Java file-based storage solution.
 
Used in `QuizCliRunner`:
 
```java
HighscoreStorage storage =
    new FileHighscoreStorageAdapter(Paths.get("highscores"));
storage.saveScore("quiz", score);
```
 
---
 
##  Behavioural Patterns
 
### 1. **Strategy**
 
Used in TicTacToe:
 
- `MoveStrategy` (interface)
- `HumanMoveStrategy`
- `RandomMoveStrategy`
- optionally decorated by `LoggingMoveStrategy`
 
The strategy determines whose move is selected and allows replacing behaviour dynamically.
 
### 2. **Observer**
 
Games notify observers about state changes via:
 
- `setObserver(GameObserver obs)`
- `observer.onStateChange(...)`
 
Example:
 
```java
ttt.setObserver(state -> printTtt((TttState) state));
```
 
This decouples the game logic from the CLI output.
 
### 3. **Command**
 
All user actions are commands:
 
- `TttPlace`
- `RpsMove`
- `GuessCommand`
- `AnswerCommand`
 
Commands are passed to the game via:
 
```java
game.handle(command);
```
 
This makes input handling fully decoupled from the game logic.
 
### 4. **Template Method**
 
Implemented in:
 
- `AbstractQuizGame` (defines algorithm)
- `MathQuiz` (provides specific questions and behaviour)
 
`init()` and `handle()` are `final`, but subclasses override `buildQuestions`, `onCorrect`, `onWrong`.
 
---
 
##  Testing
 
Tests are located under:
 
```
src/test/java/jreddig/gamescenter/games/...
```
 
They are executed **automatically** by the PowerShell script before starting the application.
 
Examples:
 
- `TicTacToeTest` – tests win detection
- `RpsTest` – tests one-round RPS
- `GuessTest` – tests number-guessing logic
- `MathQuizTest` – tests template-method–based quiz behavior
 
---
 
##  Team Collaboration
 
This project was developed as a two-person team.
 
- **Jessica Reddig**  focused on setting up the project foundation and tooling (Maven/Spring Boot skeleton, Maven Wrapper, run scripts), establishing the core game architecture (Game/Command/State/Factory/Observer, registry + CLI menu structure), and adding playable games (Tic-Tac-Toe, Number Guess) along with corresponding unit tests for all games.
- **Lars Loois** focused on extending the Gamescenter with additional games (Rock-Paper-Scissors and Math Quiz), implementing structural and behavioural design patterns (Decorator, Adapter, Template Method), and improving the overall CLI architecture and configuration. He also did the documentation.
---
 
##  Requirements
 
- Java 17+
- PowerShell (Windows) or Bash (Linux/macOS)
- No external build tools required
 
---
 
##  License
 
This project is for educational purposes.
