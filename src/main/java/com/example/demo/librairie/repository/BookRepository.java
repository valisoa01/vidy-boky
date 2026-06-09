package com.example.demo.librairie.repository;

import com.example.demo.librairie.entity.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

  List<Book> findByTitleContainingIgnorCase(String title);

  List<Book> findByDatePublication(String datePublication);
}
