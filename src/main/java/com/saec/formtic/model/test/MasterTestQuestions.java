package com.saec.formtic.model.test;

import com.saec.formtic.model.test.question.Question;
import jakarta.persistence.PrePersist;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "master_tests_questions")
@Data
public class MasterTestQuestions {
    @Id
    private ObjectId id;
    private MasterTest masterTest;
    private String logoUrl = "";
    private List<Question> questions;

    public MasterTestQuestions(MasterTest masterTest, String logoUrl, List<Question> questions) {
        this.masterTest = masterTest;
        this.logoUrl = logoUrl;
        this.questions = questions;
    }
}
