package com.example.demo.librairie.repository;

import com.example.demo.librairie.entity.BookFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface BookFormatRepository extends JpaRepository<BookFormat, UUID> {
    boolean existsByBookIdAndFormatId(UUID bookId, UUID formatId);
}
