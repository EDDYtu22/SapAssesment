package dev.edmond.sapassesment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordSwitchRequest {

    private String filePath;

    private int firstLineIndex;

    private int secondLineIndex;

    private int firstLineWordIndex;

    private int secondLineWordIndex;

}
