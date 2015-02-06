package org.apache.camel.example.server;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.Message;
import org.apache.camel.example.server.model.IncrementResponse;
import org.springframework.stereotype.Component;

@Component
public class ExceptionHandler {
	
	@Handler
    public void process(Exchange exchange) {
		
		Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
		exception.printStackTrace();
		
		System.out.println(exchange.getProperties());
		
        IncrementResponse response = new IncrementResponse();
        response.setResult(500); 
        Message out = exchange.getOut();
        out.setBody(response);
        out.setFault(true);
    }
}