package com.qingwenwei.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.qingwenwei.service.PostService;

@Controller
public class CategoryController {

	private static Integer pageSize = 10;

	@Autowired
	private PostService postService;

	@RequestMapping(value = "/category/{categoryName}", method = RequestMethod.GET)
	public String getCategoryPostsByPage(@PathVariable String categoryName, Model model,
			@RequestParam(value = "p", required = false) Integer pageNum) {
		if (null == categoryName) {
			return "error/404";
		}
		int currPage = pageNum == null ? 1 : pageNum;
		Map<String, Object> attributes = this.postService.findPostsListByCategoryByPage(categoryName, currPage,
				pageSize);
		if (null == attributes) {
			return "error/404";
		}
		model.addAllAttributes(attributes);
		return "forum/home";
	}

}
