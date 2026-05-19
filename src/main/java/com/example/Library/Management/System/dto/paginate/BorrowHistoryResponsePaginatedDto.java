package com.example.Library.Management.System.dto.paginate;

import com.example.Library.Management.System.dto.response.UserDashboardDto.IssuedBookItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowHistoryResponsePaginatedDto {
    private long dataCount;
    private List<IssuedBookItem> dataList;
}
