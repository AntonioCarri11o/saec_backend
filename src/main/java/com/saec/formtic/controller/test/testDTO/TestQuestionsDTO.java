package com.saec.formtic.controller.test.testDTO;

import com.saec.formtic.model.test.MasterTest;
import com.saec.formtic.model.test.MasterTestQuestions;
import com.saec.formtic.model.test.question.Question;
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
public class TestQuestionsDTO {
    @NotBlank(message = "The test id is mandatory")
    @Size(max = 255, message = "The master test id must not exceed 255 characters")
    private String masterTestId;

    @NotBlank(message = "The logo URL id is mandatory")
    @Size(max = 255, message = "The logo url must not exceed 255 characters")
    private String logoURL;

    @NotNull(message = "The question list must not be empty")
    private List<Question> questions;

    public MasterTestQuestions createMasterTestQuestions(MasterTest masterTest) {
        return new MasterTestQuestions(masterTest, this.logoURL, this.questions);
    }
}
