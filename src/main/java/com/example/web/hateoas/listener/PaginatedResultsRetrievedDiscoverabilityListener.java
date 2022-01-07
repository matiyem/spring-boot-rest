package com.example.web.hateoas.listener;

import com.example.web.hateoas.event.PaginatedResultsRetrievedEvent;
import com.example.web.util.LinkUtil;
import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.StringJoiner;

/**
 * created by Atiye Mousavi
 * Date: 9/14/2021
 * Time: 9:48 AM
 */
@SuppressWarnings({"rawtypes"})
@Component
public class PaginatedResultsRetrievedDiscoverabilityListener implements ApplicationListener<PaginatedResultsRetrievedEvent> {

    private static final String PAGE = "page";

    public PaginatedResultsRetrievedDiscoverabilityListener() {
        super();
    }


    @Override
    public void onApplicationEvent(PaginatedResultsRetrievedEvent ev) {
        Preconditions.checkNotNull(ev);
        addLinkHeaderOnPagedResourceRetrieval(ev.getUriBuilder(), ev.getResponse(), ev.getClazz(), ev.getPage(), ev.getTotalPages(), ev.getPageSize());
    }

    final void addLinkHeaderOnPagedResourceRetrieval(final UriComponentsBuilder uriBuilder, final HttpServletResponse response, final Class clazz, final int page, final int totalPages, final int pageSize) {
//        ر محدوده صفحه بندی ، برآوردن محدودیت HATEOAS REST به این معنی است که مشتری API قادر است صفحات بعدی و قبلی را بر اساس صفحه کنونی در ناوبری کشف کند. برای این منظور ، ما قصد داریم از سرصفحه HTTP hedear ، همراه با انواع پیوندهای "بعدی" ، "قبلی" ، "اول" و "آخرین" استفاده کنیم.
        //از uriBuilder  برای generate کردنuri ها استفاده میشود
        plural(uriBuilder, clazz);
        //از StringJoiner برای concat کردن لینک استفاده میشود
        final StringJoiner linkHeader = new StringJoiner(", ");
        if (hasNextPage(page, totalPages)) {
            final String uriForNextPage = constructNextPageUri(uriBuilder, page, pageSize);
            linkHeader.add(LinkUtil.createLinkHeader(uriForNextPage, LinkUtil.REL_NEXT));
        }
        if (hasPreviousPage(page)) {
            final String uriForPrevPage = constructPrevPageUri(uriBuilder, page, pageSize);
            linkHeader.add(LinkUtil.createLinkHeader(uriForPrevPage, LinkUtil.REL_PREV));
        }
        if (hasFirstPage(page)) {
            final String uriForFirstPage = constructFirstPageUri(uriBuilder, pageSize);
            linkHeader.add(LinkUtil.createLinkHeader(uriForFirstPage, LinkUtil.REL_FIRST));
        }
        if (hasLastPage(page, totalPages)) {
            final String uriForLastPage = constructLastPageUri(uriBuilder, totalPages, pageSize);
            linkHeader.add(LinkUtil.createLinkHeader(uriForLastPage, LinkUtil.REL_LAST));
        }

        if (linkHeader.length() > 0) {
            response.addHeader(HttpHeaders.LINK, linkHeader.toString());
        }
    }

    final String constructNextPageUri(final UriComponentsBuilder uriBuilder, final int page, final int size) {
        return uriBuilder.replaceQueryParam(PAGE, page + 1)
                .replaceQueryParam("size", size)
                .build()
                .encode()
                .toUriString();
    }
    final String constructPrevPageUri(final UriComponentsBuilder uriBuilder, final int page, final int size) {
        return uriBuilder.replaceQueryParam(PAGE, page - 1)
                .replaceQueryParam("size", size)
                .build()
                .encode()
                .toUriString();
    }

    final String constructFirstPageUri(final UriComponentsBuilder uriBuilder, final int size) {
        return uriBuilder.replaceQueryParam(PAGE, 0)
                .replaceQueryParam("size", size)
                .build()
                .encode()
                .toUriString();
    }

    final String constructLastPageUri(final UriComponentsBuilder uriBuilder, final int totalPages, final int size) {
        return uriBuilder.replaceQueryParam(PAGE, totalPages)
                .replaceQueryParam("size", size)
                .build()
                .encode()
                .toUriString();
    }
    final boolean hasLastPage(final int page, final int totalPages) {
        return (totalPages > 1) && hasNextPage(page, totalPages);
    }


    final boolean hasPreviousPage(final int page) {
        return page > 0;
    }
    final boolean hasFirstPage(final int page){
        return hasPreviousPage(page);
    }

    final boolean hasNextPage(final int page, final int totalPages) {
        return page < (totalPages - 1);
    }

    protected void plural(final UriComponentsBuilder uriBuilder, final Class clazz) {
        final String resourceName = clazz.getSimpleName().toLowerCase() + "s";
        uriBuilder.path("/" + resourceName);
    }
}
