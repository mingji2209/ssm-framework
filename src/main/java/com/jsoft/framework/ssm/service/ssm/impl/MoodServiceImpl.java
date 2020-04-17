package com.jsoft.framework.ssm.service.ssm.impl;

import com.jsoft.framework.ssm.dao.ssm.MoodDao;
import com.jsoft.framework.ssm.dao.ssm.UserDao;
import com.jsoft.framework.ssm.dao.ssm.UserMoodPraiseRelDao;
import com.jsoft.framework.ssm.dto.ssm.MoodDTO;
import com.jsoft.framework.ssm.model.ssm.Mood;
import com.jsoft.framework.ssm.model.ssm.User;
import com.jsoft.framework.ssm.model.ssm.UserMoodPraiseRel;
import com.jsoft.framework.ssm.mq.MoodProduct;
import com.jsoft.framework.ssm.service.ssm.MoodService;
import com.jsoft.framework.ssm.service.ssm.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.jms.Destination;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class MoodServiceImpl implements MoodService {

    @Resource
    private MoodDao moodDao;

    @Resource
    private UserDao userDao;

    @Resource
    private RedisService redisService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private MoodProduct moodProduct;

    @Resource
    private UserMoodPraiseRelDao userMoodPraiseRelDao;

    //key命名规范 项目名+模块名+具体内容
    private static final String PRAISE_HASH_KEY = "ssm.mood.id.list.key";

    //队列
    private static Destination destination = new ActiveMQQueue( "com.jsoft.framework.ssm.queue.hign.concurrency.praise" );

    @Override
    public List<MoodDTO> findAll() {
        List<Mood>  moodList = moodDao.findAll();
        return converMood2DTO(moodList);

    }

    @Override
    public Boolean praiseMood(int userId, int moodId) {

        // 保存说说关联关系
        UserMoodPraiseRel userMoodPraiseRel = new UserMoodPraiseRel();
       userMoodPraiseRel.setUserId( userId );
       userMoodPraiseRel.setMoodId( moodId );
       userMoodPraiseRelDao.save( userMoodPraiseRel );

       log.info( "开始更新点赞数" );

       // 跟新点赞数
        Mood mood = this.findById( moodId );
        mood.setPraiseNum( mood.getPraiseNum()+1 );
        this.update( mood );

        return Boolean.TRUE;
    }

    @Override
    public Mood findById(int id) {
        return moodDao.findById( id );
    }

    @Override
    public boolean update(Mood mood) {
        return moodDao.update(  mood );
    }

    @Override
    public boolean praiseMoodForRedis(int userId, int moodId) {
        MoodDTO moodDTO = new MoodDTO();
        moodDTO.setUserId( userId );
        moodDTO.setId( moodId );
        moodProduct.sendMessage( destination,moodDTO );
//        log.info( "Redis写入点赞数据到Set集合" );
//        redisService.sSet( PRAISE_HASH_KEY,moodId );
//        redisService.sSet( String.valueOf( moodId ),userId);
        return false;
    }

    @Override
    public List<MoodDTO> findAllForRedis() {
        List<Mood> moodList = moodDao.findAll();
        if(CollectionUtils.isEmpty( moodList )){
            return Collections.EMPTY_LIST;
        }
        List<MoodDTO> moodDTOList = new ArrayList<MoodDTO>(  );
        log.info( "通过Redis开始更新点赞数" );
        for (Mood mood:moodList) {
            MoodDTO moodDTO = new MoodDTO();
            moodDTO.setId( mood.getId() );
            moodDTO.setUserId(mood.getUserId());
            moodDTO.setPraiseNum( mood.getPraiseNum()+redisTemplate.opsForSet().size( String.valueOf( mood.getId() ) ).intValue() );
            moodDTO.setPublishTime( mood.getPublishTime() );
            moodDTO.setContent( mood.getContent() );
            User user = userDao.find( mood.getUserId() );
            moodDTO.setUserName( user.getName() );
            moodDTO.setUserAccount( user.getAccount() );
            moodDTOList.add( moodDTO );
        }

        return moodDTOList;
    }

    private List<MoodDTO> converMood2DTO(List<Mood> moodList) {
        if(CollectionUtils.isEmpty( moodList )){
            return Collections.EMPTY_LIST;
        }
        List<MoodDTO> moodDTOList = new ArrayList<MoodDTO>(  );
        for (Mood mood:moodList) {
            MoodDTO moodDTO = new MoodDTO();
            moodDTO.setId( mood.getId() );
            moodDTO.setContent( mood.getContent() );
            moodDTO.setPraiseNum( mood.getPraiseNum() );
            moodDTO.setPublishTime( mood.getPublishTime() );
            moodDTO.setUserId(mood.getUserId());
            moodDTOList.add( moodDTO );
            User user = userDao.find( mood.getUserId() );
            moodDTO.setUserName( user.getName() );
            moodDTO.setUserAccount( user.getAccount() );
        }
        return moodDTOList;
    }
}
