package edu.egg.tinder.Main.Controlador;

import edu.egg.tinder.Main.Entidades.Zona;
import edu.egg.tinder.Main.Errores.ErrorServicio;
import edu.egg.tinder.Main.Repositorios.ZonaRepositorio;
import edu.egg.tinder.Main.Servicios.UsuarioServicio;
import lombok.AllArgsConstructor;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class PortalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ZonaRepositorio zonaRepositorio;



    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/loginn")
    public String login(){
        return "loginn";
    }

    @GetMapping("/register")
    public String registro(ModelMap modelo){
        List<Zona> zonas = zonaRepositorio.findAll();
        modelo.put("zonas", zonas);
        return "register";
    }




    @PostMapping("/registrar")
    public String registro(ModelMap modelo, MultipartFile archivo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail, @RequestParam String clave1, @RequestParam String clave2, @RequestParam String idZona){

        try {
            usuarioServicio.registrar(archivo, nombre, apellido, mail, clave1, clave2, idZona);
        } catch (ErrorServicio e) {
            List<Zona> zonas = zonaRepositorio.findAll();
            modelo.put("zonas", zonas);

            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("mail", mail);          //Esto es para no perder la info en el formulario, y que vuelvan a la vista
            modelo.put("clave1", clave1);
            modelo.put("clave2", clave2);
            return "register";
        }
        modelo.put("titulo", "Bienvenido!");
        modelo.put("descripcion", "El usuario fue registrado de manera satisfactoria");
        return "exito";
    }
}
