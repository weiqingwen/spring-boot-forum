package com.qingwenwei.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qingwenwei.constant.PageMessage;
import com.qingwenwei.persistence.dao.CategoryMapper;
import com.qingwenwei.persistence.dao.CommentMapper;
import com.qingwenwei.persistence.dao.PostMapper;
import com.qingwenwei.persistence.dao.UserMapper;
import com.qingwenwei.persistence.model.Category;
import com.qingwenwei.persistence.model.Comment;
import com.qingwenwei.persistence.model.Post;
import com.qingwenwei.persistence.model.User;
import com.qingwenwei.service.PostService;
import com.qingwenwei.web.dto.CommentDto;
import com.qingwenwei.web.dto.PostDto;

@Service("postService")
@Transactional
public class PostServiceImpl implements PostService {

	private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

	// private static String CACHE_ID_PREFIX = "POST_";

	// private static int EXPIRATION_TIME_IN_MINUTES = 15;

	// @Autowired
	// private RedisTemplate<String, Post> redisTemplate;

	@Autowired
	private PostMapper postMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private CategoryMapper categoryMapper;

	@Autowired
	private CommentMapper commentMapper;

	@Override
	public void save(Post post) {
		this.postMapper.save(post);
	}

	@Override
	public void delete(Long postId) {
		this.postMapper.delete(postId);
	}

	@Override
	public void update(Post post) {
		this.postMapper.update(post);
	}

	@Override
	public Post findById(Long id) {
		// left for future implementation
		//
		// ValueOperations<String, Post> operations = redisTemplate.opsForValue();
		// String key = CACHE_ID_PREFIX + id;
		//
		// // retrieve from cache if exists in cache
		// boolean hasKey = redisTemplate.hasKey(key);
		// if (hasKey) {
		// Post post = (Post) operations.get(key);
		// logger.info("PostServiceImpl.findById() : retrieve from cache >> id: " +
		// post.getId());
		// return post;
		// }
		//
		// // retrieve from database if not exists in cache
		// Post post = this.postMapper.findById(id);
		// operations.set(key, post, EXPIRATION_TIME_IN_MINUTES, TimeUnit.MINUTES);
		// logger.info("PostServiceImpl.findById() : retrieve from database >> id: " +
		// post.getId());

		Post post = this.postMapper.findById(id);
		return post;
	}

	@Override
	public Map<String, Object> findPosts() {
		List<Post> posts = this.postMapper.findAll();
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("posts", posts);
		return attributes;
	}

	@Override
	public Map<String, Object> findPostDetailsAndCommentsByPostId(Long postId) {
		Post post = this.postMapper.findById(postId);
		if (null == post) {
			return null;
		}
		List<Comment> comments = this.commentMapper.findCommentsByPostId(postId);
		// increase hit count by one
		post.setHitCount(post.getHitCount() == null ? 1 : post.getHitCount() + 1);
		this.postMapper.update(post);
		// load attributes map
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("post", post);
		attributes.put("title", post.getTitle());
		attributes.put("comments", comments);
		attributes.put("commentDto", new CommentDto());
		return attributes;
	}

	@Override
	public Post createNewPost(PostDto newPostForm) {
		// find authenticated user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User user = this.userMapper.findByUsername(username);
		// find category
		String categoryName = newPostForm.getCategory().replace(",", "");
		Category category = this.categoryMapper.findByName(categoryName);
		// construct new post
		Post post = new Post();
		post.setTitle(newPostForm.getTitle());
		post.setBody(newPostForm.getBody());
		post.setCategory(category);
		post.setDateCreated(new Timestamp(System.currentTimeMillis()));
		post.setUser(user);
		return post;
	}

	@Override
	public Map<String, Object> findPostsByPage(int currPage, int pageSize) {
		// find posts by page
		PageHelper.startPage(currPage, pageSize);
		List<Post> posts = this.postMapper.findAll();
		PageInfo<Post> postsPageInfo = new PageInfo<>(posts);
		// find categories
		List<Category> categories = this.categoryMapper.findAll();
		// construct attributes map
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("title", PageMessage.MESSAGE_HOMEPAGE_TITLE_CN);
		attributes.put("categories", categories);
		attributes.put("posts", postsPageInfo.getList());
		attributes.put("pageNum", postsPageInfo.getPageNum());
		attributes.put("isFirstPage", postsPageInfo.isIsFirstPage());
		attributes.put("isLastPage", postsPageInfo.isIsLastPage());
		attributes.put("totalPages", postsPageInfo.getPages());
		attributes.put("pageType", "homePage");
		return attributes;
	}

	@Override
	public Map<String, Object> findPostsListByCategoryByPage(String categoryName, int currPage, int pageSize) {
		// find posts by page
		PageHelper.startPage(currPage, pageSize);
		List<Post> posts = this.postMapper.findPostsByCategory(categoryName);
		PageInfo<Post> postsPageInfo = new PageInfo<>(posts);
		// find categories
		List<Category> categories = this.categoryMapper.findAll();
		// find category details
		Category category = this.categoryMapper.findByName(categoryName);
		// construct attributes map
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("title", PageMessage.MESSAGE_HOMEPAGE_TITLE_CN);
		attributes.put("category", category);
		attributes.put("categories", categories);
		attributes.put("posts", postsPageInfo.getList());
		attributes.put("pageNum", postsPageInfo.getPageNum());
		attributes.put("isFirstPage", postsPageInfo.isIsFirstPage());
		attributes.put("isLastPage", postsPageInfo.isIsLastPage());
		attributes.put("totalPages", postsPageInfo.getPages());
		attributes.put("pageType", "categoryPage");
		return attributes;
	}

	@Override
	public int deletePostAndComments(Long postId) {
		if (null == postId) {
			return 0;
		}
		// delete comment related to post
		int commentDeletedRows = this.commentMapper.deleteCommentsByPostId(postId);
		// delete post by id
		int postDeletedRows = this.postMapper.delete(postId);
		return postDeletedRows + commentDeletedRows;
	}

	@Override
	public Map<String, Object> findPostsBetweenDateRange(String start, String end) {
		if (null == start && null == end) {
			return null;
		}
		List<Post> posts = this.postMapper.findPostsBetweenRange(start + " 00:00:00", end + " 23:59:59");
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("posts", posts);
		return attributes;
	}

}
