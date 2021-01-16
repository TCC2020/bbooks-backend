package br.edu.ifsp.spo.bulls.feed.api.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public PostTO post(@RequestBody PostTO post) {
        logger.info("Criando post: " + post.toString());
        return service.create(post);
    }

    @ApiOperation(value = "Editar uma publicação")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Publicação editada")
    })
    @PutMapping("/{idPost}")
    public PostTO put(@RequestBody PostTO post, @PathVariable UUID idPost) {
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

    @ApiOperation(value = "Ver upublicações de um perfil")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Publicação encontradas")
    })
    @GetMapping("/profile/{idProfile}")
    public List<PostTO> get(@PathVariable int idProfile) {
        logger.info("Buscando posts por profile: " + idProfile);
        return service.getByProfile(idProfile);
    }

    @ApiOperation(value = "Deletar uma publicação")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Publicação deletada")
    })
    @DeleteMapping("/{idPost}")
    public void get(@PathVariable UUID idPost) {
        logger.info("Deletando post: " + idPost);
        return service.delete(idPost);
    }

}
