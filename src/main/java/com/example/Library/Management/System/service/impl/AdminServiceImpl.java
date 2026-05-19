package com.example.Library.Management.System.service.impl;

import com.example.Library.Management.System.dto.MemberDTO;
import com.example.Library.Management.System.dto.paginate.MemberResponsePaginatedDto;
import com.example.Library.Management.System.dto.response.AdminDashboardStatsDto;
import com.example.Library.Management.System.dto.response.UserResponseDto;
import com.example.Library.Management.System.entity.Member;
import com.example.Library.Management.System.entity.Transection;
import com.example.Library.Management.System.entity.User;
import com.example.Library.Management.System.entity.ReturnBook;
import com.example.Library.Management.System.repository.*;
import com.example.Library.Management.System.service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReturnBookRepository returnBookRepository;

    @Autowired
    private ResearveBookRepository reseaveBookRepository;

    @Autowired
    private TransectionRepository transectionRepository;



    @Override
    public void addNewMember(MemberDTO memberDTO) {
        Member member=new Member(memberDTO.getMember_id(),
                memberDTO.getFirst_name(),
                memberDTO.getLast_name(),
                memberDTO.getAddress(),
                memberDTO.getDob(),
                memberDTO.getNic_no(),
                memberDTO.getPhone_no());
        memberRepository.save(member);
    }

    @Override
    public MemberResponsePaginatedDto getAllMembers(int page, int size, String searchText) {
        Page<Member> memberPage;
        if (searchText == null || searchText.isEmpty()) {
            memberPage = memberRepository.findAll(PageRequest.of(page, size));
        } else {
            memberPage = memberRepository.searchMembers(searchText, PageRequest.of(page, size));
        }

        return MemberResponsePaginatedDto.builder()
                .dataCount(memberPage.getTotalElements())
                .dataList(memberPage.getContent().stream().map(this::mapToDto).collect(Collectors.toList()))
                .build();
    }
    
    private MemberDTO mapToDto(Member member){
        MemberDTO memberDto=new MemberDTO();
        memberDto.setMember_id(member.getMember_id());
        memberDto.setFirst_name(member.getFirst_name());
        memberDto.setLast_name(member.getLast_name());
        memberDto.setAddress(member.getAddress());
        memberDto.setDob(member.getDob());
        memberDto.setNic_no(member.getNic_no());
        memberDto.setPhone_no(member.getPhone_no());
        return memberDto;
    }

    @Override
    public List<UserResponseDto> geyAccountDetails() {
        List<User>user=userRepository.findAll();
        return user.stream()
                .map(this::Userto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateAccountState(String memberId, boolean newState) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with ID: " + memberId));

        Optional<User> userOptional = userRepository.findByMemberid(member);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setActive_state(newState);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public AdminDashboardStatsDto getDashboardStats() {
        long totalBooks = bookRepository.count();
        long totalMembers = memberRepository.count();
        long issuedBooks = reportRepository.count();
        long totalReturnedBooks = returnBookRepository.count();
        long totalReservations = reseaveBookRepository.countByStateTrue();


        Date today = Date.valueOf(LocalDate.now());
        long overdueBooks = reseaveBookRepository.findExpiredReservations(today).size();


        List<Transection> recentTx = transectionRepository.findTop10ByOrderByDateDesc();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        List<AdminDashboardStatsDto.RecentTransactionDto> recentTransactions = recentTx.stream()
                .map(t -> new AdminDashboardStatsDto.RecentTransactionDto(
                        t.getRansection_id(),
                        t.getName(),
                        t.getType() != null ? t.getType().name() : "UNKNOWN",
                        t.getDate() != null ? sdf.format(t.getDate()) : ""
                ))
                .collect(Collectors.toList());

        List<Object[]> categoryCountsRaw = bookRepository.countBooksByCategory();
        List<AdminDashboardStatsDto.CategoryCountDto> categoryCounts = categoryCountsRaw.stream()
                .map(row -> new AdminDashboardStatsDto.CategoryCountDto(
                        row[0] != null ? row[0].toString() : "Uncategorized",
                        ((Number) row[1]).longValue()
                ))
                .collect(Collectors.toList());

        return new AdminDashboardStatsDto(
                totalBooks,
                totalMembers,
                issuedBooks,
                overdueBooks,
                totalReturnedBooks,
                totalReservations,
                recentTransactions,
                categoryCounts
        );
    }


    private UserResponseDto Userto(User user) {
        if (user == null) throw new RuntimeException("null");
        return UserResponseDto.builder()
                .memberid(user.getMemberid().getMember_id())
                .email(user.getEmail())
                .role(user.getRole())
                .active_state(user.getActive_state())
                .build();
    }

    @Override
    public List<com.example.Library.Management.System.dto.response.FineDto> getAllFines() {
        List<ReturnBook> returnsWithFines = returnBookRepository.findAllWithFines();
        return returnsWithFines.stream().map(r -> new com.example.Library.Management.System.dto.response.FineDto(
            r.getReturnbook_id(),
            r.getMember().getMember_id(),
            r.getMember().getFirst_name() + " " + r.getMember().getLast_name(),
            r.getBook().getBookId(),
            r.getBook().getTitle(),
            r.getPenalty_amount(),
            r.getIsPaid() != null ? r.getIsPaid() : false
        )).collect(Collectors.toList());
    }

    @Override
    public void markFineAsPaid(long returnBookId) {
        ReturnBook rb = returnBookRepository.findById(returnBookId)
            .orElseThrow(() -> new RuntimeException("Return Book record not found!"));
        rb.setIsPaid(true);
        returnBookRepository.save(rb);
    }
}
