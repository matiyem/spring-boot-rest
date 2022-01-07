package com.example.springpagination;

import com.example.springPagination.dto.PostDto;
import com.example.springPagination.model.Post;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostDtoUnitTest {
    
    private ModelMapper modelMapper = new ModelMapper();
    
    @Test
    public void whenConvertPostEntityToPostDto_thenCorrect() {
        Post post = new Post();
        post.setId(1L);
        post.setTitle(randomAlphabetic(6));
        post.setUrl("www.test.com");
        post.setUserName("atiye");
 
        PostDto postDto = modelMapper.map(post, PostDto.class);
        assertEquals(post.getId(), postDto.getId());
        assertEquals(post.getTitle(), postDto.getTitle());
        assertEquals(post.getUrl(), postDto.getUrl());
        //این دوتا فیلد اسمشون فرق داره دیباگ شود مشخص میشود که چطوری کانورت شده است
        assertEquals(post.getUserName(), postDto.getUser());
    }
 
    @Test
    public void whenConvertPostDtoToPostEntity_thenCorrect() {
        PostDto postDto = new PostDto();
        postDto.setId(1L);
        postDto.setTitle(randomAlphabetic(6));
        postDto.setUrl("www.test.com");
 
        Post post = modelMapper.map(postDto, Post.class);
        assertEquals(postDto.getId(), post.getId());
        assertEquals(postDto.getTitle(), post.getTitle());
        assertEquals(postDto.getUrl(), post.getUrl());
    }
}