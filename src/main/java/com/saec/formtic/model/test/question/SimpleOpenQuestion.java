package com.saec.formtic.model.test.question;

import com.saec.formtic.model.test.answer.Answer;
import com.saec.formtic.model.test.answer.SimpleAnswer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.TypeAlias;

@Data
@EqualsAndHashCode(callSuper=false)
@TypeAlias("SIMPLE_OPEN")
public class SimpleOpenQuestion extends Question {
    public SimpleOpenQuestion(String title) {
        super(title, QuestionCategory.SIMPLE_OPEN, new SimpleAnswer(""));
    }

    @Override
    Answer evaluate(Answer userAnswer) {
        return null;
    }
}
