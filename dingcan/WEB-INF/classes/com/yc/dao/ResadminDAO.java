package com.yc.dao;

import java.util.ArrayList;
import java.util.List;

import com.yc.commons.DbHelper;
import com.yc.po.ResadminPO;

public class ResadminDAO {

	DbHelper db = new DbHelper();
	
	/**
	 * 管理员登陆
	 * @param po
	 * @return
	 * @throws Exception
	 */
	public ResadminPO login(ResadminPO po) throws Exception {
		String sql ="select * from master where name=? and passwd=?";
		List<Object> params = new ArrayList<>();
		params.add(po.getName());
		params.add(po.getPasswd());
		List<ResadminPO> list = db.find(sql, params, ResadminPO.class);
		if(null!=list && list.size()>0){
			return list.get(0);
			
		}
		return null;
	}
}
