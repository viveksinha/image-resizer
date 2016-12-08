package com.opentable.resizer.transformers.model;

import lombok.Data;

/**
 * @author Vivek Wiki
 */
@Data
public class Image {
    private int scaledWidth = 640;
    private int scaledHeight = 350;
    private String outputFormat = "jpg";
    private boolean maintainAspectRatio = true;

}
