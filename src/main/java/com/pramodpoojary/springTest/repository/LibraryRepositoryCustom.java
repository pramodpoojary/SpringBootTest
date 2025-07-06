package com.pramodpoojary.springTest.repository;

import com.pramodpoojary.springTest.controller.Library;

import java.util.List;

public interface LibraryRepositoryCustom {
    List<Library> findBookByAuthor(String authorName);

}
