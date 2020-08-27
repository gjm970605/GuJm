package com.kgc.travel.dao;

import com.kgc.travel.domain.Favorite;

import java.util.Date;
import java.util.List;

public interface FavoriteDao {
    int countByRid(int rid);

    Favorite findByRidAndUid(int rid, int uid);

    void addFavorite(int rid, Date date, int uid);

    int countByUid(int uid);

    List<Integer> pageByUid(int uid, int startIndex, int pageSize);
}
