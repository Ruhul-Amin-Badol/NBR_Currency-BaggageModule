package com.currency.currency_module.repository;


import com.currency.currency_module.model.PvtInfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PvtInfoRepository extends JpaRepository<PvtInfo,Long>  {
     List<PvtInfo> findAll();

    List<PvtInfo> findAllByOrgName(String organigation);

    List<PvtInfo> findAllByOrgNameAndEntryBy(String organigation, String name);
    

}
