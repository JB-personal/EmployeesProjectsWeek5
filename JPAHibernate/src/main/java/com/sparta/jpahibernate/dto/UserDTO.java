package com.sparta.jpahibernate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserDTO {

    private Long id;

    private String email;

    private String password;

    private String key;

    private Instant lastUpdate;

    private String level;

}
