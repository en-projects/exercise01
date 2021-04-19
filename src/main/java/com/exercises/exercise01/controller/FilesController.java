package com.exercises.exercise01.controller;

import com.exercises.exercise01.model.GetFilesResponse;
import com.exercises.exercise01.model.WsResponse;
import com.exercises.exercise01.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
public class FilesController {

//    public static final String VER_1 = "VER=1";

    @Autowired
    private FilesService filesService;

    @PutMapping(value = "/uploadFile")
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) {
        byte[] fileData = filesService.validateFile(file);
        String filename = file.getOriginalFilename();
        filesService.uploadFile(fileData, filename);

        return ResponseEntity.ok(WsResponse.ofSuccess());
    }

    @GetMapping(value = "/getFiles")
    public ResponseEntity<?> getFiles(@RequestParam(required = false) Integer pageSize,
                                      @RequestParam(required = false) Integer pageNumber,
                                      @RequestParam(required = false) String sortBy,
                                      @RequestParam(required = false) String sortType,
                                      @RequestParam(required = false) String filter) {
        GetFilesResponse response = filesService.getFiles(pageSize, pageNumber, sortBy, sortType, filter);
        return ResponseEntity.ok(response);
    }


}
