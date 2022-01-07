package com.example.persistence.dao;

import com.example.persistence.model.Foo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * created by Atiye Mousavi
 * Date: 9/11/2021
 * Time: 2:08 PM
 */
public interface IFooDao extends JpaRepository<Foo,Long> {
}
