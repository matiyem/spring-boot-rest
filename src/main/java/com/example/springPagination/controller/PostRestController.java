package com.example.springPagination.controller;

import com.example.springPagination.dto.PostDto;
import com.example.springPagination.model.Post;
import com.example.springPagination.service.IPostService;
import com.example.springPagination.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * created by Atiye Mousavi
 * Date: 9/12/2021
 * Time: 12:26 PM
 */
@RestController
@RequestMapping("/posts")
public class PostRestController {
    @Autowired
    private IPostService postService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseBody
    public List<PostDto> getPosts(
            @PathVariable("page") int page,
            @PathVariable("size") int size,
            @PathVariable("sortDir") String sortDir,
            @PathVariable("sort") String sort) {

        List<Post> posts = postService.getPostList(page, size, sortDir, sort);
        return posts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public PostDto createPost(@RequestBody PostDto postDto) throws ParseException {
        Post post = convertToEntity(postDto);
        Post postCreated = postService.createPost(post);
        return convertToDto(postCreated);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public PostDto getPost(@PathVariable("id") Long id) {
        return convertToDto(postService.getPostById(id));
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePost(@RequestBody PostDto postDto) throws ParseException {
        Post post = convertToEntity(postDto);
        postService.updatePost(post);
    }


    private PostDto convertToDto(Post post) {
        PostDto postDto = modelMapper.map(post, PostDto.class);
        postDto.setSubmissionDate(post.getSubmissionDate(),
                userService.getCurrentUser().getPreference().getTimezone());
        return postDto;
    }

    private Post convertToEntity(PostDto postDto) throws ParseException {
        Post post = modelMapper.map(postDto, Post.class);
        post.setSubmissionDate(postDto.getSubmissionDateConverted(
                userService.getCurrentUser().getPreference().getTimezone()));

        if (postDto.getId() != null) {
            Post oldPost = postService.getPostById(postDto.getId());
            post.setRedditID(oldPost.getRedditID());
            post.setSent(oldPost.isSent());
        }
        return post;
    }
}
