package com.blog.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;
    @NotEmpty
    @Size(min=4,message = "User name must be more than of four character")
    private String name;

    @Email(message = "Your email is not valid")
    private String email;

    @NotEmpty(message = "Please enter a valid password")
    private String password;

    @NotEmpty(message = "About should not be empty ")
    private String about;
}
