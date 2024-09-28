package com.scm.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Entity
// using lambok
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int UId;
    @Column(name = "user_name",nullable = false)
    private String name;
    @Column(unique = true,nullable = false)
    private String email;
    @Getter(value = AccessLevel.NONE) // lambok not create getter of password field
    private String password;
    @Column(length = 1000)
    private String about;
    @Column(length = 1000)
    private String profilePic;
    private String phoneNumber;

    @Getter(value = AccessLevel.NONE) // lambok not create getter of enabled field
    private boolean enabled=true; // verification when account is verify then return true
    private boolean emailVerified=false;
    private boolean phoneVerified=false;

    // SELF GOOGLE
    @Enumerated(value = EnumType.STRING)
    private Providers providers=Providers.SELF;
    private String providerUserId;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Contact> contacts=new ArrayList<>();


    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList=new ArrayList<>();

    // accessing data from database for spring security

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      // list of roles[USER, ADMIN]
        //Collection of SimpleGrantedAuthority[roles{USER,ADMIN}]
       Collection<SimpleGrantedAuthority> roles=roleList.stream().map(role-> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        return roles;
    }

    // for login username is email
    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;    }

    //verification
    @Override
    public boolean isEnabled(){
        return this.enabled;
    }


    @Override
    public String getPassword() {
        return this.password;
    }
}
