package com.example.springPagination.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * created by Atiye Mousavi
 * Date: 9/12/2021
 * Time: 12:29 PM
 */
@Entity
@Data
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String url;
    private String data;
    private String redditID;
    private Date submissionDate;
    private boolean sent;
    private String userName;
}
