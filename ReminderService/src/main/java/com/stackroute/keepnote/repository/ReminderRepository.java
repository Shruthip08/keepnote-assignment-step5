package com.stackroute.keepnote.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.model.Reminder;


/*
* This class is implementing the MongoRepository interface for User.
* Annotate this class with @Repository annotation
* */
@Repository
public interface ReminderRepository extends MongoRepository<Reminder, String> {
	Reminder save(Reminder reminder);
	@Query("{ 'reminderId' : ?0 }")
	Optional <Reminder> findById(String reminderId);
	
}
