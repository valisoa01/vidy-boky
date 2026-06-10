package com.example.demo.librairie.controller;

import com.example.demo.librairie.entity.Book;
import com.example.demo.librairie.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.ok(bookService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(bookService.getById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<Book>> getByTitle(@RequestParam String title) {
        return ResponseEntity.ok(bookService.getLivreByTitle(title));
    }

    @GetMapping("/search/gender/{genderId}")
    public ResponseEntity<List<Book>> getByGenre(@PathVariable("genderId") UUID genreId) {
        return ResponseEntity.ok(bookService.getLivreByGenre(genreId));
    }

    @GetMapping("/search/date")
    public ResponseEntity<List<Book>> getByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(bookService.getLivreByDate(date));
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createLivre(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable UUID id, @RequestBody Book book) {
        return ResponseEntity.ok(bookService.updateLivre(id, book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        bookService.deleteLivre(id);
        return ResponseEntity.noContent().build();
    }
}