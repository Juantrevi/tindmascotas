package edu.egg.tinder.Main.Repositorios;

import edu.egg.tinder.Main.Entidades.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MascotaRepositorio extends JpaRepository<Mascota, String> {

    @Query("Select c FROM Mascota c WHERE c.usuario.id = :id")
        public List<Mascota> buscarMascotaPorUsuario(@Param("id")String id);

}
