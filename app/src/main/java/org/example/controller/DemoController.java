package org.example.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/demo")
@Tag(name = "Demo", description = "Demo APIs")
public class DemoController {

    @GetMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> get(@RequestParam String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("username", RandomStringUtils.randomAlphabetic(10));
        map.put("created_at", LocalDateTime.now());
        map.put("updated_at", LocalDateTime.now());
        return map;
    }
}
