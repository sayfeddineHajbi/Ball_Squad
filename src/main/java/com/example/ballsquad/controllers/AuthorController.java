package com.example.ballsquad.controllers;
import com.example.ballsquad.entities.Author;
import com.example.ballsquad.entities.AuthorApiResponse;
import com.example.ballsquad.entities.Work;
import com.example.ballsquad.entities.WorkApiResponse;
import com.example.ballsquad.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping
    public ResponseEntity<?> searchAuthors(@RequestParam("name") String name) {
        Optional<Author> optionalAuthor = authorService.findByName(name);
        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            return ResponseEntity.ok(author);
        } else {
            RestTemplate restTemplate = new RestTemplate();
            String authorsUrl = "https://openlibrary.org/search/authors.json?q={name}";
            String apiUrl = authorsUrl.replace("{name}", name);
            AuthorApiResponse authorApiResponse = restTemplate.getForObject(apiUrl, AuthorApiResponse.class);

            Author author = new Author();
            author.setName(authorApiResponse.getName());

            authorService.save(author);

            return ResponseEntity.ok(author);
        }
    }

    @GetMapping("/{authorId}/works")
    public ResponseEntity<?> searchAuthorWorks(@PathVariable("authorId") Long authorId) {
        Optional<Author> optionalAuthor = authorService.findById(authorId);
        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            List<Work> works = author.getWorks();
            if (!works.isEmpty()) {
                return ResponseEntity.ok(works);
            } else {
                RestTemplate restTemplate = new RestTemplate();
                String worksUrl = "https://openlibrary.org/authors/{authorId}/works.json?limit=100";
                String apiUrl = worksUrl.replace("{authorId}", authorId.toString());
                WorkApiResponse workApiResponse = restTemplate.getForObject(apiUrl, WorkApiResponse.class);

                // Retrieve author's works data and create Work objects
                works = new ArrayList<>();
                for (WorkApiResponse.WorkData workData : workApiResponse.getWorks()) {
                    Work work = new Work();
                    work.setTitle(workData.getTitle());
                    work.setAuthor(author);
                    works.add(work);
                }

                author.setWorks(works);
                authorService.save(author);

                return ResponseEntity.ok(works);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}