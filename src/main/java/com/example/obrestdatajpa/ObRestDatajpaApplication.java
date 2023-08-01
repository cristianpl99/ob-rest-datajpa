package com.example.obrestdatajpa;

import com.example.obrestdatajpa.entities.Book;
import com.example.obrestdatajpa.repository.BookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class ObRestDatajpaApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ObRestDatajpaApplication.class, args);
		BookRepository repository = context.getBean(BookRepository.class);

		//CRUD
		//Creacion
		System.out.println("Libros en DDBB: " + repository.findAll().size());

		Book book1 = new Book(null, "Crime and Punishment", "Fyodor Dostoievski", 800, 4000.0, LocalDate.of(2023, 12, 12), true);
		Book book2 = new Book(null, "War and Peace", "Leon Tosltoi", 600, 4000.0, LocalDate.of(2013, 12, 12), true);
		//Guardado
		repository.save(book1);
		repository.save(book2);
		//Recuperar
		System.out.println("Libros en DDBB: " + repository.findAll().size());
		//Borrar
		//repository.deleteById(1L);

		System.out.println("Libros en DDBB: " + repository.findAll().size());


	}

}
