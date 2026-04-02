# 🛡️ Quiz RPG: Java Master

A complete, OOP-driven desktop RPG game built in **Java using Swing**. Battle enemies, answer Java OOP-themed questions, level up, and defeat the ultimate Boss! 

✨ **Featured in college projects for demonstrating strong OOP principles.**

---

## 🎮 Game Features

- **Strategic Quiz Battles**: Answer Java OOP questions to attack enemies.
- **Dynamic Progression**: Enemies scale in difficulty as you win.
- **Special Boss Battles**: Encounter unique Boss mechanics every 5 levels.
- **Power-Up System**: Use points for **Healing (+40 HP)** or **Double Damage (x2)** on your next attack.
- **Real-Time Pressure**: 15-second timer for every question.
- **Critical Hits**: 15% chance to deal double damage on any attack!

## 🧠 Topics Covered

The game features 50+ hand-crafted questions on:
- **Inheritance & Polymorphism**
- **Abstraction & Encapsulation**
- **Interfaces & Abstract Classes**
- **Access Modifiers (Public, Private, etc.)**
- **Collections (ArrayList, HashMap)**
- **Constructors & Method Overloading/Overriding**

---

## 🛠️ Architecture (OOP Principles)

This project is built using **Clean Architecture (MVC-like)**:
- **Model**: `Character`, `Hero`, `Enemy`, `Boss`, `Question` (Abstraction, Inheritance).
- **Data**: `QuestionBank` (Data Persistence logic).
- **Engine**: `BattleEngine` (Game state & combat logic).
- **UI**: `GameUI` (Java Swing components & event handling).

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

# 2. Compile all files
javac -d out src/main/java/com/quizrpg/**/*.java

# 3. Launch
java -cp out com.quizrpg.Main
```

---

## 📁 Project Structure

```text
src/main/java/com/quizrpg/
├── Main.java             # Entry point
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
