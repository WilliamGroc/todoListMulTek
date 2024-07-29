package com.multek.java.back.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author wgroc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "todo")
public class Todo {
  @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
  public Long id;
  
  @Column(name = "title", nullable=false)
  public String title;

  @Column(name = "description", nullable=false)
  public String description;

  @Column(name = "checked")
  public Boolean checked = false;

  @Column(name = "createdAt", nullable=false)
  public LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "updatedAt")
  public LocalDateTime updatedAt;
}
