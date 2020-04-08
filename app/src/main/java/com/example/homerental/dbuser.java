package com.example.homerental;

public class dbuser {
    private String name,email_id;
    private Long mb;


    public dbuser(String name, Long mb,String email_id) {
        this.name = name;
        this.mb = mb;
        this.email_id=email_id;
    }


    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public dbuser() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMb(Long mb) {
        this.mb = mb;
    }

    public String getName() {
        return name;
    }

    public Long getMb() {
        return mb;
    }
}
