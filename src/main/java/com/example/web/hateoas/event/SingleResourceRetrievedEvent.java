package com.example.web.hateoas.event;

import javafx.application.Application;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

/**
 * created by Atiye Mousavi
 * Date: 9/13/2021
 * Time: 2:44 PM
 */
public class SingleResourceRetrievedEvent extends ApplicationEvent {

    private final HttpServletResponse response;
    public SingleResourceRetrievedEvent(final Object source, final HttpServletResponse response) {
        super(source);
        this.response=response;
    }
    public HttpServletResponse getResponse(){
        return response;
    }
}
