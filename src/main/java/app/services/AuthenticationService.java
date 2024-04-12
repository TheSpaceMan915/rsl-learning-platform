package app.services;

import app.dtos.unique.PersonYandexData;

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
    public PersonYandexData authenticate(String oauthToken) {
        String url = "https://login.yandex.ru/info";
        HttpEntity<Void> requestEntity = createHeaders(oauthToken);
        ResponseEntity<PersonYandexData> response = rest.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                PersonYandexData.class);
        return response.getBody();
    }

    // Creates headers for the request
    private HttpEntity<Void> createHeaders(String oauthToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "OAuth " + oauthToken);
        return new HttpEntity<>(headers);
    }
}
