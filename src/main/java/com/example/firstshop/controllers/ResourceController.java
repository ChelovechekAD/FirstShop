package com.example.firstshop.controllers;

import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Controller
public class ResourceController {

    @GetMapping("/styles/{file_name}")
    @ResponseBody
    public ResponseEntity<String> styles(@PathVariable("file_name") String name) throws IOException{
        InputStream is = getClass().getClassLoader().getResourceAsStream("static/styles/" + name + ".css");
        BufferedReader bf = new BufferedReader(new InputStreamReader(is));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = bf.readLine()) != null){
            sb.append(line).append("\n");
        }
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "text/css; charset=utf-8");
        return new ResponseEntity<>(sb.toString(), httpHeaders, HttpStatus.OK);
    }
    @GetMapping("/scripts/{file_name}")
    @ResponseBody
    public ResponseEntity<String> scripts(@PathVariable("file_name") String name) throws IOException{
        InputStream is = getClass().getClassLoader().getResourceAsStream("static/scripts/" + name + ".js");
        BufferedReader bf = new BufferedReader(new InputStreamReader(is));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = bf.readLine()) != null){
            sb.append(line).append("\n");
        }
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "text/javascript; charset=utf-8");
        return new ResponseEntity<>(sb.toString(), httpHeaders, HttpStatus.OK);
    }

}
