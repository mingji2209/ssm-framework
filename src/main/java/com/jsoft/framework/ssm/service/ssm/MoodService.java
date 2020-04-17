package com.jsoft.framework.ssm.service.ssm;

import com.jsoft.framework.ssm.dto.ssm.MoodDTO;
import com.jsoft.framework.ssm.model.ssm.Mood;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface MoodService {

    List<MoodDTO> findAll();

    // 传统点赞

    Boolean praiseMood(int userId,int moodId);

    Mood findById(int id);

    boolean update(@Param( "mood" ) Mood mood);

    boolean praiseMoodForRedis(int userId,int moodId);

    List<MoodDTO> findAllForRedis();
}
