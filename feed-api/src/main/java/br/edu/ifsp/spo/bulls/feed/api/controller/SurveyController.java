package br.edu.ifsp.spo.bulls.feed.api.controller;


import br.edu.ifsp.spo.bulls.common.api.dto.survey.SurveyTO;
import br.edu.ifsp.spo.bulls.common.api.dto.survey.SurveyVoteTO;
import br.edu.ifsp.spo.bulls.feed.api.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/surveys", produces="application/json")
@CrossOrigin(origins = "*")
public class SurveyController {
    @Autowired
    private SurveyService service;

    @PutMapping
    public SurveyTO vote(@RequestHeader("AUTHORIZATION") String token, @RequestBody SurveyVoteTO dto) {
        return service.vote(token, dto);
    }
}
