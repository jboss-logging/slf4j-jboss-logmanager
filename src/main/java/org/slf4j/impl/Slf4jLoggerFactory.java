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

import java.security.PrivilegedAction;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.jboss.logmanager.LogContext;

import static java.security.AccessController.doPrivileged;

public final class Slf4jLoggerFactory implements ILoggerFactory {

    private static final org.jboss.logmanager.Logger.AttachmentKey<Logger> key = new org.jboss.logmanager.Logger.AttachmentKey<Logger>();

    public Logger getLogger(final String name) {
        final org.jboss.logmanager.Logger lmLogger = LogContext.getLogContext().getLogger(name);
        final Logger logger = lmLogger.getAttachment(key);
        if (logger != null) {
            return logger;
        }
        return doPrivileged(new PrivilegedAction<Logger>() {
            public Logger run() {
                final Slf4jLogger newLogger = new Slf4jLogger(lmLogger);
                final Logger appearingLogger = lmLogger.attachIfAbsent(key, newLogger);
                return appearingLogger != null ? appearingLogger : newLogger;
            }
        });
    }
}
