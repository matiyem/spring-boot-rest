package com.example.springPagination.service;

import com.example.springPagination.model.Post;
import com.example.springPagination.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by Atiye Mousavi
 * Date: 9/12/2021
 * Time: 1:10 PM
 */
@Service
public class PostService implements IPostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private IUserService userService;

    @Override
    public List<Post> getPostList(int page, int size, String sortDir, String sort) {
        //همه API هایی که PagingAndSortingRepository را پیاده سازی می کنند ، یک page را برمی گردانند. در صورتی که لازم باشد لیست از page برگردانیم ، API getContent () یک لیست  ازpage را برمیگرداند.
        PageRequest pageReq =
                PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sort);

        Page<Post> posts = postRepository.findByUser(userService.getCurrentUser(), pageReq);
        return posts.getContent();
    }

    @Override
    public void updatePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.getOne(id);
    }
}
