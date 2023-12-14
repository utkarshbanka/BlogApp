package com.Blogging.Blogging.Controller;

import com.Blogging.Blogging.Services.UserService;
import com.Blogging.Blogging.payloads.ApiResponse;
import com.Blogging.Blogging.payloads.UserDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
     private UserService userService;

    @PostMapping("/account/create")
    public ResponseEntity<UserDto> CreateUser(@Valid @RequestBody UserDto userDto)
    {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserDto creatUserdto = userService.CreateUser(userDto);
        return  new ResponseEntity<>(creatUserdto, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId")Integer userId)
    {
        UserDto updateuser = userService.updateuser(userDto,userId);

        return ResponseEntity.ok(updateuser);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deletUser(@PathVariable("userId") Integer userId)
    {
        userService.deletUser(userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User Delted Succesfully",true),HttpStatus.OK);

    }

    @GetMapping("/alluser")
    public ResponseEntity<List<UserDto>> getallUser()
    {
         return ResponseEntity.ok(userService.getAll_User());
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable("userId") Integer userId)
    {
         UserDto single = userService.getUserById(userId);

         return ResponseEntity.ok(single);
    }



}
