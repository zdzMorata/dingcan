package com.yc.dao;

import java.util.ArrayList;
import java.util.List;

import com.yc.commons.DbHelper;
import com.yc.po.TaoCanPO;

public class TaoCanDAO {
	
	DbHelper db = new DbHelper();
	
	
	public List<TaoCanPO> findAll() throws Exception{
		String sql = "select * from taocan";
		return db.find(sql, null, TaoCanPO.class);
	}
	
	public List<TaoCanPO> findYanXI(TaoCanPO po) throws Exception{
		String sql = "select * from taocan where yanxi=?";
		List<Object> params = new ArrayList<Object>();
		params.add(po.getYanxi());
		return db.find(sql, params, TaoCanPO.class);
	}

	public List<TaoCanPO> findByPage(TaoCanPO po,Integer pageNum,Integer pageSize) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("select * from taocan");
		List<Object> params = null;
		if(null!=po){
			params = new ArrayList<Object>();
			
			if(null!=po.getTid()){
				params.add(po.getTid());
				sb.append(" where tid=?");
			}
			if(null!=po.getTname()){
				params.add(po.getTname());
				sb.append(" where tname =? ");
			}
		}
		sb.append(" order by tprice desc ");
		if(null!=pageNum&& null!=pageSize){
			sb.append(" limit "+((pageNum-1)*pageSize)+","+pageSize);
		}
		System.out.println(sb.toString());
		return db.find(sb.toString(), params, TaoCanPO.class);
	}
	
	
	
	public int findByTotal(TaoCanPO po) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(*) from taocan ");
		List<Object> params = null;
		if(po!=null){
			params = new ArrayList<Object>();
			
			if(null!=po.getTid()){
				params.add(po.getTid());
				sb.append(" where tid =? ");
			}
			if(null!=po.getTname()){
				params.add(po.getTname());
				sb.append(" where tname =? ");
			}
		}
		return (int) db.SelectPloymer(sb.toString(), params);
	}
	
	
	public int addFood(TaoCanPO po) throws Exception{
		String sql = "insert into taocan values(null,?,?,?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(po.getTname());
		params.add(po.getCname());
		params.add(po.getTphoto());
		params.add(po.getCaixi());
		params.add(po.getYanxi());
		return db.update(sql, params);
	}
	
	public int update(TaoCanPO po) throws Exception{
		String sql = "update taocan set tname=?,cname=?,tphoto=?,caixi=?,yanxi=? where tid=?";
		List<Object> params=new ArrayList<Object>();
		params.add(po.getTname());
		params.add(po.getCname());
		params.add(po.getTphoto());
		params.add(po.getCaixi());
		params.add(po.getYanxi());
		params.add(po.getTid());
		return db.update(sql, params);
	}
	
	
	public int del(TaoCanPO po) throws Exception{
		String sql = "delete from taocan where tid= ?";
		List<Object> params=new ArrayList<Object>();
		params.add(po.getTid());
		return db.update(sql, params);
	}
}
