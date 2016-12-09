package com.vivekwiki.transformers.configurer;

import com.vivekwiki.transformers.constants.Constant;
import com.vivekwiki.util.ResourceBundleEnum;

import java.util.ResourceBundle;

import lombok.Getter;

/**
 * @author Vivek Wiki
 */
public class ApplicationConfiguration {
    @Getter
    private static ApplicationConfiguration configuration;

    private static String pathToRawFiles;
    private static String pathToProcessedFiles;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle(ResourceBundleEnum.APPLICATION_PROPERTIES.getFileName());
        configuration = new ApplicationConfiguration(bundle);
    }

    public ApplicationConfiguration(ResourceBundle bundle) {
        pathToRawFiles = bundle.getString(Constant.PATH_TO_RAW_FILES);
        pathToProcessedFiles = bundle.getString(Constant.PATH_TO_PROCESSED_FILES);
    }

    public static String getPathToProcessedFiles() {
        return configuration.pathToProcessedFiles;
    }

}
