package com.library.library.entity;

import com.library.library.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Base64;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "BOOK")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (name = "NAME")
    private String name;

    @Column(name = "AUTHOR" )
    private String author;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column (name = "GENRE")
    @Enumerated(EnumType.STRING)
    private Genre genre;

}
