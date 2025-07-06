package com.pramodpoojary.springTest;

import com.pramodpoojary.springTest.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringTestApplication implements CommandLineRunner {

    @Autowired
    LibraryRepository libraryRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringTestApplication.class, args);
    }

    @Override
    public void run(String[] args) {
       /* Library library = libraryRepository.findById("abdec123").get();
        System.out.println(library.getAuthor());
        Library newLibrary = Library.builder().book_name("Automic Habits").id("abcde2134").isbin("1234353").aisle(23).author("Pramod P").build();
        //libraryRepository.save(newLibrary); //to add new record

        List<Library> libList = libraryRepository.findAll();
        for (Library lib : libList) {
            System.out.println(lib.getBook_name());

        }*/
    }

}
