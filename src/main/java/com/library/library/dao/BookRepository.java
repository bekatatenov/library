package com.library.library.dao;

import com.library.library.entity.Book;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    public Book getByName(String name);
    public List<Book> getBooksByNameIsContainingIgnoreCase(String name, Sort sort);
    public boolean existsByName(String name);

}
