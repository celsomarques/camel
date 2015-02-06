/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.example.server;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.Namespaces;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.example.server.model.IncrementRequest;
import org.springframework.stereotype.Component;

@Component
public class IncrementRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        JaxbDataFormat jaxb = new JaxbDataFormat(IncrementRequest.class.getPackage().getName());
        
        Namespaces ns = new Namespaces("inc", "http://camel.apache.org/example/increment");
        
        from("spring-ws:rootqname:{http://camel.apache.org/example/increment}incrementRequest?endpointMapping=#endpointMapping")
            .onCompletion().modeBeforeConsumer().onCompleteOnly().to("bean:incrementResponseProcessor").end()
            .onException(Throwable.class).handled(true).maximumRedeliveries(0).to("bean:exceptionHandler").end()
            .setHeader("input-bla").xpath("//inc:input/text()", ns)
            .unmarshal(jaxb)
            .choice()
            	.when(xpath("$in:input-bla > '100'")).throwException(new RuntimeException("Exceeded value"))
            .otherwise()
            	.to("bean:incrementTransformer")
            	.to("bean:incrementProcessor")
            .end()
            .marshal(jaxb);
    }
   
}