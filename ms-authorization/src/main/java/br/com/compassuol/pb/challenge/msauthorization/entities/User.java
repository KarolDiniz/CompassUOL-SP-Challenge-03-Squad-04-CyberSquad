package br.com.compassuol.pb.challenge.msauthorization.entities;

import lombok.Getter;

import java.util.List;

@Getter
public class User {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private List<Role> roles;

}
