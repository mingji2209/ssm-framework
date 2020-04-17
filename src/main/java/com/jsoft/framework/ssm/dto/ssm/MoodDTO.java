package com.jsoft.framework.ssm.dto.ssm;

import com.jsoft.framework.ssm.model.ssm.Mood;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@ToString
public class MoodDTO extends Mood implements Serializable {

    private int id;

    private String content;

    private int userId;

    private String userName;

    private String userAccount;

    private Date publishTime;

    private int praiseNum;

}
