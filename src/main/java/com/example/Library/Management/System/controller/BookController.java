package com.example.Library.Management.System.controller;

import com.example.Library.Management.System.dto.BookDto;
import com.example.Library.Management.System.dto.request.ReportDto;
import com.example.Library.Management.System.dto.request.ResearveBookDto;
import com.example.Library.Management.System.dto.request.ReturnBookDto;
import com.example.Library.Management.System.dto.response.IssueBookResponseDto;
import com.example.Library.Management.System.dto.response.ResearveBookResponseDto;
import com.example.Library.Management.System.dto.response.ReturnBookResponseDto;
import com.example.Library.Management.System.service.BookService;
import com.example.Library.Management.System.utill.StandardResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:4200")
public class BookController {

    @Autowired
    private BookService bookService;

    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024; // 2MB

    @PostMapping(value = "/add", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<StandardResponse> addBook(
            @RequestPart("book") String bookDTO,
            @RequestPart("file") MultipartFile file) throws IOException {

        if (file.getSize() > MAX_FILE_SIZE) {
            return ResponseEntity.badRequest()
                    .body(new StandardResponse(400, "File size exceeds 2MB limit", null));
        }

        ObjectMapper objectMapper = new ObjectMapper();
        BookDto bookDto = objectMapper.readValue(bookDTO, BookDto.class);

        BookDto savedBook = bookService.addBook(bookDto, file);

        return ResponseEntity.ok(new StandardResponse(200, "Book added successfully", savedBook));
    }


    @GetMapping("/all")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }


    @PostMapping("/reserve")
    public ResponseEntity<StandardResponse> reserveBook(@RequestBody ResearveBookDto researveBookDto) {
        bookService.reseaveBook(researveBookDto);
        return ResponseEntity.ok(
                new StandardResponse(200, "Book reserved successfully", researveBookDto.getBook_id())
        );
    }

    @PostMapping("/returnbook")
    public ResponseEntity<StandardResponse> returnBook(@RequestBody ReturnBookDto returnBookDto) {
        bookService.returnBook(returnBookDto);
        return ResponseEntity.ok(
                new StandardResponse(200, "Book returned successfully", returnBookDto.getBookid())
        );
    }


    @PostMapping("/borrowbookrecoard")
    public ResponseEntity<StandardResponse> borrowBook(@RequestBody ReportDto reportDto) {
        bookService.issueBookHandle(reportDto);
        return ResponseEntity.ok(
                new StandardResponse(200, "Book borrowed successfully", reportDto.getBook_id())
        );
    }


    @GetMapping("/reservationdetails")
    public ResponseEntity<StandardResponse> getReserveBook(){
        List<ResearveBookResponseDto> reservationdetails=bookService.getAllReservation();
        return ResponseEntity.ok(
                new StandardResponse(200,"Fetched all members successfully",reservationdetails)
        );
    }

    @GetMapping("/returnbookdetails")
    public ResponseEntity<StandardResponse>getReturnBookDetails(){
        List<ReturnBookResponseDto>returnboodetails=bookService.getAllReturnBook();
        return ResponseEntity.ok(
                new StandardResponse(200,"Fetched all returnbook details",returnboodetails)
        );
    }

    @GetMapping("/issueBookdetails")
    public ResponseEntity<StandardResponse>getIssueBookDetails(){
        List<IssueBookResponseDto>issuebookdetails=bookService.getAllIssuedBook();
        return ResponseEntity.ok(
                new StandardResponse(200,"Fetched all issued details",issuebookdetails)
        );
    }



}
