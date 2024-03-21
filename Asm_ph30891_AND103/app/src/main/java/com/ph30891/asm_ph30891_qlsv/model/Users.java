package com.ph30891.asm_ph30891_qlsv.model;

public class Users {
    private String id;
    private String email;
    private String password;
    private String name;
    private String avatar;

    public Users(String id, String email, String password, String name, String avatar) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.avatar = avatar;
    }

    public Users() {
    }

    public String getId() {
        return id;
    }

    public Users setId(String id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Users setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Users setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public Users setName(String name) {
        this.name = name;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public Users setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }
}
