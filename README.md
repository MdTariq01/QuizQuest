# 🛡️ QuizQuest: Java Master

**QuizQuest: Java Master** is a complete, OOP-driven desktop RPG game built in **Java using Swing**. Battle enemies, answer Java OOP-themed questions, level up, and defeat the ultimate Boss! 

✨ **Featured in college projects for demonstrating strong OOP principles and clean architecture.**

---

## 🎮 Game Features

- **⚔️ Strategic Quiz Battles**: Answer Java OOP questions to attack enemies.
- **📈 Adaptive Difficulty**: Enemies scale in difficulty based on your **Accuracy**.
- **👹 Special Boss Battles**: Encounter unique Boss mechanics at the end of each level.
- **⚡ Power-Up System**: Use points for **Healing (+40 HP)** or **Double Damage (x2)**.
- **🧠 Non-Repeating Questions**: Questions will not repeat within a single game session.
- **🕒 Real-Time Pressure**: 15-second timer for every question.
- **💥 Critical Hits**: 15% chance to deal double damage on any attack!

---

## 🧠 Core OOP Concepts Used

This project is a masterclass in **Object-Oriented Programming (OOP)**:

- **🔹 Abstraction**: Uses an `abstract class Character` to define the blueprint for all living entities.
- **🔹 Inheritance**: `Hero`, `Enemy`, and `Boss` extend `Character`, reusing base logic while adding unique traits.
- **🔹 Polymorphism**: The Engine treats all opponents as a generic `Enemy` reference, but they behave differently at runtime (Method Overriding).
- **🔹 Encapsulation**: Strictly uses `private` fields with `public` getters/setters to ensure valid game states.

---

## 🏛️ Architecture (MVC)

The project follows a **Modified MVC (Model-View-Controller)** pattern:
- **Model**: `Hero`, `Enemy`, `Boss`, `Question` (Entities).
- **Logic Engine**: `BattleEngine` (Controller).
- **Data Layer**: `QuestionBank` (Data management).
- **UI**: `GameUI` (Swing View).

---

## 🚀 How to Run

### Prerequisites
- **Java JDK 8 or higher** installed and added to your `PATH`.

### Running with Script (Recommended)
If you are on Windows, simply run:
```powershell
./run.ps1
```

### Manual Compilation
Alternatively, you can compile and run manually:
```powershell
# 1. Create out directory
mkdir out

# 2. Compile all files (UTF-8)
javac -encoding UTF-8 -d out (Get-ChildItem -Path src/main/java -Recurse -Filter *.java | Select-Object -ExpandProperty FullName)

# 3. Launch
java -cp out com.quizrpg.QuizQuest
```

---

## 📁 Project Structure

```text
src/main/java/com/quizrpg/
├── QuizQuest.java        # Entry point
├── data/
│   └── QuestionBank.java # Quiz storage/logic
├── engine/
│   └── BattleEngine.java # Combat controller
├── model/
│   ├── Character.java    # Base abstract class
│   ├── Hero.java         # Player entity
│   ├── Enemy.java        # Opponents
│   ├── Boss.java         # Boss opponents
│   └── Question.java     # Question POJO
└── ui/
    └── GameUI.java       # Swing user interface
```

---

## 🤝 Contributing
Feel free to fork this project and add more questions or features!

Happy Coding 🚀
