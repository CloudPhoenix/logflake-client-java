package io.logflake.utils;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LocalDateTimeAdapterTest {

    private LocalDateTimeAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new LocalDateTimeAdapter();
    }

    @Test
    void writeValidLocalDateTime() throws IOException {
        LocalDateTime dateTime = LocalDateTime.of(2023, 10, 5, 14, 30);
        StringWriter stringWriter = new StringWriter();
        JsonWriter jsonWriter = new JsonWriter(stringWriter);

        adapter.write(jsonWriter, dateTime);
        jsonWriter.close();

        assertEquals("\"2023-10-05T14:30:00\"", stringWriter.toString());
    }
    @Test
    void writeNullLocalDateTime() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JsonWriter jsonWriter = new JsonWriter(stringWriter);

        adapter.write(jsonWriter, null);
        jsonWriter.close();

        assertEquals("null", stringWriter.toString());
    }
    @Test
    void readValidLocalDateTime() throws IOException {
        String json = "\"2023-10-05T14:30:00\"";
        JsonReader jsonReader = new JsonReader(new StringReader(json));

        LocalDateTime dateTime = adapter.read(jsonReader);

        assertEquals(LocalDateTime.of(2023, 10, 5, 14, 30), dateTime);
    }
    @Test
    void readNullLocalDateTime() {
        String json = "null";
        JsonReader jsonReader = new JsonReader(new StringReader(json));


        assertThrows(IllegalStateException.class, () -> adapter.read(jsonReader));
    }
}