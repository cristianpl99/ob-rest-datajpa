package com.example.obrestdatajpa.controller;

import com.example.obrestdatajpa.entities.Book;
import com.example.obrestdatajpa.repository.BookRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    private Logger log = LoggerFactory.getLogger(BookController.class);
    private BookRepository repository;

    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    //CRUD sobre entidad Book

    //Buscar todos
    @GetMapping("/api/books")
    public List<Book> findAll(){
        return repository.findAll();
    }

    // Buscar un solo libro
    @GetMapping("/api/books/{id}")
    @ApiOperation("busca un libro") // notacion de springfox para agregar documentacion a swagger
    public ResponseEntity<Book> findOneById(@ApiParam("Ejemplo de anotacion de Swagger") @PathVariable Long id)
    {
        Optional<Book> bookOptional = repository.findById(id); //evita valores null
        //opcion 1
        /*
        if (bookOptional.isPresent()){
            return bookOptional.get();
        }else {
            return null;
        }
        */
        //opcion 2
       // return bookOptional.orElse(null);
        //opcion 3
        if (bookOptional.isPresent()){
            return ResponseEntity.ok(bookOptional.get());
        }else {
            return ResponseEntity.notFound().build();
        }

    }
    /**
     * Post no colisiona con findAll porque son distintos metodos http GET vs POST
    @param book
    @param headers
    @return
    */

    //Crear nuevo libro
    @PostMapping("/api/books")
    public ResponseEntity<Book> create(@RequestBody Book book, @RequestHeader HttpHeaders headers){
        //print del peticionario. (seguridad)
        System.out.println(headers.get("User-Agent"));
        //guarda el libro recibido por parametro en laa bbdd
        if(book.getId() != null){
            log.warn("error creando libro ya existente");
            return ResponseEntity.badRequest().build();
        }
        Book result = repository.save(book);
        return ResponseEntity.ok(result);//el libro se crea y guarda con una clave primaria (incremental)
    }
        //Actualizar un libro existente
    @PutMapping("/api/books/")
    public ResponseEntity<Book> update(@RequestBody Book book) {
        if (book.getId() == null){
            log.warn("Error queriendo updatear un libro inexistente");
            return ResponseEntity.badRequest().build();
        }
        if (!repository.existsById(book.getId())){
            log.warn("Error queriendo updatear un libro inexistente");
            return ResponseEntity.notFound().build();
        }
        Book result = repository.save(book);
        return ResponseEntity.ok(result);
    }

    //Borrar un libro
    @DeleteMapping("api/books/{id}")
    public ResponseEntity<Book> delete (@PathVariable Long id){
        if (!repository.existsById(id)){
            log.warn("Error queriendo borrar un libro inexistente");
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build(); //Respuesta de que se ha borrado correctamente
    }
    //Borrar todos los libros
    @DeleteMapping("/api/books")
    public ResponseEntity<Book> deleteAll(){
        log.info("Ejecutando borrado completo");
        repository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}

