package br.edu.ifsp.spo.bulls.usersApi.controller;

import br.edu.ifsp.spo.bulls.usersApi.domain.Author;
import br.edu.ifsp.spo.bulls.usersApi.dto.BookTO;
import br.edu.ifsp.spo.bulls.usersApi.enums.CodeException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceBadRequestException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceConflictException;
import br.edu.ifsp.spo.bulls.usersApi.exception.ResourceNotFoundException;
import br.edu.ifsp.spo.bulls.usersApi.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @MockBean
    BookService mockBookService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private BookTO bookTO;
    private BookTO bookTONoAuthors;
    private HashSet<BookTO> bookTOList = new HashSet<>();
    private List<Author> autores = new ArrayList<>();

    @BeforeEach
    void setUp() {
        autores.add(new Author("autor 1"));
        autores.add(new Author("autor 2"));
        autores.add(new Author("autor 3"));

        bookTO = new BookTO("1234653","lIVRO TESTE3", autores ,
                10, "português", "editora",  2, "livro123456 ");
        bookTO.setId(1);
        bookTOList.add(bookTO);

        bookTONoAuthors = new BookTO("1234653","lIVRO TESTE3", null ,
                10, "português", "editora",  2, "livro123456 ");
        bookTONoAuthors.setId(2);
    }

    @Test
    void save() throws Exception {

        Mockito.when(mockBookService.save(bookTO)).thenReturn(bookTO);
        mockMvc.perform(post("/books")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(bookTO)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldnt_save_if_isbn_exists() throws Exception {

        Mockito.when(mockBookService.save(bookTO)).thenThrow(new ResourceConflictException(CodeException.BK001.getText(), CodeException.BK001));

        mockMvc.perform(post("/books")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(bookTO)))
                .andExpect(status().isConflict());
    }

    @Test
    void should_return_list_of_books() throws Exception {

        Mockito.when(mockBookService.getAll()).thenReturn(bookTOList);
        mockMvc.perform(get("/books")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void getOne() throws Exception {

        Mockito.when(mockBookService.getOne(bookTO.getId())).thenReturn(bookTO);

        mockMvc.perform(get("/books/"+bookTO.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void getOneFail() throws Exception {

        Mockito.when(mockBookService.getOne(bookTO.getId())).thenThrow(new ResourceNotFoundException(CodeException.BK002.getText(), CodeException.BK002));

        mockMvc.perform(get("/books/"+ bookTO.getId())
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDelete() throws Exception {

        Mockito.doNothing().when(mockBookService).delete(bookTO.getId());
        mockMvc.perform(delete("/books/" + bookTO.getId())
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteBookNotFound() throws Exception {

        Mockito.doThrow(new ResourceNotFoundException(CodeException.BK002.getText(), CodeException.BK002)).when(mockBookService).delete(bookTO.getId());

        mockMvc.perform(delete("/books/" + bookTO.getId())
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_update() throws Exception {
        Mockito.when(mockBookService.update(bookTO)).thenReturn(bookTO);

        mockMvc.perform(put("/books/"+bookTO.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(bookTO)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldnt_update_if_book_not_found() throws Exception {
        Mockito.when(mockBookService.update(bookTO)).thenThrow(new ResourceNotFoundException(CodeException.BK002.getText(), CodeException.BK002));

        mockMvc.perform(put("/books/"+bookTO.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(bookTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldnt_update_if_book_has_no_authors() throws Exception {
        Mockito.when(mockBookService.update(bookTONoAuthors)).thenThrow(new ResourceBadRequestException(CodeException.BK003.getText(), CodeException.BK003));

        mockMvc.perform(put("/books/" + bookTONoAuthors.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(bookTONoAuthors)))
                .andExpect(status().isBadRequest());
    }

}