package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.demo.librairie.dto.BookFormatRequestDTO;
import com.example.demo.librairie.dto.BookFormatResponseDTO;
import com.example.demo.librairie.entity.Book;
import com.example.demo.librairie.entity.BookFormat;
import com.example.demo.librairie.entity.Format;
import com.example.demo.librairie.repository.BookFormatRepository;
import com.example.demo.librairie.repository.BookRepository;
import com.example.demo.librairie.repository.FormatRepository;
import com.example.demo.librairie.service.BookFormatService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookFormatServiceTest {

  @Mock private BookFormatRepository bookFormatRepository;

  @Mock private BookRepository bookRepository;

  @Mock private FormatRepository formatRepository;

  @InjectMocks private BookFormatService bookFormatService;

  private UUID bookId;
  private UUID formatId;
  private UUID bookFormatId;
  private Book book;
  private Format format;
  private BookFormat bookFormat;
  private BookFormatRequestDTO request;

  @BeforeEach
  void setUp() {
    bookId = UUID.randomUUID();
    formatId = UUID.randomUUID();
    bookFormatId = UUID.randomUUID();

    book = Book.builder().id(bookId).title("Harry Potter").build();

    format = Format.builder().id(formatId).formatType("PDF").build();

    bookFormat =
        BookFormat.builder().id(bookFormatId).price(19.99).book(book).format(format).build();

    request = new BookFormatRequestDTO(bookId, formatId, 19.99);
  }

  @Test
  void create_shouldReturnResponseDTO_whenValid() {
    when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
    when(formatRepository.findById(formatId)).thenReturn(Optional.of(format));
    when(bookFormatRepository.existsByBookIdAndFormatId(bookId, formatId)).thenReturn(false);
    when(bookFormatRepository.save(any())).thenReturn(bookFormat);

    BookFormatResponseDTO result = bookFormatService.create(request);

    assertNotNull(result);
    assertEquals(bookFormatId, result.getId());
    assertEquals(19.99, result.getPrice());
    assertEquals("Harry Potter", result.getBookTitle());
    assertEquals("PDF", result.getTypeFormat());
  }

  @Test
  void create_shouldThrowException_whenBookNotFound() {
    when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> bookFormatService.create(request));
  }

  @Test
  void create_shouldThrowException_whenFormatNotFound() {
    when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
    when(formatRepository.findById(formatId)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> bookFormatService.create(request));
  }

  @Test
  void create_shouldThrowException_whenDuplicateBookFormat() {
    when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
    when(formatRepository.findById(formatId)).thenReturn(Optional.of(format));
    when(bookFormatRepository.existsByBookIdAndFormatId(bookId, formatId)).thenReturn(true);

    assertThrows(RuntimeException.class, () -> bookFormatService.create(request));
  }

  @Test
  void findAll_shouldReturnListOfResponseDTO() {
    when(bookFormatRepository.findAll()).thenReturn(List.of(bookFormat));

    List<BookFormatResponseDTO> result = bookFormatService.findAll();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(bookFormatId, result.get(0).getId());
  }

  @Test
  void findById_shouldReturnResponseDTO_whenFound() {
    when(bookFormatRepository.findById(bookFormatId)).thenReturn(Optional.of(bookFormat));

    BookFormatResponseDTO result = bookFormatService.findById(bookFormatId);

    assertNotNull(result);
    assertEquals(bookFormatId, result.getId());
  }

  @Test
  void findById_shouldThrowException_whenNotFound() {
    when(bookFormatRepository.findById(bookFormatId)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> bookFormatService.findById(bookFormatId));
  }

  @Test
  void update_shouldReturnUpdatedResponseDTO_whenValid() {
    when(bookFormatRepository.findById(bookFormatId)).thenReturn(Optional.of(bookFormat));
    when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
    when(formatRepository.findById(formatId)).thenReturn(Optional.of(format));
    when(bookFormatRepository.save(any())).thenReturn(bookFormat);

    BookFormatResponseDTO result = bookFormatService.update(bookFormatId, request);

    assertNotNull(result);
    assertEquals(bookFormatId, result.getId());
  }

  @Test
  void update_shouldThrowException_whenBookFormatNotFound() {
    when(bookFormatRepository.findById(bookFormatId)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> bookFormatService.update(bookFormatId, request));
  }

  @Test
  void delete_shouldDeleteSuccessfully_whenFound() {
    when(bookFormatRepository.existsById(bookFormatId)).thenReturn(true);
    doNothing().when(bookFormatRepository).deleteById(bookFormatId);

    assertDoesNotThrow(() -> bookFormatService.delete(bookFormatId));
    verify(bookFormatRepository, times(1)).deleteById(bookFormatId);
  }

  @Test
  void delete_shouldThrowException_whenNotFound() {
    when(bookFormatRepository.existsById(bookFormatId)).thenReturn(false);

    assertThrows(RuntimeException.class, () -> bookFormatService.delete(bookFormatId));
  }
}
