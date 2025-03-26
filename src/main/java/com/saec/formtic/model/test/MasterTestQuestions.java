package com.saec.formtic.model.test;

import com.saec.formtic.model.test.question.Question;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collation = "master_tests_questions")
@Data
public class MasterTestQuestions {
    @Id
    private ObjectId id;
    private MasterTest masterTest;
    private String version;
    private String logoUrl = "";
    private List<Question> questions;
}
