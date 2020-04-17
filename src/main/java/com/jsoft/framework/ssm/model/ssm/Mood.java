package com.jsoft.framework.ssm.model.ssm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.util.Date;

@Setter
@Getter
@ToString
public class Mood  {

    private int id;

    private String content;

    private int userId;

    private Date publishTime;

    private int praiseNum;
}
