package com.saec.formtic.repository.test;

import com.saec.formtic.model.test.TestApplicationEmployeeAnswers;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestApplicationEmployeeAnswerRepository extends MongoRepository<TestApplicationEmployeeAnswers, ObjectId> {
}
