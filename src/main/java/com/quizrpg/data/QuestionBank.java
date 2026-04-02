package com.quizrpg.data;

import com.quizrpg.model.Question;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * QuestionBank stores and manages the quiz data.
 */
public class QuestionBank {
    private List<Question> allQuestions;
    private java.util.Set<Question> usedQuestions;
    private static final Random random = new Random();

    public QuestionBank() {
        allQuestions = new java.util.ArrayList<>();
        usedQuestions = new java.util.HashSet<>();
        loadQuestions();
    }

    private void loadQuestions() {
        // EASY QUESTIONS
        allQuestions.add(new Question("Which of the following is NOT a pillar of OOP?", new String[]{"Inheritance", "Encapsulation", "Compilation", "Polymorphism"}, 2, "EASY"));
        allQuestions.add(new Question("What is a class in Java?", new String[]{"A real-world object", "A blueprint for creating objects", "A method", "A variable"}, 1, "EASY"));
        allQuestions.add(new Question("Which keyword is used to create an instance (object) of a class?", new String[]{"class", "create", "new", "object"}, 2, "EASY"));
        allQuestions.add(new Question("Which access modifier makes a member visible only within its own class?", new String[]{"public", "protected", "default", "private"}, 3, "EASY"));
        allQuestions.add(new Question("What is the superclass of all classes in Java?", new String[]{"java.lang.Main", "java.lang.Object", "java.lang.Class", "java.lang.System"}, 1, "EASY"));
        allQuestions.add(new Question("Which keyword is used to implement inheritance in Java?", new String[]{"implements", "extends", "inherits", "super"}, 1, "EASY"));
        allQuestions.add(new Question("Can a class extend more than one class in Java?", new String[]{"Yes", "No", "Depends on Java version", "Only if it is abstract"}, 1, "EASY"));
        allQuestions.add(new Question("Bundling data and methods into a single unit is called?", new String[]{"Abstraction", "Inheritance", "Encapsulation", "Polymorphism"}, 2, "EASY"));
        allQuestions.add(new Question("Which keyword is used to refer to the current object instance?", new String[]{"super", "static", "this", "final"}, 2, "EASY"));
        allQuestions.add(new Question("What is a constructor in Java?", new String[]{"A method that returns a value", "Special method to initialize objects", "A keyword to declare a class", "A static block"}, 1, "EASY"));
        allQuestions.add(new Question("Which of these is used to define an interface?", new String[]{"class", "interface", "abstract", "extends"}, 1, "EASY"));
        allQuestions.add(new Question("What is the return type of a constructor?", new String[]{"void", "int", "None", "The class itself"}, 2, "EASY"));
        allQuestions.add(new Question("Which keyword makes a variable's value unchangeable?", new String[]{"static", "final", "const", "fixed"}, 1, "EASY"));
        allQuestions.add(new Question("Which of these is NOT a primitive data type?", new String[]{"int", "double", "String", "boolean"}, 2, "EASY"));
        allQuestions.add(new Question("What is the default value of an int in Java?", new String[]{"0", "null", "undefined", "1"}, 0, "EASY"));
        allQuestions.add(new Question("Which class is used to store key-value pairs in Java?", new String[]{"ArrayList", "HashMap", "HashSet", "LinkedList"}, 1, "EASY"));

        // MEDIUM QUESTIONS
        allQuestions.add(new Question("What is Method Overloading?", new String[]{"Same name, same parameters", "Same name, different parameters", "Different name, same parameters", "Inherited method name changed"}, 1, "MEDIUM"));
        allQuestions.add(new Question("Can an abstract class be instantiated?", new String[]{"Yes", "No", "Only if it has no methods", "Only through reflection"}, 1, "MEDIUM"));
        allQuestions.add(new Question("What does the super keyword do?", new String[]{"Calls parent constructor/method", "Prevents inheritance", "Creates a new object", "Defines a variable"}, 0, "MEDIUM"));
        allQuestions.add(new Question("Purpose of final keyword on a method?", new String[]{"Prevents call", "Prevents override", "Makes it static", "Makes it private"}, 1, "MEDIUM"));
        allQuestions.add(new Question("Dynamic Method Dispatch relates to...?", new String[]{"Overloading", "Overriding/Runtime Polymorphism", "Static binding", "Constructors"}, 1, "MEDIUM"));
        allQuestions.add(new Question("Default access modifier is visible...?", new String[]{"Everywhere", "Within private class", "Within same package", "Nowhere"}, 2, "MEDIUM"));
        allQuestions.add(new Question("A class implementing an interface must...?", new String[]{"Extend Object", "Implement all methods", "Be abstract", "Have a constructor"}, 1, "MEDIUM"));
        allQuestions.add(new Question("Keyword 'static' means...?", new String[]{"Unchanged value", "Shared by all instances", "Visible to all", "Cannot be inherited"}, 1, "MEDIUM"));
        allQuestions.add(new Question("What is an 'is-a' relationship?", new String[]{"Composition", "Inheritance", "Encapsulation", "Polymorphism"}, 1, "MEDIUM"));
        allQuestions.add(new Question("Can an interface have instance variables?", new String[]{"Yes", "No (only static final constants)", "Only if they are private", "Only starting Java 8"}, 1, "MEDIUM"));
        allQuestions.add(new Question("The '== ' operator compares...?", new String[]{"Values", "Memory references", "Types", "Class names"}, 1, "MEDIUM"));
        allQuestions.add(new Question("What is 'Composition'?", new String[]{"Is-a relation", "Has-a relation", "Inheritance", "A type of constructor"}, 1, "MEDIUM"));
        allQuestions.add(new Question("Which collection allows duplicate elements?", new String[]{"Set", "List", "Map keys", "None"}, 1, "MEDIUM"));
        allQuestions.add(new Question("Which exception is thrown on array index out of bounds?", new String[]{"NullPointerException", "ArrayIndexOutOfBoundsException", "ArithmeticException", "IOException"}, 1, "MEDIUM"));
        allQuestions.add(new Question("What is a 'package' in Java?", new String[]{"A class type", "Group of related classes", "A method", "A variable type"}, 1, "MEDIUM"));
        allQuestions.add(new Question("How does HashMap handle collisions in Java 8+?", new String[]{"Linear Probing", "LinkedList/Balanced Trees", "Throws Exception", "Deletes existing key"}, 1, "MEDIUM"));
        allQuestions.add(new Question("What is the difference between 'throw' and 'throws'?", new String[]{"Throw is for methods, throws is for objects", "Throw is to raise, throws is signature", "No difference", "Throws is for custom logic"}, 1, "MEDIUM"));

        // HARD QUESTIONS
        allQuestions.add(new Question("What is Covariant Return Type?", new String[]{"Overriding with same return type", "Overriding with subclass return type", "Overloading return types", "Generic return types"}, 1, "HARD"));
        allQuestions.add(new Question("Can you have a main method in an abstract class?", new String[]{"Yes", "No", "Only if it is static", "Only if protected"}, 0, "HARD"));
        allQuestions.add(new Question("What is an anonymous inner class?", new String[]{"Class inside a method with name", "Class without a name", "A class with only one method", "A protected class"}, 1, "HARD"));
        allQuestions.add(new Question("Java handles the Diamond Problem using...?", new String[]{"Multiple Class Inheritance", "Interfaces", "Virtual Inheritance", "Abstract Classes"}, 1, "HARD"));
        allQuestions.add(new Question("What is 'Safe Type Casting' handled by?", new String[]{"Compiler", "JVM Runtime", "Garbage Collector", "Constructor"}, 1, "HARD"));
        allQuestions.add(new Question("A 'Record' class (Java 14+) is...?", new String[]{"A mutable container", "Immutable data-carrier", "A type of enum", "A database connection"}, 1, "HARD"));
        allQuestions.add(new Question("The 'volatile' keyword ensures...?", new String[]{"Speed", "Visibility across threads", "Constant values", "Memory compression"}, 1, "HARD"));
        allQuestions.add(new Question("Tight coupling means...?", new String[]{"Low dependency", "High dependency", "Use of interfaces", "Good design"}, 1, "HARD"));
        allQuestions.add(new Question("Can a class be both 'abstract' and 'final'?", new String[]{"Yes", "No", "Only if inner", "Only in Java 17+"}, 1, "HARD"));
        allQuestions.add(new Question("Deep copy vs Shallow copy difference?", new String[]{"Deep copy copies references", "Deep copy copies actual objects", "No difference", "Shallow copy is safer"}, 1, "HARD"));
        allQuestions.add(new Question("What is the 'Reflection API' used for?", new String[]{"Compiling code", "Inspecting classes at runtime", "Graphics rendering", "Memory management"}, 1, "HARD"));
        allQuestions.add(new Question("Java Garbage Collection reclaims...?", new String[]{"Stack memory", "Heap memory (unreachable objects)", "Static memory", "CPU time"}, 1, "HARD"));
        allQuestions.add(new Question("Which interface does ArrayList implement?", new String[]{"Set", "Queue", "List", "Deque"}, 2, "HARD"));
        allQuestions.add(new Question("What is the time complexity of get(index) in ArrayList?", new String[]{"O(n)", "O(log n)", "O(1)", "O(n^2)"}, 2, "HARD"));
        allQuestions.add(new Question("Can a static method access non-static instance variables?", new String[]{"Yes", "No", "Only if they are public", "Only if final"}, 1, "HARD"));
        allQuestions.add(new Question("What is the time complexity of HashMap.put() in ideal case?", new String[]{"O(n)", "O(log n)", "O(1)", "O(n log n)"}, 2, "HARD"));
        allQuestions.add(new Question("What happens when hashcode() is not overridden correctly for HashMap keys?", new String[]{"Compile error", "Duplicate keys may exist/lost items", "JVM crashes", "Automatic fix"}, 1, "HARD"));
    }

    public Question getRandomQuestionByDifficulty(String difficulty) {
        // Filter by difficulty AND not used
        List<Question> filtered = allQuestions.stream()
                .filter(q -> q.getDifficulty().equalsIgnoreCase(difficulty))
                .filter(q -> !usedQuestions.contains(q))
                .collect(Collectors.toList());

        // Fallback: If no UNUSED questions for this difficulty, pick any UNUSED question
        if (filtered.isEmpty()) {
            filtered = allQuestions.stream()
                    .filter(q -> !usedQuestions.contains(q))
                    .collect(Collectors.toList());
        }

        // Entire pool is exhausted
        if (filtered.isEmpty()) {
            return null;
        }

        Question selected = filtered.get(random.nextInt(filtered.size()));
        usedQuestions.add(selected); // Mark as used
        return selected;
    }

    public void resetQuestions() {
        usedQuestions.clear();
    }

    public void shuffleQuestions() {
        Collections.shuffle(allQuestions);
    }
}
