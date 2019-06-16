/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mike.game.guess.animal;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author mrani
 */
public class QuestionAnswer implements Serializable {
    
    private List<Animal> possibleAnswers;
    
    private QEnum questionEnum;
    
    private int uniqueCharIdx;

    private String uniqueCharQuestion;
    private String currQuestion;
    private String answerText;
    private String answerDesc;

    private boolean foundAnswer;

    private Boolean userAns;

    public boolean getFoundAnswer() {
        return foundAnswer;
    }

    public void setFoundAnswer(boolean foundAnswer) {
        this.foundAnswer = foundAnswer;
    }
    
    public String getAnswerDesc() {
        return answerDesc;
    }

    public void setAnswerDesc(String answerDesc) {
        this.answerDesc = answerDesc;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
            
    public int getUniqueCharIdx() {
        return uniqueCharIdx;
    }

    public void setUniqueCharIdx(int uniqueCharIdx) {
        this.uniqueCharIdx = uniqueCharIdx;
    }
    
    public String getUniqueCharQuestion() {
        return uniqueCharQuestion;
    }

    public void setUniqueCharQuestion(String uniqueCharQuestion) {
        this.uniqueCharQuestion = uniqueCharQuestion;
    }

    public List<Animal> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(List<Animal> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    public QEnum getQuestionEnum() {
        return questionEnum;
    }

    public void setQuestionEnum(QEnum questionEnum) {
        this.questionEnum = questionEnum;
    }

    public String getCurrQuestion() {
        return currQuestion;
    }

    public void setCurrQuestion(String currQuestion) {
        this.currQuestion = currQuestion;
    }

    public Boolean getUserAns() {
        return userAns;
    }

    public void setUserAns(Boolean userAns) {
        this.userAns = userAns;
    }
}
