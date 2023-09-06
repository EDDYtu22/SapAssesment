package dev.edmond.sapassesment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LineSwitchRequest {

    private String filePath;

    private int firstLineIndex;

    private int secondLineIndex;
}
