package com.africacrypto.service;

import com.africacrypto.dto.LoginRequest;
import com.africacrypto.dto.RegisterRequest;
import com.africacrypto.dto.UserDTO;

public interface UserService {
    UserDTO register(RegisterRequest request);
    String login(LoginRequest request); // returns JWT
}
