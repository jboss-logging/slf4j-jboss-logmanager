package org.slf4j.impl;

import org.jboss.logmanager.LogContext;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:jperkins@redhat.com">James R. Perkins</a>
 */
public class LoggerTestCase {
    private static final java.util.logging.Logger ROOT = LogContext.getLogContext().getLogger("");
    private static final QueueHandler HANDLER = new QueueHandler();
    private static final Collection<Handler> CURRENT_HANDLERS = new ArrayList<>();

    @BeforeClass
    public static void configureLogManager() {
        // By default JBoss Logging should choose JUL as a log manager since no log manager has been defined
        final Handler[] currentHandlers = ROOT.getHandlers();
        if (currentHandlers != null) {
            for (Handler handler : currentHandlers) {
                CURRENT_HANDLERS.add(handler);
                ROOT.removeHandler(handler);
            }
        }
        ROOT.addHandler(HANDLER);
    }

    @AfterClass
    public static void cleanup() {
        ROOT.removeHandler(HANDLER);
        for (Handler handler : CURRENT_HANDLERS) {
            ROOT.addHandler(handler);
        }
    }

    @After
    public void clearHandler() {
        HANDLER.reset();
    }

    @Test
    public void testLogger() {
        final Logger logger = LoggerFactory.getLogger(LoggerTestCase.class);
        assertTrue(expectedTypeMessage(Slf4jLogger.class, logger.getClass()),
                logger instanceof Slf4jLogger);

        // Ensure the logger logs something
        final String testMsg = "This is a test message";
        logger.info(testMsg);
        LogRecord record = HANDLER.messages.poll();
        assertNotNull(record);
        assertEquals("Expected message not found.", testMsg, record.getMessage());
        assertNull("Expected parameters to be null, when no args.", record.getParameters());

        // Test a formatted message
        logger.info("This is a test formatted {}", "message");
        record = HANDLER.messages.poll();
        assertNotNull(record);
        assertEquals("Expected formatted message not found.", "This is a test formatted message", record.getMessage());
        assertArrayEquals("Expected parameter not found.", new Object[]{"message"}, record.getParameters());
    }

    @Test
    public void testLoggerWithExceptions() {
        final Logger logger = LoggerFactory.getLogger(LoggerTestCase.class);

        final RuntimeException e = new RuntimeException("Test exception");
        final String testMsg = "This is a test message";
        logger.info(testMsg, e);
        LogRecord record = HANDLER.messages.poll();
        assertNotNull(record);
        assertEquals("Expected message not found.", testMsg, record.getMessage());
        assertEquals("Cause is different from the expected cause", e, record.getThrown());

        // Test format with the last parameter being the throwable which should set be set on the record
        logger.info("This is a test formatted {}", "message", e);
        record = HANDLER.messages.poll();
        assertNotNull(record);
        assertEquals("Expected formatted message not found.", "This is a test formatted message", record.getMessage());
        assertEquals("Cause is different from the expected cause", e, record.getThrown());

    }

    @Test
    public void testMDC() {
        assertSame(expectedTypeMessage(Slf4jMDCAdapter.class, MDC.getMDCAdapter().getClass()), MDC.getMDCAdapter().getClass(), Slf4jMDCAdapter.class);
        final String key = Long.toHexString(System.currentTimeMillis());
        MDC.put(key, "value");
        assertEquals("MDC value should be \"value\"", "value", MDC.get(key));
        assertEquals("MDC value should be \"value\"", "value", org.jboss.logmanager.MDC.get(key));
    }

    private static String expectedTypeMessage(final Class<?> expected, final Class<?> found) {
        return String.format("Expected type %s but found type %s", expected.getName(), found.getName());
    }

    private static class QueueHandler extends Handler {
        final BlockingDeque<LogRecord> messages = new LinkedBlockingDeque<>();

        @Override
        public void publish(final LogRecord record) {
            messages.add(record);
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() throws SecurityException {
            messages.clear();
        }

        void reset() {
            messages.clear();
            setLevel(Level.ALL);
        }
    }
}