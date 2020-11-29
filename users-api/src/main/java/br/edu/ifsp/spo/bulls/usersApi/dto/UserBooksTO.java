package br.edu.ifsp.spo.bulls.usersApi.dto;

import br.edu.ifsp.spo.bulls.usersApi.domain.Tag;
import br.edu.ifsp.spo.bulls.usersApi.enums.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value = "Objeto de trânsito: Livros do usuário ")
public class UserBooksTO {
    @ApiModelProperty(value = "Identificador")
    private Long id;
    @ApiModelProperty(value = "Número identificador do livro da Api do google")
    private String idBookGoogle;
    @ApiModelProperty(value = "Qauntidade de páginas do livro da Api do google")
    private int page;
    @ApiModelProperty(value = "Lista de tags que o livro possui")
    private List<Tag> tags = new ArrayList<>();
    @ApiModelProperty(value = "Status do livro")
    @Enumerated(EnumType.STRING)
    private Status status;
    @ApiModelProperty(value = "Data que o livro foi adicionado na estante")
    private LocalDateTime addDate;
    @ApiModelProperty(value = "Livro")
    private int idBook;
    @ApiModelProperty(value = "Usuario dono da estante virtual")
    private int profileId;
}
