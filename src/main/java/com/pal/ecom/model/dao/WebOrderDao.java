package com.pal.ecom.model.dao;

import com.pal.ecom.model.LocalUser;
import com.pal.ecom.model.WebOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebOrderDao extends JpaRepository<WebOrder, Long> {

    List<WebOrder> findByUser(LocalUser user);
}
