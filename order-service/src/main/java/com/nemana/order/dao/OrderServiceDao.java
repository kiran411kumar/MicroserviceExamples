package com.nemana.order.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderServiceDao extends JpaRepository<OrderEntity, Integer> {

}
