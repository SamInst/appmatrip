package com.example.Hotel.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tb_client")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long client_id;

    @Column(name = "client_user_name", length = 100)
    private String username;

    @Column(name = "client_complete_name", length = 100)
    private String nameComplete;


    @Column(name = "client_cpf")
    private String cpf;

    @Column(name = "client_phone")
    private String phone;

    @Column(name = "client_phone_confirm")
    private String phoneConfirm;

    @Column(name = "client_email")
    private String email;

    @Column(name = "client_email_confirm")
    private String emailConfirm;

    @Column(name = "client_password")
    private String password;


    @Column(name = "client_password_confirm")
    private String passwordConfirm;
    @Column(name = "client_adress")
    private String address;

    @Column(name = "client_birth")
    private LocalDate dataNacimento;




    public String getAddress() {
        return address;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setAddress(String adress) {
        this.address = adress;
    }
    public void setClient_id(Long id) {
        this.client_id = id;
    }
    public Long getClient_id() {return client_id;}
    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente client = (Cliente) o;
        return Objects.equals(client_id, client.client_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client_id);
    }
}