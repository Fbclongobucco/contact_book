package com.buccodev.contact_book.resources;

import com.buccodev.contact_book.dto.UserDTO;
import com.buccodev.contact_book.entities.Users;
import com.buccodev.contact_book.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersResource {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllusers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size){

        Page<Users> user = userService.getAllUsers(page, size);

        List<UserDTO> userDTOS = user.stream().map(x-> new UserDTO(x.getName(), x.getPassword(), x.getEmail())).toList();

        return ResponseEntity.ok(userDTOS);

    }

}
