package com.saec.formtic.repository.test;

import com.saec.formtic.model.test.MasterTest;
import com.saec.formtic.model.test.MasterTestQuestions;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TestQuestionsRepository extends MongoRepository<MasterTestQuestions, ObjectId> {
    Optional<MasterTestQuestions> findByMasterTest(MasterTest masterTest);
}
