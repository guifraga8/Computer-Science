package main.java.com.quiz;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private String statement;
    private List<String> choices;
    private int answer;

    public Question(String statement, int answer) {
        this.statement = statement;
        this.choices = new ArrayList<>();
        this.answer = answer;
    }

    public String getStatement() {
        return this.statement;
    }
    public List<String> getChoices() {
        return this.choices;
    }
    public int getAnswer() {
        return this.answer;
    }

    public void addChoice(String choice) {
        this.choices.add(choice);
    }
}
