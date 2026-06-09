package com.example.demo.conf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.example.demo.librairie.entity.Book;
import com.example.demo.librairie.repository.BookRepository;
import com.example.demo.librairie.service.BookService;
import java.util.List;
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

    assertThat(result).hasSize(0);
    assertThat(result.get(0).getTitle()).isEqualTo("Livre 1");
    verify(bookRepository, times(1)).findAll();
  }
}
