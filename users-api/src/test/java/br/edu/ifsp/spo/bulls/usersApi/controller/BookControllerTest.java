package br.edu.ifsp.spo.bulls.usersApi.controller;

import br.edu.ifsp.spo.bulls.usersApi.domain.Author;
import br.edu.ifsp.spo.bulls.usersApi.dto.BookTO;
import br.edu.ifsp.spo.bulls.usersApi.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    BookService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void save() throws Exception {

        BookTO book = new BookTO("1234653","lIVRO TESTE3", this.listaAutores() ,10, "português", "editora",  Calendar.getInstance(), "livro123456 ");

        mockMvc.perform(post("/books")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk());
    }

    @Test
    void saveFail() throws Exception {

        service.save(new BookTO("1234596","lIVRO TESTE3", this.listaAutores() ,10, "português", "editora",  Calendar.getInstance(), "livro123456 "));

        BookTO book = new BookTO("1234596","lIVRO TESTE3", this.listaAutores() ,10, "português", "editora",  Calendar.getInstance(), "livro123456 ");

        mockMvc.perform(post("/books")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isConflict());
    }

    @Test
    void testGet() throws Exception {
        BookTO book = new BookTO("12346234","lIVRO TESTE3", this.listaAutores() ,10, "português", "editora",  Calendar.getInstance(), "livro123456 ");

        service.save(book);

        mockMvc.perform(get("/books")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void getOne() throws Exception {
        BookTO book = service.save(new BookTO("1245688","lIVRO TESTE3", this.listaAutores() ,10, "português", "editora",  Calendar.getInstance(), "livro123456 "));

        mockMvc.perform(get("/books/"+book.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void getOneFail() throws Exception {

        mockMvc.perform(get("/books/"+ new Random().nextInt())
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDelete() throws Exception {
        BookTO book = service.save(new BookTO("1342345256","lIVRO TESTE3", this.listaAutores() ,10, "português", "editora",  Calendar.getInstance(), "livro123456 "));

        mockMvc.perform(delete("/books/" + book.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteBookNotFound() throws Exception {

        mockMvc.perform(delete("/books/" + new Random().nextInt())
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void update() throws Exception {
        BookTO book = service.save(new BookTO("2347904","lIVRO TESTE3", this.listaAutores() ,10, "português", "editora",  Calendar.getInstance(), "livro123456 "));
        book.setTitle("Alterado");

        mockMvc.perform(put("/books/"+book.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk());
    }

    @Test
    void updateFail() throws Exception {
        BookTO book = new BookTO("2347904","lIVRO TESTE3", this.listaAutores() ,10, "português", "editora",  Calendar.getInstance(), "livro123456 ");

        mockMvc.perform(put("/books/"+book.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isNotFound());
    }

    List<Author> listaAutores(){
        List<Author> autores = new ArrayList<Author>();

        autores.add(new Author("autor 1"));
        autores.add(new Author("autor 2"));
        autores.add(new Author("autor 3"));

        return autores;
    }
}