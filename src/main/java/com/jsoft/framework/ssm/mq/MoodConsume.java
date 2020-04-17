package com.jsoft.framework.ssm.mq;

import com.jsoft.framework.ssm.dto.ssm.MoodDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@Component
@Slf4j
public class MoodConsume implements MessageListener {

    private static final String PRAISE_HASH_KEY = "ssm.mood.id.list.key";

    @Resource
    private RedisTemplate redisTemplate;

    public void onMessage(Message message){
        try {
            MoodDTO moodDTO = (MoodDTO) ((ActiveMQObjectMessage)message).getObject();
            log.info( "消费者处理数据"+moodDTO.toString());
            redisTemplate.opsForSet().add( PRAISE_HASH_KEY,moodDTO.getId() );
            redisTemplate.opsForSet().add( String.valueOf( moodDTO.getId() ),moodDTO.getUserId() );
            log.info( "消费者--->用户 id"+moodDTO.getUserId()+"给说说id"+moodDTO.getId()+"点赞" );
        } catch (JMSException e) {
            log.error( "消费者有逻辑错误" );
            e.printStackTrace();
        }
    }


}
