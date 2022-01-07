package com.example.springPagination.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * created by Atiye Mousavi
 * Date: 9/12/2021
 * Time: 12:32 PM
 */

@Entity
@Data
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String timezone;
}
