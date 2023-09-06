package dev.edmond.sapassesment.service;

import dev.edmond.sapassesment.dto.FileCheckDto;
import dev.edmond.sapassesment.dto.LineSwitchRequest;
import dev.edmond.sapassesment.dto.WordSwitchRequest;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FileService {


    public String isValidFile(FileCheckDto fileCheckDto) {
        File file = new File(fileCheckDto.getFilePath());
        if (file.exists() && file.getName().endsWith(".txt")) {
            return "Valid File!";
        } else {
            return "Invalid File!";
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

        // Check if the line indices are valid
        if (firstLine < 1 || firstLine > lines.size() || secondLine < 1 || secondLine > lines.size()) {
            throw new IllegalArgumentException("Incorrect line indexes");
        }

        // Switch the lines (assuming 1-based indices)
        String temp = lines.get(firstLine - 1);
        lines.set(firstLine - 1, lines.get(secondLine - 1));
        lines.set(secondLine - 1, temp);

        // Write the modified content back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(lineSwitchRequest.getFilePath()))) {
            for (String updatedLine : lines) {
                writer.write(updatedLine);
                writer.newLine(); // Add a newline after each line
            }
        } catch (IOException e) {
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

        // Check if the line indices are valid
        if (firstLine < 1 || firstLine > lines.size() || secondLine < 1 || secondLine > lines.size()) {
            throw new IllegalArgumentException("Incorrect line indexes");
        }

        List<String> firstLineWords = Arrays.stream(lines.get(firstLine - 1).split("\s+")).toList();
        List<String> secondLineWords = Arrays.stream(lines.get(secondLine - 1).split("\s+")).toList();

        if (firstLineWordIndex < 1 || firstLineWordIndex > firstLineWords.size() ||
                secondLineWordIndex < 1 || secondLineWordIndex > secondLineWords.size()) {
            throw new IllegalArgumentException("Invalid word indexes");
        }

//        lines.set(firstLineWordIndex - 1, String.join(" ", firstLineWords));
//        lines.set(secondLineWordIndex - 1, String.join(" ", secondLineWords));

        String temp = firstLineWords.get(firstLineWordIndex - 1);
        firstLineWords.set(firstLineWordIndex - 1, secondLineWords.get(secondLineWordIndex - 1));
        secondLineWords.set(secondLineWordIndex - 1, temp);

        lines.set(firstLine, String.join(" ", firstLineWords));
        lines.set(secondLine, String.join(" ", secondLineWords));


        // Write the modified content back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(wordSwitchRequest.getFilePath()))) {
            for (String updatedLine : lines) {
                writer.write(updatedLine);
                writer.newLine(); // Add a newline after each line
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return "Words switched successfully";
    }
}
