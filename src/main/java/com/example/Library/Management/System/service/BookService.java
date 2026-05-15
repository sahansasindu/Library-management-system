package com.example.Library.Management.System.service;



import com.example.Library.Management.System.dto.BookDto;
import com.example.Library.Management.System.dto.paginate.BookResponsePaginatedDto;
import com.example.Library.Management.System.dto.request.ReportDto;
import com.example.Library.Management.System.dto.request.ResearveBookDto;
import com.example.Library.Management.System.dto.request.ReturnBookDto;
import com.example.Library.Management.System.dto.response.IssueBookResponseDto;
import com.example.Library.Management.System.dto.response.ResearveBookResponseDto;
import com.example.Library.Management.System.dto.response.ReturnBookResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {

    // Remove method body; only signature here
    BookDto addBook(BookDto bookDto, MultipartFile file) throws IOException;
    BookResponsePaginatedDto getAllBooks(int page, int size, String searchText);

    public void reseaveBook(ResearveBookDto researveBookDto);

    public void returnBook(ReturnBookDto returnBookDto);

    public void issueBookHandle(ReportDto reportDto);


    List<ResearveBookResponseDto> getAllReservation();

    List<ResearveBookResponseDto> getReservationByMemberId(String memberId);

    List<ReturnBookResponseDto> getAllReturnBook();

    List<IssueBookResponseDto> getAllIssuedBook();
}
