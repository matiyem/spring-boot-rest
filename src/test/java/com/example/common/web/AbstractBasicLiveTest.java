package com.example.common.web;

import com.example.persistence.model.Foo;
import com.google.common.net.HttpHeaders;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.io.Serializable;
import java.util.List;

import static com.example.web.util.HTTPLinkHeaderUtil.extractURIByRel;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;



/**
 * created by Atiye Mousavi
 * Date: 9/14/2021
 * Time: 11:41 AM
 */

public abstract class AbstractBasicLiveTest<T extends Serializable> extends AbstractLiveTest<T> {
    public AbstractBasicLiveTest(Class<T> clazz) {
        super(clazz);
    }

    @Test
    public void whenResourceAreRetrievedPage_then200IsRecevied() {
        create();
        final Response response = RestAssured.get(getURL() + "?page=0&size=10");

        assertThat(response.getStatusCode(), is(200));
    }

    @Test
    public void whenPageOfResourcesAreRetrievedOutOfBounds_then404IsReceived() {
        final String url = getURL() + "?page=" + randomNumeric(5) + "&size=10";
        final Response response = RestAssured.get(url);
        assertThat(response.getStatusCode(), is(404));
    }

    @Test
    public void givenResourcesExist_whenFirstPageIsRetrieved_thenPageContainsResources() {
        create();
        final Response response = RestAssured.given().accept(MediaType.APPLICATION_JSON_VALUE).get(getURL() + "?page=0&size=10");
        assertFalse(response.body().as(List.class).isEmpty());
    }

    @Test
    public void whenFirstPageOfResourcesAreRetrieved_thenSecondPageIsNext() {
        create();
        create();
        create();

        final Response response = RestAssured.get(getURL() + "?page=0&size=2");

        final String uriToNextPage = extractURIByRel(response.getHeader(HttpHeaders.LINK), "next");
        assertEquals(getURL() + "?page=1&size=2", uriToNextPage);
    }
    @org.junit.Test
    public void whenFirstPageOfResourcesAreRetrieved_thenNoPreviousPage() {
        final Response response = RestAssured.get(getURL() + "?page=0&size=2");

        final String uriToPrevPage = extractURIByRel(response.getHeader(HttpHeaders.LINK), "prev");
        assertNull(uriToPrevPage);
    }

    @org.junit.Test
    public void whenSecondPageOfResourcesAreRetrieved_thenFirstPageIsPrevious() {
        create();
        create();

        final Response response = RestAssured.get(getURL() + "?page=1&size=2");

        final String uriToPrevPage = extractURIByRel(response.getHeader(HttpHeaders.LINK), "prev");
        assertEquals(getURL() + "?page=0&size=2", uriToPrevPage);
    }

    @org.junit.Test
    public void whenLastPageOfResourcesIsRetrieved_thenNoNextPageIsDiscoverable() {
        create();
        create();
        create();

        final Response first = RestAssured.get(getURL() + "?page=0&size=2");
        final String uriToLastPage = extractURIByRel(first.getHeader(HttpHeaders.LINK), "last");

        final Response response = RestAssured.get(uriToLastPage);

        final String uriToNextPage = extractURIByRel(response.getHeader(HttpHeaders.LINK), "next");
        assertNull(uriToNextPage);
    }

    // etags

    @org.junit.Test
    public void givenResourceExists_whenRetrievingResource_thenEtagIsAlsoReturned() {
        // Given
        final String uriOfResource = createAsUri();

        // When
        final Response findOneResponse = RestAssured.given()
                .header("Accept", "application/json")
                .get(uriOfResource);

        // Then
        assertNotNull(findOneResponse.getHeader(HttpHeaders.ETAG));
    }

    @org.junit.Test
    public void givenResourceWasRetrieved_whenRetrievingAgainWithEtag_thenNotModifiedReturned() {
        // Given
        final String uriOfResource = createAsUri();
        final Response findOneResponse = RestAssured.given()
                .header("Accept", "application/json")
                .get(uriOfResource);
        final String etagValue = findOneResponse.getHeader(HttpHeaders.ETAG);

        // When
        final Response secondFindOneResponse = RestAssured.given()
                .header("Accept", "application/json")
                .headers("If-None-Match", etagValue)
                .get(uriOfResource);

        // Then
        assertTrue(secondFindOneResponse.getStatusCode() == 304);
    }

    @org.junit.Test
    public void givenResourceWasRetrievedThenModified_whenRetrievingAgainWithEtag_thenResourceIsReturned() {
        // Given
        final String uriOfResource = createAsUri();
        final Response firstFindOneResponse = RestAssured.given()
                .header("Accept", "application/json")
                .get(uriOfResource);
        final String etagValue = firstFindOneResponse.getHeader(HttpHeaders.ETAG);
        final long createdId = firstFindOneResponse.jsonPath().getLong("id");

        Foo updatedFoo = new Foo("updated value");
        updatedFoo.setId(createdId);
        Response updatedResponse = RestAssured.given().contentType(ContentType.JSON).body(updatedFoo)
                .put(uriOfResource);
        assertThat(updatedResponse.getStatusCode() == 200);

        // When
        final Response secondFindOneResponse = RestAssured.given()
                .header("Accept", "application/json")
                .headers("If-None-Match", etagValue)
                .get(uriOfResource);

        // Then
        assertTrue(secondFindOneResponse.getStatusCode() == 200);
    }

    @org.junit.Test
    @Ignore("Not Yet Implemented By Spring - https://jira.springsource.org/browse/SPR-10164")
    public void givenResourceExists_whenRetrievedWithIfMatchIncorrectEtag_then412IsReceived() {
        // Given
        final String uriOfResource = createAsUri();

        // When
        final Response findOneResponse = RestAssured.given()
                .header("Accept", "application/json")
                .headers("If-Match", randomAlphabetic(8))
                .get(uriOfResource);

        // Then
        assertTrue(findOneResponse.getStatusCode() == 412);
    }
}
