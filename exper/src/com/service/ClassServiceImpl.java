package com.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mapping.ClassMapper;
import com.pojo.ClassInfo;
@Service
public class ClassServiceImpl {
	@Resource
	private ClassMapper classDao;
	
	public ClassInfo getClassById(int id){
		return this.classDao.selectByPrimaryKey(id);
	}

	
	
	public List<ClassInfo> findClassWhereSql(Map params) {
		return this.classDao.selectByWhere(params);
	}


	
	public Long findClassCountByWhere(Map params) {
		return this.classDao.selectCountByWhere(params);
	}						 


	
	public int inserClass(ClassInfo info) {
		return this.classDao.insert(info);
	}


	
	public int updateClass(ClassInfo info) {
		return this.classDao.updateByPrimaryKey(info);
	}

	
	public int deleteClass(Integer id) {
		return this.classDao.deleteByPrimaryKey(id);
	}



	public void updateClassRest(Integer id) {
		this.classDao.updateRest(id);
	}



	public void cancelClass(Integer id) {
		this.classDao.cancelClass(id);
	}
}
