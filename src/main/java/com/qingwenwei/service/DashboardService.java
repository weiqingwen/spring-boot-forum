package com.qingwenwei.service;

import java.util.Map;

import com.qingwenwei.web.dto.PostDto;

public interface DashboardService {

	Map<String, Object> getDashboard(String tab, String start, String end);

	Map<String, Object> getPostEditJson(Long postId);

	Map<String, Object> editPost(PostDto newPostForm);

	Map<String, Object> getNumOfPostsByCategoriesForPieChart();

	Map<String, Object> getNumOfPostsByMonthForBarChart();

}
