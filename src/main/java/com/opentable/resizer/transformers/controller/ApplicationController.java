package com.opentable.resizer.transformers.controller;

import com.opentable.resizer.constants.Constant;
import com.opentable.resizer.transformers.configurer.ApplicationConfiguration;
import com.opentable.resizer.transformers.handler.ImageResizeHandler;
import com.opentable.resizer.transformers.model.FileUpload;
import com.opentable.resizer.transformers.model.Image;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Vivek Wiki
 */
@Slf4j
@RestController
@RequestMapping(value = Constant.TRANSFORMER)
public class ApplicationController {

    @RequestMapping(method = RequestMethod.GET)
    protected ModelAndView uploadImage() {
        return new ModelAndView(Constant.UPLOAD_IMAGE, "command", new Image());
    }

    @RequestMapping(value = Constant.IMAGE, method = RequestMethod.POST)
    protected void resizeImage(@ModelAttribute Image image) throws IOException {
        ImageResizeHandler.resizeImage(ApplicationConfiguration.getPathToRawFiles(),
                ApplicationConfiguration.getPathToProcessedFiles(), image.getScaledWidth(),
                image.getScaledHeight(), image.getOutputFormat(), image.isMaintainAspectRatio());
    }

    @RequestMapping(value = Constant.UPLOAD, method = RequestMethod.POST)
    protected ModelAndView uploadFile(@ModelAttribute("uploadForm") FileUpload fileUpload) {
        List<String> fileNames = new ArrayList<>();
        String directory = ApplicationConfiguration.getPathToRawFiles();

        ListUtils.emptyIfNull(fileUpload.getFiles()).stream()
                .filter(file1 -> StringUtils.isNoneBlank(file1.getOriginalFilename()))
                .forEach(file -> {
                    try {
                        String fileName = file.getOriginalFilename();
                        file.transferTo(new File(directory + fileName));
                        fileNames.add(fileName);
                    } catch (Exception e) {
                        log.error("Error while uploading files", e);
                    }
                });

        return new ModelAndView("upload_success", "files", fileNames);
    }

}
