package edu.egg.tinder.Main.Repositorios;

import edu.egg.tinder.Main.Entidades.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VotosRepositorio extends JpaRepository<Voto, String> {

    @Query("SELECT c FROM Voto c WHERE c.mascota1.id = :id ORDER BY c.fechaVoto DESC")//Buscamos todos los votos que realice como mascota1 ordenados de manera desc por fecha
    public List<Voto> buscarVotosPropios(String id);

    @Query("SELECT c FROM Voto c WHERE c.Mascota2.id = :id ORDER BY c.fechaVoto DESC")//Buscamos todos los votos recibidos ordenados de manera desc por fecha
    public List<Voto> buscarVotosRecibidos(String id);
}
