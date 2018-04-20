package com.mapping;

import java.util.List;
import java.util.Map;

import com.pojo.User;

public interface UserMapper {
    public int deleteByPrimaryKey(Integer id);

    public int insert(User record);

    public int insertSelective(User record);

    public User selectByPrimaryKey(Integer id);

    public int updateByPrimaryKeySelective(User record);

    public int updateByPrimaryKey(User record);
    /**
     * 根据用户名和密码查找用户
     * @param user
     * @return
     */
    public User selectByUserNoAndPwd(User user);

	public List<User> selectUserByWhere(Map params);

	public Long selectUserCountByWhere(Map params);
}