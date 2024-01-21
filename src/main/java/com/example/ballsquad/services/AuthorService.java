package com.example.ballsquad.services;

import com.example.ballsquad.entities.Author;
import com.example.ballsquad.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    public Optional<Author> findByName(String name){
        return authorRepository.findByName(name);
    }
    public Author save(Author author){
        return authorRepository.save(author);
    }

    public Optional<Author> findById(Long authorId) {
        return authorRepository.findById(authorId);
    }
}
