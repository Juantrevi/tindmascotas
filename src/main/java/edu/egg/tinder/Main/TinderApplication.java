package edu.egg.tinder.Main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan("edu.egg.tinder.Main.Controlador")
public class TinderApplication{


	public static void main(String[] args) {

		SpringApplication.run(TinderApplication.class, args);
	}



}
