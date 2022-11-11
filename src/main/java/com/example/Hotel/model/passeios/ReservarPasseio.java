package com.example.Hotel.model.passeios;

import com.example.Hotel.model.outros.Cliente;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tb_reservar_passeios")
public class ReservarPasseio {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_passeio", nullable = false)
    private Passeio passeio;

    @ManyToOne
    @JoinColumn(name = "id_client",nullable = false)
    private Cliente client;

    @Column(name = "amount_adult",nullable = false)
    private Integer adults;

    @Column(name = "amount_childs")
    private Integer childs;

    @Column(name = "data_marcada",nullable = false)
    private LocalDate data;

    @Column(name = "horario")
    private String horario;

    public LocalDate getData() {
        return data;
    }

    @ManyToOne
    @JoinColumn(name = "guia_turistico")
    private GuiaTuristico guiaTuristico;

    public GuiaTuristico getGuiaTuristico() {
        return guiaTuristico;
    }

    public void setGuiaTuristico(GuiaTuristico guiaTuristico) {
        this.guiaTuristico = guiaTuristico;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String hora) {
        this.horario = hora;
    }

    public Passeio getPasseio() {
        return passeio;
    }

    public void setPasseio(Passeio passeio) {
        this.passeio = passeio;
    }

    public Cliente getClient() {
        return client;
    }

    public void setClient(Cliente client) {
        this.client = client;
    }

    public Integer getAdults() {
        return adults;
    }

    public void setAdults(Integer adults) {
        this.adults = adults;
    }

    public Integer getChilds() {
        return childs;
    }

    public void setChilds(Integer childs) {
        this.childs = childs;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }
}
