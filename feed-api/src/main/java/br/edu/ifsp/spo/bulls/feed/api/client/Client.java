package br.edu.ifsp.spo.bulls.feed.api.client;

import br.edu.ifsp.spo.bulls.common.api.dto.ProfileTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class Client {

    private WebClient wc;

    @Value("${server.users}")
    private String usersApi;

    public ProfileTO getProfileByToken(String token) {
        this.wc =  WebClient
                .create(usersApi + "token/" + token);

        ProfileTO profile = wc.get().exchange().block()
                .bodyToMono(ProfileTO.class).block();

        return profile;
    }
}
