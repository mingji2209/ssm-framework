package com.jsoft.framework.ssm.dao.ssm;

import com.jsoft.framework.ssm.model.ssm.UserMoodPraiseRel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMoodPraiseRelDao {

    boolean save(@Param( "userMoodPraiseRel" ) UserMoodPraiseRel userMoodPraiseRel);

}
