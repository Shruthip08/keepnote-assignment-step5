package com.stackroute.keepnote.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stackroute.keepnote.exception.CategoryDoesNoteExistsException;
import com.stackroute.keepnote.exception.CategoryNotCreatedException;
import com.stackroute.keepnote.exception.CategoryNotFoundException;

import com.stackroute.keepnote.model.Category;

import com.stackroute.keepnote.repository.CategoryRepository;


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

public class CategoryServiceImpl implements CategoryService {

	/*
	 * Autowiring should be implemented for the CategoryRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	
	private CategoryRepository categoryRepository;
	@Autowired
	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	/*
	 * This method should be used to save a new category.Call the corresponding
	 * method of Respository interface.
	 */
	public Category createCategory(Category category) throws CategoryNotCreatedException {

		Optional<Category> currentCategory = this.categoryRepository.findById(category.getId());
		if (currentCategory != null) {
			this.categoryRepository.save(category);
			return category;
		} else {
			throw new CategoryNotCreatedException("Category was not created");
		}
	}

	/*
	 * This method should be used to delete an existing category.Call the
	 * corresponding method of Respository interface.
	 */
	@Override
	public boolean deleteCategory(String categoryId) throws CategoryDoesNoteExistsException {

		boolean status = false;
		Optional<Category> optional = this.categoryRepository.findById(categoryId);
		if(optional.isPresent()) {
			
			this.categoryRepository.delete(optional.get());
			status = true;
		}
		else {
			throw new CategoryDoesNoteExistsException("Category not found");
		}
		
		return status;
	}

	/*
	 * This method should be used to update a existing category.Call the
	 * corresponding method of Respository interface.
	 */
	@Override
	public Category updateCategory(Category category, String categoryId) throws CategoryNotFoundException{
		
			Optional<Category> optional = this.categoryRepository.findById(category.getId());
			System.out.println("optional IN SERVICE IMPL" + optional);
			if(optional==null) {
				
				throw null;
			}else {
				this.categoryRepository.save(category);
				return category;
			}
	}
			

	/*
	 * This method should be used to get a category by categoryId.Call the
	 * corresponding method of Respository interface.
	 */
	@Override
	public Category getCategoryById(String categoryId) throws CategoryNotFoundException {
		
		Optional<Category> result = this.categoryRepository.findById(categoryId);
		 return result.orElseThrow(() -> new NoSuchElementException(categoryId));
}

	/*
	 * This method should be used to get a category by userId.Call the corresponding
	 * method of Respository interface.
	 */
	@Override
	public List<Category> getAllCategoryByUserId(String userId) {
		
		Optional<Category> result1 = this.categoryRepository.findById(userId);
		if(result1 !=null) 
			return this.categoryRepository.findAllCategoryByCategoryCreatedBy(userId);
		return this.categoryRepository.findAllCategoryByCategoryCreatedBy(userId);
	}

}
