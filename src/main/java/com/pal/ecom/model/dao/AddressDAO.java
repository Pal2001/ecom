package com.pal.ecom.model.dao;

import com.pal.ecom.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressDAO extends JpaRepository<Address,Long> {

    List<Address> findByUser_Id(Long id);
}
