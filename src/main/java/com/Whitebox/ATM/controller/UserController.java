package com.Whitebox.ATM.controller;
import com.Whitebox.ATM.dao.UserDao;
import com.Whitebox.ATM.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class UserController {

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    //TODO endpointurile de withdraw etc

}
