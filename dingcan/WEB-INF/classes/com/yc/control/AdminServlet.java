package com.yc.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.dao.ResadminDAO;
import com.yc.po.ResadminPO;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/admin.action")
public class AdminServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	ResadminDAO dao = new ResadminDAO();
    
	
	
	
	
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		System.out.println(request.getRequestURI());
		if("login".equals(op)){
			doLogin(request, response);
		}else if("register".equals(op)){
			doRegister(request, response);
		}else if("findAll".equals(op)){
			doFindAll(request, response);
		}else if("checkName".equals(op)){
			doCheckName(request, response);
		}
	}

	
	
	
	protected void doRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	protected void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("name"));
		ResadminPO po = fromRquesttoObject(request, ResadminPO.class);
		System.out.println(po);
		try {
			ResadminPO admin =dao.login(po);
			if(null==admin){
				printtoString(response, "error");
			}else{
				//将管理员登陆的对象存到session中
				request.getSession().setAttribute("admin", admin);
				printtoString(response, "success");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
	protected void doFindAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	protected void doCheckName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
