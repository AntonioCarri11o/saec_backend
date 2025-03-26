package com.saec.formtic.model.test.question;

import com.saec.formtic.model.test.answer.Answer;

public interface Evaluable {
    public Answer autoEvaluate(Question question);
}
