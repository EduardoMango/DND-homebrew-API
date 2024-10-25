package com.MangoEduardo.DND.homebrew.API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class 	DndHomebrewApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DndHomebrewApiApplication.class, args);
	}

	//MOVER LOGICA DE MAPEO DE CONTROLLER A SERVICE. QUE LOS CONTROLLER SOLO UTILICEN DTO NO ENTITY
}
