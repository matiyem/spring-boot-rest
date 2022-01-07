package com.example.requestResponceBody;

import com.example.service.ExampleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by Atiye Mousavi
 * Date: 9/11/2021
 * Time: 5:00 PM
 */
@RestController
@RequestMapping("/post")
@Slf4j
public class ExamplePostController {

    @Autowired
    ExampleService exampleService;

    @PostMapping("/request")
    public ResponseEntity postController(LoginForm loginForm){
        log.debug("POST received-serializing LoginForm: "+loginForm.getPassword()+" " + loginForm.getUserName());
        exampleService.fakeAuthentication(loginForm);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PostMapping("/response")
    public ResponseTransfer postResponseController(LoginForm loginForm){
        log.debug("POST received - serializing LoginForm: "+ loginForm.getPassword() + " " + loginForm.getUserName());
        return new ResponseTransfer("Thanks For Posting!!!");
    }

    @PostMapping(value = "/content",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseTransfer postResponseJsonContent(LoginForm loginForm){
        log.debug("POST received - serializing LoginForm: "+ loginForm.getPassword()+" "+loginForm.getUserName());
        return new ResponseTransfer("JSON Content!");
    }
    public ResponseTransfer postResponseXmlContent(LoginForm loginForm){
        log.debug("Post received - serializing LoginForm: " + loginForm.getPassword()+ " " + loginForm.getUserName());
        return  new ResponseTransfer("XML content!");
    }
}
