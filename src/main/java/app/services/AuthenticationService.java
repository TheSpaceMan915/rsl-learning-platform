package app.services;

import app.dtos.unique.GetPersonRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service class for handling authentication using Yandex ID API.
 * This class provides methods to authenticate users and retrieve their personal data from Yandex.
 *
 * @author  Nikita Kolychev
 */
@Service
public class AuthenticationService {

    private final RestTemplate rest;

    /**
     * Constructs an AuthenticationService with the provided RestTemplate.
     * @param rest the RestTemplate object for HTTP requests
     */
    public AuthenticationService(RestTemplate rest) {
        this.rest = rest;
    }

    /**
     * Authenticates a user using their OAuth token and retrieves their personal data from Yandex.
     * This method makes a GET request to the Yandex ID API and expects a {@link GetPersonRequest} object in response.
     *
     * @param oauthToken the OAuth token used for authentication with the Yandex API
     * @return {@link GetPersonRequest} object containing user's personal data retrieved from Yandex
     */
    public GetPersonRequest authenticate(String oauthToken) {
        String url = "https://login.yandex.ru/info";
        HttpEntity<Void> requestEntity = createHeaders(oauthToken);
        ResponseEntity<GetPersonRequest> response = rest.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                GetPersonRequest.class);
        return response.getBody();
    }

    /**
     * Creates HTTP headers necessary for the authentication request.
     * Adds the OAuth token to the request headers.
     *
     * @param oauthToken the OAuth token to be added to the headers
     * @return {@link HttpEntity} with the OAuth authorization headers
     */
    private HttpEntity<Void> createHeaders(String oauthToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "OAuth " + oauthToken);
        return new HttpEntity<>(headers);
    }
}
