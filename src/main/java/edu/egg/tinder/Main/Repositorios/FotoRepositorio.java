package edu.egg.tinder.Main.Repositorios;

import edu.egg.tinder.Main.Entidades.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FotoRepositorio extends JpaRepository<Foto, String> {

    @Query("Select c FROM Foto c WHERE c.id = :id")
    public List<Foto> buscarMascotaFoto(@Param("id")String id);

}
