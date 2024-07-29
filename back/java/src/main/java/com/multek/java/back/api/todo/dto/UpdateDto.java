/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.multek.java.back.api.todo.dto;

import lombok.Data;

/**
 *
 * @author wgroc
 */
@Data
public class UpdateDto{
  private String title;
  private String description;
  private boolean checked;
}