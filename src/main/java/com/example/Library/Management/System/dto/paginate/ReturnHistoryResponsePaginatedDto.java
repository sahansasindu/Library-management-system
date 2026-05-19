package com.example.Library.Management.System.dto.paginate;

import com.example.Library.Management.System.dto.response.UserDashboardDto.ReturnedBookItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnHistoryResponsePaginatedDto {
    private long dataCount;
    private List<ReturnedBookItem> dataList;
}
