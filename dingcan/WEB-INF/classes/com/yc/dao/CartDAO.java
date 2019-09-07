package com.yc.dao;

import java.util.ArrayList;
import java.util.List;

import com.yc.commons.DbHelper;
import com.yc.po.HistoryPO;
import java.util.Random;

public class CartDAO {
	
	DbHelper db = new DbHelper();

	
	public int addCart(HistoryPO po) throws Exception{
		String sql = "insert into history values(null,null,?,?,?,1,?,?,?)";
		List<Object> params = new ArrayList<Object>();
		Random random = new  Random();
		int s = random.nextInt(1001)+500;
		int ss = random.nextInt(501)+500;
		po.setCprice(s);
		po.setLirun(ss);
		params.add(po.getCid());
		params.add(po.getCprice());
		params.add(po.getZuoshu());
		params.add(po.getLirun());
		params.add(po.getName());
		params.add(po.getPhone());
		System.out.println(po.getCid()+"----"+po.getCprice()+"---"+po.getZuoshu());
		return db.update(sql, params);
	}
}
