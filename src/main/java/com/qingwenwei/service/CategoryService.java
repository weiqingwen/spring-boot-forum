package com.qingwenwei.service;

import java.util.List;
import java.util.Map;

import com.qingwenwei.persistence.model.Category;

public interface CategoryService {

	Map<String, Object> getNewPostPageWithCategoryName(String categoryName);

	Map<String, Object> getNewPostPageWithCategorySelect();

	int save(Category category);

	List<Category> findAll();

}
