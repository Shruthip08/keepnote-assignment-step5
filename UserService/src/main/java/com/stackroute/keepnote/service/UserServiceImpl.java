
package com.stackroute.keepnote.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stackroute.keepnote.exceptions.UserAlreadyExistsException;
import com.stackroute.keepnote.exceptions.UserNotFoundException;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.repository.UserRepository;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service

public class UserServiceImpl implements UserService {

	/*
	 * Autowiring should be implemented for the UserRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	
	private UserRepository userRepository;
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	/*
	 * This method should be used to save a new user.Call the corresponding
	 * method of Respository interface.
	 */


	public User registerUser(User user) throws UserAlreadyExistsException {
	
		  User users = null;
		  
		  Optional<User> optional = this.userRepository.findById(user.getUserId());
		  if(optional.isPresent() ) {
			  throw new UserAlreadyExistsException("user exists already");
		  }
		  else {
			  users = this.userRepository.save(user);
			  return  user;
		  }

}
	
	

	/*
	 * This method should be used to update a existing user.Call the
	 * corresponding method of Respository interface.
	 */

	@Override
	public User updateUser(String userid, User user) throws UserNotFoundException  {
		Optional<User> optional = this.userRepository.findById(userid);
		System.out.println("updateuser service optional value");
		if(optional.isPresent()) {
			this.userRepository.save(optional.get());
			
		}
		else {
			throw new UserNotFoundException("user Not Found");
		}			
		
		return user;
		
	}

	/*
	 * This method should be used to delete an existing user. Call the
	 * corresponding method of Respository interface.
	 */

	
	@Override
	public boolean deleteUser(String id) throws UserNotFoundException {
		boolean status = false;
		Optional<User> optional = this.userRepository.findById(id);
		if(optional.isPresent()) {
			
			this.userRepository.delete(optional.get());
			status = true;
		}
		else {
			throw new UserNotFoundException("User not found");
		}
		return status;
	}

	
	/*
	 * This method should be used to get a user by userId.Call the corresponding
	 * method of Respository interface.
	 */
	public User getUserById(String userId) throws UserNotFoundException {

		User result = this.userRepository.findById(userId).get();
		System.out.println("User SERVICEIMPL ******" + result);
		if (result == null)
			throw new UserNotFoundException("UserNotFoundException");
		else 
			
			return result;

}


	public User findById(String userId) throws UserNotFoundException {
		// TODO Auto-generated method stub
		return userRepository.findById(userId).get();
	}
	@Override
	public User updateUser(User user) throws UserNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	

	}
