package com.qingwenwei.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.qingwenwei.service.PostService;

@Controller
public class HomeController {
	
	// this variable should be declared in configuration file
	private static Integer pageSize = 10;
	
	@Autowired
	private PostService postService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getHomePostsByPage(Model model, @RequestParam(value = "p", required = false) Integer pageNum) {
		int currPage = pageNum == null ? 1 : pageNum;
		Map<String, Object> attributes = this.postService.findPostsByPage(currPage, pageSize);
		if (null == attributes) {
			return "error/404";
		}
		model.addAllAttributes(attributes);
		return "forum/home";
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String forbidden() {
		return "error/403";
	}

}