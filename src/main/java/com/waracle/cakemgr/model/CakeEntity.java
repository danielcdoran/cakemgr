package com.waracle.cakemgr.model;

import org.springframework.lang.NonNull;

import jakarta.persistence.*;

@Entity
@Table(name = "Employee")
public class CakeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", unique = true, nullable = false)
  private long employeeId;

  @Column(name = "EMAIL", unique = true, nullable = false, length = 100)
  private String title;

  @Column(name = "FIRST_NAME", unique = false, nullable = false, length = 100)
  private String description;

  @Column(name = "LAST_NAME", unique = false, nullable = false, length = 300)
  private String image;

  public CakeEntity() {}

  public CakeEntity(@NonNull String title, @NonNull String description, @NonNull String image) {
    this.title = title;
    this.description = description;
    this.image = image;
  }

  public CakeEntity(long employeeId, @NonNull String title, @NonNull String description, @NonNull String image) {
    this.employeeId = employeeId;
    this.title = title;
    this.description = description;
    this.image = image;
  }

  public CakeEntity(@NonNull Cake cakeNoID) {
    if ( !cakeNoID.isValid() ) throw new IllegalArgumentException("Invalid CakeEntity Constructor");
    this.title = cakeNoID.getTitle();
    this.description = cakeNoID.getDesc();
    this.image = cakeNoID.getImage();
  }

  public long getId() {
    return employeeId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(@NonNull String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(@NonNull String description) {
    this.description = description;
  }

  public String getImage() {
    return image;
  }

  public void setImage(@NonNull String image) {
    this.image = image;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    CakeEntity that = (CakeEntity) obj;

    if (employeeId != that.employeeId) return false;
    if (this.getTitle() != that.getTitle()) return false;
    if (this.getDescription() != that.getDescription()) return false;
    if (this.getImage() != that.getImage()) return false;

    return true;
  }

  public boolean equalContent(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    CakeEntity that = (CakeEntity) obj;
    if (this.getTitle() != that.getTitle()) return false;
    if (this.getDescription() != that.getDescription()) return false;
    if (this.getImage() != that.getImage()) return false;

    return true;
  }

  @Override
  public String toString() {
    return (
      "CakeEntity [id=" +
      employeeId +
      ", title=" +
      title +
      ", desc=" +
      description +
      ", image=" +
      image +
      "]"
    );
  }
}
