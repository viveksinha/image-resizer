package com.opentable.transformers.controller;

import com.opentable.transformers.configurer.ApplicationConfiguration;
import com.opentable.transformers.constants.Constant;
import com.opentable.transformers.manager.ImageResizeManager;
import com.opentable.transformers.model.Image;

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
    protected ModelAndView home() {
        return new ModelAndView(Constant.UPLOAD_IMAGE, Constant.IMAGE, new Image());
    }

    @RequestMapping(value = Constant.UPLOAD, method = RequestMethod.POST)
    protected ModelAndView uploadFile(@ModelAttribute Image image) throws IOException {
        return ImageResizeManager.resizeImage(image);
    }

    @RequestMapping(value = Constant.IMAGE_COLLECTION_URL, method = RequestMethod.GET)
    protected ModelAndView imageCollection() throws IOException {
        List<String> fileNames = new ArrayList<>();
        for (File file : new File(ApplicationConfiguration.getPathToProcessedFiles()).listFiles()) {
            fileNames.add(file.getName());
        }
        return new ModelAndView(Constant.IMAGE_COLLECTION, Constant.FILES, fileNames);
    }


}

