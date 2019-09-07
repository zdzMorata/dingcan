package com.yc.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yc.commons.DbHelper;
import com.yc.po.FoodPO;

public class DianCaiDAO {
	DbHelper db = new DbHelper();

	
	public List<FoodPO> findSelect(FoodPO po) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("select * from caidan ");
		//System.out.println(po);
		List<Object> params= null;
		if (null!=po){
			params = new ArrayList<>();
			if (null!=po.getCname()){
				params.add(po.getCname());
				sb.append(" where cname=?");
			}
			if (null!=po.getLeix()){
				params.add(po.getLeix());
				sb.append(" where leix=?");
			}
			if (null!=po.getCid()){
				params.add(po.getCid());
				sb.append(" where cid=?");
			}
			if (null!=po.getCaixi()){
				params.add(po.getCaixi());
				sb.append(" where caixi=?");
			}
		}
		sb.append(" order by lirun desc");
		//System.out.println(sb);
		return db.find(sb.toString(), params, FoodPO.class);
	}
	
	
	
	public List<Map<String, Object>> findSelect1(FoodPO po) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("select * from caidan ");
		//System.out.println(po);
		List<Object> params= null;
		if (null!=po){
			params = new ArrayList<>();
			if (null!=po.getCname()){
				params.add(po.getCname());
				sb.append(" where cname=?");
			}
			if (null!=po.getLeix()){
				params.add(po.getLeix());
				sb.append(" where leix=?");
			}
			if (null!=po.getCid()){
				params.add(po.getCid());
				sb.append(" where cid=?");
			}
			if (null!=po.getCaixi()){
				params.add(po.getCaixi());
				sb.append(" where caixi=?");
			}
		}
		sb.append(" order by lirun desc");
		//System.out.println(sb);
		return db.selecTMultiObject(sb.toString(), params);
	}
	
}
