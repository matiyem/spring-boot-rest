package com.example.persistence.config;

import org.hibernate.dialect.H2Dialect;

/**
 * created by Atiye Mousavi
 * Date: 9/11/2021
 * Time: 10:25 AM
 */
public class CustomH2Dialect extends H2Dialect {

    @Override
    public boolean dropConstraints() {
        return true;
    }

    @Override
    public boolean supportsIfExistsAfterAlterTable() {
        return true;
    }
}
