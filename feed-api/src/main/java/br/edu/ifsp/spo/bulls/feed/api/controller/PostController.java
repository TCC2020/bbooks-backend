package br.edu.ifsp.spo.bulls.feed.api.controller;

import br.edu.ifsp.spo.bulls.feed.api.domain.Post;
import br.edu.ifsp.spo.bulls.feed.api.dto.PostTO;
import br.edu.ifsp.spo.bulls.feed.api.service.PostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/post", produces="application/json")
@CrossOrigin(origins = "*")
public class PostController {

    private final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostService service;

    @ApiOperation(value = "Cria uma nova publicação")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Publicação criada")
    })
    @PostMapping
    public Post post(@RequestBody Post post) {
        logger.info("Criando post: " + post.toString());
        return service.create(post);
    }

    @ApiOperation(value = "Editar uma publicação")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Publicação editada")
    })
    @PutMapping("/{idPost}")
    public Post put(@RequestBody Post post, @PathVariable UUID idPost) {
        logger.info("Editando post: " + post.toString());
        return service.update(post, idPost);
    }

    @ApiOperation(value = "Ver uma publicação")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Publicação encontrada")
    })
    @GetMapping("/{idPost}")
    public PostTO get(@PathVariable UUID idPost) {
        logger.info("Buscando post: " + idPost);
        return service.get(idPost);
    }

    @ApiOperation(value = "Ver uma publicação")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Publicação encontrada")
    })
    @GetMapping("/comment/{idPost}")
    public Page<PostTO> getComment(@PathVariable UUID idPost, @RequestParam int page, @RequestParam int size) {
        logger.info("Buscando comenários do post: " + idPost);
        return service.getComment(idPost, page, size);
    }

    @ApiOperation(value = "Ver publicações de um perfil")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Publicação encontradas")
    })
    @GetMapping("/profile/{idProfile}")
    public Page<PostTO> get(@PathVariable int idProfile, @RequestParam int page, @RequestParam int size) {
        logger.info("Buscando posts por profile: " + idProfile);

        return service.getByProfile(idProfile, page, size);
    }

    @ApiOperation(value = "Deletar uma publicação")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Publicação deletada")
    })
    @DeleteMapping("/{idPost}")
    public void delete(@PathVariable UUID idPost) {
        logger.info("Deletando post: " + idPost);
        service.delete(idPost);
    }

}
