package com.opentable.resizer.transformers.model;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Vivek Wiki
 */
public class FileUpload {

    private List<MultipartFile> multipartFiles;

    public List<MultipartFile> getFiles() {
        return multipartFiles;
    }

    public void setFiles(List<MultipartFile> files) {
        this.multipartFiles = files;
    }
}
