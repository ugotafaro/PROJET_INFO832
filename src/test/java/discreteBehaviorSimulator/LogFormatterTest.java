package discreteBehaviorSimulator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import static org.junit.jupiter.api.Assertions.*;

class LogFormatterTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    public void testFormatValidLogRecord() {
        LogFormatter formatter = new LogFormatter();

        LogRecord rec = new LogRecord(Level.INFO, "Test message");
        rec.setMillis(1630425600000L); // Timestamp du LogRecord

        String expectedMessage = "2021.08.31 18:00:00.00: INFO\nTest message\n";

        String formattedMessage = formatter.format(rec);

        assertEquals(expectedMessage, formattedMessage);
    }

    @Test
    public void testFormatNullLogRecord() {
        LogFormatter formatter = new LogFormatter();

        String formattedMessage = formatter.format(null);

        String expectedMessage = "";

        assertEquals(expectedMessage, formattedMessage);
    }

    @Test
    public void testFormatNullLogLevel() {
        LogFormatter formatter = new LogFormatter();

        LogRecord rec = new LogRecord(null, "Test message");
        rec.setMillis(1630425600000L); // Timestamp du LogRecord

        String expectedMessage = "2021.08.31 00:00:00.00: null\nTest message\n";

        String formattedMessage = formatter.format(rec);

        assertEquals(expectedMessage, formattedMessage);
    }

    @Test
    public void testFormatNullMessage() {
        LogFormatter formatter = new LogFormatter();

        LogRecord rec = new LogRecord(Level.INFO, null);
        rec.setMillis(1630425600000L); // Timestamp du LogRecord

        String expectedMessage = "2021.08.31 00:00:00.00: INFO\nnull\n";

        String formattedMessage = formatter.format(rec);

        assertEquals(expectedMessage, formattedMessage);
    }

    @Test
    public void testCalcDateValidDate() {
        LogFormatter formatter = new LogFormatter();

        long currentTimeMillis = System.currentTimeMillis();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SS");
        dateFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
        String expectedDate = dateFormat.format(new Date(currentTimeMillis));

        String formattedDate = formatter.calcDate(currentTimeMillis);

        assertEquals(expectedDate, formattedDate);
    }

    @Test
    public void testCalcDateNegative() {
        LogFormatter formatter = new LogFormatter();

        long negativeTime = -1000;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SS");
        dateFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
        String expectedDate = dateFormat.format(new Date(negativeTime));

        String formattedDate = formatter.calcDate(negativeTime);

        assertEquals(expectedDate, formattedDate);
    }

    @Test
    public void testCalcDateZero() {
        LogFormatter formatter = new LogFormatter();

        long zeroTime = 0;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SS");
        dateFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
        String expectedDate = dateFormat.format(new Date(zeroTime));

        String formattedDate = formatter.calcDate(zeroTime);

        assertEquals(expectedDate, formattedDate);
    }

    @Test
    public void testCalcDateFuture() {
        LogFormatter formatter = new LogFormatter();

        long maxTime = Long.MAX_VALUE;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SS");
        dateFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
        String expectedDate = dateFormat.format(new Date(maxTime));

        String formattedDate = formatter.calcDate(maxTime);

        assertEquals(expectedDate, formattedDate);
    }

    @Test
    public void testGetHeadAndTail() {

        LogFormatter formatter = new LogFormatter();

        Handler handler = new CustomHandler();

        String expectedHead = "";
        String expectedTail = "";

        assertEquals(expectedHead, formatter.getHead(handler));

        assertEquals(expectedTail, formatter.getTail(handler));

        handler = null;

        assertEquals(expectedHead, formatter.getHead(handler));

        assertEquals(expectedTail, formatter.getTail(handler));


        handler = null; // Traiter comme un handler null

        assertEquals(expectedHead, formatter.getHead(handler));

        assertEquals(expectedTail, formatter.getTail(handler));
    }

    private static class CustomHandler extends Handler {
        @Override
        public void publish(LogRecord record) {
            // Implémentation vide car nous ne testons pas cette partie
        }

        @Override
        public void flush() {
            // Implémentation vide car nous ne testons pas cette partie
        }

        @Override
        public void close() throws SecurityException {
            // Implémentation vide car nous ne testons pas cette partie
        }
    }
}