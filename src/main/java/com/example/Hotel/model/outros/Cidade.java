package com.example.Hotel.model.outros;

import javax.persistence.*;
import java.util.Objects;
@Entity
    @Table (name="tb_city")
    public class Cidade {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        @Column(name = "name_city", length = 45)
        private String name;

        @ManyToOne
        @JoinColumn(name = "state_id")
        private Estado state;


    public Estado getState() {return state;}
    public void setState(Estado state) {this.state = state;}
    public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public long getId() {
        return id;
    }
        public void setId(long id) {
        this.id = id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cidade city)) return false;
        return id == city.id && Objects.equals(name, city.name);
    }
    @Override
        public int hashCode() {
            return Objects.hash(id);
        }
}

