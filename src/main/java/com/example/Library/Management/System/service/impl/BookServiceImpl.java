package com.example.Library.Management.System.service.impl;

import com.example.Library.Management.System.dto.BookDto;
import com.example.Library.Management.System.dto.ReportDto;
import com.example.Library.Management.System.dto.ResearveBookDto;
import com.example.Library.Management.System.dto.ReturnBookDto;
import com.example.Library.Management.System.entity.*;
import com.example.Library.Management.System.repository.*;
import com.example.Library.Management.System.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ResearveBookRepository researveBookRepository;


    @Autowired
    private ConditionRepository conditionRepository;


    @Autowired
    private ReturnBookRepository returnBookRepository;

    @Autowired
    private ReportRepository reportRepository;





    @Override
    public BookDto addBook(BookDto bookDto, MultipartFile file) throws IOException {
        String filePath = saveImage(file);

        // Manually mapping DTO to Entity
        Book book = new Book();
        //book.setBookId(bookDto.getBookid());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());
        book.setCategory(bookDto.getCategory());
        book.setQty(bookDto.getQty());
        book.setPhotoUrl(filePath);

        book = bookRepository.save(book);


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

    @Override
    public void reseaveBook(ResearveBookDto researveBookDto) {

        Member member = memberRepository.findById(researveBookDto.getMember_id())
                .orElseThrow(() -> new RuntimeException("Member not found with ID: " + researveBookDto.getMember_id()));


        Book book = bookRepository.findByBookId(researveBookDto.getBook_id())
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + researveBookDto.getBook_id()));

        LocalDate currentDate = LocalDate.now();
        Date reservedDate = Date.valueOf(currentDate);

        ReseaveBook reseaveBook = new ReseaveBook(member, book,reservedDate);
        researveBookRepository.save(reseaveBook);


    }

    @Override
    public void returnBook(ReturnBookDto returnBookDto) {

        double penaltyCost = 0.0;
        LocalDate dueDate=null;
        LocalDate receivedDate = null;


        // Fetch the issue details
        Report report=reportRepository.findissuedetails(
                returnBookDto.getBookid(), returnBookDto.getMemberid()
        );


        if (report != null) {

            receivedDate = returnBookDto.getRecived_date().toLocalDate();
            dueDate=report.getDueDate().toLocalDate();

            System.out.println("Due Date: " + dueDate);
            System.out.println("Received Date: " + receivedDate);

            if (receivedDate.isAfter(dueDate)) {
                penaltyCost = getPenaltyCost();
            } else {
                System.out.println("Book returned on time.");
            }

            Book book = bookRepository.findByBookId(returnBookDto.getBookid())
                    .orElseThrow(() -> new RuntimeException("Book not found with ID: " + returnBookDto.getBookid()));


            Member member = memberRepository.findById(returnBookDto.getMemberid())
                    .orElseThrow(() -> new RuntimeException("Member not found with ID: " + returnBookDto.getMemberid()));


            Date sqlReceivedDate = Date.valueOf(receivedDate);


            ReturnBook returnBook = new ReturnBook(book, member, sqlReceivedDate, penaltyCost);
            returnBookRepository.save(returnBook);

        } else {
            System.out.println("No reservation found.");
        }



    }




    // Fetch penalty cost
    private double getPenaltyCost() {
        return conditionRepository.findById(1).orElse(new Condition()).getPenalty_cost();
    }

    @Override
    public void issueBookHandle(ReportDto reportDto) {

        // Get the current date
        LocalDate currentDate = LocalDate.now();
        LocalDate dueDate = currentDate.plusDays(30);

        // Convert LocalDate to SQL Date
        Date issueDateSql = Date.valueOf(currentDate);
        Date dueDateSql = Date.valueOf(dueDate);

        // Fetch Member and Book from database
        Member member = memberRepository.findById(reportDto.getMember_id())
                .orElseThrow(() -> new RuntimeException("Member not found with ID: " + reportDto.getMember_id()));

        Book book = bookRepository.findByBookId(reportDto.getBook_id())
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + reportDto.getBook_id()));

        // Create and save the report
        Report report = new Report(issueDateSql, dueDateSql, member, book);
        reportRepository.save(report);


    }


    private BookDto mapToDto(Book book) {
        BookDto dto = new BookDto();
        //dto.setBookid(book.getBookId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setCategory(book.getCategory());
        dto.setQty(book.getQty());
        dto.setPhotoUrl(book.getPhotoUrl());
        return dto;
    }
}
