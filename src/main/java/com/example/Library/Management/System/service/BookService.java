package com.example.Library.Management.System.service;



import com.example.Library.Management.System.dto.BookDto;
import com.example.Library.Management.System.dto.paginate.BookResponsePaginatedDto;
import com.example.Library.Management.System.dto.request.ReportDto;
import com.example.Library.Management.System.dto.request.ResearveBookDto;
import com.example.Library.Management.System.dto.request.ReturnBookDto;
import com.example.Library.Management.System.dto.response.IssueBookResponseDto;
import com.example.Library.Management.System.dto.response.ResearveBookResponseDto;
import com.example.Library.Management.System.dto.response.ReturnBookResponseDto;
import com.example.Library.Management.System.dto.response.UserDashboardDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {


    BookDto addBook(BookDto bookDto, MultipartFile file) throws IOException;
    BookResponsePaginatedDto getAllBooks(int page, int size, String searchText);
    BookResponsePaginatedDto getAllBooksForAdmin(int page, int size, String searchText);

    public void reseaveBook(ResearveBookDto researveBookDto);

    public void returnBook(ReturnBookDto returnBookDto);

    public void issueBookHandle(ReportDto reportDto);


    List<ResearveBookResponseDto> getAllReservation();

    com.example.Library.Management.System.dto.paginate.ReservedBookResponsePaginatedDto getAllReservation(int page, int size);

    List<ResearveBookResponseDto> getReservationByMemberId(String memberId);

    List<ReturnBookResponseDto> getAllReturnBook();

    com.example.Library.Management.System.dto.paginate.ReturnBookResponsePaginatedDto getAllReturnBook(int page, int size, String searchText);

    List<IssueBookResponseDto> getAllIssuedBook();

    com.example.Library.Management.System.dto.paginate.IssueBookResponsePaginatedDto getAllIssuedBook(int page, int size, String searchText);

    void changeActiveState(String bookId, Boolean activeState);

    UserDashboardDto getUserDashboard(String memberId);

    com.example.Library.Management.System.dto.paginate.BorrowHistoryResponsePaginatedDto getBorrowHistory(String memberId, int page, int size);

    com.example.Library.Management.System.dto.paginate.ReturnHistoryResponsePaginatedDto getReturnHistory(String memberId, int page, int size);

    List<String> getAllCategories();
}
