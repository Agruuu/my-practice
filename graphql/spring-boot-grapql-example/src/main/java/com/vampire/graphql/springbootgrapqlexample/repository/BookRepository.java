package com.vampire.graphql.springbootgrapqlexample.repository;

import com.vampire.graphql.springbootgrapqlexample.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
public interface BookRepository extends JpaRepository<Book, String> {
}
