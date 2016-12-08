package com.opentable.resizer.transformers.model;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import lombok.Data;

/**
 * @author Vivek Wiki
 */
@Data
public class Image {
    private int scaledWidth;
    private int scaledHeight;
    private String outputFormat;
    private boolean maintainAspectRatio;
    private List<MultipartFile> multipartFiles;


}
