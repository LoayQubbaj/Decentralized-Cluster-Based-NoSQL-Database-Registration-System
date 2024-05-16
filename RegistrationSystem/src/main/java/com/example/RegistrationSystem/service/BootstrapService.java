package com.example.RegistrationSystem.service;

import com.example.RegistrationSystem.repo.BootstrapRepo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.ui.Model;

import java.util.HashMap;

import static com.example.RegistrationSystem.service.NodeService.assignNodeToToken;

public class BootstrapService {
    private static BootstrapService instance;
    private BootstrapRepo bootstrapRepo=new BootstrapRepo();
private BootstrapService(){

}
public static BootstrapService getInstance(){
    if(instance==null){
        instance=new BootstrapService();
    }
    return instance;

}
    public void prepareSupervisorData(String username, Model model){

        JSONObject jsonObject = new JSONObject(bootstrapRepo.registerNewSupervisor(username));
        String token = jsonObject.getString("token");
        int nodePort = jsonObject.getInt("nodePort");

        model.addAttribute("username", username);
        model.addAttribute("token", token);
        model.addAttribute("nodePort",nodePort-4000);
        assignNodeToToken(token, nodePort);
    }

    public void removeSupervisorData(String token){
        bootstrapRepo.removeSupervisor(token);
    }
    public void getAllSupervisors(Model model){
        JSONArray allSupervisors=bootstrapRepo.getAllSupervisors();
        model.addAttribute("allSupervisors",allSupervisors);

    }
}
