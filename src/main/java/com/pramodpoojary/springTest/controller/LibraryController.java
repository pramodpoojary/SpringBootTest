package com.pramodpoojary.springTest.controller;

import com.pramodpoojary.springTest.repository.LibraryRepository;
import com.pramodpoojary.springTest.service.LibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class LibraryController {
    @Autowired
    LibraryRepository libraryRepository;

    @Autowired
    LibraryService libraryService;

    private static final Logger logger = LoggerFactory.getLogger(LibraryController.class);

    @PostMapping("/addBook")
    public ResponseEntity addBookImplementation(@RequestBody Library library) {
        AddBookResponse response = new AddBookResponse();
        String id = libraryService.buildId(library.getIsbin(), library.getAisle());
        if (!libraryService.checkBookExists(id)) {
            logger.info("Book doesn't exist");
            HttpHeaders headers = new HttpHeaders();
            headers.add("uniqueId", id);
            library.setId(id);
            logger.info("Request Body : {}", library.toString());
            libraryRepository.save(library);
            response.setMsg("Success Book is added.");
            response.setId(id);
            return new ResponseEntity<AddBookResponse>(response, headers, HttpStatus.CREATED);
        } else {
            logger.warn("Book already exists : {}", id);
            response.setMsg("Book already exists");
            response.setId(id);
            return new ResponseEntity<AddBookResponse>(response, HttpStatus.ACCEPTED);

        }
    }

    @GetMapping("/getBookById/{id}")
    public Library getBookById(@PathVariable(value = "id") String id) {
        try {
            return libraryRepository.findById(id).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getBookByAuthor")
    public List<Library> getBokByAuthor(@RequestParam(value = "authorName") String authorName) {
        return libraryRepository.findBookByAuthor(authorName);
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<Library> updateBook(@PathVariable(value = "id") String id, @RequestBody Library library) {
        if (libraryService.getBookById(id).getId().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Library existingLibrary = libraryService.getBookById(id);
        existingLibrary.setAisle(library.getAisle());
        existingLibrary.setAuthor(library.getAuthor());
        existingLibrary.setBook_name(library.getBook_name());
        libraryRepository.save(existingLibrary);
        return new ResponseEntity<Library>(existingLibrary, HttpStatus.OK);
    }

    @DeleteMapping("/deleteBook")
    public ResponseEntity<String> deleteBook(@RequestBody Library library) {
        if (libraryService.getBookById(library.getId()).getId().isEmpty()) {
            return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
        }
        Library existingBook = libraryService.getBookById(library.getId());
        libraryRepository.delete(existingBook);
        return new ResponseEntity<>("Book deleted successfully", HttpStatus.CREATED);
    }

    @GetMapping("/getAllBooks")
    public List<Library> getAllBooks() {
        return libraryRepository.findAll();
    }

}
