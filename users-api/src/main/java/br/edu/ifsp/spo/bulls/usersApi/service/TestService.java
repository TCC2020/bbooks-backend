package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.dto.TestTO;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    public TestTO getTest(){
        TestTO testTO = new TestTO();
        testTO.setTeamName("Bull");
        testTO.setUniversity("IFSP - SPO");
        testTO.setYear(Long.valueOf(2020));
        return testTO;
    }

}
