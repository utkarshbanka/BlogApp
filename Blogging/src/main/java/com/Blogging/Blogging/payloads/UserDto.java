package com.Blogging.Blogging.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {


    private Integer id;
    @NotEmpty
    private String name;

    @Email
    private String email;

    @NotEmpty
    @Size(min = 8, max = 100)
    private String password;

    @NotEmpty
    private String about;

}
