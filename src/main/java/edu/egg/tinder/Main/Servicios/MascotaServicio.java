package edu.egg.tinder.Main.Servicios;

import edu.egg.tinder.Main.Entidades.Foto;
import edu.egg.tinder.Main.Entidades.Mascota;
import edu.egg.tinder.Main.Errores.ErrorServicio;
import edu.egg.tinder.Main.Repositorios.MascotaRepositorio;
import edu.egg.tinder.Main.Repositorios.UsuarioRepositorio;
import edu.egg.tinder.Main.Enumeraciones.Sexo;
import edu.egg.tinder.Main.Entidades.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
public class MascotaServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private MascotaRepositorio mascotaRepositorio;
    @Autowired
    private FotoServicio fotoServicio;
    @Transactional
    public void agregarMascota(MultipartFile archivo, String id, String nombremascota, Sexo sexomascota) throws ErrorServicio {

        Usuario usuario = usuarioRepositorio.findById(id).get();

        validar(nombremascota, sexomascota);

        Mascota mascota = new Mascota();
        mascota.setNombre(nombremascota);
        mascota.setSexo(sexomascota);
        mascota.setAlta(new Date());

        Foto foto = fotoServicio.guardar(archivo);
        mascota.setFoto(foto);
        mascotaRepositorio.save(mascota);

    }
    @Transactional
    public void modificarMascota(MultipartFile archivo, String idUsuario, String idMascota, String nombreMascota, Sexo sexoMascota) throws ErrorServicio {

        validar(nombreMascota, sexoMascota);

        Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota); //Va a la base de datos y busca una mascota por id

        if (respuesta.isPresent()) {
            Mascota mascota = respuesta.get();
            if (mascota.getUsuario().getId().equals(idUsuario)) { //Si el id que nos pasaron es del dueño, modificamos
                mascota.setNombre(nombreMascota);
                mascota.setSexo(sexoMascota);

                String idFoto = null;
                if (mascota.getFoto()!= null){
                    idFoto = mascota.getFoto().getId();
                }
                Foto foto = fotoServicio.actualizar(idFoto, archivo);
                mascota.setFoto(foto);
                mascotaRepositorio.save(mascota);
            } else {
                throw new ErrorServicio("No tiene permisos para realizar la operacion");
            }
        } else {
            throw new ErrorServicio("No existe mascota con ese id");
        }

    }

    public void validar(String nombre, Sexo sexo) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }

        if (sexo == null) {
            throw new ErrorServicio("El sexo no puede ser nulo");
        }
    }
    @Transactional
    public void eliminar(String idUsuario, String idMascota) throws ErrorServicio {
        Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota); //Va a la base de datos y busca una mascota por id

        if (respuesta.isPresent()) {
            Mascota mascota = respuesta.get();
            if (mascota.getUsuario().getId().equals(idUsuario)) { //Si el id que nos pasaron es del dueño, modificamos
                mascota.setBaja(new Date());
                mascotaRepositorio.save(mascota);
            }else{
                throw new ErrorServicio("No tiene permisos para eliminar");
            }
        }else{
            throw new ErrorServicio("No existe la mascota");
        }
    }
}