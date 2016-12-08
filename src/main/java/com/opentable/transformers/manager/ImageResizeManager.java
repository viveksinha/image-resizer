package com.opentable.transformers.manager;

import com.opentable.transformers.constants.Constant;
import com.opentable.transformers.configurer.ApplicationConfiguration;
import com.opentable.transformers.handler.ImageResizeHandler;
import com.opentable.transformers.model.Image;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

import static com.opentable.transformers.constants.Constant.ERROR;
import static com.opentable.transformers.constants.Constant.ERROR_MESSAGE;

/**
 * @author Vivek Wiki
 */
@Slf4j
public class ImageResizeManager {

    public static ModelAndView resizeImage(Image image) {
        ModelAndView modelAndView = validateImage(image);
        if (Objects.nonNull(modelAndView)) {
            return modelAndView;
        }
        return imageResizeHelper(image);
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

    private static ModelAndView imageResizeHelper(Image image) {
        List<String> fileNames = new ArrayList<>();
        ListUtils.emptyIfNull(image.getMultipartFiles()).stream()
                .filter(file1 -> StringUtils.isNoneBlank(file1.getOriginalFilename()))
                .forEach(file -> {
                    String fileName = file.getOriginalFilename();
                    // To upload Raw files as well
                    // file.transferTo(new File(pathToRawFiles + fileName));
                    boolean success = false;
                    try {
                        success = ImageResizeHandler.resizeImage(file.getInputStream(),
                                ApplicationConfiguration.getPathToProcessedFiles() + fileName,
                                image.getScaledWidth(), image.getScaledHeight(),
                                image.getOutputFormat(), image.isMaintainAspectRatio());
                    } catch (IOException e) {
                        log.error("Error while getting file-input-stream", e);
                        success = false;
                    }
                    if (success) {
                        fileNames.add(fileName);
                    }
                });
        return new ModelAndView(Constant.SUCCESS, Constant.FILES, fileNames);
    }
}
