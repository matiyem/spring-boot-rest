package com.example.persistence.service;

import com.example.persistence.IOperations;
import com.example.persistence.model.Foo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * created by Atiye Mousavi
 * Date: 9/11/2021
 * Time: 4:47 PM
 */
public interface IFooService extends IOperations<Foo> {
    Page<Foo> findPaginated(Pageable pageable);
}
