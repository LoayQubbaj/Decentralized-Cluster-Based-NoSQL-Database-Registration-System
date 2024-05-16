package com.example.RegistrationSystem.repo;

import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.example.RegistrationSystem.service.NodeService.getNodePort;

public class NodeRepo {
    private RestTemplate restTemplate = new RestTemplate();
    public void buildDatabaseSchema() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("username", "root");
        headers.set("token", "root123");
        headers.set("Content-Type", "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        String createDatabaseURL = "http://localhost:4001/database/createDB/RegistrationSystem";
        restTemplate.exchange(createDatabaseURL, HttpMethod.POST, requestEntity, String.class);

        try {
            String developersFilePath = "RegistrationSystem/src/main/java/com/example/RegistrationSystem/schema/developersSchema.json";
            String developersSchema = new String(Files.readAllBytes(Paths.get(developersFilePath)));
            String createCollectionDevelopersURL = "http://localhost:4001/collection/createCollection/RegistrationSystem/developers";
            HttpEntity<String> requestEntityDeveloper = new HttpEntity<>(developersSchema, headers);
            restTemplate.exchange(createCollectionDevelopersURL, HttpMethod.POST, requestEntityDeveloper, String.class);

            String testerFilePath = "RegistrationSystem/src/main/java/com/example/RegistrationSystem/schema/testerSchema.json";
            String testerSchema = new String(Files.readAllBytes(Paths.get(testerFilePath)));
            String createCollectionTesterURL = "http://localhost:4001/collection/createCollection/RegistrationSystem/testers";
            HttpEntity<String> requestEntityTester = new HttpEntity<>(testerSchema, headers);
            restTemplate.exchange(createCollectionTesterURL, HttpMethod.POST, requestEntityTester, String.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean isAdmin(String username, String token) {
        String url = "http://localhost:" + 4001 + "/validation/isAdmin/" + username + "/" + token;
        return Boolean.TRUE.equals(restTemplate.getForObject(url, Boolean.class));
    }
    public boolean isSupervisor(String username, String token) {
        if(!"null".equals(getNodePort(token))) {
            int nodePort=Integer.parseInt(getNodePort(token));
            String url = "http://localhost:" + nodePort + "/validation/isValidUser/" + username + "/" + token;
            return Boolean.TRUE.equals(restTemplate.getForObject(url, Boolean.class));
        }
        else return false;
    }

    public void addNewDoc(JSONObject data, String collectionName, HttpSession httpSession) {
        String nodePort = httpSession.getAttribute("nodePort").toString();
        String url = "http://localhost:" + nodePort + "/document/addDoc/RegistrationSystem/"+collectionName;
        HttpEntity<String> requestEntity =getRequestEntity(data.toString(),httpSession);
        restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    }


    public String showAllDeveloperData(HttpSession httpSession){
        String nodePort = httpSession.getAttribute("nodePort").toString();
        String url="http://localhost:"+nodePort+"/document/getAllDocuments/RegistrationSystem/developers";
        HttpEntity<String> requestEntity = getRequestEntity("",httpSession);
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class).getBody();
    }


    public HttpEntity<String> getRequestEntity(String data,HttpSession httpSession){
        String username = httpSession.getAttribute("username").toString();
        String token = httpSession.getAttribute("token").toString();
        HttpHeaders headers = new HttpHeaders();
        headers.set("username", username);
        headers.set("token", token);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>(data,headers);
        return requestEntity;
    }

    public String showAllDocInCollection(String collectionName, HttpSession httpSession){
        String nodePort = httpSession.getAttribute("nodePort").toString();
        String url="http://localhost:"+nodePort+"/document/getAllDocuments/RegistrationSystem/"+collectionName;
        HttpEntity<String> requestEntity = getRequestEntity("",httpSession);
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class).getBody();
    }
    public String showAllDataAffinity(HttpSession httpSession){
        String nodePort = httpSession.getAttribute("nodePort").toString();
        String url="http://localhost:"+nodePort+"/affinity/getAllDocToNodeAffinity";
        HttpEntity<String> requestEntity = getRequestEntity("",httpSession);
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class).getBody();
    }
}
