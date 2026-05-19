package com.example.Library.Management.System.dto.paginate;

import com.example.Library.Management.System.dto.response.ReturnBookResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnBookResponsePaginatedDto {
    private long dataCount;
    private List<ReturnBookResponseDto> dataList;
}
