package com.lambda.function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class RegistrationHandler implements RequestHandler<Request, Response> {

    public Response handleRequest(Request request, Context context) {
        String greetings = String.format("Hello, %s%s", request.getFirstName(), request.getLastName());
        LambdaLogger logger = context.getLogger();
        logger.log("########## Log output: " + greetings);
        return new Response(request.getFirstName(), request.getLastName());
    }
}
