package com.chitchat.web.controller;

import com.chitchat.grpc.service.GreeterGrpc;
import com.chitchat.grpc.service.HelloRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/greeter")
@Tag(name = "Greeter", description = "Greeter APIs")
public class GreeterController {

    @GrpcClient("local")
    private GreeterGrpc.GreeterBlockingStub greeterStub;

    @GetMapping(path = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> hello(@RequestParam String name) {
        HelloRequest request = HelloRequest.newBuilder()
                .setName(name)
                .build();
        Map<String, Object> map = new HashMap<>();
        map.put("message", greeterStub.sayHello(request).getMessage());
        return map;
    }
}
