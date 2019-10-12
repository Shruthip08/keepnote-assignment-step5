package com.stackroute.keepnote.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.NoteUser;


/*
* This class is implementing the MongoRepository interface for Note.
* Annotate this class with @Repository annotation
* */
@Repository
public interface NoteRepository extends MongoRepository<NoteUser, String> {

	@Query("{ 'userId' : ?0 }")
	Optional <NoteUser> findById(String userId);
	

	
	
	@SuppressWarnings("unchecked")
	NoteUser save(NoteUser noteUser);

}