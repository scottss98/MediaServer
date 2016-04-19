package com.triples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.triples.*;

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
public class MediaServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(MediaServerApplication.class, args);
	}
}
