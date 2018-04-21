package com.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mapping.ParentClassMapper;
import com.pojo.ParentClassInfo;
@Service
public class ParentClassServiceImpl {
	@Resource
	private ParentClassMapper classDao;
	
	public ParentClassInfo getClassById(int id){
		return this.classDao.selectByPrimaryKey(id);
	}

	
	
	public List<ParentClassInfo> findClassWhereSql(Map params) {
		return this.classDao.selectByWhere(params);
	}


	
	public Long findClassCountByWhere(Map params) {
		return this.classDao.selectCountByWhere(params);
	}						 


	
	public int inserClass(ParentClassInfo info) {
		return this.classDao.insert(info);
	}


	
	public int updateClass(ParentClassInfo info) {
		return this.classDao.updateByPrimaryKey(info);
	}

	
	public int deleteClass(Integer id) {
		return this.classDao.deleteByPrimaryKey(id);
	}

}
