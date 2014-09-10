/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package net.netne.mina;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import net.netne.mina.ssl.BogusSslContextFactory;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.apache.mina.filter.ssl.SslFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * (<b>Entry point</b>) Echo server
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class Launcher {
    /** Choose your favorite port number. */
    private static final int PORT = 8088;

    /** Set this to true if you want to make the server SSL */
    private static final boolean USE_SSL = false;
    
    public static List<IoSession> sessionPool = new ArrayList<IoSession>();
    
    public void start() throws Exception{
    	NioSocketAcceptor acceptor = new NioSocketAcceptor();
        DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();

        MdcInjectionFilter mdcInjectionFilter = new MdcInjectionFilter();
        chain.addLast("mdc", mdcInjectionFilter);

        // Add SSL filter if SSL is enabled.
        if (USE_SSL) {
            addSSLSupport(chain);
        }

        chain.addLast("codec", new ProtocolCodecFilter(
                new TextLineCodecFactory(Charset.forName("UTF-8"))));

        addLogger(chain);

        // Bind
        acceptor.setHandler(new MessagetHandler());
        acceptor.bind(new InetSocketAddress(PORT));
        System.out.println("服务器启动...");
    }
    
    private static void addLogger(DefaultIoFilterChainBuilder chain)
            throws Exception {
        chain.addLast("logger", new LoggingFilter());
    }

    private static void addSSLSupport(DefaultIoFilterChainBuilder chain)
            throws Exception {
        SslFilter sslFilter = new SslFilter(BogusSslContextFactory
                .getInstance(true));
        chain.addLast("sslFilter", sslFilter);
        System.out.println("SSL ON");
    }
}
