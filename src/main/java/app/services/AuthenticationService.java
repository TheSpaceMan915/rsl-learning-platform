package app.services;

import app.dtos.PersonRequestDto;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthenticationService {

    private RestTemplate rest;

    public AuthenticationService(RestTemplate rest) {
        this.rest = rest;
    }

//    Authenticates the Person using Yandex ID API and gets the Person's data in response.
    public PersonRequestDto authenticate(String oauthToken) {
        String url = "https://login.yandex.ru/info";
        HttpEntity<Void> requestEntity = setupRequest(oauthToken);
        ResponseEntity<PersonRequestDto> response = rest.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                PersonRequestDto.class);
        return response.getBody();
    }

    // Sets up a request for Yandex ID API
    private HttpEntity<Void> setupRequest(String oauthToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "OAuth " + oauthToken);
        return new HttpEntity<>(headers);
    }
}
