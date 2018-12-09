package com.qingwenwei.persistence.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.qingwenwei.persistence.model.Post;

@Mapper
public interface PostMapper {

	int save(Post post);

	int delete(Long postId);

	int update(Post post);

	Post findById(Long postId);

	List<Post> findAll();

	List<Post> findPostsByCategory(String categoryName);

	List<Post> findPostsByUserId(Long userId);

	List<Post> findPostsBetweenRange(@Param("startDateStr")String startDate, @Param("endDateStr")String endDate);

	Long countNumOfPostsByCategoryId(Long categoryId);

	Long countNumOfPostsByMonth(Integer month);
	
}