package br.com.itau.fraud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"br.com.itau"})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
