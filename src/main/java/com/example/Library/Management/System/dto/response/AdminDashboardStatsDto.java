package com.example.Library.Management.System.dto.response;

import java.util.List;

public class AdminDashboardStatsDto {

    private long totalBooks;
    private long totalMembers;
    private long issuedBooks;
    private long overdueBooks;
    private long totalReturnedBooks;
    private long totalReservations;
    private List<RecentTransactionDto> recentTransactions;
    private List<CategoryCountDto> categoryCounts;

    public AdminDashboardStatsDto() {}

    public AdminDashboardStatsDto(long totalBooks, long totalMembers, long issuedBooks,
                                   long overdueBooks, long totalReturnedBooks,
                                   long totalReservations, List<RecentTransactionDto> recentTransactions,
                                   List<CategoryCountDto> categoryCounts) {
        this.totalBooks = totalBooks;
        this.totalMembers = totalMembers;
        this.issuedBooks = issuedBooks;
        this.overdueBooks = overdueBooks;
        this.totalReturnedBooks = totalReturnedBooks;
        this.totalReservations = totalReservations;
        this.recentTransactions = recentTransactions;
        this.categoryCounts = categoryCounts;
    }

    public long getTotalBooks() { return totalBooks; }
    public void setTotalBooks(long totalBooks) { this.totalBooks = totalBooks; }

    public long getTotalMembers() { return totalMembers; }
    public void setTotalMembers(long totalMembers) { this.totalMembers = totalMembers; }

    public long getIssuedBooks() { return issuedBooks; }
    public void setIssuedBooks(long issuedBooks) { this.issuedBooks = issuedBooks; }

    public long getOverdueBooks() { return overdueBooks; }
    public void setOverdueBooks(long overdueBooks) { this.overdueBooks = overdueBooks; }

    public long getTotalReturnedBooks() { return totalReturnedBooks; }
    public void setTotalReturnedBooks(long totalReturnedBooks) { this.totalReturnedBooks = totalReturnedBooks; }

    public long getTotalReservations() { return totalReservations; }
    public void setTotalReservations(long totalReservations) { this.totalReservations = totalReservations; }

    public List<RecentTransactionDto> getRecentTransactions() { return recentTransactions; }
    public void setRecentTransactions(List<RecentTransactionDto> recentTransactions) { this.recentTransactions = recentTransactions; }

    public List<CategoryCountDto> getCategoryCounts() { return categoryCounts; }
    public void setCategoryCounts(List<CategoryCountDto> categoryCounts) { this.categoryCounts = categoryCounts; }

    // Inner DTO for recent transactions
    public static class RecentTransactionDto {
        private long id;
        private String name;
        private String type;
        private String date;

        public RecentTransactionDto() {}

        public RecentTransactionDto(long id, String name, String type, String date) {
            this.id = id;
            this.name = name;
            this.type = type;
            this.date = date;
        }

        public long getId() { return id; }
        public void setId(long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public String getDate() { return date; }
        public void setDate(String date) { this.date = date; }
    }

    // Inner DTO for category counts
    public static class CategoryCountDto {
        private String name;
        private long count;

        public CategoryCountDto() {}

        public CategoryCountDto(String name, long count) {
            this.name = name;
            this.count = count;
        }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public long getCount() { return count; }
        public void setCount(long count) { this.count = count; }
    }
}
