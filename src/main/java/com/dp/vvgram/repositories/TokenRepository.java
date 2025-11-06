package com.dp.vvgram.repositories;

import com.dp.vvgram.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByValue(String token);

    Optional<Token> findByValueAndExpired(String value, boolean expired);
}
