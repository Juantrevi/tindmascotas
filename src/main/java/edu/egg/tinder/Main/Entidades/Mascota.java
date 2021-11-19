package edu.egg.tinder.Main.Entidades;

import edu.egg.tinder.Main.Enumeraciones.Sexo;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Mascota {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    @Temporal(TemporalType.TIMESTAMP)
    private Date alta;
    @Temporal(TemporalType.TIMESTAMP)
    private Date baja;
    @ManyToOne
    private Usuario usuario;
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
    @OneToOne
    private Foto foto;

    public Mascota() {
    }

    public Mascota(String id, String nombre, Date alta, Date baja, Usuario usuario, Sexo sexo, Foto foto) {
        this.id = id;
        this.nombre = nombre;
        this.alta = alta;
        this.baja = baja;
        this.usuario = usuario;
        this.sexo = sexo;
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getAlta() {
        return alta;
    }

    public void setAlta(Date alta) {
        this.alta = alta;
    }

    public Date getBaja() {
        return baja;
    }

    public void setBaja(Date baja) {
        this.baja = baja;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }
}
