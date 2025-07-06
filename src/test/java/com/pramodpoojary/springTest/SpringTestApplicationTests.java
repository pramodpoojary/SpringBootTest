package com.pramodpoojary.springTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pramodpoojary.springTest.controller.AddBookResponse;
import com.pramodpoojary.springTest.controller.Library;
import com.pramodpoojary.springTest.controller.LibraryController;
import com.pramodpoojary.springTest.repository.LibraryRepository;
import com.pramodpoojary.springTest.service.LibraryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SpringTestApplicationTests {
    @Autowired
    LibraryController libraryController;

    @MockitoBean
    LibraryService libraryService;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    LibraryRepository libraryRepository;


    @Test
    public void checkIdBuildLogic() {
        LibraryService libraryService = new LibraryService();
        String id = libraryService.buildId("1234", 22);
        assertEquals(id, "123422");
        String id2 = libraryService.buildId("Z1234", 22);
        assertEquals(id2, "OLDZ123422");

    }

    @Test
    public void addBookTest() {
        Library lib = buildLibrary();
        //mock
        when(libraryService.buildId(lib.getIsbin(), lib.getAisle())).thenReturn(lib.getId());
        when(libraryService.checkBookExists(lib.getId())).thenReturn(false);
        ResponseEntity response = libraryController.addBookImplementation(lib);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        AddBookResponse addBookResponse = (AddBookResponse) response.getBody();
        assertEquals(addBookResponse.getId(), lib.getId());
        assertEquals(addBookResponse.getMsg(), "Success Book is added.");
    }

    public Library buildLibrary() {
        return Library.builder().id("12322").isbin("123").aisle(22).book_name("Pramod Test").author("Pramod Poojary").build();
    }

    @Test
    public void addBookControllerTest() throws Exception {
        Library lib = buildLibrary();
        ObjectMapper map = new ObjectMapper();
        String jsonString = map.writeValueAsString(lib);
        //mock
        when(libraryService.buildId(lib.getIsbin(), lib.getAisle())).thenReturn(lib.getId());
        when(libraryService.checkBookExists(lib.getId())).thenReturn(false);
        when(libraryRepository.save(any())).thenReturn(lib);
        this.mockMvc.perform(post("/addBook").contentType(MediaType.APPLICATION_JSON).content(jsonString)).andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(lib.getId()));
    }

    @Test
    public void getBookByAuthorTest() throws Exception {
        List<Library> li = new ArrayList<>();
        li.add(buildLibrary());
        li.add(buildLibrary());
        when(libraryRepository.findBookByAuthor(any())).thenReturn(li);

        this.mockMvc.perform(get("/getBookByAuthor").queryParam("authorName", "Pramod Poojary")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$.[0].id").value("12322"));

    }

    @Test
    public void updateBookTest() throws Exception {
        Library lib = buildLibrary();
        Library updateLib = updateLibrary();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(updateLib);

        when(libraryService.getBookById(any())).thenReturn(lib);
        this.mockMvc.perform(put("/updateBook/" + lib.getId()).contentType(MediaType.APPLICATION_JSON).content(jsonString)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.book_name").value(updateLib.getBook_name()))
                .andExpect(content().json("{\"book_name\":\"Pramod Update\",\"id\":\"12322\",\"aisle\":22,\"isbin\":\"123\",\"author\":\"Pramod P\"}"));
    }

    public Library updateLibrary() {
        return Library.builder().id("12222").isbin("122").aisle(22).book_name("Pramod Update").author("Pramod P").build();
    }

    @Test
    public void deleteBookTest() throws Exception {
        Library lib = buildLibrary();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(lib);
        when(libraryService.getBookById(any())).thenReturn(lib);
        //if your method return void , this is optional here as we have mocked the LibraryRepository
        doNothing().when(libraryRepository).delete(lib);
        this.mockMvc.perform(delete("/deleteBook").contentType(MediaType.APPLICATION_JSON).content("{\"id\":\"12322\"}")).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("Book deleted successfully"));

    }


}
