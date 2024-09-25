package com.MangoEduardo.DND.homebrew.API;

import org.springframework.boot.SpringApplication;

public class TestDndHomebrewApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(DndHomebrewApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
