package com.spiralin.spiralinapp.domain.entity;

import com.spiralin.spiralinapp.domain.enums.TokenType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Token {
    @Id
    private String id;

    @NonNull
    @Indexed(unique = true)
    private String token;

    private TokenType tokenType;

    private boolean expired;

    private boolean revoked;

    @NonNull
    private String user_id;
}
