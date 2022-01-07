package com.example.web.hateoas.listener;

import com.example.web.hateoas.event.ResourceCreatedEvent;
import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

/**
 * created by Atiye Mousavi
 * Date: 9/14/2021
 * Time: 10:57 AM
 */
@Component
public class ResourceCreatedDiscoverabilityListener implements ApplicationListener<ResourceCreatedEvent> {
    @Override
    public void onApplicationEvent(final ResourceCreatedEvent resourceCreatedEvent) {
        Preconditions.checkNotNull(resourceCreatedEvent);

        final HttpServletResponse response=resourceCreatedEvent.getResponse();
        final long idOfNewResource=resourceCreatedEvent.getIdOfNewResource();
        addLinkHeaderOnResourceCreation(response, idOfNewResource);

    }
    void addLinkHeaderOnResourceCreation(final HttpServletResponse response,final long idOfNewResource){
        final URI uri= ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{idOfNewResource}").buildAndExpand(idOfNewResource).toUri();
        response.setHeader(HttpHeaders.LOCATION, uri.toASCIIString());

    }
}
