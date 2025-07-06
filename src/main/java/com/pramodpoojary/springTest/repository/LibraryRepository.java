package com.pramodpoojary.springTest.repository;

import com.pramodpoojary.springTest.controller.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepository extends JpaRepository<Library, String> , LibraryRepositoryCustom{
}
