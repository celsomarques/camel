package org.apache.camel.example.server;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.example.server.model.IncrementRequest;
import org.springframework.stereotype.Component;

@Component
public class IncrementTransformer {

	@Handler
	public String process(IncrementRequest request, Exchange exchange) {
		
		System.out.println("Requested input: " + request.getInput());
		System.out.println("Input header: " + exchange.getIn().getHeader("input-bla"));
		System.out.println(exchange.getIn().getHeaders());
		
		return "Transformed";
	}
}