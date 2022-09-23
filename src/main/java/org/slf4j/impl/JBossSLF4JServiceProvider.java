/*
 * JBoss, Home of Professional Open Source.
 *
 * Copyright 2022 Red Hat, Inc., and individual contributors
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

import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

/**
 * ServiceProvider to grant access to the factories.
 *
 * @author <a href="mailto:boris@unckel.net">Boris Unckel</a>
 */
public class JBossSLF4JServiceProvider implements SLF4JServiceProvider{

     private final ILoggerFactory loggerFactory;
     private final IMarkerFactory markerFactory;
     private final MDCAdapter mdcAdapter;

     public JBossSLF4JServiceProvider() {
          this.loggerFactory = new Slf4jLoggerFactory();
          this.markerFactory = new BasicMarkerFactory();
          this.mdcAdapter = new Slf4jMDCAdapter();
     }

     @Override
     public ILoggerFactory getLoggerFactory() {
          return this.loggerFactory;
     }

     @Override
     public IMarkerFactory getMarkerFactory() {
          return this.markerFactory;
     }

     @Override
     public MDCAdapter getMDCAdapter() {
          return this.mdcAdapter;
     }

     @Override
     public String getRequestedApiVersion() {
          return StaticLoggerBinder.REQUESTED_API_VERSION;
     }

     @Override
     public void initialize() {

     }
}
