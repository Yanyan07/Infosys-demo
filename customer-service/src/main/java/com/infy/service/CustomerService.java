package com.infy.service;

import com.infy.dto.LoginDTO;
import com.infy.dto.RegisterDTO;

public interface CustomerService {
    String register(RegisterDTO customerDTO);
    String login(LoginDTO loginDTO);

}
