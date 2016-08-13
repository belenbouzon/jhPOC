package com.belen.pruebajh.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Gama.
 */
@Entity
@Table(name = "gama")
public class Gama implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "cliente")
    private String cliente;

    @Column(name = "tono")
    private String tono;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getTono() {
        return tono;
    }

    public void setTono(String tono) {
        this.tono = tono;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Gama gama = (Gama) o;
        if(gama.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, gama.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Gama{" +
            "id=" + id +
            ", cliente='" + cliente + "'" +
            ", tono='" + tono + "'" +
            '}';
    }
}
