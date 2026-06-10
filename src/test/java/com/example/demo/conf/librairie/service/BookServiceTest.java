package com.example.demo.conf.librairie.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.librairie.entity.Book;
import com.example.demo.librairie.repository.BookRepository;
import com.example.demo.librairie.service.BookService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

  @Mock private BookRepository bookRepository;

  @InjectMocks private BookService bookService;

  @Test
  void getAll_ShouldReturnListOfBooks() {
    Book book1 = Book.builder().title("Livre 1").build();
    Book book2 = Book.builder().title("Livre 2").build();
    when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

    List<Book> result = bookService.getAll();

    assertEquals(2, result.size());
    assertEquals("Livre 1", result.get(0).getTitle());
    verify(bookRepository, times(1)).findAll();
  }

  @Test
  void getById_WhenBookExists_ShouldReturnBook() {
    UUID bookId = UUID.randomUUID();
    Book expectedBook = Book.builder().id(bookId).title("Livre Unique").build();
    when(bookRepository.findById(bookId)).thenReturn(Optional.of(expectedBook));

    Book result = bookService.getById(bookId);

    assertNotNull(result);
    assertEquals(bookId, result.getId());
    assertEquals("Livre Unique", result.getTitle());
    verify(bookRepository, times(1)).findById(bookId);
  }

  @Test
  void getById_WhenBookDoesNotExist_ShouldThrowException() {
    UUID bookId = UUID.randomUUID();
    when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

    RuntimeException exception =
        assertThrows(RuntimeException.class, () -> bookService.getById(bookId));

    assertTrue(exception.getMessage().contains("Book not found with id: " + bookId));
    verify(bookRepository, times(1)).findById(bookId);
  }
}
