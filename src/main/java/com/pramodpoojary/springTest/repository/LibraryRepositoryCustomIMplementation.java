package com.pramodpoojary.springTest.repository;

import com.pramodpoojary.springTest.controller.Library;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class LibraryRepositoryCustomIMplementation implements LibraryRepositoryCustom {
    @Autowired
    LibraryRepository repository;

    @Override
    public List<Library> findBookByAuthor(String authorName) {
        List<Library> libraryList = repository.findAll();
        List<Library> filteredList = new ArrayList<Library>();
        for (Library item : libraryList) {
            if (item.getAuthor().toLowerCase().contains(authorName.toLowerCase())) {
                filteredList.add(item);
            }
        }
        return filteredList;
    }
}
