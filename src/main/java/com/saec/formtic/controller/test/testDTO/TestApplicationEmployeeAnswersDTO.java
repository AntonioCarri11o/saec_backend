package com.saec.formtic.controller.test.testDTO;

import com.saec.formtic.model.test.TestApplication;
import com.saec.formtic.model.test.TestApplicationEmployeeAnswers;
import com.saec.formtic.model.test.answer.Answer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestApplicationEmployeeAnswersDTO {
    @NotBlank(message = "Test application is mandatory")
    @Size(max = 255, message = "Test application id must not exceed 255 characters")
    private String testApplicationId;

    @NotNull(message = "Answers list must not be empty")
    private List<Answer> answers;

    public TestApplicationEmployeeAnswers createTestApplicationEmployeeAnswers(TestApplication testApplication) {
        return new TestApplicationEmployeeAnswers(
                testApplication,
                this.answers
        );
    }
}
