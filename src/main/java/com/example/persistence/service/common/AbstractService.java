package com.example.persistence.service.common;

import com.example.persistence.IOperations;
import com.google.common.collect.Lists;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;


/**
 * created by Atiye Mousavi
 * Date: 9/11/2021
 * Time: 4:27 PM
 */
@Transactional
public abstract class AbstractService<T extends Serializable> implements IOperations<T> {

    @Override
    @Transactional(readOnly = true)
    public T findById(final long id) {
        return getDao().findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return Lists.newArrayList(getDao().findAll());
    }

    @Override
    public Page<T> findPaginated(final int page, final int size) {
        return getDao().findAll(PageRequest.of(page, size));
    }

    @Override
    public T creat(final T entity) {
        return getDao().save(entity);
    }

    @Override
    public T update(final T entity) {
        return getDao().save(entity);
    }

    @Override
    public void delete(final T entity) {
         getDao().delete(entity);
    }

    @Override
    public void deleteById(final long entityId) {
        getDao().deleteById(entityId);

    }
//موجودیت PagingAndSortingRepository روشهای خارج از جعبه را ارائه می دهد که از پارامتر Pageable نیز پشتیبانی می کند.
    protected abstract PagingAndSortingRepository<T, Long> getDao();
}
