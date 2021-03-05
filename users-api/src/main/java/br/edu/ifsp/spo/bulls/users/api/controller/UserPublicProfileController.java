package br.edu.ifsp.spo.bulls.users.api.controller;

import br.edu.ifsp.spo.bulls.common.api.dto.user.UserPublicProfileCreateTO;
import br.edu.ifsp.spo.bulls.common.api.dto.user.UserPublicProfileTO;
import br.edu.ifsp.spo.bulls.common.api.dto.user.UserPublicProfileUpdateTO;
import br.edu.ifsp.spo.bulls.users.api.service.UserPublicProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/users/public-profiles", produces="application/json")
@CrossOrigin(origins = "*")
public class UserPublicProfileController {
    @Autowired
    private UserPublicProfileService service;

    @PostMapping
    public UserPublicProfileTO create(@RequestHeader("AUTHORIZATION") String token, @RequestBody UserPublicProfileCreateTO publicProfile) {
        return service.create(token, publicProfile);
    }

    @PutMapping
    public UserPublicProfileTO update(@RequestHeader("AUTHORIZATION") String token, @RequestBody UserPublicProfileUpdateTO publicProfile) {
        return service.update(token, publicProfile);
    }

    @PutMapping("/follow/{id}")
    public HttpStatus follow(@RequestHeader("AUTHORIZATION") String token, @RequestParam("id") UUID publicProfileId) {
        return service.follow(token, publicProfileId);
    }

    @PutMapping("/unfollow/{id}")
    public void unfollow(@RequestHeader("AUTHORIZATION") String token, @RequestParam("id") UUID publicProfileId) {
        service.unfollow(token, publicProfileId);
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader("AUTHORIZATION") String token, @RequestParam("id") UUID publicProfileId) {
        service.delete(token, publicProfileId);
    }
}
