package br.edu.ifsp.spo.bulls.competition.api.service;

import br.edu.ifsp.spo.bulls.common.api.dto.BookAdTO;
import br.edu.ifsp.spo.bulls.common.api.dto.UserTO;
import br.edu.ifsp.spo.bulls.competition.api.domain.BookAd;
import br.edu.ifsp.spo.bulls.competition.api.feign.UserCommonFeign;
import br.edu.ifsp.spo.bulls.competition.api.repository.BookAdRepository;
import br.edu.ifsp.spo.bulls.competition.api.util.BookAdUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(loader= AnnotationConfigContextLoader.class, classes = {BookAdService.class, BookAdUtil.class})
public class BookAdServiceTest {
    @MockBean
    private BookAdRepository repository;

    @MockBean
    private UserCommonFeign feign;

    @Autowired
    private BookAdService service;

    @Autowired
    private BookAdUtil util;

    private BookAdTO bookAdTO = new BookAdTO();

    private BookAd bookAd = new BookAd();

    private UserTO userTO = new UserTO();
    @BeforeEach
    public void setup() {
        bookAdTO.setId(UUID.fromString("e769397d-f8a5-4c21-a7b9-b709ab5ad7b8"));
        bookAdTO.setUserId(UUID.fromString("05cb05b7-6443-4d57-bba7-6e79ff05074c"));

        bookAd.setId(UUID.fromString("e769397d-f8a5-4c21-a7b9-b709ab5ad7b8"));
        bookAd.setUserId(UUID.fromString("05cb05b7-6443-4d57-bba7-6e79ff05074c"));

        userTO.setId(UUID.fromString("05cb05b7-6443-4d57-bba7-6e79ff05074c"));
    }

    @Test
    public void shouldCreate() {
        Mockito.when(repository.save(bookAd)).thenReturn(bookAd);
        Mockito.when(feign.getUserInfo("token")).thenReturn(userTO);
        BookAdTO res = service.create("token", bookAdTO);
        assertEquals(bookAdTO, res);
    }

    @Test
    public void shouldReturn() {
//        Page<BookAd>
//        Pageable pageRequest = PageRequest.of(
//                0,
//                1,
//                Sort.Direction.ASC,
//                "id");
//        Mockito.when(repository.findAll()).thenReturn(list);
//        assertEquals(util.toDtoList(list), service.getAds(0, 1));
    }

}
