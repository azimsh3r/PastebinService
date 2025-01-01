package com.azimsh3r.apiservice.controller;

import com.azimsh3r.apiservice.dto.PasteRequestDTO;
import com.azimsh3r.apiservice.service.PasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/paste")
public class PasteController {
    private final PasteService pasteService;

    @Autowired
    public PasteController(PasteService pasteService) {
        this.pasteService = pasteService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createPaste(@RequestBody PasteRequestDTO pasteRequestDTO) {
        String hash = pasteService.createPaste(pasteRequestDTO);

        return ResponseEntity.ok(Map.of(
                "hash", hash,
                "timestamp", LocalDateTime.now()
        ));
    }

    @GetMapping("/{hash}")
    public ResponseEntity<String> getPaste(@PathVariable String hash) {
        String text = pasteService.getPaste(hash);
        return ResponseEntity.ok(text);
    }
}
