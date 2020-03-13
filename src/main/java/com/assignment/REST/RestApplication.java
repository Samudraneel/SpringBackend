package com.assignment.REST;

import com.assignment.REST.dao.JukeboxDataAccessService;
import com.assignment.REST.dao.SettingsDataAccessService;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
public class RestApplication {

	public static void main(String[] args) throws IOException, ParseException {
		SettingsDataAccessService temp = new SettingsDataAccessService();
		JukeboxDataAccessService temp2 = new JukeboxDataAccessService();
		SpringApplication.run(RestApplication.class, args);
	}

}
