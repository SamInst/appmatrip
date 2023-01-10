package com.example.Hotel.model.outros;

import javax.persistence.*;

@Entity
@Table(name = "tb_categoria")
public class Categoria {
    @Id
    private Long id;

@Column(name = "categoria")
    private String name;

    public Categoria(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
}
