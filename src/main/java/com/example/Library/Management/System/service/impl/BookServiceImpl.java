package com.example.Library.Management.System.service.impl;

import com.example.Library.Management.System.dto.BookDto;
import com.example.Library.Management.System.dto.paginate.BookResponsePaginatedDto;
import com.example.Library.Management.System.dto.request.ReportDto;
import com.example.Library.Management.System.dto.request.ResearveBookDto;
import com.example.Library.Management.System.dto.request.ReturnBookDto;
import com.example.Library.Management.System.dto.response.IssueBookResponseDto;
import com.example.Library.Management.System.dto.response.ResearveBookResponseDto;
import com.example.Library.Management.System.dto.response.ReturnBookResponseDto;
import com.example.Library.Management.System.entity.*;
import com.example.Library.Management.System.enums.PenaltyCost;
import com.example.Library.Management.System.repository.*;
import com.example.Library.Management.System.service.BookService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    private ReturnBookRepository returnBookRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private TransectionRepository transectionRepository;

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
    public BookResponsePaginatedDto getAllBooks(int page, int size, String searchText) {
        Page<Book> booksPage;
        if (searchText == null || searchText.isEmpty()) {
            booksPage = bookRepository.findByActiveStateTrue(PageRequest.of(page, size));
        } else {
            booksPage = bookRepository.findByActiveStateTrueAndSearch(searchText, PageRequest.of(page, size));
        }

        return BookResponsePaginatedDto.builder()
                .dataCount(booksPage.getTotalElements())
                .dataList(booksPage.getContent().stream().map(this::mapToDto).collect(Collectors.toList()))
                .build();
    }

    @Override
    public BookResponsePaginatedDto getAllBooksForAdmin(int page, int size, String searchText) {
        Page<Book> booksPage;
        if (searchText == null || searchText.isEmpty()) {
            booksPage = bookRepository.findAll(PageRequest.of(page, size));
        } else {
            booksPage = bookRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(searchText,
                    searchText, PageRequest.of(page, size));
        }

        return BookResponsePaginatedDto.builder()
                .dataCount(booksPage.getTotalElements())
                .dataList(booksPage.getContent().stream().map(this::mapToDto).collect(Collectors.toList()))
                .build();
    }

    private BookDto mapToDto(Book book) {
        BookDto dto = new BookDto();
        dto.setBookid(book.getBookId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setCategory(book.getCategory());
        dto.setQty(book.getQty());
        dto.setActiveState(book.getActive_state());

        if (book.getPhoto() != null) {
            String base64Image = Base64.getEncoder().encodeToString(book.getPhoto());
            dto.setPhotoBase64("data:image/jpeg;base64," + base64Image);
        }

        return dto;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void updateExpiredReservations() {
        LocalDate today = LocalDate.now();
        Date currentDate = Date.valueOf(today);

        List<ReseaveBook> expiredReservations = reseaveBookRepository.findExpiredReservations(currentDate);

        for (ReseaveBook reservation : expiredReservations) {
            reservation.setState(false);

            reseaveBookRepository.save(reservation);
            
            Transection transection = new Transection("Reservation expired for book " + reservation.getBook().getBookId() + " by member " + reservation.getMember().getMember_id(), new java.util.Date(), com.example.Library.Management.System.enums.TransectionType.OVERDUE);
            transectionRepository.save(transection);
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
        
        int durationDays = com.example.Library.Management.System.enums.ReceiveBookDuration.duraton.getDuration();
        LocalDate dueLocalDate = currentDate.plusDays(durationDays);
        Date dueDate = Date.valueOf(dueLocalDate);

        try {
            ReseaveBook reseaveBook = new ReseaveBook(member, book, reservedDate);
            reseaveBook.setDueDate(dueDate);
            reseaveBookRepository.save(reseaveBook);
            
            Transection transection = new Transection("Book reserved: " + book.getBookId() + " by member: " + member.getMember_id(), new java.util.Date(), com.example.Library.Management.System.enums.TransectionType.RECEIVE);
            transectionRepository.save(transection);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error reserving book: " + e.getMessage());
        }

    }

    @Override
    public void returnBook(ReturnBookDto returnBookDto) {

        double penaltyCost = 0.0;
        LocalDate dueDate;
        LocalDate receivedDate;

        Report report = reportRepository.findissuedetails(
                returnBookDto.getBookid(), returnBookDto.getMemberid());

        if (report != null) {

            receivedDate = returnBookDto.getRecived_date().toLocalDate();
            dueDate = report.getDueDate().toLocalDate();

            if (receivedDate.isAfter(dueDate)) {
                long daysLate = ChronoUnit.DAYS.between(dueDate, receivedDate);
                penaltyCost = daysLate * PenaltyCost.LATEFEE_PERDAY.getCost();
            } else {
                System.out.println("Book returned on time.");
            }

            Book book = bookRepository.findByBookId(returnBookDto.getBookid())
                    .orElseThrow(() -> new RuntimeException("Book not found with ID: " + returnBookDto.getBookid()));

            Member member = memberRepository.findById(returnBookDto.getMemberid())
                    .orElseThrow(
                            () -> new RuntimeException("Member not found with ID: " + returnBookDto.getMemberid()));

            Date sqlReceivedDate = Date.valueOf(receivedDate);

            ReturnBook returnBook = new ReturnBook(book, member, sqlReceivedDate, penaltyCost);
            returnBookRepository.save(returnBook);
            
            Transection returnTransection = new Transection("Book returned: " + book.getBookId() + " by member: " + member.getMember_id(), new java.util.Date(), com.example.Library.Management.System.enums.TransectionType.RETURN);
            transectionRepository.save(returnTransection);

            if (penaltyCost > 0) {
                Transection overdueTransection = new Transection("Overdue book returned: " + book.getBookId() + " by member: " + member.getMember_id(), new java.util.Date(), com.example.Library.Management.System.enums.TransectionType.OVERDUE);
                transectionRepository.save(overdueTransection);
            }

        } else {
            System.out.println("No reservation found.");
            throw new RuntimeException("No issue record found for book ID: " + returnBookDto.getBookid()
                    + " and member ID: " + returnBookDto.getMemberid());

        }

    }

    @Override
    public void issueBookHandle(ReportDto reportDto) {

        LocalDate currentDate = LocalDate.now();
        LocalDate dueDate = currentDate.plusDays(30);

        Date issueDateSql = Date.valueOf(currentDate);
        Date dueDateSql = Date.valueOf(dueDate);

        Member member = memberRepository.findById(reportDto.getMember_id())
                .orElseThrow(() -> new RuntimeException("Member not found with ID: " + reportDto.getMember_id()));

        Book book = bookRepository.findByBookId(reportDto.getBook_id())
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + reportDto.getBook_id()));

        if (book.getQty() <= 0) {
            throw new RuntimeException("Book is out of stock: " + reportDto.getBook_id());
        }

        ReseaveBook reservation = reseaveBookRepository.findReservation(reportDto.getBook_id(),
                reportDto.getMember_id());

        if (reservation != null) {

            reservation.setState(false);
            reseaveBookRepository.save(reservation);
        }
        book.setQty(book.getQty() - 1);
        bookRepository.save(book);

        Report report = new Report(issueDateSql, dueDateSql, member, book);
        try {
            reportRepository.save(report);
            
            Transection transection = new Transection("Book issued: " + book.getBookId() + " to member: " + member.getMember_id(), new java.util.Date(), com.example.Library.Management.System.enums.TransectionType.BORROW);
            transectionRepository.save(transection);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error issuing book: " + e.getMessage());
        }

    }

    @Override
    public List<ResearveBookResponseDto> getAllReservation() {
        List<ReseaveBook> researveBookDtoList = reseaveBookRepository.findByStateTrue();
        return researveBookDtoList.stream()
                .map(this::researveBookDtoTO)
                .collect(Collectors.toList());
    }

    @Override
    public com.example.Library.Management.System.dto.paginate.ReservedBookResponsePaginatedDto getAllReservation(int page, int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
        org.springframework.data.domain.Page<ReseaveBook> pageResult = reseaveBookRepository.findByStateTrue(pageable);
        List<ResearveBookResponseDto> list = pageResult.getContent().stream()
                .map(this::researveBookDtoTO)
                .collect(Collectors.toList());
        return com.example.Library.Management.System.dto.paginate.ReservedBookResponsePaginatedDto.builder()
                .dataCount(pageResult.getTotalElements())
                .dataList(list)
                .build();
    }

    @Override
    public List<ResearveBookResponseDto> getReservationByMemberId(String memberId) {
        List<ReseaveBook> researveBookDtoList = reseaveBookRepository.findByMemberId(memberId);
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
        List<ReturnBook> returnBookList = returnBookRepository.findAll();
        return returnBookList.stream()
                .map(this::returnBookResponseDtoTo)
                .collect(Collectors.toList());
    }

    @Override
    public com.example.Library.Management.System.dto.paginate.ReturnBookResponsePaginatedDto getAllReturnBook(int page, int size, String searchText) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
        org.springframework.data.domain.Page<ReturnBook> pageResult;
        if (searchText != null && !searchText.trim().isEmpty()) {
            pageResult = returnBookRepository.searchReturnedBooks(searchText.trim(), pageable);
        } else {
            pageResult = returnBookRepository.findAll(pageable);
        }
        List<ReturnBookResponseDto> list = pageResult.getContent().stream()
                .map(this::returnBookResponseDtoTo)
                .collect(Collectors.toList());
        return com.example.Library.Management.System.dto.paginate.ReturnBookResponsePaginatedDto.builder()
                .dataCount(pageResult.getTotalElements())
                .dataList(list)
                .build();
    }

    private ReturnBookResponseDto returnBookResponseDtoTo(ReturnBook returnBook) {
        ReturnBookResponseDto responseDto = new ReturnBookResponseDto();
        responseDto.setBook_id(returnBook.getBook().getBookId());
        responseDto.setMember_id(returnBook.getMember().getMember_id());
        responseDto.setPenalty_amount(returnBook.getPenalty_amount());
        responseDto.setRecived_date(returnBook.getRecived_date());
        return responseDto;
    }

    @Override
    public List<IssueBookResponseDto> getAllIssuedBook() {
        List<Report> reportList = reportRepository.findAll();
        return reportList.stream()
                .map(this::issueBookResponseDtoTO)
                .collect(Collectors.toList());
    }

    @Override
    public com.example.Library.Management.System.dto.paginate.IssueBookResponsePaginatedDto getAllIssuedBook(int page, int size, String searchText) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
        org.springframework.data.domain.Page<Report> pageResult;
        if (searchText != null && !searchText.trim().isEmpty()) {
            pageResult = reportRepository.searchIssuedBooks(searchText.trim(), pageable);
        } else {
            pageResult = reportRepository.findAll(pageable);
        }
        List<IssueBookResponseDto> list = pageResult.getContent().stream()
                .map(this::issueBookResponseDtoTO)
                .collect(Collectors.toList());
        return com.example.Library.Management.System.dto.paginate.IssueBookResponsePaginatedDto.builder()
                .dataCount(pageResult.getTotalElements())
                .dataList(list)
                .build();
    }

    private IssueBookResponseDto issueBookResponseDtoTO(Report report) {
        IssueBookResponseDto issueBookResponseDto = new IssueBookResponseDto();
        issueBookResponseDto.setBook_id(report.getBook().getBookId());
        issueBookResponseDto.setMember_id(report.getMember().getMember_id());
        issueBookResponseDto.setIssueDate(report.getIssueDate());
        issueBookResponseDto.setDueDate(report.getDueDate());
        return issueBookResponseDto;

    }

    @Override
    @Transactional
    public void changeActiveState(String bookId, Boolean activeState) {
        Book book = bookRepository.findByBookId(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + bookId));
        book.setActive_state(activeState);
        bookRepository.save(book);
    }

    @Override
    public com.example.Library.Management.System.dto.response.UserDashboardDto getUserDashboard(String memberId) {

        List<ReseaveBook> reserved = reseaveBookRepository.findByMemberId(memberId);
        List<com.example.Library.Management.System.dto.response.UserDashboardDto.ReservedBookItem> reservedItems =
            reserved.stream().map(r -> new com.example.Library.Management.System.dto.response.UserDashboardDto.ReservedBookItem(
                r.getBook().getBookId(),
                r.getBook().getTitle(),
                r.getBook().getAuthor(),
                r.getReservedDate() != null ? r.getReservedDate().toString() : "",
                r.getDueDate() != null ? r.getDueDate().toString() : "",
                r.getState()
            )).collect(Collectors.toList());


        List<ReturnBook> returnBooks = returnBookRepository.findByMemberId(memberId);
        List<String> returnedBookIds = returnBooks.stream().map(r -> r.getBook().getBookId()).collect(Collectors.toList());


        List<Report> reports = reportRepository.findByMemberId(memberId);
        List<com.example.Library.Management.System.dto.response.UserDashboardDto.IssuedBookItem> issuedItems =
            reports.stream().map(r -> new com.example.Library.Management.System.dto.response.UserDashboardDto.IssuedBookItem(
                r.getBook().getBookId(),
                r.getBook().getTitle(),
                r.getBook().getAuthor(),
                r.getIssueDate() != null ? r.getIssueDate().toString() : "",
                r.getDueDate() != null ? r.getDueDate().toString() : "",
                returnedBookIds.contains(r.getBook().getBookId())
            )).collect(Collectors.toList());

        List<com.example.Library.Management.System.dto.response.UserDashboardDto.ReturnedBookItem> returnedItems =
            returnBooks.stream().map(r -> new com.example.Library.Management.System.dto.response.UserDashboardDto.ReturnedBookItem(
                r.getBook().getBookId(),
                r.getBook().getTitle(),
                r.getBook().getAuthor(),
                r.getRecived_date() != null ? r.getRecived_date().toString() : "",
                r.getPenalty_amount(),
                r.getIsPaid() != null ? r.getIsPaid() : false
            )).collect(Collectors.toList());

        double totalFines = returnBooks.stream()
            .filter(r -> r.getIsPaid() == null || !r.getIsPaid())
            .mapToDouble(ReturnBook::getPenalty_amount).sum();

        return new com.example.Library.Management.System.dto.response.UserDashboardDto(
                reservedItems, issuedItems, returnedItems, totalFines);
    }

    @Override
    public com.example.Library.Management.System.dto.paginate.BorrowHistoryResponsePaginatedDto getBorrowHistory(String memberId, int page, int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
        org.springframework.data.domain.Page<Report> reportsPage = reportRepository.findByMemberId(memberId, pageable);
        List<ReturnBook> returnBooks = returnBookRepository.findByMemberId(memberId);
        List<String> returnedBookIds = returnBooks.stream().map(r -> r.getBook().getBookId()).collect(Collectors.toList());

        List<com.example.Library.Management.System.dto.response.UserDashboardDto.IssuedBookItem> list = reportsPage.getContent().stream().map(r -> new com.example.Library.Management.System.dto.response.UserDashboardDto.IssuedBookItem(
            r.getBook().getBookId(),
            r.getBook().getTitle(),
            r.getBook().getAuthor(),
            r.getIssueDate() != null ? r.getIssueDate().toString() : "",
            r.getDueDate() != null ? r.getDueDate().toString() : "",
            returnedBookIds.contains(r.getBook().getBookId())
        )).collect(Collectors.toList());

        return com.example.Library.Management.System.dto.paginate.BorrowHistoryResponsePaginatedDto.builder()
            .dataCount(reportsPage.getTotalElements())
            .dataList(list)
            .build();
    }

    @Override
    public com.example.Library.Management.System.dto.paginate.ReturnHistoryResponsePaginatedDto getReturnHistory(String memberId, int page, int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
        org.springframework.data.domain.Page<ReturnBook> returnBooksPage = returnBookRepository.findByMemberId(memberId, pageable);

        List<com.example.Library.Management.System.dto.response.UserDashboardDto.ReturnedBookItem> list = returnBooksPage.getContent().stream().map(r -> new com.example.Library.Management.System.dto.response.UserDashboardDto.ReturnedBookItem(
            r.getBook().getBookId(),
            r.getBook().getTitle(),
            r.getBook().getAuthor(),
            r.getRecived_date() != null ? r.getRecived_date().toString() : "",
            r.getPenalty_amount(),
            r.getIsPaid() != null ? r.getIsPaid() : false
        )).collect(Collectors.toList());

        return com.example.Library.Management.System.dto.paginate.ReturnHistoryResponsePaginatedDto.builder()
            .dataCount(returnBooksPage.getTotalElements())
            .dataList(list)
            .build();
    }

    @Override
    public List<String> getAllCategories() {
        List<Object[]> categoryCountsRaw = bookRepository.countBooksByCategory();
        return categoryCountsRaw.stream()
                .map(row -> row[0] != null ? row[0].toString() : "Uncategorized")
                .filter(cat -> !cat.equalsIgnoreCase("Uncategorized"))
                .distinct()
                .collect(Collectors.toList());
    }

}
