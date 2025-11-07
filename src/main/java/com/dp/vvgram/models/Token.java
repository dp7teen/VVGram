package com.dp.vvgram.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter @Setter
public class Token extends BaseModel implements Serializable {
    private String value;
    private boolean revoked;
    private boolean expired;
}
