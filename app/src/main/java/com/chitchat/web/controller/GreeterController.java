package com.chitchat.web.controller;

import com.chitchat.grpc.service.GreeterGrpc;
import com.chitchat.grpc.service.HelloRequest;
import com.chitchat.grpc.service.HelloResponse;
import com.chitchat.web.dto.HelloRequestDto;
import com.chitchat.web.dto.HelloResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/greeter")
@Tag(name = "Greeter", description = "Greeter APIs")
public class GreeterController {

    @GrpcClient("local")
    private GreeterGrpc.GreeterBlockingStub greeterStub;

    @PostMapping(path = "/hello",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public HelloResponseDto hello(@RequestBody HelloRequestDto requestDto) {
        HelloRequest request = HelloRequest.newBuilder()
                .setName(requestDto.getName())
                .build();
        HelloResponse response = greeterStub.sayHello(request);
        return new HelloResponseDto(response.getMessage());
    }
}
