package controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;

public class BaseController {
	private HibernateTemplate ht;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		ht=new HibernateTemplate(sessionFactory);
	}
	protected void delete(Object obj)
	{
		ht.delete(obj);
	}
	/**
	 * 获取查询的第一个对象
	 * @param queryString
	 * @param objs
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected Object getFirst(String queryString, Object... objs)
	{
		
		List list=find(queryString,objs);
		if(list.size()>0)
			return list.get(0);
		else
			return null;
	}
	
	@SuppressWarnings("rawtypes")
	protected List find(String queryString, Object... objs)
	{
		
		List list=this.ht.find(queryString,objs);

		if(list==null)
			return new ArrayList();
		else
			return list;
	}
	
	@SuppressWarnings("rawtypes")
	protected List findTopN(int n,String queryString, Object... objs)
	{
		List list=find(queryString,objs);
		int toIndex=n>list.size()?list.size():n;
		return list.subList(0, toIndex);
	}
	
	protected <T> T get(Class<T> entityClass,Serializable id)
	{
		return ht.get(entityClass, id);
	}
	
	protected void save(Object obj)
	{
		ht.save(obj);
	}
	
	protected void update(Object obj)
	{
		ht.update(obj);
	}
	
	protected void bulkUpdate(String queryString,Object...values)
	{
		ht.bulkUpdate(queryString, values);
	}
}
