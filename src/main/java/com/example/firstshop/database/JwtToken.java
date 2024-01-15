package com.example.firstshop.database;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash("User")
public class JwtToken implements Serializable {
    private String id;
    private String refreshToken;
}
