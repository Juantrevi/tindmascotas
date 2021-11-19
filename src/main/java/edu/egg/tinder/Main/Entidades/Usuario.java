package edu.egg.tinder.Main.Entidades;

import edu.egg.tinder.Main.Entidades.Foto;
import edu.egg.tinder.Main.Entidades.Zona;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private String apellido;
    private String mail;
    private String clave;
    @Temporal(TemporalType.TIMESTAMP)
    private Date alta;
    @Temporal(TemporalType.TIMESTAMP)
    private Date baja;
    @ManyToOne //El usuario tiene una zona pero del otro lado una zona puede tener muchos usuarios
    private Zona zona;
    @OneToOne
    private Foto foto;

    public Usuario() {
    }


    public Usuario(String nombre, String apellido, String mail, String clave, Date alta, Date baja, Zona zona, Foto foto) {

        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.clave = clave;
        this.alta = alta;
        this.baja = baja;
        this.zona = zona;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
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

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }
}
