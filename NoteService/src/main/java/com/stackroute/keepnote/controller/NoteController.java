
package com.stackroute.keepnote.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exception.NoteAlreadyExistsException;
import com.stackroute.keepnote.exception.NoteNotFoundExeption;

import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.NoteUser;

import com.stackroute.keepnote.service.NoteService;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
public class NoteController {
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	/*
	 * Autowiring should be implemented for the NoteService. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword
	 */
	
	private NoteService noteService;
	

	private ResponseEntity<?> responseEntity;
	@Autowired
	public NoteController(NoteService noteService) {
		this.noteService=noteService;
	}

	/*
	 * Define a handler method which will create a specific note by reading the
	 * Serialized object from request body and save the note details in the
	 * database.This handler method should return any one of the status messages
	 * basis on different situations: 
	 * 1. 201(CREATED) - If the note created successfully. 
	 * 2. 409(CONFLICT) - If the noteId conflicts with any existing user.
	 * 
	 * This handler method should map to the URL "/api/v1/note" using HTTP POST method
	 */
	@PostMapping(value = "/api/v1/note")
	public ResponseEntity<?> createNote(@RequestBody Note note) throws NoteNotFoundExeption{
		try {
			boolean savedNote = this.noteService.createNote(note);
		return new ResponseEntity<>(savedNote, HttpStatus.CREATED);
		}catch (NoteNotFoundExeption e){
			return new ResponseEntity<Note>(HttpStatus.CONFLICT);
		}
	}
	
	
	/*
	 * Define a handler method which will delete a note from a database.
	 * This handler method should return any one of the status messages basis 
	 * on different situations: 
	 * 1. 200(OK) - If the note deleted successfully from database. 
	 * 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 *
	 * This handler method should map to the URL "/api/v1/note/{id}" using HTTP Delete
	 * method" where "id" should be replaced by a valid noteId without {}
	 */
	@DeleteMapping("/api/v1/note/{userid}/{noteid}")

	public ResponseEntity<?> deleteNote(@PathVariable String userid,@PathVariable int noteid) throws NoteNotFoundExeption{
		this.noteService.deleteNote(userid,noteid);
		return this.responseEntity = new ResponseEntity<>("Note with Id:" + noteid + " is deleted with userid" + userid + "successfully", HttpStatus.OK);
		
	}
	
	@DeleteMapping("/api/v1/note/{userid}")

	public ResponseEntity<?> deleteAllNotes(@PathVariable String userid) throws NoteNotFoundExeption{
		try {
			this.noteService.deleteAllNotes(userid);
			return this.responseEntity = new ResponseEntity<>("Deleted all notes of user:" + userid +  "successfully", HttpStatus.OK);
		} catch (NoteNotFoundExeption e) {
			return new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
		}
		
	}
	/*
	 * Define a handler method which will update a specific note by reading the
	 * Serialized object from request body and save the updated note details in a
	 * database. 
	 * This handler method should return any one of the status messages
	 * basis on different situations: 
	 * 1. 200(OK) - If the note updated successfully.
	 * 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/note/{id}" using HTTP PUT method.
	 */
	@PutMapping("/api/v1/note/{noteid}/{userId}")
	public ResponseEntity<?> updateNote(@RequestBody Note note,@PathVariable int noteid,@PathVariable String userId) throws NoteNotFoundExeption{
		Note noteuser = null;
		try {
				noteuser = this.noteService.updateNote(note,noteid,userId);
			if (noteuser!=null)
				return new ResponseEntity<>(note, HttpStatus.OK);
		}catch (NoteNotFoundExeption e) {
			return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
	
		}
		return new ResponseEntity<>(note, HttpStatus.OK);
	}
	
	/*
	 * Define a handler method which will get us the all notes by a userId.
	 * This handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the note found successfully. 
	 * 
	 * This handler method should map to the URL "/api/v1/note" using HTTP GET method
	 */
			@GetMapping("/api/v1/note/{userid}/{noteid}")
			public ResponseEntity<?> getNoteById(@PathVariable String userid,@PathVariable int noteid) throws NoteNotFoundExeption{
				Note noteUser = null;
			try {

				noteUser = this.noteService.getNoteByNoteId(userid,noteid);
				if (noteUser != null) {
					return new ResponseEntity<Note>(noteUser,HttpStatus.OK);
				}

			} catch (NoteNotFoundExeption e) {
				// TODO Auto-generated catch block
				return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Note>(noteUser,HttpStatus.OK);
			}
	/*
	 * Define a handler method which will show details of a specific note created by specific 
	 * user. This handler method should return any one of the status messages basis on
	 * different situations: 
	 * 1. 200(OK) - If the note found successfully. 
	 * 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 * This handler method should map to the URL "/api/v1/note/{userId}/{noteId}" using HTTP GET method
	 * where "id" should be replaced by a valid reminderId without {}
	 * 
	 */
	@GetMapping("/api/v1/note/{userId}")
	public ResponseEntity<?> getAllNotesByUserId(@PathVariable("userId") String userId) {
		List<Note> notes = null;
		notes = this.noteService.getAllNoteByUserId(userId);
		
			return new ResponseEntity<List<Note>>( notes, HttpStatus.OK);
						
	}
	
}