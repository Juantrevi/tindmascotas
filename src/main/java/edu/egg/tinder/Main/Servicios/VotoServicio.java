package edu.egg.tinder.Main.Servicios;

import edu.egg.tinder.Main.Entidades.Mascota;
import edu.egg.tinder.Main.Entidades.Voto;
import edu.egg.tinder.Main.Errores.ErrorServicio;
import edu.egg.tinder.Main.Repositorios.MascotaRepositorio;
import edu.egg.tinder.Main.Repositorios.VotosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class VotoServicio {

    @Autowired
    private MascotaRepositorio mascotaRepositorio;
    @Autowired
    private VotosRepositorio votosRepositorio;
    @Autowired
    private NotificacionServicio notificacionServicio;

    public void votar(String idUsuario, String idMascota1, String idMascota2) throws ErrorServicio {
        Voto voto = new Voto();
        voto.setFechaVoto(new Date());
        if (idMascota1.equals(idMascota2)){
            throw new ErrorServicio("No puede votarse a si mismo");
        }

        Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota1);
        if (respuesta.isPresent()){
            Mascota mascota1 = respuesta.get();
            if (mascota1.getUsuario().getId().equals(idUsuario)){
                voto.setMascota1(mascota1);
            }else{
                throw new ErrorServicio("No tiene permisos para votar");
            }
        }else {
            throw new ErrorServicio("No existe una mascota vinculada a ese id");
        }

        Optional<Mascota> respuesta2 = mascotaRepositorio.findById(idMascota2);
        if (respuesta2.isPresent()){
            Mascota mascota2 = respuesta2.get();
            voto.setMascota2(mascota2);
            notificacionServicio.enviarMail("Tu mascota fue votada!", "Votacion", mascota2.getUsuario().getMail());
        }else{
            throw new ErrorServicio("No existe mascota con ese id");
        }
        votosRepositorio.save(voto);
    }

    public void responderVoto(String idUsuario, String idVoto) throws ErrorServicio {
        Optional<Voto> respuesta = votosRepositorio.findById(idVoto);
        if (respuesta.isPresent()){
            Voto voto = respuesta.get();
            voto.setFechaRespuesta(new Date());
            if (voto.getMascota2().getUsuario().getId().equals(idUsuario)) {
                notificacionServicio.enviarMail("Tu voto fue devuelto", "Votacion", voto.getMascota1().getUsuario().getMail());
                votosRepositorio.save(voto);


            }else{
                throw new ErrorServicio("No tiene permiso para realizar la operacion");
            }
        }else{
            throw new ErrorServicio("No existe el voto solicitado");
        }
     }
    }
