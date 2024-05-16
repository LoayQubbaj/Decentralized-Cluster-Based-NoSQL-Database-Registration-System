package com.example.RegistrationSystem.service;

import com.example.RegistrationSystem.repo.NodeRepo;
import jakarta.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.ui.Model;

import java.util.HashMap;

public class NodeService {
    private NodeRepo nodeRepo=new NodeRepo();

    private static NodeService instance;
    private static HashMap<String, Integer> tokenNode = new HashMap<>();


    private NodeService(){}
    public static NodeService getInstance(){
        if(instance==null){
            instance=new NodeService();
        }
        return instance;
    }
    public void buildDatabaseSchema() {
        nodeRepo.buildDatabaseSchema();
    }
    public boolean isAdmin(String username, String token, HttpSession httpSession, Model model) {
        if (nodeRepo.isAdmin(username, token)) {

            model.addAttribute("username", username);
            model.addAttribute("token", token);

            httpSession.setAttribute("username", username);
            httpSession.setAttribute("token", token);

            return true;
        } else {
            return false;
        }
    }

    public static void assignNodeToToken(String token, int nodePort) {
        tokenNode.put(token, nodePort);
    }
    public static String getNodePort(String token) {
        return String.valueOf(tokenNode.get(token));

    }

    public boolean isSupervisor(String username, String token,HttpSession httpSession, Model model) {

        model.addAttribute("username", username);
        model.addAttribute("token", token);

        if (nodeRepo.isSupervisor(username, token)) {

            int nodePort = Integer.parseInt(getNodePort(token));

            model.addAttribute("username", username);
            model.addAttribute("token", token);
            model.addAttribute("nodePort", nodePort);

            httpSession.setAttribute("username", username);
            httpSession.setAttribute("token", token);
            httpSession.setAttribute("nodePort", nodePort);
            httpSession.setAttribute("content-type","application/json");

            return true;
        } else {

            return false;
        }
    }
    public void addNewDeveloper( String developerName,  String developerPhone,
                               String developerAddress,  int departmentNumber,
                               HttpSession httpSession){
        JSONObject developerData = new JSONObject();
        developerData.put("name", developerName);
        developerData.put("phone", developerPhone);
        developerData.put("address", developerAddress);
        developerData.put("departmentNumber", departmentNumber);
        nodeRepo.addNewDoc(developerData,"developers",httpSession);
    }

   public void showAllDeveloperData(HttpSession httpSession, Model model){
        String response =nodeRepo.showAllDeveloperData(httpSession);
       JSONArray AllDeveloperData=new JSONArray(new JSONObject(response).get("message").toString());
       model.addAttribute("AllDeveloperData", AllDeveloperData);
   }


    public void addNewTester( String testerName, String phone, HttpSession httpSession){
        JSONObject studentData = new JSONObject();
        studentData.put("testerName", testerName);
        studentData.put("phone", phone);
        nodeRepo.addNewDoc(studentData,"testers",httpSession);
    }
    public void showAllTesters(HttpSession httpSession, Model model){
        String response =nodeRepo.showAllDocInCollection("testers",httpSession);
        JSONArray allTester=new JSONArray(new JSONObject(response).get("message").toString());
        model.addAttribute("allTester", allTester);
    }
    public void showAllAffinityData(HttpSession httpSession, Model model){
        String response =nodeRepo.showAllDataAffinity(httpSession);
        JSONArray AffinityData=new JSONArray(new JSONObject(response).get("message").toString());
        model.addAttribute("AffinityData", AffinityData);
    }

}
