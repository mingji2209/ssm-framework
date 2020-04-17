package com.jsoft.framework.ssm.task;

import com.jsoft.framework.ssm.model.ssm.Mood;
import com.jsoft.framework.ssm.model.ssm.UserMoodPraiseRel;
import com.jsoft.framework.ssm.service.ssm.MoodService;
import com.jsoft.framework.ssm.service.ssm.UserMoodPraiseRelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Set;


@Slf4j
@Component
@Configurable
@EnableScheduling
public class PraiseDataSaveDBJob {

    @Resource
    private RedisTemplate redisTemplate;
    private static final String PRAISE_HASH_KEY = "ssm.mood.id.list.key";
    @Resource
    private UserMoodPraiseRelService userMoodPraiseRelService;
    @Resource
    private MoodService moodService;

    // 10秒执行一次
    @Scheduled(cron = "0/10 * * * * *")
    public void savePraiseDataToDB2(){
        log.info( "从redis中写入数据到MySQL" );
        Set<Integer> moods = redisTemplate.opsForSet().members( PRAISE_HASH_KEY );
        if(CollectionUtils.isEmpty( moods )){
            return;
        }
        for (int moodId:moods
             ) {
            Set<Integer> users = redisTemplate.opsForSet().members( String.valueOf( moodId ) );
            if(CollectionUtils.isEmpty( users )){
                return;
            }
            // 循环保存数据到mysql

            for (int userId:users
                 ) {
                UserMoodPraiseRel userMoodPraiseRel = new UserMoodPraiseRel();
                userMoodPraiseRel.setUserId( userId );
                userMoodPraiseRel.setMoodId( moodId );
                userMoodPraiseRelService.save( userMoodPraiseRel );

            }
            log.info( "更新点赞数"+moodId);
            // 跟新点赞数
            Mood mood = moodService.findById( moodId);
            mood.setPraiseNum( mood.getPraiseNum()+redisTemplate.opsForSet().size( String.valueOf( moodId ) ).intValue() );
            moodService.update( mood );

            // 清空缓存
            redisTemplate.delete( String.valueOf( moodId ));

        }
        redisTemplate.delete( PRAISE_HASH_KEY );
    }
}
