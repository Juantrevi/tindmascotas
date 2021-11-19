package edu.egg.tinder.Main.Entidades;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Voto {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVoto;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRespuesta;
    @ManyToOne
    private Mascota mascota1;
    @ManyToOne
    private Mascota Mascota2;

    public Voto() {
    }

    public Voto(Date fechaVoto, Date fechaRespuesta, Mascota mascota1, Mascota getMascota2) {
        this.fechaVoto = fechaVoto;
        this.fechaRespuesta = fechaRespuesta;
        this.mascota1 = mascota1;
        this.Mascota2 = getMascota2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFechaVoto() {
        return fechaVoto;
    }

    public void setFechaVoto(Date fechaVoto) {
        this.fechaVoto = fechaVoto;
    }

    public Date getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(Date fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }

    public Mascota getMascota1() {
        return mascota1;
    }

    public void setMascota1(Mascota mascota1) {
        this.mascota1 = mascota1;
    }

    public Mascota getMascota2() {
        return Mascota2;
    }

    public void setMascota2(Mascota mascota2) {
        Mascota2 = mascota2;
    }
}
