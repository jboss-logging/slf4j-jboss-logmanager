/*
 * JBoss, Home of Professional Open Source.
 *
 * Copyright 2014 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.slf4j.impl;

import org.slf4j.Marker;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.spi.LocationAwareLogger;
import org.jboss.logmanager.Logger;
import org.jboss.logmanager.ExtLogRecord;
import org.jboss.logmanager.Level;

import java.io.Serializable;
import java.io.ObjectStreamException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public final class Slf4jLogger implements Serializable, LocationAwareLogger {
    private final Logger logger;
    private static final String LOGGER_CLASS_NAME = Slf4jLogger.class.getName();
    private static final long serialVersionUID = -8422185592693034532L;

    private static final int ALT_ERROR_INT = org.jboss.logmanager.Level.ERROR.intValue();
    private static final int ALT_WARN_INT = org.jboss.logmanager.Level.WARN.intValue();
    private static final int ALT_INFO_INT = org.jboss.logmanager.Level.INFO.intValue();
    private static final int ALT_DEBUG_INT = org.jboss.logmanager.Level.DEBUG.intValue();
    private static final int ALT_TRACE_INT = org.jboss.logmanager.Level.TRACE.intValue();

    public Slf4jLogger(final Logger logger) {
        this.logger = logger;
    }

    public String getName() {
        return logger.getName();
    }

    @Override
    public void log(final Marker marker, final String fqcn, final int levelVal, final String fmt, final Object[] argArray, final Throwable t) {
        final java.util.logging.Level level;
        switch (levelVal) {
            case LocationAwareLogger.TRACE_INT: level = org.jboss.logmanager.Level.TRACE; break;
            case LocationAwareLogger.DEBUG_INT: level = org.jboss.logmanager.Level.DEBUG; break;
            case LocationAwareLogger.INFO_INT: level = org.jboss.logmanager.Level.INFO; break;
            case LocationAwareLogger.WARN_INT: level = org.jboss.logmanager.Level.WARN; break;
            case LocationAwareLogger.ERROR_INT: level = org.jboss.logmanager.Level.ERROR; break;
            default: level = org.jboss.logmanager.Level.DEBUG; break;
        }
        if (logger.isLoggable(level)) {
            final String message = MessageFormatter.arrayFormat(fmt, argArray).getMessage();
            log(marker, level, fqcn, message, t, argArray);
        }
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isLoggable(Level.TRACE);
    }

    @Override
    public void trace(final String msg) {
        if (ALT_TRACE_INT < logger.getEffectiveLevel()) {
            return;
        }
        log(null, org.jboss.logmanager.Level.TRACE, msg, null);
    }

    @Override
    public void trace(final String format, final Object arg) {
        if (ALT_TRACE_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg);
        log(null, org.jboss.logmanager.Level.TRACE, formattingTuple.getMessage(), formattingTuple.getThrowable(), arg);
    }

    @Override
    public void trace(final String format, final Object arg1, final Object arg2) {
        if (ALT_TRACE_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg1, arg2);
        log(null, org.jboss.logmanager.Level.TRACE, formattingTuple.getMessage(), formattingTuple.getThrowable(), arg1, arg2);
    }

    @Override
    public void trace(final String format, final Object... arguments) {
        if (ALT_TRACE_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, arguments);
        log(null, org.jboss.logmanager.Level.TRACE, formattingTuple.getMessage(), formattingTuple.getThrowable(), arguments);
    }

    @Override
    public void trace(final String msg, final Throwable t) {
        if (ALT_TRACE_INT < logger.getEffectiveLevel()) {
            return;
        }
        log(null, org.jboss.logmanager.Level.TRACE, msg, t);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return isTraceEnabled();
    }

    @Override
    public void trace(Marker marker, String msg) {
        if (ALT_TRACE_INT < logger.getEffectiveLevel()) {
            return;
        }
        log(marker, org.jboss.logmanager.Level.TRACE, msg, null);
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {
        if (ALT_TRACE_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg);
        log(marker, org.jboss.logmanager.Level.TRACE, formattingTuple.getMessage(), formattingTuple.getThrowable(), arg);
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        if (ALT_TRACE_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg1, arg2);
        log(marker, org.jboss.logmanager.Level.TRACE, formattingTuple.getMessage(), formattingTuple.getThrowable(), arg1, arg2);
    }

    @Override
    public void trace(Marker marker, String format, Object... arguments) {
        if (ALT_TRACE_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, arguments);
        log(marker, org.jboss.logmanager.Level.TRACE, formattingTuple.getMessage(), formattingTuple.getThrowable(), arguments);

    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {
        if (ALT_TRACE_INT < logger.getEffectiveLevel()) {
            return;
        }
        log(marker, org.jboss.logmanager.Level.TRACE, msg, t);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isLoggable(Level.DEBUG);
    }

    @Override
    public void debug(final String msg) {
        if (ALT_DEBUG_INT < logger.getEffectiveLevel()) {
            return;
        }
        log(null, org.jboss.logmanager.Level.DEBUG, msg, null);
    }

    @Override
    public void debug(final String format, final Object arg) {
        if (ALT_DEBUG_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg);
        log(null, org.jboss.logmanager.Level.DEBUG, formattingTuple.getMessage(), formattingTuple.getThrowable(), arg);
    }

    @Override
    public void debug(final String format, final Object arg1, final Object arg2) {
        if (ALT_DEBUG_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg1, arg2);
        log(null, org.jboss.logmanager.Level.DEBUG, formattingTuple.getMessage(), formattingTuple.getThrowable(), arg1, arg2);
    }

    @Override
    public void debug(final String format, final Object... arguments) {
        if (ALT_DEBUG_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, arguments);
        log(null, org.jboss.logmanager.Level.DEBUG, formattingTuple.getMessage(), formattingTuple.getThrowable(), arguments);
    }

    @Override
    public void debug(final String msg, final Throwable t) {
        if (ALT_DEBUG_INT < logger.getEffectiveLevel()) {
            return;
        }
        log(null, org.jboss.logmanager.Level.DEBUG, msg, t);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return isDebugEnabled();
    }

    @Override
    public void debug(Marker marker, String msg) {
        if (ALT_DEBUG_INT < logger.getEffectiveLevel()) {
            return;
        }
        log(marker, org.jboss.logmanager.Level.DEBUG, msg, null);
    }

    @Override
    public void debug(Marker marker, String format, Object arg) {
        if (ALT_DEBUG_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg);
        log(marker, org.jboss.logmanager.Level.DEBUG, formattingTuple.getMessage(), formattingTuple.getThrowable(), arg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        if (ALT_DEBUG_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg1, arg2);
        log(marker, org.jboss.logmanager.Level.DEBUG, formattingTuple.getMessage(), formattingTuple.getThrowable(), arg1, arg2);
    }

    @Override
    public void debug(Marker marker, String format, Object... arguments) {
        if (ALT_DEBUG_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, arguments);
        log(marker, org.jboss.logmanager.Level.DEBUG, formattingTuple.getMessage(), formattingTuple.getThrowable(), arguments);
    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {
        if (ALT_DEBUG_INT < logger.getEffectiveLevel()) {
            return;
        }
        log(marker, org.jboss.logmanager.Level.DEBUG, msg, t);
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isLoggable(Level.INFO);
    }

    @Override
    public void info(final String msg) {
        if (ALT_INFO_INT < logger.getEffectiveLevel()) {
            return;
        }
        log(null, org.jboss.logmanager.Level.INFO, msg, null);
    }

    @Override
    public void info(final String format, final Object arg) {
        if (ALT_INFO_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg);
        log(null, org.jboss.logmanager.Level.INFO, formattingTuple.getMessage(), formattingTuple.getThrowable(), arg);
    }

    @Override
    public void info(final String format, final Object arg1, final Object arg2) {
        if (ALT_INFO_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg1, arg2);
        log(null, org.jboss.logmanager.Level.INFO, formattingTuple.getMessage(), formattingTuple.getThrowable(), arg1, arg2);
    }

    @Override
    public void info(final String format, final Object... arguments) {
        if (ALT_INFO_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, arguments);
        log(null, org.jboss.logmanager.Level.INFO, formattingTuple.getMessage(), formattingTuple.getThrowable(), arguments);
    }

    @Override
    public void info(final String msg, final Throwable t) {
        if (ALT_INFO_INT < logger.getEffectiveLevel()) {
            return;
        }
        log(null, org.jboss.logmanager.Level.INFO, msg, t);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return isInfoEnabled();
    }

    @Override
    public void info(Marker marker, String msg) {
        if (ALT_INFO_INT < logger.getEffectiveLevel()) {
            return;
        }
        log(marker, org.jboss.logmanager.Level.INFO, msg, null);
    }

    @Override
    public void info(Marker marker, String format, Object arg) {
        if (ALT_INFO_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg);
        log(marker, org.jboss.logmanager.Level.INFO, formattingTuple.getMessage(), formattingTuple.getThrowable(), arg);
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        if (ALT_INFO_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg1, arg2);
        log(marker, org.jboss.logmanager.Level.INFO, formattingTuple.getMessage(), formattingTuple.getThrowable(), arg1, arg2);
    }

    @Override
    public void info(Marker marker, String format, Object... arguments) {
        if (ALT_INFO_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, arguments);
        log(marker, org.jboss.logmanager.Level.INFO, formattingTuple.getMessage(), formattingTuple.getThrowable(), arguments);
    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {
        if (ALT_INFO_INT < logger.getEffectiveLevel()) {
            return;
        }
        log(marker, org.jboss.logmanager.Level.INFO, msg, t);
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isLoggable(Level.WARN);
    }

    @Override
    public void warn(final String msg) {
        if (ALT_WARN_INT < logger.getEffectiveLevel()) {
            return;
        }
        log(null, org.jboss.logmanager.Level.WARN, msg, null);
    }

    @Override
    public void warn(final String format, final Object arg) {
        if (ALT_WARN_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg);
        log(null, org.jboss.logmanager.Level.WARN, formattingTuple.getMessage(), formattingTuple.getThrowable(), arg);
    }

    @Override
    public void warn(final String format, final Object... arguments) {
        if (ALT_WARN_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, arguments);
        log(null, org.jboss.logmanager.Level.WARN, formattingTuple.getMessage(), formattingTuple.getThrowable(), arguments);
    }

    @Override
    public void warn(final String format, final Object arg1, final Object arg2) {
        if (ALT_WARN_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg1, arg2);
        log(null, org.jboss.logmanager.Level.WARN, formattingTuple.getMessage(), formattingTuple.getThrowable(), arg1, arg2);
    }

    @Override
    public void warn(final String msg, final Throwable t) {
        if (ALT_WARN_INT < logger.getEffectiveLevel()) {
            return;
        }
        log(null, org.jboss.logmanager.Level.WARN, msg, t);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return isWarnEnabled();
    }

    @Override
    public void warn(Marker marker, String msg) {
        if (ALT_WARN_INT < logger.getEffectiveLevel()) {
            return;
        }
        log(marker, org.jboss.logmanager.Level.WARN, msg, null);
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {
        if (ALT_WARN_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg);
        log(marker, org.jboss.logmanager.Level.WARN, formattingTuple.getMessage(), formattingTuple.getThrowable(), arg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        if (ALT_WARN_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg1, arg2);
        log(marker, org.jboss.logmanager.Level.WARN, formattingTuple.getMessage(), formattingTuple.getThrowable(), arg1, arg2);
    }

    @Override
    public void warn(Marker marker, String format, Object... arguments) {
        if (ALT_WARN_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, arguments);
        log(marker, org.jboss.logmanager.Level.WARN, formattingTuple.getMessage(), formattingTuple.getThrowable(), arguments);
    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {
        if (ALT_WARN_INT < logger.getEffectiveLevel()) {
            return;
        }
        log(marker, org.jboss.logmanager.Level.WARN, msg, t);
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isLoggable(Level.ERROR);
    }

    @Override
    public void error(final String msg) {
        if (ALT_ERROR_INT < logger.getEffectiveLevel()) {
            return;
        }
        log(null, org.jboss.logmanager.Level.ERROR, msg, null);
    }

    @Override
    public void error(final String format, final Object arg) {
        if (ALT_ERROR_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg);
        log(null, org.jboss.logmanager.Level.ERROR, formattingTuple.getMessage(), formattingTuple.getThrowable(), arg);
    }

    @Override
    public void error(final String format, final Object arg1, final Object arg2) {
        if (ALT_ERROR_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg1, arg2);
        log(null, org.jboss.logmanager.Level.ERROR, formattingTuple.getMessage(), formattingTuple.getThrowable(), arg1, arg2);
    }

    @Override
    public void error(final String format, final Object... arguments) {
        if (ALT_ERROR_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, arguments);
        log(null, org.jboss.logmanager.Level.ERROR, formattingTuple.getMessage(), formattingTuple.getThrowable(), arguments);
    }

    @Override
    public void error(final String msg, final Throwable t) {
        if (ALT_ERROR_INT < logger.getEffectiveLevel()) {
            return;
        }
        log(null, org.jboss.logmanager.Level.ERROR, msg, t);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return isErrorEnabled();
    }

    @Override
    public void error(Marker marker, String msg) {
        if (ALT_ERROR_INT < logger.getEffectiveLevel()) {
            return;
        }
        log(marker, org.jboss.logmanager.Level.ERROR, msg, null);
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
        if (ALT_ERROR_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg);
        log(marker, org.jboss.logmanager.Level.ERROR, formattingTuple.getMessage(), formattingTuple.getThrowable(), arg);
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        if (ALT_ERROR_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg1, arg2);
        log(marker, org.jboss.logmanager.Level.ERROR, formattingTuple.getMessage(), formattingTuple.getThrowable(), arg1, arg2);
    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {
        if (ALT_ERROR_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, arguments);
        log(marker, org.jboss.logmanager.Level.ERROR, formattingTuple.getMessage(), formattingTuple.getThrowable(), arguments);
    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {
        if (ALT_ERROR_INT < logger.getEffectiveLevel()) {
            return;
        }
        log(null, org.jboss.logmanager.Level.ERROR, msg, t);
    }

    protected Object readResolve() throws ObjectStreamException {
        return this;
    }

    private void log(final Marker marker, final java.util.logging.Level level, final String message, final Throwable t) {
        final ExtLogRecord rec = new ExtLogRecord(level, message, LOGGER_CLASS_NAME);
        rec.setThrown(t);
        setMarker(rec, marker);
        logger.logRaw(rec);
    }

    private void log(final Marker marker, final java.util.logging.Level level, final String message, final Throwable t, final Object... params) {
        log(marker, level, LOGGER_CLASS_NAME, message, t, params);
    }

    private void log(final Marker marker, final java.util.logging.Level level, final String fqcn, final String message, final Throwable t, final Object[] params) {
        final ExtLogRecord rec = new ExtLogRecord(level, message, ExtLogRecord.FormatStyle.NO_FORMAT, fqcn);
        rec.setThrown(t);
        rec.setParameters(params);
        setMarker(rec, marker);
        logger.logRaw(rec);
    }

    private void setMarker( ExtLogRecord rec, Marker marker) {
        if (MARKER_SETTER != null) {
            try {
                MARKER_SETTER.invokeExact(rec, marker);
            } catch (Throwable e) {
                // ignored
            }
        }
    }

    private static final MethodHandle MARKER_SETTER;

    static {
        MethodHandle setMarker = null;
        try {
            setMarker = MethodHandles.lookup().findVirtual(ExtLogRecord.class, "setMarker", MethodType.methodType(void.class, Object.class));
        } catch (ReflectiveOperationException ignored) {
            // old version of jboss-logmanager
        } finally {
            MARKER_SETTER = setMarker;
        }
    }

}
