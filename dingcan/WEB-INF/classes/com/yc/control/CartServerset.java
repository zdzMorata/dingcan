package com.yc.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.dao.CartDAO;
import com.yc.po.HistoryPO;

/**
 * Servlet implementation class CartServerset
 */
@WebServlet("/add.action")
public class CartServerset extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	CartDAO dao = new CartDAO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		if ("addOrder".equals(op)){
			doAddOrder(request,response);
		}
	}

	
	protected void doAddOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String tel = request.getParameter("tel");
		String num = request.getParameter("num");
		String [] cids=request.getParameterValues("ss[]"); 
		//System.out.println(name+"---"+tel+"---"+num+"---"+cids);
		HistoryPO po = new HistoryPO();
		List<String> list = new ArrayList<>();
		for (int i =0;i<cids.length;i++){
			//System.out.println(cids[i]);
			list.add(cids[i]);
		}
		//System.out.println(list);
		String ss = String.join(",", list);
		po.setCid(ss);
		po.setName(name);
		po.setPhone(Integer.parseInt(tel));
		po.setZuoshu(Integer.parseInt(num));
		//System.out.println(ss);
		try {
			int i= dao.addCart(po);
			//System.out.println(i);
			if (i>0){
				printtoString(response, "success");
			}else{
				printtoString(response, "error");
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
