package com.example.springPagination.model;

import lombok.Data;

import javax.persistence.*;

/**
 * created by Atiye Mousavi
 * Date: 9/12/2021
 * Time: 12:35 PM
 */
@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToOne
    Preference preference;
}
