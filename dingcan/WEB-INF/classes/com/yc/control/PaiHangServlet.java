package com.yc.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.biz.impl.Deal;
import com.yc.biz.impl.PaiHang;
import com.yc.dao.DianCaiDAO;
import com.yc.po.FoodPO;

/**
 * Servlet implementation class PaiHangServlet
 */
@WebServlet("/doPaiHang.action")
public class PaiHangServlet extends BaseServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DianCaiDAO dao = new DianCaiDAO();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		if("PaiHang".equals(op)){
			doPaiHang(request,response);
		}
		if("addPaiHangCart".equals(op)){
			doAddPaiHangCart(request,response);
		}
	}

	
	protected void doPaiHang(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pythonfilepath = request.getSession().getServletContext().getRealPath("/");
		List<String> list = new ArrayList<>();
		
		String cid = String.join(",", list);
		PaiHang ph = new PaiHang();
		String cc = ph.paiHang(pythonfilepath);
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
	
	
	protected void doAddPaiHangCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
