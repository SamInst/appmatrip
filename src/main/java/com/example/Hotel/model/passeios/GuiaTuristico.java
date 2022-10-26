package com.example.Hotel.model.passeios;

import javax.persistence.*;

@Entity
@Table (name = "tb_guia")
public class GuiaTuristico {
    @Id
    private Long id;
    @Column(name = "nome_do_guia")

    private String nome;
    private String descricaoGuia;

    @Column(name = "qualidade_do_guia")
    private int qualidadeGuia;

    @ManyToOne
    @JoinColumn(name = "guia_passeio")
    private Passeio passeio;


    public Passeio getPasseio() {
        return passeio;
    }

    public void setPasseio(Passeio passeio) {
        this.passeio = passeio;
    }

    public void setId(Long id) {this.id = id;}
    public Long getId() {return id;}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricaoGuia() {
        return descricaoGuia;
    }

    public void setDescricaoGuia(String descricaoGuia) {
        this.descricaoGuia = descricaoGuia;
    }

    public int getQualidadeGuia() {
        return qualidadeGuia;
    }

    public void setQualidadeGuia(int qualidadeGuia) {
        this.qualidadeGuia = qualidadeGuia;
    }
}
