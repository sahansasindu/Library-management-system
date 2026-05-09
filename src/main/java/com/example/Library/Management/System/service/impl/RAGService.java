package com.example.Library.Management.System.service.impl;

import com.example.Library.Management.System.entity.Book;
import com.example.Library.Management.System.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RAGService {

    private final BookRepository bookRepository;
    private final OpenAIService openAIService;

    public RAGService(BookRepository bookRepository, OpenAIService openAIService) {
        this.bookRepository = bookRepository;
        this.openAIService = openAIService;
    }

    public String askQuestion(String question) {

        String keyword = question
                .replaceAll("(?i)do you have|books?|any|show me|a |an |the ", "")
                .replaceAll("[^a-zA-Z0-9 ]", "")
                .trim();
        if (keyword.isEmpty()) keyword = question.replaceAll("[^a-zA-Z0-9 ]", "").trim();


        List<Book> books = bookRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(keyword, keyword);


        if (books.isEmpty()) {
            String[] words = keyword.split("\\s+");
            for (String word : words) {
                if (word.length() > 2) {
                    List<Book> partial = bookRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(word, word);
                    books.addAll(partial);
                }
            }

            books = books.stream().distinct().collect(Collectors.toList());
        }

        // Build context from results
        StringBuilder context = new StringBuilder();
        if (books.isEmpty()) {
            context.append("No books found matching the query.\n");
        } else {
            for (Book book : books) {
                context.append("Title: ").append(book.getTitle())
                        .append(", Author: ").append(book.getAuthor())
                        .append(", Category: ").append(book.getCategory())
                        .append(", Status: ").append(book.getQty() > 0 ? "Available" : "Out of stock")
                        .append("\n");
            }
        }

        // prompt
        String prompt = "You are a helpful library assistant. Use the following library data to answer the user's question.\n"
                + "If the data is empty, politely say we don't have those books.\n\n"
                + "Library Data:\n"
                + context.toString()
                + "\nUser Question: " + question;


        return openAIService.askLLM(prompt);
    }
}
