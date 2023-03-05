package com.github.awsp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {
  private boolean deleted = false;
  public abstract Long getId();
}
