package com.stackroute.keepnote.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stackroute.keepnote.model.User;

/*
* This class is implementing the MongoRepository interface for User.
* Annotate this class with @Repository annotation
* */
@Transactional
@Repository
public interface UserRepository extends MongoRepository<User, String> {
	
	@Query("{ 'userId' : ?0 }")
	Optional <User> findById(String userId);
	
	User save(User user);
	

}
