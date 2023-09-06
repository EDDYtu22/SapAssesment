package dev.edmond.sapassesment.service;

import dev.edmond.sapassesment.dto.FileCheckDto;
import dev.edmond.sapassesment.dto.LineSwitchRequest;
import dev.edmond.sapassesment.dto.WordSwitchRequest;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {


    public String isValidFile(FileCheckDto fileCheckDto) {
        File file = new File(fileCheckDto.getFilePath());
        if (file.exists() && file.getName().endsWith(".txt")) {
            return "Valid File!";
        } else {
            throw new RuntimeException("File doesn't exist or is in invalid format!");
        }
    }

    public String switchLines(LineSwitchRequest lineSwitchRequest) {

        int firstLine = lineSwitchRequest.getFirstLineIndex();
        int secondLine = lineSwitchRequest.getSecondLineIndex();

        List<String> lines = new ArrayList<>();

        try {

            BufferedReader reader = new BufferedReader(new FileReader(lineSwitchRequest.getFilePath()));

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage());
        }

        if (firstLine < 1 || firstLine > lines.size() || secondLine < 1 || secondLine > lines.size()) {
            throw new IllegalArgumentException("Incorrect line indexes");
        }

        String temp = lines.get(firstLine - 1);
        lines.set(firstLine - 1, lines.get(secondLine - 1));
        lines.set(secondLine - 1, temp);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(lineSwitchRequest.getFilePath()))) {
            for (String updatedLine : lines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return "Lines switched successfully";
    }

    public String switchWords(WordSwitchRequest wordSwitchRequest) {

        int firstLine = wordSwitchRequest.getFirstLineIndex();
        int secondLine = wordSwitchRequest.getSecondLineIndex();
        int firstLineWordIndex = wordSwitchRequest.getFirstLineWordIndex();
        int secondLineWordIndex = wordSwitchRequest.getSecondLineWordIndex();

        List<String> lines = new ArrayList<>();

        try {

            BufferedReader reader = new BufferedReader(new FileReader(wordSwitchRequest.getFilePath()));

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage());
        }

        if (firstLine < 1 || firstLine > lines.size() || secondLine < 1 || secondLine > lines.size()) {
            throw new IllegalArgumentException("Incorrect line indexes");
        }

        String[] firstLineWords = lines.get(firstLine - 1).split("\s+");
        String[] secondLineWords = lines.get(secondLine - 1).split("\s+");

        if (firstLineWordIndex < 1 || firstLineWordIndex > firstLineWords.length ||
                secondLineWordIndex < 1 || secondLineWordIndex > secondLineWords.length) {
            throw new IllegalArgumentException("Invalid word indexes");
        }


        String temp = Array.get(firstLineWords, firstLineWordIndex - 1).toString();
        Array.set(firstLineWords, firstLineWordIndex - 1, Array.get(secondLineWords, secondLineWordIndex - 1));
        Array.set(secondLineWords, secondLineWordIndex - 1, temp);

        lines.set(firstLine - 1 , String.join(" ", firstLineWords));
        lines.set(secondLine - 1, String.join(" ", secondLineWords));


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(wordSwitchRequest.getFilePath()))) {
            for (String updatedLine : lines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return "Words switched successfully";
    }
}
