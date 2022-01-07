package com.example.persistence.service.impl;

import com.example.persistence.dao.IFooDao;
import com.example.persistence.model.Foo;
import com.example.persistence.service.IFooService;
import com.example.persistence.service.common.AbstractService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * created by Atiye Mousavi
 * Date: 9/11/2021
 * Time: 4:56 PM
 */
@Service
@Transactional
public class FooService extends AbstractService<Foo> implements IFooService {

    @Autowired
    private IFooDao dao;

    public FooService(){super();}

    @Override
    public Page<Foo> findPaginated(Pageable pageable) {
        return dao.findAll(pageable);
    }

    @Override
    protected PagingAndSortingRepository<Foo, Long> getDao() {
        return dao;
    }

    @Override
    @Transactional
    public List<Foo> findAll() {
        return Lists.newArrayList(getDao().findAll());
    }
}
