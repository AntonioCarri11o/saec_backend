package com.saec.formtic.model.test.question;

import com.saec.formtic.model.test.answer.Answer;
import com.saec.formtic.model.test.answer.SimpleAnswer;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SimpleOpenQuestion extends Question {
    public SimpleOpenQuestion(String title) {
        super(title, QuestionCategory.SIMPLE_OPEN);
    }

    public SimpleAnswer evaluate(Answer answer) {
        if(!(answer instanceof SimpleAnswer)) throw new IllegalArgumentException("A simple open question implements SimpleAnswer not an Answer");
        
        SimpleAnswer simpleAnswer = (SimpleAnswer) answer;
        if(simpleAnswer.getAnswerString().isEmpty()) throw new IllegalArgumentException("The answer cannot be empty");

        return new SimpleAnswer(((SimpleAnswer) answer).getAnswerString(), answer.isCorrect());
    }
}
