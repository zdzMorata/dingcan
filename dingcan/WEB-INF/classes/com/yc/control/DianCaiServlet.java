package com.yc.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.SmartUpload;
import com.yc.dao.DianCaiDAO;
import com.yc.po.FoodPO;

/**
 * Servlet implementation class DianCai
 */
@WebServlet("/diancai.action")
public class DianCaiServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	DianCaiDAO dao = new DianCaiDAO();
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op= request.getParameter("op");
		String leix = request.getParameter("leix");
		String caixi=request.getParameter("caixi");
		//System.out.println(request.getRequestURI());
		if ("findLeiX".equals(op) && leix!=null){
			doFindLeiX(request,response,leix);
		}
		
		if ("addCart".equals(op)){
			doAddCart(request, response);
		}
		if ("selectFood".equals(op)){
			doSelectFood(request,response);
		}
	}

	
	protected void doFindLeiX(HttpServletRequest request, HttpServletResponse response,String leix) throws ServletException, IOException {
		FoodPO po = new FoodPO();
		po.setLeix(leix);
		try {
			List<FoodPO> list = dao.findSelect(po);
			if (list!=null && list.size()>0){
				printtoString(response, list);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	protected void doAddCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	
	
	protected void doSelectFood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cname = request.getParameter("cname");
		//System.out.println(cname);
		FoodPO po = new FoodPO();
		try {
			if (cname==null){
				List<FoodPO> list = dao.findSelect(null);
			}else{
				po.setCname(cname);
				List<FoodPO> list = dao.findSelect(po);
				if (list!=null && list.size()>0){
					printtoString(response, list);
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
