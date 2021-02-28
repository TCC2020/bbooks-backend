package br.edu.ifsp.spo.bulls.competition.api.domain;

import br.edu.ifsp.spo.bulls.common.api.bean.StringConverter;
import br.edu.ifsp.spo.bulls.common.api.enums.BookCondition;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
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
    private String title;
    private String description;
    @Column(name = "images", length = 1000)
    @Convert(converter = StringConverter.class)
    private List<String> images;
    private String isbn;
    @Column(nullable = false)
    private UUID userId;
    @Enumerated(EnumType.STRING)
    private BookCondition condition;
    private String idBookGoogle;
    private String bookId;
    @Column(length = 16)
    private String contact;
    private String address;
    @OneToOne
    private AdReview review;
    private Boolean isOpen;

    @PrePersist
    public void prePersist() {
        isOpen = true;
    }
}
