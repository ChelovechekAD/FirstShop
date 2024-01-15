package com.example.firstshop.dto.request;


import lombok.*;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CatalogAddRequest {
    private String type;
    private String name;
    private String color;
    private String company;
    private Double price;
    private Integer count;
    private String rate;
    private String description;


}
