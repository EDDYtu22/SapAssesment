package dev.edmond.sapassesment.controller;

import dev.edmond.sapassesment.dto.FileCheckDto;
import dev.edmond.sapassesment.dto.LineSwitchRequest;
import dev.edmond.sapassesment.dto.WordSwitchRequest;
import dev.edmond.sapassesment.service.FileService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;


    @PostMapping("/check-file")
    public ResponseEntity<String> checkFile(@RequestBody FileCheckDto fileCheckDto){

        return ResponseEntity.ok().body(fileService.isValidFile(fileCheckDto));

    }

    @PostMapping("/switch-lines")
    public ResponseEntity<String> switchFileLines(@RequestBody LineSwitchRequest lineSwitchRequest){

        return ResponseEntity.ok().body(fileService.switchLines(lineSwitchRequest));

    }

    @PostMapping("/switch-words")
    public ResponseEntity<String> switchFileWords(@RequestBody WordSwitchRequest wordSwitchRequest){

        return ResponseEntity.ok().body(fileService.switchWords(wordSwitchRequest));

    }

}
