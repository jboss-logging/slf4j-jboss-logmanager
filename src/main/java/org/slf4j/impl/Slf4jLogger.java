/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.slf4j.impl;

import org.slf4j.Marker;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.spi.LocationAwareLogger;
import org.jboss.logmanager.Logger;
import org.jboss.logmanager.ExtLogRecord;
import org.jboss.logmanager.Level;

import java.io.Serializable;
import java.io.ObjectStreamException;

public final class Slf4jLogger extends MarkerIgnoringBase implements Serializable, LocationAwareLogger {
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
        final String message = MessageFormatter.arrayFormat(fmt, argArray).getMessage();
        // ignore marker
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
            log(level, fqcn, message, t);
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
        log(org.jboss.logmanager.Level.TRACE, msg, null);
    }

    @Override
    public void trace(final String format, final Object arg) {
        if (ALT_TRACE_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg);
        log(org.jboss.logmanager.Level.TRACE, formattingTuple.getMessage(), formattingTuple.getThrowable());
    }

    @Override
    public void trace(final String format, final Object arg1, final Object arg2) {
        if (ALT_TRACE_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg1, arg2);
        log(org.jboss.logmanager.Level.TRACE, formattingTuple.getMessage(), formattingTuple.getThrowable());
    }

    @Override
    public void trace(final String format, final Object... arguments) {
        if (ALT_TRACE_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, arguments);
        log(org.jboss.logmanager.Level.TRACE, formattingTuple.getMessage(), formattingTuple.getThrowable());
    }

    @Override
    public void trace(final String msg, final Throwable t) {
        if (ALT_TRACE_INT < logger.getEffectiveLevel()) {
            return;
        }
        log(org.jboss.logmanager.Level.TRACE, msg, t);
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
        log(org.jboss.logmanager.Level.DEBUG, msg, null);
    }

    @Override
    public void debug(final String format, final Object arg) {
        if (ALT_DEBUG_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg);
        log(org.jboss.logmanager.Level.DEBUG, formattingTuple.getMessage(), formattingTuple.getThrowable());
    }

    @Override
    public void debug(final String format, final Object arg1, final Object arg2) {
        if (ALT_DEBUG_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg1, arg2);
        log(org.jboss.logmanager.Level.DEBUG, formattingTuple.getMessage(), formattingTuple.getThrowable());
    }

    @Override
    public void debug(final String format, final Object... arguments) {
        if (ALT_DEBUG_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, arguments);
        log(org.jboss.logmanager.Level.DEBUG, formattingTuple.getMessage(), formattingTuple.getThrowable());
    }

    @Override
    public void debug(final String msg, final Throwable t) {
        if (ALT_DEBUG_INT < logger.getEffectiveLevel()) {
            return;
        }
        log(org.jboss.logmanager.Level.DEBUG, msg, t);
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
        log(org.jboss.logmanager.Level.INFO, msg, null);
    }

    @Override
    public void info(final String format, final Object arg) {
        if (ALT_INFO_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg);
        log(org.jboss.logmanager.Level.INFO, formattingTuple.getMessage(), formattingTuple.getThrowable());
    }

    @Override
    public void info(final String format, final Object arg1, final Object arg2) {
        if (ALT_INFO_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg1, arg2);
        log(org.jboss.logmanager.Level.INFO, formattingTuple.getMessage(), formattingTuple.getThrowable());
    }

    @Override
    public void info(final String format, final Object... arguments) {
        if (ALT_INFO_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, arguments);
        log(org.jboss.logmanager.Level.INFO, formattingTuple.getMessage(), formattingTuple.getThrowable());
    }

    @Override
    public void info(final String msg, final Throwable t) {
        if (ALT_INFO_INT < logger.getEffectiveLevel()) {
            return;
        }
        log(org.jboss.logmanager.Level.INFO, msg, t);
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
        log(org.jboss.logmanager.Level.WARN, msg, null);
    }

    @Override
    public void warn(final String format, final Object arg) {
        if (ALT_WARN_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg);
        log(org.jboss.logmanager.Level.WARN, formattingTuple.getMessage(), formattingTuple.getThrowable());
    }

    @Override
    public void warn(final String format, final Object... arguments) {
        if (ALT_WARN_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, arguments);
        log(org.jboss.logmanager.Level.WARN, formattingTuple.getMessage(), formattingTuple.getThrowable());
    }

    @Override
    public void warn(final String format, final Object arg1, final Object arg2) {
        if (ALT_WARN_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg1, arg2);
        log(org.jboss.logmanager.Level.WARN, formattingTuple.getMessage(), formattingTuple.getThrowable());
    }

    @Override
    public void warn(final String msg, final Throwable t) {
        if (ALT_WARN_INT < logger.getEffectiveLevel()) {
            return;
        }
        log(org.jboss.logmanager.Level.WARN, msg, t);
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
        log(org.jboss.logmanager.Level.ERROR, msg, null);
    }

    @Override
    public void error(final String format, final Object arg) {
        if (ALT_ERROR_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg);
        log(org.jboss.logmanager.Level.ERROR, formattingTuple.getMessage(), formattingTuple.getThrowable());
    }

    @Override
    public void error(final String format, final Object arg1, final Object arg2) {
        if (ALT_ERROR_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.format(format, arg1, arg2);
        log(org.jboss.logmanager.Level.ERROR, formattingTuple.getMessage(), formattingTuple.getThrowable());
    }

    @Override
    public void error(final String format, final Object... arguments) {
        if (ALT_ERROR_INT < logger.getEffectiveLevel()) {
            return;
        }
        final FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, arguments);
        log(org.jboss.logmanager.Level.ERROR, formattingTuple.getMessage(), formattingTuple.getThrowable());
    }

    @Override
    public void error(final String msg, final Throwable t) {
        if (ALT_ERROR_INT < logger.getEffectiveLevel()) {
            return;
        }
        log(org.jboss.logmanager.Level.ERROR, msg, t);
    }

    @Override
    protected Slf4jLogger readResolve() throws ObjectStreamException {
        return this;
    }

    private void log(final java.util.logging.Level level, final String message, final Throwable t) {
        log(level, LOGGER_CLASS_NAME, message, t);
    }

    private void log(final java.util.logging.Level level,  final String fqcn, final String message, final Throwable t) {
        final ExtLogRecord rec = new ExtLogRecord(level, message, fqcn);
        rec.setThrown(t);
        logger.logRaw(rec);
    }
}
