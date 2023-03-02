package com.mstudent.model.dto.request.Cost;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cost {
    private Long id;
    private String month;
    private String state;


    private BigDecimal price;


    private Long studentId;


    private Long roomId;
}
