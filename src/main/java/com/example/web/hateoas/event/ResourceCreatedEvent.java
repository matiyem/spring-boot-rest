package com.example.web.hateoas.event;

import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

/**
 * created by Atiye Mousavi
 * Date: 9/13/2021
 * Time: 6:12 PM
 */
public class ResourceCreatedEvent extends ApplicationEvent {
    private final HttpServletResponse response;
    private final long idOfNewResource;


    public ResourceCreatedEvent(final Object source,final HttpServletResponse response,final long idOfNewResource) {
        super(source);
        this.response=response;
        this.idOfNewResource=idOfNewResource;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public long getIdOfNewResource() {
        return idOfNewResource;
    }
}
