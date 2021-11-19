package edu.egg.tinder.Main.Servicios;

import edu.egg.tinder.Main.Entidades.Foto;
import edu.egg.tinder.Main.Errores.ErrorServicio;
import edu.egg.tinder.Main.Repositorios.FotoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class FotoServicio {

    @Autowired
    private FotoRepositorio fotoRepositorio;

    @Transactional //Son los metodos que impactan en la BD, si se llega a lanzar una excepcion, se hace un rollback y no se plasma nada en la BD
    public Foto guardar(MultipartFile archivo) throws ErrorServicio {

        if (archivo!=null){
            try {
                Foto foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());
                return fotoRepositorio.save(foto);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
            return null;
        }
    @Transactional
        public Foto actualizar(String idFoto, MultipartFile archivo) throws ErrorServicio{
            if (archivo!=null){
                try {
                    Foto foto = new Foto();
                        if (idFoto!=null){
                            Optional<Foto> respuesta = fotoRepositorio.findById(idFoto);
                                if (respuesta.isPresent()){
                                    foto = respuesta.get();
                                }
                        }
                    foto.setMime(archivo.getContentType());
                    foto.setNombre(archivo.getName());
                    foto.setContenido(archivo.getBytes());
                    return fotoRepositorio.save(foto);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
            return null;
        }
    }

