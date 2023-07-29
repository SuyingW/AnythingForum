package com.cpsc304.anything.contollers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class AnythingController {
    @GetMapping("/")
    public Map<String, String> test() {
        return Collections.singletonMap("response", "your string value");
    }
}
