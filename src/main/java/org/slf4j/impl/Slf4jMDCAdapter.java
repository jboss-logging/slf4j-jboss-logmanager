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

import java.util.Map;
import org.slf4j.spi.MDCAdapter;
import org.jboss.logmanager.MDC;

public final class Slf4jMDCAdapter implements MDCAdapter {

    public void put(final String key, final String val) {
        MDC.put(key, val);
    }

    public String get(final String key) {
        return MDC.get(key);
    }

    public void remove(final String key) {
        MDC.remove(key);
    }

    public void clear() {
        MDC.clear();
    }

    public Map<String, String> getCopyOfContextMap() {
        return MDC.copy();
    }

    public void setContextMap(final Map contextMap) {
        MDC.clear();
        for (Map.Entry<?, ?> entry : ((Map<?, ?>) contextMap).entrySet()) {
            final Object key = entry.getKey();
            final Object value = entry.getValue();
            if (key != null && value != null) {
                MDC.put(key.toString(), value.toString());
            }
        }
    }
}
