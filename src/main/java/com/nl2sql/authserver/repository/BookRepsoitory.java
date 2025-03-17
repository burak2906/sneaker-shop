package com.nl2sql.authserver.repository;

import com.nl2sql.authserver.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepsoitory extends JpaRepository<Book, UUID> {

    Optional<List<Book>> getBooksByAuthor(String author);

}