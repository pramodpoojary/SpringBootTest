package com.pramodpoojary.springTest;

import com.pramodpoojary.springTest.controller.Library;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

@SpringBootTest
public class testsIT {
    //TestRestTemplate or RestAssured we can use
    @Test
    @Order(1)
    public void getAuthorNameBookTest() throws JSONException {
        String expected = "[\n" +
                "    {\n" +
                "        \"book_name\": \"Dummy1\",\n" +
                "        \"id\": \"12345\",\n" +
                "        \"aisle\": 45,\n" +
                "        \"isbin\": \"123\",\n" +
                "        \"author\": \"Pramod Poojary\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"book_name\": \"Dummy2\",\n" +
                "        \"id\": \"12445\",\n" +
                "        \"aisle\": 45,\n" +
                "        \"isbin\": \"124\",\n" +
                "        \"author\": \"Pramod Poojary\"\n" +
                "    }\n" +
                "]";
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/getBookByAuthor?authorName=Pramod Poojary", String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    @Order(2)
    public void adddBookTest() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        Library lib = buildLibrary();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Library> request = new HttpEntity<>(lib, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/addBook", request, String.class);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(lib.getId(), response.getHeaders().get("uniqueId").get(0));


    }

    public Library buildLibrary() {
        return Library.builder().id("12322").isbin("123").aisle(22).book_name("Pramod Test").author("Pramod Poojary").build();
    }

}
