package com.example.demo.librairie.repository;

import com.example.demo.librairie.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByTitleContainingIgnorCase(String title);

    List<Book> findByDatePublication(String datePublication);

}
