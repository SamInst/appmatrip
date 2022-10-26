package com.example.Hotel.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table (name="tb_states")
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "state_name")
    private String name;


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {this.id = id;}
    public Long getId() {return id;}
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estado state = (Estado) o;
        return Objects.equals(id, state.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
