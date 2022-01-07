package com.example.test;

import java.io.Serializable;
import java.util.List;

/**
 * created by Atiye Mousavi
 * Date: 9/14/2021
 * Time: 11:49 AM
 */

public interface IMarshaller {
//public interface IMarshaller<T extends Serializable> {
    //برای ورودی t  یا باید بصورت زیر باید تعریف شود یا باید هدر کلاس را بصورت بالا تعریف کنیم تا بتوانیم <T> را برداریم
    <T> String encode(final T entity);

    <T> T decode(final String entityAsString, final Class<T> clazz);

    <T> List<T> decodeList(final String entitiesAsString, final Class<T> Clazz);

    String getMime();

}
