package com.stockmarket.company.entity;

import javax.persistence.*;

@Entity
@Table(name = "MyUser")
@NamedQuery(name="MyUser.findByEmail", query = "SELECT u from MyUser u WHERE u.email = :email")
@NamedQuery(name="MyUser.findByUsername", query = "SELECT u from MyUser u WHERE u.username = :username")
public class MyUser {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false, unique = true)
    public String email;

    private String mobile;

    private Boolean confirmed;

    private Boolean admin;

    // TODO: enumerate role
//    public enum Role {
//        ADMIN, MYUSER;
//    }

    // CONSTRUCTOR

    public MyUser() {
        super();
    }

    public MyUser(String username, String password, String email, String mobile, Boolean confirmed, Boolean admin) {
        super();
        this.username = username;
        this.password = password;
        this.email = email;
        this.mobile = mobile;
        this.confirmed = confirmed;
        this.admin = admin;
    }

    // GETTERS AND SETTERS


    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getConfirmed() {
        return this.confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Boolean getAdmin() {
        return this.admin;
    }

    public void setAdmin(Boolean admin) {
       this.admin = admin;
    }


    @Override
    public String toString() {
        return "MyUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", confirmed=" + confirmed +
                ", admin=" + admin +
                '}';
    }
}
