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

    @Override
    Answer evaluate(Answer questionAnswer, Answer userAnswer) {
        if (!(questionAnswer instanceof SimpleAnswer) || !(userAnswer instanceof SimpleAnswer)) {
            throw new ClassCastException("SimpleOpenQuestion expects SimpleAnswer, but got: "
                    + questionAnswer.getClass().getName() + " and "
                    + userAnswer.getClass().getName());
        }
        SimpleAnswer simpleQuestionAnswer = (SimpleAnswer) questionAnswer;
        SimpleAnswer simpleUserAnswer = (SimpleAnswer) userAnswer;
        return new SimpleAnswer(simpleUserAnswer.getAnswerString(), simpleQuestionAnswer.isCorrect());
    }
}
