package com.example.web.controller;

import com.example.persistence.model.Foo;
import com.example.persistence.service.IFooService;
import com.example.web.exception.CustomException1;
import com.example.web.exception.CustomException2;
import com.example.web.exception.MyResourceNotFoundException;
import com.example.web.hateoas.event.PaginatedResultsRetrievedEvent;
import com.example.web.hateoas.event.ResourceCreatedEvent;
import com.example.web.hateoas.event.SingleResourceRetrievedEvent;
import com.example.web.util.RestPreconditions;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.List;

/**
 * created by Atiye Mousavi
 * Date: 9/13/2021
 * Time: 1:38 PM
 */
@RestController
@RequestMapping("/foos")
@Slf4j
public class FooController {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private IFooService service;

    // Note: the global filter overrides the ETag value we set here. We can still analyze its behaviour in the Integration Test.
    @GetMapping(value = "/{id}/custom-etag")
    public ResponseEntity<Foo> findByIdWithCustomEtag(@PathVariable("id") final long id, final HttpServletResponse response){
        //responseEntity قسمتی از (HATEOAS) است
        final Foo foo= RestPreconditions.checkFound(service.findById(id));

        eventPublisher.publishEvent(new SingleResourceRetrievedEvent(this,response));
        return ResponseEntity.ok().eTag(Long.toString(foo.getVersion())).body(foo);
    }
    // read - one
    @GetMapping(value = "/{id}")
    public Foo findById(@PathVariable("id") final Long id,final HttpServletResponse response){
        try{
            final Foo resourceById=RestPreconditions.checkFound(service.findById(id));
            eventPublisher.publishEvent(new SingleResourceRetrievedEvent(this,response));
            return resourceById;
        }catch (MyResourceNotFoundException exc){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Foo not Found",exc);
        }
    }
    // read - all
    @GetMapping
    public List<Foo> findAll(){
        return service.findAll();
    }
    @GetMapping(params = {"page","size"})
    public List<Foo> findPaginated(@RequestParam("page")final int page, @RequestParam("size")final int size
    , final UriComponentsBuilder uriBuilder,final HttpServletResponse response){
        final Page<Foo> resultPage = service.findPaginated(page, size);
        if (page>resultPage.getTotalPages()){
            throw new MyResourceNotFoundException();
        }
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(Foo.class,uriBuilder,response,page,resultPage.getTotalPages(),size));
        return resultPage.getContent();
    }
    @GetMapping("/pageable")
    public List<Foo> findPaginateWithPageable(Pageable pageable,final UriComponentsBuilder uriBuilder,final HttpServletResponse response){
        //pageable یک آبجکت است برای مپ کردن page size و همچنین برای sort کردن است
        final Page<Foo> resultPage=service.findPaginated(pageable);
        if (pageable.getPageNumber() >resultPage.getTotalPages()){
            throw new MyResourceNotFoundException();
        }
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(Foo.class,uriBuilder,response,pageable.getPageNumber(),resultPage.getTotalPages(),pageable.getPageSize()));
        return resultPage.getContent();
    }

    //write
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Foo create(@RequestBody final Foo resource,final HttpServletResponse response){
        Preconditions.checkNotNull(resource);
        final Foo foo=service.creat(resource);

        eventPublisher.publishEvent(new ResourceCreatedEvent(this,response,foo.getId()));
        return foo;
    }
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathParam("id") final Long id,@RequestBody final Foo resource){
        Preconditions.checkNotNull(resource);
        RestPreconditions.checkFound(service.findById(resource.getId()));
        service.update(resource);
    }
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)//برای مپ کردن response بر روی http code ها
    public void delete(@PathVariable("id") final Long id){
        service.deleteById(id);
    }

    @ExceptionHandler({CustomException1.class, CustomException2.class})
    public void handleException(final Exception ex){
        final String err="Application specific error handling";
        log.error(err,ex);
    }
}
