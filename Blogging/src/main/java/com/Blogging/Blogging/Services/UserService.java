package com.Blogging.Blogging.Services;

import com.Blogging.Blogging.Model.User;
import com.Blogging.Blogging.payloads.UserDto;

import java.util.List;

public interface UserService {

      public UserDto CreateUser(UserDto user);
      public UserDto updateuser(UserDto user, Integer userId);
      public UserDto getUserById(Integer userId);

      List<UserDto> getAll_User();
      public void deletUser(Integer userId);

}
