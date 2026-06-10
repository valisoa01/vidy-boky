package com.example.demo.librairie.controller;

import com.example.demo.librairie.entity.Book;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.example.demo.librairie.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class BookController {
  private final BookService bookService;

  @GetMapping
  public ResponseEntity<List<Book>> getAll() {
    return ResponseEntity.ok(bookService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Book> getById(@PathVariable UUID id) {
    return bookService
        .getById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/search/title")
  public ResponseEntity<List<Book>> getByTitle(@RequestParam String title) {
    return ResponseEntity.ok(bookService.getByTitle(title));
  }

  @GetMapping("/search/gender/{genderId}")
  public ResponseEntity<List<Book>> getByGender(@PathVariable UUID genderId) {
    return ResponseEntity.ok(bookService.getByGender(genderId));
  }

  @GetMapping("/search/date")
  public ResponseEntity<List<Book>> getByDate(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
    return ResponseEntity.ok(bookService.getByDate(date));
  }

  @PostMapping
  public ResponseEntity<Book> create(@RequestParam Book book) {
    return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(book));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Book> update(@PathVariable UUID id, @RequestBody Book book) {
    return ResponseEntity.ok(bookService.update(id, book));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    bookService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
