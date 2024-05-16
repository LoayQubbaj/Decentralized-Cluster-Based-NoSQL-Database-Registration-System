package com.example.RegistrationSystem.repo;

import jakarta.servlet.http.HttpSession;
import org.json.JSONArray;
import org.springframework.web.client.RestTemplate;

public class BootstrapRepo {
    private RestTemplate restTemplate = new RestTemplate();

    public String registerNewSupervisor(String username) {
        String url = "http://localhost:4000/bootstrap/registerNewUser/" + username;
        return restTemplate.getForObject(url, String.class);
    }
    public JSONArray getAllSupervisors() {
        String url = "http://localhost:4000/bootstrap/getAllUsers";
        String usersData = restTemplate.getForObject(url, String.class);
        return new JSONArray(usersData);
    }
    public void removeSupervisor(String token) {
        String url = "http://localhost:4000/bootstrap/removeUser/" + token;
        restTemplate.getForObject(url, String.class);
    }


}
