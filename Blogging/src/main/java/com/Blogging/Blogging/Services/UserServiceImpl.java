package com.Blogging.Blogging.Services;
import com.Blogging.Blogging.Exception.ResourceNotFoundException;
import com.Blogging.Blogging.Model.User;
import com.Blogging.Blogging.Repositry.UserRepo;
import com.Blogging.Blogging.payloads.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl  implements  UserService{


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto CreateUser(UserDto userdto) {

        User user = this.dtoUser(userdto);
        User saveduser = userRepo.save(user);

        return this.getUserDto(saveduser);

    }

    @Override
    public UserDto updateuser(UserDto userdto, Integer userId) {

        User user = userRepo.findById(userId).
                orElseThrow(()-> new ResourceNotFoundException("User","id", userId.longValue()));

         user.setAbout(userdto.getAbout());
         user.setEmail(userdto.getEmail());
         user.setName(userdto.getName());
         user.setPassword(userdto.getPassword());


          User updateduser = userRepo.save(user);

        return  this.getUserDto(updateduser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId.longValue()));

        return getUserDto(user);
    }

    @Override
    public List<UserDto> getAll_User() {

        List<User> usera = userRepo.findAll();

        List<UserDto> userd = usera.stream().map(user->getUserDto(user)).collect(Collectors.toList());

       return  userd;
    }

    @Override
    public void deletUser(Integer userId) {

         User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","ID", userId.longValue()));

           userRepo.delete(user);
    }

    private User dtoUser(UserDto userDto)
    {

        User user = modelMapper.map(userDto,User.class);

        return user;

    }

    private UserDto getUserDto(User user)
    {
        UserDto userDto = modelMapper.map(user,UserDto.class);

        return  userDto;
    }




}
