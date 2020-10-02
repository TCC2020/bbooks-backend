package br.edu.ifsp.spo.bulls.usersApi.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tracking", produces="application/json")
@CrossOrigin(origins = "*")
public class ReadingTrackingController {
}
