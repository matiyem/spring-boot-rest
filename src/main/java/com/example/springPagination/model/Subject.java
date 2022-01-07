package com.example.springPagination.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * created by Atiye Mousavi
 * Date: 9/12/2021
 * Time: 12:33 PM
 */

@Entity
@NoArgsConstructor
@Data
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

}
