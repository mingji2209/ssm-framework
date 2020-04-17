package com.jsoft.framework.ssm.dao.ssm;

import com.jsoft.framework.ssm.model.ssm.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    User find(int id);
}
