package com.currency.currency_module.repository;

import com.currency.currency_module.model.PpmInfo;
import com.currency.currency_module.model.PvtInfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PpmInfoRepository extends JpaRepository<PpmInfo,Long>  {
     List<PpmInfo> findAll();
      List<PpmInfo> findByApplicationType(Integer applicationType);
      Integer countByApplicationType(Integer applicationType);

}