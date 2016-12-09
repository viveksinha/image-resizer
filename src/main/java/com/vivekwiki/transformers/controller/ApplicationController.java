package com.vivekwiki.transformers.controller;

import com.vivekwiki.transformers.configurer.ApplicationConfiguration;
import com.vivekwiki.transformers.constants.Constant;
import com.vivekwiki.transformers.manager.ImageResizeManager;
import com.vivekwiki.transformers.model.Image;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Vivek Wiki
 */
@RestController
@RequestMapping(value = Constant.TRANSFORMER)
public class ApplicationController {

    @RequestMapping(method = RequestMethod.GET)
    protected ModelAndView home() {
        return new ModelAndView(Constant.UPLOAD_IMAGE, Constant.IMAGE, new Image());
    }

    @RequestMapping(value = Constant.UPLOAD, method = RequestMethod.POST)
    protected ModelAndView uploadFile(@ModelAttribute Image image, HttpServletRequest request) throws IOException {
        return ImageResizeManager.resizeImage(image, request);
    }

    @RequestMapping(value = Constant.IMAGE_COLLECTION_URL, method = RequestMethod.GET)
    protected ModelAndView imageCollection() throws IOException {
        List<String> fileNames = new ArrayList<>();
        for (File file : new File(ApplicationConfiguration.getPathToProcessedFiles()).listFiles()) {
            fileNames.add(file.getName());
        }
        return new ModelAndView(Constant.IMAGE_COLLECTION, Constant.FILES, fileNames);
    }

    @RequestMapping(value = Constant.IMAGE + "/{fileName:.+}", method = RequestMethod.GET)
    @ResponseBody
    protected void image(@PathVariable String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        File file = new File(ApplicationConfiguration.getPathToProcessedFiles(), fileName);
        response.setHeader("Content-Type", request.getSession().getServletContext().getMimeType(fileName));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        Files.copy(file.toPath(), response.getOutputStream());
    }
}

