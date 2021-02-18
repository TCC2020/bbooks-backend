package br.edu.ifsp.spo.bulls.competition.api.domain;

import br.edu.ifsp.spo.bulls.common.api.bean.StringConverter;
import br.edu.ifsp.spo.bulls.common.api.enums.BookCondition;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.OneToOne;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "book_ads")
@Data
@ApiModel(value = "Objeto de domínio: anúncio de livro")
public class BookAd {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    private String description;
    @Column(name = "images")
    @Convert(converter = StringConverter.class)
    private List<String> images;
    private String isbn;
    @Column(nullable = false)
    private UUID userId;
    private BookCondition condition;
    private String idBookGoogle;
    private String bookId;
    @OneToOne
    private AdReview review;
}
