package com.computer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cost")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cost {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "month")
  private String month;

  @Column(name = "state")
  private String state;

  @Column(name = "price")
  private BigDecimal price;

  @Column(name = "studentId")
  private int studentId;

  @Column(name = "roomId")
  private int roomId;
}
