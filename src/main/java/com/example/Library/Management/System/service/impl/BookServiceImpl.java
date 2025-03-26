package com.example.Library.Management.System.service.impl;

import com.example.Library.Management.System.dto.BookDto;
import com.example.Library.Management.System.dto.request.ReportDto;
import com.example.Library.Management.System.dto.request.ResearveBookDto;
import com.example.Library.Management.System.dto.request.ReturnBookDto;
import com.example.Library.Management.System.dto.response.IssueBookResponseDto;
import com.example.Library.Management.System.dto.response.ResearveBookResponseDto;
import com.example.Library.Management.System.dto.response.ReturnBookResponseDto;
import com.example.Library.Management.System.entity.*;
import com.example.Library.Management.System.repository.*;
import com.example.Library.Management.System.service.BookService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {


    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ResearveBookRepository reseaveBookRepository;


    @Autowired
    private ConditionRepository conditionRepository;


    @Autowired
    private ReturnBookRepository returnBookRepository;

    @Autowired
    private ReportRepository reportRepository;


    @Override
    @Transactional
    public BookDto addBook(BookDto bookDto, MultipartFile file) {
        try {
            Book book = new Book();
            book.setBookId(bookDto.getBookid());
            book.setTitle(bookDto.getTitle());
            book.setAuthor(bookDto.getAuthor());
            book.setIsbn(bookDto.getIsbn());
            book.setCategory(bookDto.getCategory());
            book.setQty(bookDto.getQty());

            if (file != null && !file.isEmpty()) {
                book.setPhoto(file.getBytes());
            }

            book = bookRepository.save(book);
            return mapToDto(book);
        } catch (IOException e) {
            throw new RuntimeException("Error processing file: " + e.getMessage());
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private BookDto mapToDto(Book book) {
        BookDto dto = new BookDto();
        dto.setBookid(book.getBookId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setCategory(book.getCategory());
        dto.setQty(book.getQty());

        if (book.getPhoto() != null) {
            String base64Image = Base64.getEncoder().encodeToString(book.getPhoto());
            dto.setPhotoBase64("data:image/jpeg;base64," + base64Image);
        }

        return dto;
    }



    @Scheduled(cron = "0 0 0 * * ?") // Runs at midnight daily
    @Transactional
    public void updateExpiredReservations() {
        LocalDate today = LocalDate.now();
        Date currentDate = Date.valueOf(today);

        List<ReseaveBook> expiredReservations = reseaveBookRepository.findExpiredReservations(currentDate);

        for (ReseaveBook reservation : expiredReservations) {
            reservation.setState(false);
            reseaveBookRepository.save(reservation);
        }
    }


    @Override
    public void reseaveBook(ResearveBookDto researveBookDto) {

        Member member = memberRepository.findById(researveBookDto.getMember_id())
                .orElseThrow(() -> new RuntimeException("Member not found with ID: " + researveBookDto.getMember_id()));


        Book book = bookRepository.findByBookId(researveBookDto.getBook_id())
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + researveBookDto.getBook_id()));

        LocalDate currentDate = LocalDate.now();
        Date reservedDate = Date.valueOf(currentDate);

        try {
            ReseaveBook reseaveBook = new ReseaveBook(member, book, reservedDate);
            reseaveBookRepository.save(reseaveBook);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error reserving book: " + e.getMessage());
        }

    }

    @Override
    public void returnBook(ReturnBookDto returnBookDto) {

        double penaltyCost = 0.0;
        LocalDate dueDate;
        LocalDate receivedDate;


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
            throw new RuntimeException("No issue record found for book ID: " + returnBookDto.getBookid() + " and member ID: " + returnBookDto.getMemberid());

        }


    }


    // Fetch penalty cost
    private double getPenaltyCost() {
        return conditionRepository.findById(1).orElse(new Condition()).getPenalty_cost();
    }

    @Override
    public void issueBookHandle(ReportDto reportDto) {


        LocalDate currentDate = LocalDate.now();
        LocalDate dueDate = currentDate.plusDays(30);

        // Convert LocalDate to SQL Date
        Date issueDateSql = Date.valueOf(currentDate);
        Date dueDateSql = Date.valueOf(dueDate);


        Member member = memberRepository.findById(reportDto.getMember_id())
                .orElseThrow(() -> new RuntimeException("Member not found with ID: " + reportDto.getMember_id()));

        Book book = bookRepository.findByBookId(reportDto.getBook_id())
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + reportDto.getBook_id()));


        if (book.getQty() <= 0) {
            throw new RuntimeException("Book is out of stock: " + reportDto.getBook_id());
        }
        // Find existing reservation
        ReseaveBook reservation = reseaveBookRepository.findReservation(reportDto.getBook_id(), reportDto.getMember_id());

        if (reservation != null) {
            // Update reservation state
            reservation.setState(false);
            reseaveBookRepository.save(reservation);
        }

        // Reduce book count
        book.setQty(book.getQty() - 1);
        bookRepository.save(book); // Save updated quantity


        Report report = new Report(issueDateSql, dueDateSql, member, book);
        try {
            reportRepository.save(report);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error issuing book: " + e.getMessage());
        }

    }
    @Override
    public List<ResearveBookResponseDto> getAllReservation() {
        List<ReseaveBook> researveBookDtoList = reseaveBookRepository.findAll();
        return researveBookDtoList.stream()
                .map(this::researveBookDtoTO)
                .collect(Collectors.toList());
    }


    private ResearveBookResponseDto researveBookDtoTO(ReseaveBook reseaveBook) {
        ResearveBookResponseDto resivebookDto = new ResearveBookResponseDto();
        resivebookDto.setMember_id(reseaveBook.getMember().getMember_id());
        resivebookDto.setBook_id(reseaveBook.getBook().getBookId());
        resivebookDto.setReservedDate(reseaveBook.getReservedDate());
        resivebookDto.setState(reseaveBook.getState());

        return resivebookDto;
    }

    @Override
    public List<ReturnBookResponseDto> getAllReturnBook() {
        List<ReturnBook>returnBookList=returnBookRepository.findAll();
        return returnBookList.stream()
                .map(this::returnBookResponseDtoTo)
                .collect(Collectors.toList());
    }

    private ReturnBookResponseDto returnBookResponseDtoTo(ReturnBook returnBook){
        ReturnBookResponseDto responseDto=new ReturnBookResponseDto();
        responseDto.setBook_id(returnBook.getBook().getBookId());
        responseDto.setMember_id(returnBook.getMember().getMember_id());
        responseDto.setPenalty_amount(responseDto.getPenalty_amount());
        responseDto.setRecived_date(responseDto.getRecived_date());
        return responseDto;
    }

    @Override
    public List<IssueBookResponseDto> getAllIssuedBook() {
        List<Report>reportList=reportRepository.findAll();
        return reportList.stream()
                .map(this::issueBookResponseDtoTO)
                .collect(Collectors.toList());
    }

    private IssueBookResponseDto issueBookResponseDtoTO(Report report){
        IssueBookResponseDto issueBookResponseDto=new IssueBookResponseDto();
        issueBookResponseDto.setBook_id(report.getBook().getBookId());
        issueBookResponseDto.setMember_id(report.getMember().getMember_id());
        issueBookResponseDto.setIssueDate(report.getIssueDate());
        issueBookResponseDto.setDueDate(report.getDueDate());
        return issueBookResponseDto;

    }




}
