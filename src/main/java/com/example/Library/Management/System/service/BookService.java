package com.example.Library.Management.System.service;



import com.example.Library.Management.System.dto.BookDto;
import com.example.Library.Management.System.dto.ReportDto;
import com.example.Library.Management.System.dto.ResearveBookDto;
import com.example.Library.Management.System.dto.ReturnBookDto;
import com.example.Library.Management.System.dto.request.ResearveBookResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {

    // Remove method body; only signature here
    BookDto addBook(BookDto bookDto, MultipartFile file) throws IOException;
    List<BookDto> getAllBooks();

    public void reseaveBook(ResearveBookDto researveBookDto);

    public void returnBook(ReturnBookDto returnBookDto);

    public void issueBookHandle(ReportDto reportDto);


    List<ResearveBookResponseDto> getAllReservation();
}
