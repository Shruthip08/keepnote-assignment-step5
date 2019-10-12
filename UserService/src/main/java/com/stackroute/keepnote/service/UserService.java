package com.stackroute.keepnote.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exceptions.UserAlreadyExistsException;
import com.stackroute.keepnote.exceptions.UserNotFoundException;
import com.stackroute.keepnote.model.User;

public interface UserService {

	/*
	 * Should not modify this interface. You have to implement these methods in
	 * corresponding Impl classes
	 */

	User registerUser(User user) throws UserAlreadyExistsException;

	    User updateUser(String userid, User user) throws UserNotFoundException;

	    boolean deleteUser(String userId) throws UserNotFoundException;


	    public User getUserById(String userId) throws UserNotFoundException;

		User updateUser(User user) throws UserNotFoundException;
}
