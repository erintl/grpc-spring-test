package com.github.erintl.grpcspringtestclient;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
@RequestMapping(value = "/v1/tests")
public class TestController
{
    private CacheClient cacheClient;

    @Autowired
    public void setCacheClient(CacheClient cacheClient)
    {
        this.cacheClient = cacheClient;
    }

    @PostMapping
    public ResponseEntity<?> setItem()
    {
        log.info("set item endpoint hit");
        cacheClient.setCache();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
