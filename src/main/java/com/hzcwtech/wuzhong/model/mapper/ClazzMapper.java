package com.hzcwtech.wuzhong.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hzcwtech.mybatis.Pager;
import com.hzcwtech.wuzhong.model.Clazz;
import com.hzcwtech.wuzhong.model.School;

/**
 * @author Administrator
 *
 */
public interface ClazzMapper {
	
	
	/**获取所有班级
	 * @return
	 */

	public List<Clazz> getClazzList();
	
	/*更新获取班级列表*/
	
	public List<Clazz> updataGetClazzList(Integer classId);
	
	/**根据学校id查询出所有班级
	 * @param schoolId
	 * @return
	 */
	
	public List<Clazz>getClazzListBySchoolId(Integer schoolId);
	
	
	
	
	/**插入班级
	 * @param clazz
	 */
	public void insertClazz(Clazz clazz);
	
	
	/**根据班级Id删除班级
	 * @param id
	 */
	
	public void deleteClazzById(@Param("classId") Integer classId);
	
	
	/**更新班级信息
	 * @param clazz
	 */
	public void updateClazz(Clazz clazz);
	
	
	/**根据班级id获取班级信息
	 * @param id
	 * @return
	 */
	
	public Clazz getClazzByClazzId(Integer id);
	
	
	
	
	
	/**模糊查询班级
	 * @param pager
	 * @param q
	 * @param schoolId
	 * @return
	 */
	public List<School> searchClazzList(@Param("pager") Pager pager,
			@Param("q") String q,@Param("schoolId") Integer schoolId);
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
