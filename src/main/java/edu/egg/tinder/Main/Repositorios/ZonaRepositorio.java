package edu.egg.tinder.Main.Repositorios;

import edu.egg.tinder.Main.Entidades.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZonaRepositorio extends JpaRepository<Zona, String> {

}
