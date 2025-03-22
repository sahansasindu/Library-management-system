package com.example.Library.Management.System.service.impl;

import com.example.Library.Management.System.dto.BookDto;
import com.example.Library.Management.System.entity.Book;
import com.example.Library.Management.System.repository.BookRepository;
import com.example.Library.Management.System.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookDto addBook(BookDto bookDto, MultipartFile file) throws IOException {
        String filePath = saveImage(file);

        // Manually mapping DTO to Entity
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());
        book.setCategory(bookDto.getCategory());
        book.setQty(bookDto.getQty());
        book.setPhotoUrl(filePath);

        book = bookRepository.save(book);

        // Manually mapping Entity to DTO
        return mapToDto(book);
    }

    private String saveImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path directoryPath = Paths.get(uploadDir);
        Files.createDirectories(directoryPath);
        Path filePath = directoryPath.resolve(fileName);
        Files.write(filePath, file.getBytes());
        return filePath.toString();
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private BookDto mapToDto(Book book) {
        BookDto dto = new BookDto();
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setCategory(book.getCategory());
        dto.setQty(book.getQty());
        dto.setPhotoUrl(book.getPhotoUrl());
        return dto;
    }
}
