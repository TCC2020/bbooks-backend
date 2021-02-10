package br.edu.ifsp.spo.bulls.competition.api.service;

import br.edu.ifsp.spo.bulls.competition.api.repository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ExchangeService {
    @Autowired
    private ExchangeRepository repository;

}
