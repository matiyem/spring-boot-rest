package com.example.springPagination.repository;

import com.example.springPagination.model.Post;
import com.example.springPagination.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * created by Atiye Mousavi
 * Date: 9/12/2021
 * Time: 12:44 PM
 */
//برای استفاده از متد های paging باید از PagingAndSortingRepository استفاده شود
    //بصورت دیفالت پیج سایز 20 است اگر مقدار کمتر یا بیشتر میخواهیم باید مقدار آن را set کنید
public interface PostRepository extends JpaRepository<Post,Long>, PagingAndSortingRepository<Post,Long>{

    @Query("select u from Post u where u.userName=:userName")
    Page<Post> findByUser(@Param("userName") String userName, Pageable pageable);

    default Page<Post> findByUser(User user,Pageable pageReq){
        return findByUser(user.getName(),pageReq);
    }
}

