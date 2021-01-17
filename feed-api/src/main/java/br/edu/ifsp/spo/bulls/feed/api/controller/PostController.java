package br.edu.ifsp.spo.bulls.feed.api.controller;

import br.edu.ifsp.spo.bulls.feed.api.domain.Post;
import br.edu.ifsp.spo.bulls.feed.api.service.PostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/post", produces="application/json")
@CrossOrigin(origins = "*")
public class PostController {

    private final Logger logger = LoggerFactory.getLogger(FeedController.class);

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
    public Post get(@PathVariable UUID idPost) {
        logger.info("Buscando post: " + idPost);
        return service.get(idPost);
    }

    @ApiOperation(value = "Ver publicações de um perfil")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Publicação encontradas")
    })
    @GetMapping("/profile/{idProfile}")
    public List<Post> get(@PathVariable int idProfile) {
        logger.info("Buscando posts por profile: " + idProfile);
        return service.getByProfile(idProfile);
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
