package com.yc.dao;

import java.util.ArrayList;
import java.util.List;

import com.yc.commons.DbHelper;
import com.yc.po.FoodPO;

public class FoodDAO {
	
	DbHelper db = new DbHelper();
	
	public List<FoodPO> findAll() throws Exception{
		String sql = "select * from caidan";
		return db.find(sql, null, FoodPO.class);
	}

	public List<FoodPO> findByPage(FoodPO po,Integer pageNum,Integer pageSize) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("select * from caidan");
		List<Object> params = null;
		if(null!=po){
			params = new ArrayList<Object>();
			
			if(null!=po.getCid()){
				params.add(po.getCid());
				sb.append(" where cid=?");
			}
			if(null!=po.getCname()){
				params.add(po.getCname());
				sb.append(" where cname =? ");
			}
		}
		sb.append(" order by lirun desc ");
		if(null!=pageNum&& null!=pageSize){
			sb.append(" limit "+((pageNum-1)*pageSize)+","+pageSize);
		}
		System.out.println(sb.toString());
		return db.find(sb.toString(), params, FoodPO.class);
	}
	
	
	
	public int findByTotal(FoodPO po) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(*) from caidan ");
		List<Object> params = null;
		if(po!=null){
			params = new ArrayList<Object>();
			
			if(null!=po.getCid()){
				params.add(po.getCid());
				sb.append(" where cid =? ");
			}
			if(null!=po.getCname()){
				params.add(po.getCname());
				sb.append(" where cname =? ");
			}
		}
		return (int) db.SelectPloymer(sb.toString(), params);
	}
	
	
	
	
	public int addFood(FoodPO po) throws Exception{
		String sql = "insert into caidan values(null,?,?,?,?,0,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(po.getCaixi());
		params.add(po.getCname());
		params.add(po.getCprice());
		params.add(po.getLeix());
		params.add(po.getLirun());
		params.add(po.getCphoto());
		return db.update(sql, params);
	}
	
	public int update(FoodPO po) throws Exception{
		String sql = "update caidan set caixi=?,cname=?,cprice=?,cphoto=? where cid=?";
		List<Object> params=new ArrayList<Object>();
		params.add(po.getCaixi());
		params.add(po.getCname());
		params.add(po.getCprice());
		params.add(po.getCphoto());
		params.add(po.getCid());
		return db.update(sql, params);
	}
	
	
	public int del(FoodPO po) throws Exception{
		String sql = "delete from caidan where cid= ?";
		List<Object> params=new ArrayList<Object>();
		params.add(po.getCid());
		return db.update(sql, params);
	}
}
