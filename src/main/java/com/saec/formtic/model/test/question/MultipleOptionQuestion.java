package com.saec.formtic.model.test.question;

import com.saec.formtic.model.test.answer.Answer;
import com.saec.formtic.model.test.answer.MultipleAnswer;
import com.saec.formtic.model.test.answer.SimpleAnswer;

public class MultipleOptionQuestion extends Question {
    String correctAnswer;
    public MultipleOptionQuestion(String title, MultipleAnswer answer, String correctAnswer) {
        super(title, QuestionCategory.MULTIPLE_OPTIONS, answer);
        this.correctAnswer = correctAnswer;
    }

    @Override
    Answer evaluate(Answer userAnswer) {
        SimpleAnswer simpleAnswer;
        if (userAnswer instanceof SimpleAnswer) throw new ClassCastException(String.format("Wrong type of answer in %s", this.getTitle()));
        userAnswer.setCorrect(correctAnswer.equals(((SimpleAnswer) userAnswer).getAnswerString()));
        return userAnswer;
    }
}
