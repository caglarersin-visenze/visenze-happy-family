package com.visenze.happyfamily.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.visenze.happyfamily.entity.ProductEntity;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long>{

}
