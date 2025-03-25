package com.saec.formtic.model.test.answer;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class SimpleAnswer extends Answer {
    private String answerString;

    public SimpleAnswer(String answer, boolean correct) {
        super(AnswerType.SIMPLE_ANSWER, correct);
        this.answerString = answer;
    }
}
