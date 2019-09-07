package com.yc.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.biz.impl.DingJia;
import com.yc.biz.impl.XiaoLiang;

/**
 * Servlet implementation class XiaoLiangServlet
 */
@WebServlet("/xiaoliang.action")
public class XiaoLiangServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		if ("doXiaoLiang".equals(op)){
			doXiaoLiang(request,response);
		}
	}

	protected void doXiaoLiang(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String caixi = request.getParameter("caixi");
		String leixi = request.getParameter("leixi");
		String cname = request.getParameter("cname");
		String price = request.getParameter("price");
		String pythonfilepath = request.getSession().getServletContext().getRealPath("/");
		XiaoLiang xl = new XiaoLiang();
		String result = xl.xiaoliang(caixi, leixi, cname, price, pythonfilepath);
		System.out.println(result);
		if (result!=null){
			String sscc=result.replace("[", "");
			String scc=sscc.replace("]", "");
			printtoString(response, scc);
		}else{
			printtoString(response, "error");
		}
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
