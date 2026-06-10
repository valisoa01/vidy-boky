package com.example.demo.conf.librairie.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.librairie.entity.Book;
import com.example.demo.librairie.entity.BookFormat;
import com.example.demo.librairie.repository.BookRepository;
import com.example.demo.librairie.service.BookService;
import java.time.LocalDate;
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

  @Test
  void getFormatByLivre_ShouldReturnListOfFormats() {
    UUID bookId = UUID.randomUUID();
    BookFormat format1 = BookFormat.builder().id(UUID.randomUUID()).build();
    BookFormat format2 = BookFormat.builder().id(UUID.randomUUID()).build();
    List<BookFormat> expectedFormats = List.of(format1, format2);

    Book book = Book.builder().id(bookId).formats(expectedFormats).build();

    when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

    List<BookFormat> result = bookService.getFormatByLivre(bookId);

    assertNotNull(result);
    assertEquals(2, result.size());
    verify(bookRepository, times(1)).findById(bookId);
  }

  @Test
  void getLivreByTitle_ShouldReturnMatchingBooks() {
    String searchTitle = "java";
    Book book1 = Book.builder().title("Effective Java").build();
    Book book2 = Book.builder().title("Java for Beginners").build();
    when(bookRepository.findByTitleContainingIgnoreCase(searchTitle))
        .thenReturn(List.of(book1, book2));

    List<Book> result = bookService.getLivreByTitle(searchTitle);

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("Effective Java", result.get(0).getTitle());
    verify(bookRepository, times(1)).findByTitleContainingIgnoreCase(searchTitle);
  }

  @Test
  void getLivreByGenre_ShouldReturnBooksWithMatchingGenre() {
    UUID genreId = UUID.randomUUID();
    Book book = Book.builder().title("Livre de Science-Fiction").build();
    when(bookRepository.findByGenresId(genreId)).thenReturn(List.of(book));

    List<Book> result = bookService.getLivreByGenre(genreId);

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("Livre de Science-Fiction", result.get(0).getTitle());
    verify(bookRepository, times(1)).findByGenresId(genreId);
  }

  @Test
  void getLivreByDute_ShouldReturnBooksWithMatchingPublicationDate() {
    LocalDate searchDate = LocalDate.of(2026, 3, 20);
    Book book = Book.builder().title("Livre Temporel").publicationDate(searchDate).build();
    when(bookRepository.findByPublicationDate(searchDate)).thenReturn(List.of(book));

    List<Book> result = bookService.getLivreByDate(searchDate);

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(searchDate, result.get(0).getPublicationDate());
    verify(bookRepository, times(1)).findByPublicationDate(searchDate);
  }

  @Test
  void createLivre_ShouldSaveAndReturnBook() {
    Book bookToSave = Book.builder().title("Nouveau Livre").isbn("123-456").build();
    UUID generatedId = UUID.randomUUID();
    Book savedBook =
        Book.builder()
            .id(generatedId)
            .title("Nouveau Livre")
            .isbn("123-456")
            .creationDate(LocalDate.now())
            .build();

    when(bookRepository.save(bookToSave)).thenReturn(savedBook);

    Book result = bookService.createLivre(bookToSave);

    assertNotNull(result);
    assertEquals(generatedId, result.getId());
    assertEquals("Nouveau Livre", result.getTitle());
    assertNotNull(result.getCreationDate());
    verify(bookRepository, times(1)).save(bookToSave);
  }

  @Test
  void updateLivre_WhenBookExists_ShouldUpdateAndReturnBook() {
    UUID bookId = UUID.randomUUID();
    Book existingBook = Book.builder().id(bookId).title("Ancien Titre").isbn("111").build();
    Book updatedInfo = Book.builder().title("Nouveau Titre").isbn("222").build();
    Book savedBook = Book.builder().id(bookId).title("Nouveau Titre").isbn("222").build();

    when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
    when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

    Book result = bookService.updateLivre(bookId, updatedInfo);

    assertNotNull(result);
    assertEquals("Nouveau Titre", result.getTitle());
    assertEquals("222", result.getIsbn());
    verify(bookRepository, times(1)).findById(bookId);
    verify(bookRepository, times(1)).save(any(Book.class));
  }

  @Test
  void deleteLivre_WhenBookExists_ShouldDeleteSuccessfully() {
    UUID bookId = UUID.randomUUID();
    when(bookRepository.existsById(bookId)).thenReturn(true);
    doNothing().when(bookRepository).deleteById(bookId);

    bookService.deleteLivre(bookId);

    verify(bookRepository, times(1)).existsById(bookId);
    verify(bookRepository, times(1)).deleteById(bookId);
  }
}
