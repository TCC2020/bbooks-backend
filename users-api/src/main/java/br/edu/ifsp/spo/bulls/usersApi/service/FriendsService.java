package br.edu.ifsp.spo.bulls.usersApi.service;

import br.edu.ifsp.spo.bulls.usersApi.dto.FriendTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class FriendsService {
    @Autowired
    private UserService service;

    public HttpStatus add(FriendTO friendTO, String token) {
        return HttpStatus.ACCEPTED;
    }
}
