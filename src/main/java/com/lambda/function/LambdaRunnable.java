package com.lambda.function;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class LambdaRunnable implements Runnable {

    private static Logger LOGGER = LoggerFactory.getLogger(Runner.class);
    private static final String AWS_ACCESS_KEY = "Type your aws access key";
    private static final String AWS_SECRET_KEY = "Type your aws secret key";
    private static final List<String> functions = Arrays.asList("lambda-graal-java", "lambda-graal-native");

    private static final String PAYLOAD = "{ \"input\": \"test\" }";

    private static final int ATTEMPTS = 10;

    @Override
    public void run() {

        repeat(ATTEMPTS, () -> {
            ((Runnable) () -> {
                invokeLambdaFunction("lambda-graal-java");
                invokeLambdaFunction("lambda-graal-native");
            }).run();
        });
    }


    private String invokeLambdaFunction(String lambdaFunctionName) {
        InvokeRequest invokeRequest = new InvokeRequest()
                .withFunctionName(lambdaFunctionName)
                .withPayload(PAYLOAD);

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);

        AWSLambda awsLambda = AWSLambdaClientBuilder.standard()
                .withRegion(Regions.AP_NORTHEAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();

        InvokeResult invokeResult = awsLambda.invoke(invokeRequest);
        ByteBuffer byteBuffer = invokeResult.getPayload();
        return new String(byteBuffer.array(), StandardCharsets.UTF_8);
    }

    private void repeat(int count, Runnable action) {
        IntStream.range(0, count).forEach(i -> action.run());
    }
}
