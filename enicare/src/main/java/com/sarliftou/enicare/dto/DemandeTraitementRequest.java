package com.sarliftou.enicare.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DemandeTraitementRequest {
    private Long adminId;
    private String decision;
    private String message;
}
