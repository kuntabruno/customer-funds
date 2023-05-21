package com.demo.customerfunds.models;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class BaseModel {
    private Long id;
    private OffsetDateTime createdAt;
}
