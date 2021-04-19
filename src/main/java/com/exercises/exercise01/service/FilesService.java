package com.exercises.exercise01.service;


import com.exercises.exercise01.model.FileDetailModel;
import com.exercises.exercise01.model.GetFilesResponse;
import com.exercises.exercise01.util.SystemEnv;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FilesService {

    private static final int MAX_FILE_SIZE = 5;
    public static final String DATE_PATTERN = "MM/dd/yyyy";

    public byte[] validateFile(MultipartFile file) {

        byte[] fileData;
        try {
            fileData = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Error reading file", e);
        }

        String originalFilename = file.getOriginalFilename();


        long fileSize = file.getSize();
        if (fileSize > (MAX_FILE_SIZE * FileUtils.ONE_MB)) {
            throw new RuntimeException("File size is over " + MAX_FILE_SIZE + "MB. File size: " + fileSize);
        }

        return fileData;
    }

    public void uploadFile(byte[] fileData, String filename) {

        try {
            File file = new File(FilenameUtils.concat(SystemEnv.getTempOutputDir(), filename));
            FileUtils.writeByteArrayToFile(file, fileData);
        } catch (Exception e) {
            throw new RuntimeException("Error upload file", e);
        }

    }

    public GetFilesResponse getFiles(Integer pageSize, Integer pageNumber, String sortBy, String sortType, String filter) {

        File dir = new File(SystemEnv.getTempOutputDir());

        File[] files;
        if (StringUtils.trimToNull(filter) != null) {
            FilenameFilter textFileFilter = (file, name) -> StringUtils.contains(name, filter);
            files = dir.listFiles(textFileFilter);
        } else {
            files = dir.listFiles();
        }

        if (files == null || files.length == 0) {
            return new GetFilesResponse(Collections.emptyList(), 0, 0, 0);
        }

        int counter = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        List<FileDetailModel> fileModels = new ArrayList<>(files.length);
        for (File file : files) {
            BasicFileAttributes attrs;
            try {
                attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                FileTime creationTime = attrs.creationTime();
                String creationTimeFormatted = simpleDateFormat.format(new Date(creationTime.toMillis()));

                fileModels.add(FileDetailModel.of(counter, file.getName(), creationTimeFormatted));
                counter++;
            } catch (IOException e) {
                throw new RuntimeException("Error get files", e);
            }
        }

        if ("date".equals(StringUtils.trimToEmpty(sortBy)) &&
                ("asc".equals(StringUtils.trimToEmpty(sortType)) || "desc".equals(StringUtils.trimToEmpty(sortType)))) {
            sortType = StringUtils.trimToEmpty(sortType);
            String finalSortType = sortType;
            fileModels.sort((o1, o2) -> {
                try {
                    Date o1Date = FastDateFormat.getInstance(DATE_PATTERN).parse(o1.getUploadDate());
                    Date o2Date = FastDateFormat.getInstance(DATE_PATTERN).parse(o2.getUploadDate());
                    return "asc".equals(finalSortType) ? o1Date.compareTo(o2Date) : o2Date.compareTo(o1Date);
                } catch (ParseException e) {
                    return 0;
                }
            });
        }

        if (pageSize != null && pageNumber != null) {
            if (fileModels.size() < pageSize) {
                return new GetFilesResponse(fileModels, 1, fileModels.size(), pageSize);
            }
            List<List<FileDetailModel>> subLists = Lists.partition(fileModels, pageSize);
            int minPageNumber = Math.min(subLists.size(), pageNumber);
            return new GetFilesResponse(subLists.get(minPageNumber - 1), minPageNumber, fileModels.size(), pageSize);
        }

        return new GetFilesResponse(fileModels, pageNumber, fileModels.size(), pageSize);

    }
}
