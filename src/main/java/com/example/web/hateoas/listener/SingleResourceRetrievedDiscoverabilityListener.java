package com.example.web.hateoas.listener;

import com.example.web.hateoas.event.SingleResourceRetrievedEvent;
import com.example.web.util.LinkUtil;
import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

/**
 * created by Atiye Mousavi
 * Date: 9/14/2021
 * Time: 11:38 AM
 */
@Component
public class SingleResourceRetrievedDiscoverabilityListener implements ApplicationListener<SingleResourceRetrievedEvent> {
    @Override
    public void onApplicationEvent(SingleResourceRetrievedEvent singleResourceRetrievedEvent) {
        Preconditions.checkNotNull(singleResourceRetrievedEvent);

        final HttpServletResponse response = singleResourceRetrievedEvent.getResponse();
        addLinkHeaderOnSingleResourceRetrieval(response);
    }
    void addLinkHeaderOnSingleResourceRetrieval(final HttpServletResponse response) {
        final String requestURL = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri().toASCIIString();
        final int positionOfLastSlash = requestURL.lastIndexOf("/");
        final String uriForResourceCreation = requestURL.substring(0, positionOfLastSlash);

        final String linkHeaderValue = LinkUtil.createLinkHeader(uriForResourceCreation, "collection");
        response.addHeader(HttpHeaders.LINK, linkHeaderValue);
    }
}
