package com.example.springPagination.repository;

import com.example.springPagination.model.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * created by Atiye Mousavi
 * Date: 9/12/2021
 * Time: 12:54 PM
 */

public interface SubjectRepository extends PagingAndSortingRepository<Subject,Long> {

    //اگر می خواهیم صفحه بندی را در custom repository خود پیاده سازی کنیم ، باید یک پارامتر اضافی Pageable را ارسال کنیم و مطمئن شویم که API یک page را برمی گرداند:
    //url که برای متد زیر در خروجی میبینیم مانند زیر است
    //  "href" : "http://localhost:8080/subjects/search/nameContains{?name,page,size,sort}",



    //برای custom کردن url و همچنین مپ کردن بر روی یک repository است
    //ورودی descriptin شرح collection resource است
    //ورودی exported یک flag است  که آیا export شده است یا خیر
    //ورودی path برای بخشی که قرار است روی آن پخش شود
    //ورودی rel زمانی استفاده میشود برای generate کردن یک link به این resource
    @RestResource(path = "nameContains")
    public Page<Subject> findByNameContaining(@Param("name") String name, Pageable p);
}
