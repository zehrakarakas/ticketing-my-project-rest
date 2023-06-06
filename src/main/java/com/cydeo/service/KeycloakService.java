package com.cydeo.service;

import com.cydeo.dto.UserDTO;

import javax.ws.rs.core.Response;

public interface KeycloakService {

    Response userCreate(UserDTO dto);  //Response coming in the javax //that Response create a user in the keycloak//Response class access this user
    void delete(String username);
}
