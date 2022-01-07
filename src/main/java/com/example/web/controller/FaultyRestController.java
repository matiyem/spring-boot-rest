package com.example.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by Atiye Mousavi
 * Date: 9/13/2021
 * Time: 1:35 PM
 */
@RestController
public class FaultyRestController {

    @GetMapping("/exception")
    public ResponseEntity<Void> requestWithException(){
        throw new RuntimeException("Error in the faulty controller");

    }
}
