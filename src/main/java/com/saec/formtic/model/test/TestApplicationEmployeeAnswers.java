package com.saec.formtic.model.test;

import com.saec.formtic.model.test.answer.Answer;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "test_application_employee_answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestApplicationEmployeeAnswers {
    @Id
    ObjectId id;
    private TestApplication testApplication;
    private List<Answer> answers;

    public TestApplicationEmployeeAnswers(TestApplication testApplication, List<Answer> answers) {
        this.testApplication = testApplication;
        this.answers = answers;
    }
}
