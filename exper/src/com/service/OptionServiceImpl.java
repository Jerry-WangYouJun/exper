package com.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mapping.OptionMapper;
import com.pojo.OptionInfo;
@Service
public class OptionServiceImpl {
	@Resource
	private OptionMapper optionDao;
	
	public OptionInfo getOptionById(int id){
		return this.optionDao.selectByPrimaryKey(id);
	}

	
	
	public List<OptionInfo> findOptionWhereSql(Map params) {
		return this.optionDao.selectByWhere(params);
	}


	
	public Long findCountByWhere(Map params) {
		return this.optionDao.selectCountByWhere(params);
	}						 


	
	public int insert(OptionInfo info) {
		return this.optionDao.insert(info);
	}


	
	public int update(OptionInfo info) {
		return this.optionDao.updateByPrimaryKey(info);
	}

	
	public int delete(Integer id) {
		return this.optionDao.deleteByPrimaryKey(id);
	}
}
