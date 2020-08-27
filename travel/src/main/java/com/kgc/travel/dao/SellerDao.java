package com.kgc.travel.dao;

import com.kgc.travel.domain.Seller;

public interface SellerDao {
    Seller findBySid(int sid);
}
