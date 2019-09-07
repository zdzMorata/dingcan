package com.yc.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.dao.DianCaiDAO;
import com.yc.po.FoodPO;

/**
 * Servlet implementation class DiancaiCaiXi
 */
@WebServlet("/diancai111.action")
public class DiancaiCaiXi extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	DianCaiDAO dao = new DianCaiDAO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		String caixi = request.getParameter("caixi");
		if ("findCaiXi".equals(op)){
			doFindCaiXi(request,response,caixi);
		}
	}

	protected void doFindCaiXi(HttpServletRequest request, HttpServletResponse response,String caixi) throws ServletException, IOException {
		FoodPO po = new FoodPO();
		po.setCaixi(caixi);
		//System.out.println(caixi);
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
