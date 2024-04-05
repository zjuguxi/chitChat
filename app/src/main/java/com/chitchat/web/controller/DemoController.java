package com.chitchat.web.controller;

import com.chitchat.protobuf.HelloRequest;
import com.chitchat.protobuf.HelloServiceGrpc;
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
@RequestMapping(path = "/demo")
@Tag(name = "Demo", description = "Demo APIs")
public class DemoController {

    @GrpcClient("local")
    private HelloServiceGrpc.HelloServiceBlockingStub helloServiceStub;

    @GetMapping(path = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> get(@RequestParam String name) {
        HelloRequest request = HelloRequest.newBuilder()
                .setName(name)
                .build();
        Map<String, Object> map = new HashMap<>();
        map.put("message", helloServiceStub.sayHello(request).getMessage());
        return map;
    }
}
