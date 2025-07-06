package com.pramodpoojary.springTest.controller;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Storage")
public class Library {
    @Column(name = "book_name")
    private String book_name;
    @Column(name = "id")
    @Id
    private String id;
    @Column(name = "aisle")
    private Integer aisle;
    @Column(name = "isbin")
    private String isbin;
    @Column(name = "author")
    private String author;
}
