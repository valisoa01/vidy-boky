package com.example.demo.librairie.repository;

<<<<<<< HEAD
import com.example.demo.librairie.entity.BookFormat;
=======
 import com.example.demo.librairie.entity.BookFormat;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
>>>>>>> c52fa6db5f35dd9cbd0672a62170f7a03668df55
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface BookFormatRepository extends JpaRepository<BookFormat, UUID> {
<<<<<<< HEAD
    boolean existsByBookIdAndFormatId(UUID bookId, UUID formatId);
=======

  List<BookFormat> findByBookId(UUID bookId);
  List<BookFormat> findByFormatId(UUID formatId);
  Optional<BookFormat> findByBookIdAndFormatId(UUID bookId, UUID formatId);
  List<BookFormat> findByPriceLessThanEqual(BigDecimal maxPrice);
  List<BookFormat> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
  boolean existsByBookIdAndFormatId(UUID bookId, UUID formatId);

  @Query("SELECT bf FROM BookFormat bf WHERE LOWER(bf.format.formatType) = LOWER(:formatType)")
  List<BookFormat> findByFormatType(@Param("formatType") String formatType);

  @Query("SELECT bf FROM BookFormat bf WHERE LOWER(bf.book.title) LIKE LOWER(CONCAT('%', :title, '%'))")
  List<BookFormat> findByBookTitleContaining(@Param("title") String title);

  @Modifying
  @Transactional
  @Query("UPDATE BookFormat bf SET bf.price = :price WHERE bf.book.id = :bookId AND bf.format.id = :formatId")
  int updatePrice(@Param("bookId") UUID bookId, @Param("formatId") UUID formatId, @Param("price") BigDecimal price);

  @Modifying
  @Transactional
  void deleteByBookIdAndFormatId(UUID bookId, UUID formatId);

  @Modifying
  @Transactional
  void deleteByBookId(UUID bookId);

  @Modifying
  @Transactional
  void deleteByFormatId(UUID formatId);
>>>>>>> c52fa6db5f35dd9cbd0672a62170f7a03668df55
}