package com.mapping;

import java.util.List;
import java.util.Map;

import com.pojo.ClassInfo;
import com.pojo.ParentClassInfo;

public interface ParentClassMapper {
    public int deleteByPrimaryKey(Integer id);

    public int insert(ParentClassInfo record);

    public ParentClassInfo selectByPrimaryKey(Integer id);

    public int updateByPrimaryKey(ParentClassInfo record);

	public List<ParentClassInfo> selectByWhere(Map params);

	public Long selectCountByWhere(Map params);

}