package com.yc.commons;

import com.yc.biz.impl.DingJia;
import com.yc.control.BaseServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PriceServlet
 */
@WebServlet("/price.action")
public class PriceServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		if ("doPrice".equals(op)){
			doPrice(request,response);
		}
	}

	protected void doPrice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String caixi = request.getParameter("caixi");
		String leixi = request.getParameter("leixi");
		String lirun = request.getParameter("lirun");
		String pythonfilepath = request.getSession().getServletContext().getRealPath("/");
		DingJia dj = new DingJia();
		String result = dj.dingjia(caixi, leixi, lirun, pythonfilepath); 
		System.out.println(result);
		if (result!=null){
			printtoString(response, result);
		}else{
			printtoString(response, "error");
		}
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
