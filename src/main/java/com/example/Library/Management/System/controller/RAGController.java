package com.example.Library.Management.System.controller;

import com.example.Library.Management.System.service.impl.RAGService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rag")
@CrossOrigin
public class RAGController {

    private final RAGService ragService;

    public RAGController(RAGService ragService) {
        this.ragService = ragService;
    }

    @GetMapping
    public String ask(@RequestParam String question) {
        return ragService.askQuestion(question);
    }
}
