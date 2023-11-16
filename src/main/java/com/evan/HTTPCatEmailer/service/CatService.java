package com.evan.HTTPCatEmailer.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CatService {

    private final WebClient webClient;

    public CatService(WebClient webClient){
        this.webClient = webClient;
    }

    public Mono<String> requestStatusFromCatCloneId(Long id){
        return webClient.get()
                .uri("http://localhost:8080/catAPI/sendStatusCode/id/" + id)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<ResponseEntity<byte[]>> requestImageFromCatCloneId(Long id){
        Mono<byte[]> imageBytes =  webClient.get()
                .uri("http://localhost:8080/catAPI/sendImage/id/" + id)
                .retrieve()
                .bodyToMono(byte[].class);
        return imageBytes.map(catImage -> {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(catImage, headers, org.springframework.http.HttpStatus.OK);
        });
    }

    public Mono<ResponseEntity<byte[]>> requestImageFromCatCloneStatus(String status){
        Mono<byte[]> imageBytes =  webClient.get()
                .uri("http://localhost:8080/catAPI/sendImage/status/" + status)
                .retrieve()
                .bodyToMono(byte[].class);
        return imageBytes.map(catImage -> {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(catImage, headers, org.springframework.http.HttpStatus.OK);
        });
    }

    public Mono<byte[]> convertMonoCatImageToMonoByteArray(Mono<ResponseEntity<byte[]>> monoImage) {
        return monoImage.map(HttpEntity::getBody);
    }
}
