package com.example;

import com.github.ltsopensource.spring.boot.annotation.EnableJobClient;
import com.github.ltsopensource.spring.boot.annotation.EnableTaskTracker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import spring.boot.lts.annotation.EnableLTSJobAutoConfig;

@SpringBootApplication
@EnableJobClient
@EnableTaskTracker
@EnableLTSJobAutoConfig
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
