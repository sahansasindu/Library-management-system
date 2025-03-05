package com.example.Library.Management.System.service.impl;

import com.example.Library.Management.System.dto.BookDto;
import com.example.Library.Management.System.entity.Book;
import com.example.Library.Management.System.repository.BookRepository;
import com.example.Library.Management.System.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookDto addBook(BookDto bookDto, MultipartFile file) throws IOException {
        String filePath = saveImage(file);

        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());
        book.setCategory(bookDto.getCategory());
        book.setQty(bookDto.getQty());
        book.setPhotoUrl(filePath); // Save the image path

        book = bookRepository.save(book);

        // Set the photo URL in the BookDto after saving the book
        bookDto.setPhotoUrl(book.getPhotoUrl());
        return bookDto;
    }

    private String saveImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
        Files.createDirectories(path.getParent());
        Files.write(path, bytes);
        return file.getOriginalFilename(); // Returning the relative file name (e.g., "image.png")
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(book -> {
            BookDto dto = new BookDto();
            dto.setTitle(book.getTitle());
            dto.setAuthor(book.getAuthor());
            dto.setIsbn(book.getIsbn());
            dto.setCategory(book.getCategory());
            dto.setQty(book.getQty());
            dto.setPhotoUrl(book.getPhotoUrl());
            return dto;
        }).collect(Collectors.toList());
    }
}
