package com.example.springPagination.service;

import com.example.springPagination.model.Post;

import java.util.List;

/**
 * created by Atiye Mousavi
 * Date: 9/12/2021
 * Time: 12:28 PM
 */

public interface IPostService {
    List<Post> getPostList(int page, int size, String sortDir, String sort);

    void updatePost(Post post);

    Post createPost(Post post);

    Post getPostById(Long id);
}
