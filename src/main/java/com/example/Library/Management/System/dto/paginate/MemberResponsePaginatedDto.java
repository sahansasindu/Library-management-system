package com.example.Library.Management.System.dto.paginate;

import com.example.Library.Management.System.dto.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponsePaginatedDto {
    private long dataCount;
    private List<MemberDTO> dataList;
}
