package com.waracle.cakemgr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waracle.cakemgr.model.CakeEntity;

public interface CakeEntityRepository extends JpaRepository<CakeEntity, Long> {
  // List<CakeEntity> findByPublished(String image);

  List<CakeEntity> findByTitleContaining(String title);
}
