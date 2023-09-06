package dev.edmond.sapassesment.integration;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Tag("integration")

public class FileEndpointsTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void shouldReturnThatFileIsValidFile() throws Exception {
        mockMvc.perform(
                        post("/api/file/check-file")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "filePath": "/Users/edko/Main/SapAssesment/src/test/resources/testFile.txt"
                                        }
                                        """)

                )
                .andExpect(status().isOk())
                .andExpect(content().string("Valid File!"));

    }

    @Test
    public void shouldReturnInvalidFileError() throws Exception {
        mockMvc.perform(
                        post("/api/file/check-file")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "filePath": "incorrectPath"
                                        }
                                        """)

                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string("File doesn't exist or is in invalid format!"));

    }

    @Test
    public void shouldReturnLineSwitchSuccessful() throws Exception {
        mockMvc.perform(
                        post("/api/file/switch-lines")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "filePath": "/Users/edko/Main/SapAssesment/src/test/resources/testFile.txt",
                                            "firstLineIndex": 1,
                                            "secondLineIndex": 2
                                        }
                                        """)

                )
                .andExpect(status().isOk())
                .andExpect(content().string("Lines switched successfully"));

    }

    @Test
    public void shouldReturnIncorrectLineIndexException() throws Exception {
        mockMvc.perform(
                        post("/api/file/switch-lines")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "filePath": "/Users/edko/Main/SapAssesment/src/test/resources/testFile.txt",
                                            "firstLineIndex": 1,
                                            "secondLineIndex": 3
                                        }
                                        """)

                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Incorrect line indexes"));

    }

    @Test
    public void shouldSwitchWordsSuccessfully() throws Exception {
        mockMvc.perform(
                        post("/api/file/switch-words")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "filePath": "/Users/edko/Main/SapAssesment/src/test/resources/testFile.txt",
                                            "firstLineIndex": 1,
                                            "firstLineWordIndex": 1,
                                            "secondLineIndex": 2,
                                            "secondLineWordIndex": 1
                                        }
                                        """)

                )
                .andExpect(status().isOk())
                .andExpect(content().string("Words switched successfully"));

    }

    @Test
    public void shouldReturnIncorrectWordIndexesException() throws Exception {
        mockMvc.perform(
                        post("/api/file/switch-words")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "filePath": "/Users/edko/Main/SapAssesment/src/test/resources/testFile.txt",
                                            "firstLineIndex": 1,
                                            "firstLineWordIndex": 1,
                                            "secondLineIndex": 2,
                                            "secondLineWordIndex": 7
                                        }
                                        """)

                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid word indexes"));

    }
}
