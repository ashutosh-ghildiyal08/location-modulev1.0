package com.location.LocationModule.Controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.location.LocationModule.Service.UserService;
import com.location.LocationModule.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper mapper;

    @JmsListener(destination = "demo")
    public void consume(String message) throws JsonProcessingException {

        //2. Convert JSON to List of Person objects
        //Define Custom Type reference for List<Person> type
        TypeReference<List<User>> mapType = new TypeReference<>() {};
        List<User> jsonToPersonList = mapper.readValue(message, mapType);
        for (User user: jsonToPersonList) {
            System.out.println(user);
        }
        userService.addUsers(jsonToPersonList);

    }
    @GetMapping("/users")
    public List<User> getAllLocation() {
        return userService.getAllUsers();
    }

    @PostMapping("/users")
    public String addUsers(List<User> userList) {
        userService.addUsers(userList);
        return "Users Saved";
    }



}

