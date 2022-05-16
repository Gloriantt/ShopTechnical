package com.Library.demo.Repos;

import com.Library.demo.Models.Basket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BasketRepository extends CrudRepository<Basket, Long> {

    @Query(value = "select B from Basket as B inner join B.author as aut inner join B.order as ord where aut.id = :id ")
    List<Basket> findAll(@Param("id") Long id);


}
