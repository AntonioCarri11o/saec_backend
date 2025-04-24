package com.saec.formtic.model.test.question;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.saec.formtic.model.test.answer.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "questionCategory"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SimpleOpenQuestion.class, name = "SIMPLE_OPEN"),
        @JsonSubTypes.Type(value = MultipleOpenQuestion.class, name = "MULTIPLE_OPEN"),
        @JsonSubTypes.Type(value = MultipleOptionQuestion.class, name = "MULTIPLE_OPTIONS"),
        @JsonSubTypes.Type(value = SortingQuestion.class, name = "SORTING"),
        @JsonSubTypes.Type(value = SimplePairingQuestion.class, name = "SIMPLE_PAIRING"),
        @JsonSubTypes.Type(value = MultiplePairingQuestion.class, name = "MULTIPLE_PAIRING")
})
public abstract class Question {
    private String title;
    private QuestionCategory questionCategory;
    private Answer answer;
    abstract Answer evaluate(Answer userAnswer) throws ClassCastException;
}
