package com.example.demo.librairie.repository;

import com.example.demo.librairie.entity.BookFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookFormatRepository extends JpaRepository<BookFormat, UUID> {

    List<BookFormat> findAll();

    List<BookFormat> findAllById(Iterable<UUID> uuids);

    Optional<BookFormat> findByFormatName(String formatName);

    List<BookFormat> findByFormatNameContainingIgnoreCase(String keyword);

    List<BookFormat> findByPriceMultiplierGreaterThan(Double min);

    List<BookFormat> findByPriceMultiplierLessThan(Double max);

    List<BookFormat> findByPriceMultiplierBetween(Double min, Double max);

    @Modifying
    @Transactional
    void deleteByFormatName(String formatName);

    @Modifying
    @Transactional
    long deleteByPriceMultiplierLessThan(Double max);

    @Modifying
    @Transactional
    @Query("UPDATE BookFormat bf SET bf.priceMultiplier = :newPrice WHERE bf.formatName = :name")
    int updatePriceMultiplierByFormatName(@Param("name") String formatName, @Param("newPrice") Double newPrice);

    boolean existsByFormatName(String formatName);

    long countByPriceMultiplierGreaterThan(Double min);
}