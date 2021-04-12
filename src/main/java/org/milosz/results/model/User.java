package org.milosz.results.model;


import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Entity
public class User {

    @javax.persistence.Id
    @GeneratedValue
    private Long id;

    private String login;

    private Boolean disabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public User() {
    }

    public User(Long id, String login, Boolean disabled) {
        this.id = id;
        this.login = login;
        this.disabled = disabled;
    }
}
