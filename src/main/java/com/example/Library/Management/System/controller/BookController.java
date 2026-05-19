package com.example.Library.Management.System.controller;

import com.example.Library.Management.System.dto.BookDto;
import com.example.Library.Management.System.dto.request.ReportDto;
import com.example.Library.Management.System.dto.request.ResearveBookDto;
import com.example.Library.Management.System.dto.request.ReturnBookDto;
import com.example.Library.Management.System.dto.response.ResearveBookResponseDto;
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

    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024;

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
    public ResponseEntity<StandardResponse> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String searchText) {
        return ResponseEntity.ok(
                new StandardResponse(200, "Fetched all books successfully",
                        bookService.getAllBooks(page, size, searchText)));
    }

    @GetMapping("/all-for-admin")
    public ResponseEntity<StandardResponse> getAllBooksForAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String searchText) {
        return ResponseEntity.ok(
                new StandardResponse(200, "Fetched all books for admin successfully",
                        bookService.getAllBooksForAdmin(page, size, searchText)));
    }

    @PostMapping("/reserve")
    public ResponseEntity<StandardResponse> reserveBook(@RequestBody ResearveBookDto researveBookDto) {
        bookService.reseaveBook(researveBookDto);
        return ResponseEntity.ok(
                new StandardResponse(200, "Book reserved successfully", researveBookDto.getBook_id()));
    }

    @PostMapping("/returnbook")
    public ResponseEntity<StandardResponse> returnBook(@RequestBody ReturnBookDto returnBookDto) {
        bookService.returnBook(returnBookDto);
        return ResponseEntity.ok(
                new StandardResponse(200, "Book returned successfully", returnBookDto.getBookid()));
    }

    @PostMapping("/borrowbookrecoard")
    public ResponseEntity<StandardResponse> borrowBook(@RequestBody ReportDto reportDto) {
        bookService.issueBookHandle(reportDto);
        return ResponseEntity.ok(
                new StandardResponse(200, "Book borrowed successfully", reportDto.getBook_id()));
    }

    @GetMapping("/reservationdetails")
    public ResponseEntity<StandardResponse> getReserveBook(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(
                new StandardResponse(200, "Fetched all reserved books successfully",
                        bookService.getAllReservation(page, size)));
    }

    @GetMapping("/reservationdetails/{memberId}")
    public ResponseEntity<StandardResponse> getReserveBookByMemberId(@PathVariable String memberId) {
        List<ResearveBookResponseDto> reservationdetails = bookService.getReservationByMemberId(memberId);
        return ResponseEntity.ok(
                new StandardResponse(200, "Fetched reservation details successfully", reservationdetails));
    }

    @GetMapping("/returnbookdetails")
    public ResponseEntity<StandardResponse> getReturnBookDetails(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String searchText) {
        return ResponseEntity.ok(
                new StandardResponse(200, "Fetched all returnbook details successfully",
                        bookService.getAllReturnBook(page, size, searchText)));
    }

    @GetMapping("/issueBookdetails")
    public ResponseEntity<StandardResponse> getIssueBookDetails(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String searchText) {
        return ResponseEntity.ok(
                new StandardResponse(200, "Fetched all issued details successfully",
                        bookService.getAllIssuedBook(page, size, searchText)));
    }

    @PutMapping("/changeActiveState/{bookId}")
    public ResponseEntity<StandardResponse> changeActiveState(
            @PathVariable String bookId,
            @RequestParam Boolean activeState) {
        bookService.changeActiveState(bookId, activeState);
        return ResponseEntity.ok(
                new StandardResponse(200, "Active state updated successfully", bookId));
    }

    @GetMapping("/userdashboard/{memberId}")
    public ResponseEntity<StandardResponse> getUserDashboard(@PathVariable String memberId) {
        return ResponseEntity.ok(
                new StandardResponse(200, "User dashboard data fetched successfully",
                        bookService.getUserDashboard(memberId)));
    }

    @GetMapping("/borrowhistory/{memberId}")
    public ResponseEntity<StandardResponse> getBorrowHistory(
            @PathVariable String memberId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(
                new StandardResponse(200, "Fetched borrow history successfully",
                        bookService.getBorrowHistory(memberId, page, size)));
    }

    @GetMapping("/returnhistory/{memberId}")
    public ResponseEntity<StandardResponse> getReturnHistory(
            @PathVariable String memberId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(
                new StandardResponse(200, "Fetched return history successfully",
                        bookService.getReturnHistory(memberId, page, size)));
    }

    @GetMapping("/categories")
    public ResponseEntity<StandardResponse> getAllCategories() {
        return ResponseEntity.ok(
                new StandardResponse(200, "Fetched all categories successfully",
                        bookService.getAllCategories()));
    }

}
