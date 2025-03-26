package com.saec.formtic.model.test.question;

import com.saec.formtic.model.test.answer.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Question {
    private String title;
    private QuestionCategory questionCategory;
}
