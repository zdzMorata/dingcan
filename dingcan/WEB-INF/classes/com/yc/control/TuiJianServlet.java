package com.yc.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.biz.impl.Apriori_history;
import com.yc.biz.impl.Deal;
import com.yc.dao.DianCaiDAO;
import com.yc.dao.FoodDAO;
import com.yc.po.FoodPO;
import com.yc.po.HistoryPO;

/**
 * Servlet implementation class TuiJianServlet
 */
@WebServlet("/TuiJian.action")
public class TuiJianServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	DianCaiDAO dao = new DianCaiDAO();
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		if ("tuijian".equals(op)){
			doTuiJian(request,response);
		}
		if ("addTuiJianCart".equals(op)){
			doAddTuiJianCart(request,response);
		}
	}
	
	
	protected void doTuiJian(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pythonfilepath = request.getSession().getServletContext().getRealPath("/");
		String [] cids= request.getParameterValues("cids[]");
		List<String> list = new ArrayList<>();
		if (cids!=null){
			for (int i =0;i<cids.length;i++){
				//System.out.println(cids[i]);
				list.add(cids[i]);
			}
			//System.out.println(list);
			String cid = String.join(",", list);
			Deal deal = new Deal();
			String cc = deal.tuijian(cid, pythonfilepath);
			if(cc!=null){
				String sscc=cc.replace("[", "");
				String scc=sscc.replace("]", "");
				String sc=scc.replace(" ", "");
				String [] a= sc.split(",");
				
				List<String> list1 = new ArrayList<>();
				for (int i =0; i<a.length;i++){
					list1.add(a[i]);
				}
				printtoString(response, list1);
			}
		}
	}
	
	
	protected void doAddTuiJianCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FoodPO po = new FoodPO();
		int cid = Integer.parseInt(request.getParameter("cid"));
		//System.out.println(cid);
		po.setCid(cid);
		try {
			List<FoodPO> list = dao.findSelect(po);
			//System.out.println(list);
			if (list!=null && list.size()>0){
				printtoString(response, list);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
