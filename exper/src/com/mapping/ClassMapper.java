package com.mapping;

import java.util.List;
import java.util.Map;

import com.pojo.ClassInfo;

public interface ClassMapper {
    public int deleteByPrimaryKey(Integer id);

    public int insert(ClassInfo record);

   // public int insertSelective(ClassInfo record);

    public ClassInfo selectByPrimaryKey(Integer id);

   // public int updateByPrimaryKeySelective(ClassInfo record);

    public int updateByPrimaryKey(ClassInfo record);

	public List<ClassInfo> selectByWhere(Map params);

	public Long selectCountByWhere(Map params);

	public void updateRest(Integer id);

	public void cancelClass(Integer id);
}