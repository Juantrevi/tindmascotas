package edu.egg.tinder.Main.Servicios;

import edu.egg.tinder.Main.Entidades.Foto;
import edu.egg.tinder.Main.Entidades.Zona;
import edu.egg.tinder.Main.Errores.ErrorServicio;
import edu.egg.tinder.Main.Repositorios.UsuarioRepositorio;
import edu.egg.tinder.Main.Entidades.Usuario;
import edu.egg.tinder.Main.Repositorios.ZonaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
//@ComponentScan("edu.egg.tinder.Main.Repositorios")
//@AllArgsConstructor
public class UsuarioServicio implements UserDetailsService {

//    @Autowired //Lo que le decimos al servidor de aplicaciones es que a esta variable la inicialice el, y no hace falta inicializarla
    private final UsuarioRepositorio usuarioRepositorio;
//    @Autowired
    private final FotoServicio fotoServicio;
//    @Autowired
    private final NotificacionServicio notificacionServicio;
//    @Autowired
    private ZonaRepositorio zonaRepositorio;

    @Autowired
    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio, FotoServicio fotoServicio, NotificacionServicio notificacionServicio, ZonaRepositorio zonaRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.fotoServicio = fotoServicio;
        this.notificacionServicio = notificacionServicio;
        this.zonaRepositorio = zonaRepositorio;
    }





    private void validar(String nombre, String apellido, String mail, String clave1, String clave2, Zona zona) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()){
            throw new ErrorServicio("Nombre no puede estar vacio");
        }
        if (apellido == null || apellido.isEmpty()){
            throw new ErrorServicio("Apellido no puede estar vacio");
        }
        if (mail == null || mail.isEmpty()){
            throw new ErrorServicio("mail no puede estar vacio");
        }
        if (clave1 == null || clave1.isEmpty() || clave1.length()<=6){
            throw new ErrorServicio("clave no puede estar vacio y tiene que tener mas de 8 digitos");
        }
        if (!clave1.equals(clave2)){
            throw new ErrorServicio("Las claves deben ser iguales");
        }

        if (zona == null){
            throw new ErrorServicio("No se encontro la zona solicitada");
        }
    }
    @Transactional
    public void registrar(MultipartFile archivo, String nombre, String apellido, String mail, String clave1, String clave2, String idZona) throws ErrorServicio { //Esta info viene del formulario que lleno para registrarse
        Zona zona = zonaRepositorio.getById(idZona);
        validar(nombre, apellido, mail, clave1, clave2, zona);

        Usuario usuario = new Usuario(); //(nombre, apellido, clave, mail);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setZona(zona);
       String encriptada = new BCryptPasswordEncoder().encode(clave1);
        usuario.setClave(encriptada);
        usuario.setAlta(new Date());

        Foto foto = fotoServicio.guardar(archivo);
        usuario.setFoto(foto);

        usuarioRepositorio.save(usuario);

       // notificacionServicio.enviarMail("Bienvenido al tinder de mascota", "BIENVENIDO!", usuario.getMail());
    }
    @Transactional
    public void modificarUsuario(MultipartFile archivo, String id, String nombre, String apellido, String mail, String clave1, String clave2, String idZona ) throws ErrorServicio {

        Zona zona = zonaRepositorio.getOne(idZona);


        validar(nombre, apellido, mail, clave1, clave2, zona);
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id); //Optional a traves del id busca el usuario en BD y devuelve una clase OPTIONAL

        if (respuesta.isPresent()) { //Si es que encontro un usuario con ese id (Si el resultado esta presente)

            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setMail(mail);
            usuario.setZona(zona);
            String encriptada = new BCryptPasswordEncoder().encode(clave1);
            usuario.setClave(encriptada);

            String idFoto = null;

            if (usuario.getFoto()!= null){
                idFoto = usuario.getFoto().getId();
            }
            Foto foto = fotoServicio.actualizar(idFoto, archivo);
            usuario.setFoto(foto);
            usuarioRepositorio.save(usuario);
        }else{
            throw new ErrorServicio("No se encontro el usuario solicitado");
        }
    }
    @Transactional
    public void deshabilitar(String id) throws ErrorServicio {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) { //Si es que encontro un usuario con ese id (Si el resultado esta presente)

            Usuario usuario = respuesta.get();
            usuario.setBaja(new Date());

            usuarioRepositorio.save(usuario);
        }else{
            throw new ErrorServicio("No se encontro el usuario solicitado");
        }
    }
    @Transactional
    public void habilitar(String id) throws ErrorServicio {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) { //Si es que encontro un usuario con ese id (Si el resultado esta presente)

            Usuario usuario = respuesta.get();
            usuario.setAlta(new Date());

            usuarioRepositorio.save(usuario);
        }else{
            throw new ErrorServicio("No se encontro el usuario solicitado");
        }
    }
    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarUsuarioPorMail(mail);
        if (usuario!=null){

            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p1 = new SimpleGrantedAuthority("MODULO_FOTOS");
            permisos.add(p1);
            GrantedAuthority p2 = new SimpleGrantedAuthority("MODULO_MASCOTAS");
            permisos.add(p2);
            GrantedAuthority p3 = new SimpleGrantedAuthority("MODULO_VOTOS");
            permisos.add(p3);

            User user = new User(usuario.getMail(), usuario.getClave(), permisos);
            return user;
        }else return null;
    }
}
