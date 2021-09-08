package com.db.edu.team03.handler;

import com.db.edu.team03.server.handler.FileHandler;
import com.db.edu.team03.server.handler.HistoryLogger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class HistoryLoggerTest {
    private HistoryLogger historyLogger;
    private FileHandler fileHandler;

    @BeforeEach
    public void setUp() {
        fileHandler = mock(FileHandler.class);

        historyLogger = new HistoryLogger(fileHandler);
    }

    @Test
    public void shouldReadHistoryWhenCallReaddHistory() {
        historyLogger.readHistory();

        verify(fileHandler, times(1)).readHistory();
    }

    @Test
    public void shouldWriteHistoryWhenCallSaveHistory() {
        String message = "message";

        historyLogger.saveHistory(message);

        verify(fileHandler, times(1)).write(message);
    }

    @Test
    public void shouldReturnCorrectHistory() {
        List<String> histList = new ArrayList<>();
        histList.add("empty history");
        when(fileHandler.readHistory()).thenReturn(histList);

        Assertions.assertEquals(historyLogger.readHistory(), String.format("=========%sempty history%s=========", System.lineSeparator(), System.lineSeparator()));
    }
}
