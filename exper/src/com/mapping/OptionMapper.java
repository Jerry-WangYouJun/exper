package com.mapping;

import java.util.List;
import java.util.Map;

import com.pojo.OptionInfo;

public interface OptionMapper {
    public int deleteByPrimaryKey(Integer id);

    public int insert(OptionInfo record);

   // public int insertSelective(OptionInfo record);

    public OptionInfo selectByPrimaryKey(Integer id);

   // public int updateByPrimaryKeySelective(OptionInfo record);

    public int updateByPrimaryKey(OptionInfo record);

	public List<OptionInfo> selectByWhere(Map params);

	public Long selectCountByWhere(Map params);
}