package com.evan.HTTPCatEmailer.service;

import com.evan.HTTPCatEmailer.model.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CatService {

    private final WebClient webClient;

    public CatService(WebClient webClient){
        this.webClient = webClient;
    }

    public Mono<HttpStatus> requestHttpStatusFromCatCloneId(Long id){
        return webClient.get()
                .uri("http://localhost:8080/catAPI/sendHttpStatus/id/" + id)
                .retrieve()
                .bodyToMono(HttpStatus.class);
    }
}
