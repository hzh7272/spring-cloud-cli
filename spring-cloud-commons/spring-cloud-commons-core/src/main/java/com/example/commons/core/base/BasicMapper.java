package com.example.commons.core.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MyBatis工具基础数据操作接口
 * @version 1.0.0
 * @author hzh 2018/9/5 下午11:27
 */
public interface BasicMapper<C, I> {

	/**
	 * 保存
	 * @param model 数据库表数据模型
	 */
	void save(C model);

	/**
	 * 批量保存
	 * @param modelList 数据库表数据模型列表
	 */
	void saveBatch(List<C> modelList);

	/**
	 * 根据ID逻辑删除数据
	 * @param id 数据ID
	 */
	void deleteById(@Param("id") I id);

	/**
	 * 根据ID查询数据
	 * @param id 数据ID
	 * @return 返回数据模型
	 */
	C findById(@Param("id") I id);

	/**
	 * 根据ID更新数据
	 * @param model 数据模型
	 */
	void updateById(C model);
}
