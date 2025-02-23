package com.back.backend.mapper;


import com.back.backend.entities.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO {
    private String username;
    private String firstname;
    private String email;
    private String password;
    private String authorities;

    public  static UserDTO fromUser(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setFirstname(user.getFirstname());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setAuthorities(user.getAuthorities());
        return userDTO;
    }

    public  static List<UserDTO> fromUserList(List<User> users) {
       List<UserDTO> userDTOs = new ArrayList<>();
        users.forEach(user -> userDTOs.add(UserDTO.fromUser(user)));
        return userDTOs;
    }


}
