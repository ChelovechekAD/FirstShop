package com.example.firstshop.controllers;


import com.example.firstshop.database.Bricks;
import com.example.firstshop.dto.request.CatalogAddRequest;
import com.example.firstshop.dto.response.CatalogAddResponse;
import com.example.firstshop.repository.BricksRepos;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CatalogFuncController {

    private final BricksRepos bricksRepos;

    @PostMapping(value = "/add/catalog")
    @ResponseBody
    private ResponseEntity<CatalogAddResponse> add(@RequestBody CatalogAddRequest request){
        System.out.println(request.getColor());
        String type = request.getType();
        if (type.equals("bricks")){
            Bricks obj = new Bricks();
            obj.setName(request.getName());
            obj.setColor(request.getColor());
            obj.setCompany(request.getCompany());
            obj.setCount(request.getCount());
            obj.setDescription(request.getDescription());
            obj.setRate(request.getRate());
            obj.setPrice(request.getPrice());
            bricksRepos.save(obj);
        }
        //

        return ResponseEntity.ok(CatalogAddResponse.builder()
                .httpCode("201 Created")
                .message("Success!")
                .build());

    }

}
