package com.example.Library.Management.System.controller;

import com.example.Library.Management.System.dto.BookDto;
import com.example.Library.Management.System.dto.ResearveBookDto;
import com.example.Library.Management.System.dto.ReturnBookDto;
import com.example.Library.Management.System.entity.ReseaveBook;
import com.example.Library.Management.System.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin
public class BookController {

    @Autowired
    private BookService bookService; // Injecting interface


    @PostMapping("/add")
    public ResponseEntity<BookDto> addBook(
            @RequestPart("book") String bookDTO,
            @RequestPart("file") MultipartFile file) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        BookDto bookDto = objectMapper.readValue(bookDTO, BookDto.class);
        return ResponseEntity.ok(bookService.addBook(bookDto, file));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }


    @PostMapping("/reserve")
    public void reserveBook(@RequestBody ResearveBookDto researveBookDto){
        bookService.reseaveBook(researveBookDto);
    }

    @PostMapping("/returnbook")
    public void returnBook(@RequestBody ReturnBookDto returnBookDto){
        bookService.returnBook(returnBookDto);
    }

}
