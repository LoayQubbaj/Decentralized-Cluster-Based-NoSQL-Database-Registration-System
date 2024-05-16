package com.example.RegistrationSystem.controller;

import com.example.RegistrationSystem.service.NodeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import com.example.RegistrationSystem.service.BootstrapService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {
    private NodeService nodeService = NodeService.getInstance();

    private BootstrapService bootstrapService = BootstrapService.getInstance();

    @GetMapping("/")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("registerNewSupervisor")
    public String registerNewSupervisor() {
        return "register-Supervisor";
    }

    @PostMapping("returnSupervisorData")
    public String showSupervisorData(@RequestParam String username, Model model) {
        bootstrapService.prepareSupervisorData(username, model);
        return "return-Supervisor-Data";
    }

    @GetMapping("enterAsAdmin")
    public String enterAsAdmin() {
        return "enter-as-admin";
    }

    @GetMapping("removeSupervisorPage")
    public String removeUser() {
        return "remove-supervisor";
    }

    @PostMapping("removeSupervisor")
    public String removeSupervisorData(@RequestParam String token) {
        bootstrapService.removeSupervisorData(token);
        return "success";
    }

    @GetMapping("allSupervisors")
    public String allSupervisors(Model model) {
        bootstrapService.getAllSupervisors(model);
        return "show-all-supervisors-data";
    }

    @PostMapping("isAdmin")
    public String isAdmin(@RequestParam String username, @RequestParam String token, Model model, HttpSession httpSession) {
        if (nodeService.isAdmin(username, token, httpSession, model)) {
            return "admin-page";
        } else return "failed";

    }


    //sign in supervisor
    @GetMapping("firstPage")
    public String firstPage() {
        return "system";
    }

    @GetMapping("signInSupervisorPage")
    public String signInSupervisorPage() {
        return "sign-in-supervisor-page";
    }
    @PostMapping("system")
    public String isSupervisor(@RequestParam String username, @RequestParam String token, HttpSession httpSession, Model model) {
        if (nodeService.isSupervisor(username, token, httpSession, model)) {
            return "system";
        } else {
            return "failed";
        }

    }

    //add new developer
    @GetMapping("addNewDeveloper")
    public String RegisterNewDeveloper() {
        return "add-New-Developer";
    }

    @PostMapping("saveDeveloperData")
    public String saveDeveloperData(@RequestParam String developerName, @RequestParam String developerPhone, @RequestParam String developerAddress, @RequestParam int departmentNumber, HttpSession httpSession) {
        nodeService.addNewDeveloper(developerName, developerPhone, developerAddress, departmentNumber, httpSession);
        return "success";
    }

    //show all developers data
    @GetMapping("showAllDeveloperData")
    public String showAllDeveloperData(HttpSession httpSession, Model model) {
        nodeService.showAllDeveloperData(httpSession,model);
        return "show-All-Developer-Data";
    }


    //add new tester
    @GetMapping("addNewTester")
    public String addNewTester() {
        return "add-new-tester";
    }

    @PostMapping("saveTesterData")
    public String saveTesterData(@RequestParam String testerName, @RequestParam String phone, HttpSession httpSession, Model model) {
        nodeService.addNewTester(testerName, phone, httpSession);
        return "success";
    }
    //show all tester
    @GetMapping("allTesters")
    public String allTesters(HttpSession httpSession, Model model) {
        nodeService.showAllTesters(httpSession,model);
        return "show-all-tester";
    }
    @GetMapping("loadBalance")
    public String showLoadBalance(HttpSession httpSession, Model model) {
        nodeService.showAllAffinityData(httpSession,model);
        return "affinity-load-balance";
    }

}

