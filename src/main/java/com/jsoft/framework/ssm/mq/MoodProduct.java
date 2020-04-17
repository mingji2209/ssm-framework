package com.jsoft.framework.ssm.mq;

import com.jsoft.framework.ssm.dto.ssm.MoodDTO;
import com.jsoft.framework.ssm.model.ssm.Mood;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Destination;
import java.util.logging.Logger;

/**
 * 生产者
 */

@Component
@Slf4j
public class MoodProduct {

    @Resource
    private JmsTemplate jmsTemplate;

    public void sendMessage(Destination destination, final MoodDTO mood){
        log.info( "生产者--->用户 id"+mood.getUserId()+"给说说id"+mood.getId()+"点赞" );
        jmsTemplate.convertAndSend( destination,mood );
    }


}
