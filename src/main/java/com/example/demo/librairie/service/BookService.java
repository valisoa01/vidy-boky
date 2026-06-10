package com.example.demo.librairie.service;

import com.example.demo.librairie.entity.Book;
import com.example.demo.librairie.entity.BookFormat;
import com.example.demo.librairie.repository.BookRepository;

import java.time.LocalDate;
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

  public Book getById(java.util.UUID id) {
    return bookRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
  }

  public List<BookFormat> getFormatByLivre(UUID bookId) {
    Book book =
            bookRepository
                    .findById(bookId)
                    .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));
    return book.getFormats();
  }

  public List<Book> getLivreByTitle(String title) {
    return bookRepository.findByTitleContainingIgnoreCase(title);
  }

  public List<Book> getLivreByGenre(UUID genreId) {
    return bookRepository.findByGenresId(genreId);
  }

  public List<Book> getLivreByDate(LocalDate date) {
    return bookRepository.findByPublicationDate(date);
  }

}
