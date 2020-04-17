package com.jsoft.framework.ssm.dao.ssm;

import com.jsoft.framework.ssm.model.ssm.Mood;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoodDao {

    List<Mood> findAll();

    Mood findById(int id);

    boolean update(@Param( "mood" ) Mood mood);
}
