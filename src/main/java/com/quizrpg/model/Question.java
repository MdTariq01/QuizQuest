package com.quizrpg.model;

/**
 * Question class represents a quiz question with options and answers.
 */
public class Question {
    private String text;
    private String[] options;
    private int correctAnswer;
    private String difficulty;

    public Question(String text, String[] options, int correctAnswer, String difficulty) {
        this.text = text;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
    }

    public boolean checkAnswer(int answerIndex) {
        return answerIndex == correctAnswer;
    }

    public String getText() { return text; }
    public String[] getOptions() { return options; }
    public int getCorrectAnswer() { return correctAnswer; }
    public String getDifficulty() { return difficulty; }
}
