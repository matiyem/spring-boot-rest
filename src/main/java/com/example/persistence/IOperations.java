package com.example.persistence;


import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

/**
 * created by Atiye Mousavi
 * Date: 9/11/2021
 * Time: 2:25 PM
 */
public interface IOperations<T extends Serializable> {

    T findById(final long id);

    List<T> findAll();

    Page<T> findPaginated(int page, int size);

    T creat(final T entity);
    T update(final T entity);
    void delete (final T entity);
    void deleteById(final long entityId);
}
