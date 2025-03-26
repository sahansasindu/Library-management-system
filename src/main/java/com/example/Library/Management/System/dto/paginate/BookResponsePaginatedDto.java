package com.example.Library.Management.System.dto.paginate;

import com.example.Library.Management.System.dto.BookDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookResponsePaginatedDto {
    private long dataCount;
    private List<BookDto> dataList;
}
