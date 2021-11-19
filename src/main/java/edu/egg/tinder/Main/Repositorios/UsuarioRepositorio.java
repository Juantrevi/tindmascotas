package edu.egg.tinder.Main.Repositorios;

import edu.egg.tinder.Main.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    @Query("SELECT c FROM  Usuario c WHERE c.mail = :mail")
    public Usuario buscarUsuarioPorMail(@Param("mail") String mail);


}
