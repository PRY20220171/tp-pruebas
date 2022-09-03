package com.example.backpruebas;

import com.example.backpruebas.config.CassandraConfig;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@EnableRabbit
@SpringBootApplication
public class BackPruebasApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackPruebasApplication.class, args);
    }

}
