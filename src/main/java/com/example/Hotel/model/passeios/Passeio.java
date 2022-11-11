package com.example.Hotel.model.passeios;

import com.example.Hotel.model.outros.Categoria;
import com.example.Hotel.model.outros.Cidade;

import javax.persistence.*;

@Entity
@Table(name = "tb_passeios")
public class Passeio {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Categoria categoria;

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Column(name = "nome_passeio")
    private String nomePasseio;

    @Column(name = "descricao_passeio", length = 999)
    private String descricao;

//    @ManyToOne
//    @JoinColumn(name = "guia_turistico")
//    private GuiaTuristico guiaTuristico;

    @Embedded
    @JoinColumn(name = "preco_passeio")
    private PasseiosPrecos passeiosPrecos;

    @ManyToOne
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;

    private int estrela;

    private Integer Destaque;

    public Integer getDestaque() {
        return Destaque;
    }

    public void setDestaque(Integer destaque) {
        Destaque = destaque;
    }

    public PasseiosPrecos getPasseiosPrecos() {
        return passeiosPrecos;
    }

    public int getEstrela() {
        return estrela;
    }

    public void setEstrela(int estrela) {
        this.estrela = estrela;
    }

    public void setPasseiosPrecos(PasseiosPrecos passeiosPrecos) {
        this.passeiosPrecos = passeiosPrecos;
    }

    public String getNomePasseio() {
        return nomePasseio;
    }
    public void setNomePasseio(String nomePasseio) {
        this.nomePasseio = nomePasseio;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public Cidade getCidade() {
        return cidade;
    }
    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
}
