package com.vivekwiki.transformers.manager;

import com.vivekwiki.transformers.configurer.ApplicationConfiguration;
import com.vivekwiki.transformers.constants.Constant;
import com.vivekwiki.transformers.handler.ImageResizeHandler;
import com.vivekwiki.transformers.model.Image;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import static com.vivekwiki.transformers.constants.Constant.ERROR;
import static com.vivekwiki.transformers.constants.Constant.ERROR_MESSAGE;

/**
 * @author Vivek Wiki
 */
public class ImageResizeManager {

    public static ModelAndView resizeImage(Image image, HttpServletRequest request) {
        ModelAndView modelAndView = validateImage(image);
        if (Objects.nonNull(modelAndView)) {
            return modelAndView;
        }
        return imageResizeHelper(image, request);
    }

    private static ModelAndView validateImage(Image image) {
        String error;
        if (Objects.isNull(image)) {
            error = "Please input correct data";
            return new ModelAndView(ERROR, ERROR_MESSAGE, error);
        }
        if (image.getMultipartFiles().size() == 1 && image.getMultipartFiles().get(0).getSize() == 0) {
            error = "Please attach the image to be resized";
            return new ModelAndView(ERROR, ERROR_MESSAGE, error);
        }
        if (StringUtils.isBlank(image.getOutputFormat()) || image.getScaledHeight() < 1
                || image.getScaledWidth() < 1) {
            error = "Please enter correct input regarding the dimensions of the resized image";
            return new ModelAndView(ERROR, ERROR_MESSAGE, error);
        }
        return null;
    }

    private static ModelAndView imageResizeHelper(Image image, HttpServletRequest request) {
        List<String> fileNames = new ArrayList<>();
        ListUtils.emptyIfNull(image.getMultipartFiles()).stream()
                .filter(file1 -> StringUtils.isNoneBlank(file1.getOriginalFilename()))
                .forEach(file -> {
                    String fileName = file.getOriginalFilename();
                    // To upload Raw files as well
                    // file.transferTo(new File(pathToRawFiles + fileName));
                    boolean success = false;
                    processedDirectory(ApplicationConfiguration.getPathToProcessedFiles());
                    try {
                        success = ImageResizeHandler.resizeImage(file.getInputStream(),
                                new StringBuffer(ApplicationConfiguration.getPathToProcessedFiles())
                                        .append("/").append(fileName).toString(),
                                image.getScaledWidth(), image.getScaledHeight(),
                                image.getOutputFormat(), image.isMaintainAspectRatio());
                    } catch (IOException e) {
                        System.out.println("Error while getting file-input-stream" + Arrays.toString(e.getStackTrace()));
                        success = false;
                    }
                    if (success) {
                        fileNames.add(fileName);
                    }
                });
        return new ModelAndView(Constant.SUCCESS, Constant.FILES, fileNames);
    }

    private static void processedDirectory(String processedDirectoryName) {
        File directoryName = new File(processedDirectoryName);

        if (!directoryName.exists()) {
            System.out.println("creating directory: " + directoryName);
            directoryName.mkdir();
        }
        System.out.println(" Directory Exists: " + directoryName.exists());
    }
}
