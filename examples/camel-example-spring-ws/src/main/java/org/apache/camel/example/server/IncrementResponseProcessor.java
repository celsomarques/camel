package org.apache.camel.example.server;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.example.server.model.IncrementResponse;
import org.springframework.stereotype.Component;

@Component
public class IncrementResponseProcessor {
	
	@Handler
    public void process(Exchange exchange) {
		
		System.out.println("Transformed message: " + exchange.getIn().getBody());
		
        IncrementResponse response = new IncrementResponse();
        response.setResult(200); 
        exchange.getOut().setBody(response);
    }
}