
package com.stackroute.keepnote.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exceptions.UserAlreadyExistsException;
import com.stackroute.keepnote.exceptions.UserNotFoundException;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.service.UserService;

/*
 * As in this assignment, we are working on creating RESTful web service, hence annotate
 * the class with @RestController annotation. A class annotated with the @Controller annotation
 * has handler methods which return a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
@Transactional
public class UserController {

	/*
	 * Autowiring should be implemented for the UserService. (Use
	 * Constructor-based autowiring) Please note that we should not create an
	 * object using the new keyword
	 */
	@Autowired
	UserService userService;
	
	private ResponseEntity<?> responseEntity;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	/*
	 * Define a handler method which will create a specific user by reading the
	 * Serialized object from request body and save the user details in the
	 * database. This handler method should return any one of the status
	 * messages basis on different situations: 1. 201(CREATED) - If the user
	 * created successfully. 2. 409(CONFLICT) - If the userId conflicts with any
	 * existing user
	 * 
	 * This handler method should map to the URL "/user" using HTTP POST method
	 */
//	@PostMapping(value = "/api/v1/user")
//	public ResponseEntity<User> registerUser(@RequestBody User user) {
//		System.out.println("Calling registerUser");
//		try {
//			userService.registerUser(user);
//		} catch (UserAlreadyExistsException e) {
//			// TODO Auto-generated catch block
//			return new ResponseEntity<User>(HttpStatus.CONFLICT);
//		
//		}
//
//		return new ResponseEntity<User>(user, HttpStatus.CREATED);
//	}
	@PostMapping(value = "/api/v1/user")
	public ResponseEntity<?> saveUser(@RequestBody User user) throws UserAlreadyExistsException{
		try {
			
			User saveduser= this.userService.registerUser(user);
			this.responseEntity = new ResponseEntity<>(saveduser, HttpStatus.CREATED);
		} catch (UserAlreadyExistsException e) {
			return this.responseEntity = new ResponseEntity<>("Conflict.", HttpStatus.CONFLICT);
		} 
		
		return responseEntity;
	}

	/*
	 * Define a handler method which will update a specific user by reading the
	 * Serialized object from request body and save the updated user details in
	 * a database. This handler method should return any one of the status
	 * messages basis on different situations: 1. 200(OK) - If the user updated
	 * successfully. 2. 404(NOT FOUND) - If the user with specified userId is
	 * not found.
	 * 
	 * This handler method should map to the URL "/api/v1/user/{id}" using HTTP
	 * PUT method.
	 */

	@PutMapping("/api/v1/user/{id}")
	public ResponseEntity<?> updateUser( @PathVariable String id, @RequestBody User user) throws UserNotFoundException{
		try {
					
			this.userService.updateUser(id, user);
			
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		} 
			
		
	}

	/*
	 * Define a handler method which will delete a user from a database. This
	 * handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the user deleted successfully from
	 * database. 2. 404(NOT FOUND) - If the user with specified userId is not
	 * found.
	 *
	 * This handler method should map to the URL "/api/v1/user/{id}" using HTTP
	 * Delete method" where "id" should be replaced by a valid userId without {}
	 */

	@DeleteMapping("/api/v1/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") String id) throws UserNotFoundException{
		try {
			this.userService.deleteUser(id);
			this.responseEntity = new ResponseEntity<>("User with Id:" + id +" is deleted successfully", HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
		}
		
		return this.responseEntity;
	}

	/*
	 * Define a handler method which will show details of a specific user. This
	 * handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the user found successfully. 2.
	 * 404(NOT FOUND) - If the user with specified userId is not found. This
	 * handler method should map to the URL "/api/v1/user/{id}" using HTTP GET
	 * method where "id" should be replaced by a valid userId without {}
	 */
	@GetMapping("/api/v1/user/{id}")
	public ResponseEntity<User> getUser(@PathVariable("id") String id) {
		User user = null;
		try {

			user = userService.getUserById(id);
			if (user == null) {
				return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			}

		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
}
