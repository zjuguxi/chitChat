package com.chitchat.web.controller;

import com.chitchat.util.IpAddressUtils;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequestMapping(path = "/api/info")
public class InformationController {

    @GetMapping(path = "/ip", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getIpAddress() {
        return IpAddressUtils.getIpAddress();
    }
}
