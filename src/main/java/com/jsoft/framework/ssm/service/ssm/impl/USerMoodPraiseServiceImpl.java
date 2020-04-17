package com.jsoft.framework.ssm.service.ssm.impl;

import com.jsoft.framework.ssm.dao.ssm.UserMoodPraiseRelDao;
import com.jsoft.framework.ssm.model.ssm.UserMoodPraiseRel;
import com.jsoft.framework.ssm.service.ssm.UserMoodPraiseRelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class USerMoodPraiseServiceImpl implements UserMoodPraiseRelService {

    @Resource
    private UserMoodPraiseRelDao userMoodPraiseRelDao;
    @Override
    public boolean save(UserMoodPraiseRel userMoodPraiseRel) {
        return userMoodPraiseRelDao.save( userMoodPraiseRel );
    }
}
