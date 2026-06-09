package com.example.demo.librairie.service;

import com.example.demo.librairie.entity.Book;
import com.example.demo.librairie.repository.BookRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
  private final BookRepository bookRepository;

  public List<Book> getAll() {
    return bookRepository.findAll();
  }

  public Book getById(UUID id) {
    return bookRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
  }
}
