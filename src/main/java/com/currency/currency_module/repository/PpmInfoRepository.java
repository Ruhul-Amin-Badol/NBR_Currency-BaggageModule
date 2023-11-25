package com.currency.currency_module.repository;

import com.currency.currency_module.model.PpmInfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PpmInfoRepository extends JpaRepository<PpmInfo,Long>  {
     List<PpmInfo> findAll();
}