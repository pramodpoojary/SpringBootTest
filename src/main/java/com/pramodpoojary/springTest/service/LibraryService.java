package com.pramodpoojary.springTest.service;

import com.pramodpoojary.springTest.controller.Library;
import com.pramodpoojary.springTest.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LibraryService {

    @Autowired
    LibraryRepository libraryRepository;

    public String buildId(String isbin, Integer aisle) {
        if (isbin.startsWith("Z")) {
            return "OLD" + isbin + aisle;
        }
        return isbin + aisle;
    }

    public boolean checkBookExists(String id) {
        Optional<Library> lib = libraryRepository.findById(id);
        return lib.isPresent();
    }

    public Library getBookById(String id) {
        return libraryRepository.findById(id).get();
    }
}
