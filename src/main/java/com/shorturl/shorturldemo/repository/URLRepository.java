package com.shorturl.shorturldemo.repository;

import com.shorturl.shorturldemo.model.ShortURL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface URLRepository extends JpaRepository<ShortURL, Long> {

    @Query(value = "from ShortURL u where u.id = :id")
    Optional<ShortURL> findById(@Param(value = "id") Long id);

    @Query(value = "from ShortURL u where u.originalURL = :originalURL")
    Optional<ShortURL> findByOriginalURL(
            @Param(value = "originalURL") String originalURL);

    @Query(value = "from ShortURL u where u.shortURL = :shortURL")
    Optional<ShortURL> findByShortURL(
            @Param(value = "shortURL") String shortURL);

    @Query(nativeQuery = true, value = "SELECT nextval('seq_unique_id')")
    Long getIdWithNextUniqueId();
}
