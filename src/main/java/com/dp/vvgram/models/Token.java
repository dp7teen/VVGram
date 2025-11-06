package com.dp.vvgram.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Token extends BaseModel {
    private String value;
    private boolean revoked;
    private boolean expired;
}
