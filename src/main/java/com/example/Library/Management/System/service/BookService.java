package com.example.Library.Management.System.service;



import com.example.Library.Management.System.dto.BookDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {

    // Remove method body; only signature here
    BookDto addBook(BookDto bookDto, MultipartFile file) throws IOException;

    List<BookDto> getAllBooks();
}
