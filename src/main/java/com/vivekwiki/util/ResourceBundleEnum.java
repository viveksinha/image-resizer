package com.vivekwiki.util;

public enum ResourceBundleEnum {
    APPLICATION_PROPERTIES("application");

    private String fileName;

    ResourceBundleEnum(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
