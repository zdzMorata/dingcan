package com.yc.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
import com.yc.dao.FoodDAO;
import com.yc.po.FoodPO;

/**
 * Servlet implementation class FoodServlet
 */
@WebServlet("/resfood.action")
public class FoodServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	FoodDAO dao = new FoodDAO();
    

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op =  request.getParameter("op");
		if("findAll".equals(op)){
			doFindAll(request,response);
		}
		if("find".equals(op)){
			doFind(request,response);
		}
		if("add".equals(op)){
			doAdd(request,response);
		}
		if("del".equals(op)){
			System.out.println("**********");
			int cid = Integer.parseInt(request.getParameter("cid"));
			doDel(request,response,cid);
		}
		if("update".equals(op)){
			int cid = Integer.parseInt(request.getParameter("cid"));
			doUpdate(request, response, cid);
		}
	}

	protected void doFindAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//System.out.println("++++++++++++++++++");
			List<FoodPO> list = dao.findAll();
			//System.out.println(list);
			printtoString(response, list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	protected void doFind(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageNumStr = request.getParameter("page");
		String pageSizeStr =request.getParameter("rows");
		int num = 0;//当前页面数
		int size=0; //每个显示行数
		
		if(null!=pageNumStr && !"".equals(pageNumStr)){
			num =Integer.parseInt(pageNumStr);
		}
		if(null!=pageSizeStr && !"".equals(pageSizeStr)){
			size =Integer.parseInt(pageSizeStr);
		}
		try {
			List<FoodPO> list = dao.findByPage(null, num, size);
			int total =dao.findByTotal(null);
			StringBuffer sb = new StringBuffer();
			//将数据拼接成easyui  datagrid需要的格式
			sb.append("{\"total\":"+total+",\"rows\":");
			Gson gson = new Gson();
			sb.append(gson.toJson(list));
			sb.append("}");
			//System.out.println(sb.toString());  //在控制台输出拼接后的字符串
			printtoString(response, sb.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	protected void doAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SmartUpload su = new  SmartUpload();
		try {
			//su.initialize(pageContext);
			su.service(request, response);
			su.setTotalMaxFileSize(100000000);
			su.setAllowedFilesList("zip,rar");
			su.setDeniedFilesList("exe,bat,jsp,htm,html,,");
			su.upload();
			String cid = su.getRequest().getParameter("cid");
			//System.out.println(cid);
			String caixi = su.getRequest().getParameter("caixi");
			String cname = su.getRequest().getParameter("cname");
			int cprice = Integer.parseInt(su.getRequest().getParameter("cprice"));
			String leixi = su.getRequest().getParameter("leixi");
			int lirun = Integer.parseInt(su.getRequest().getParameter("lirun"));
			String cphoto = su.getRequest().getParameter("cphoto");
			FoodPO po = new FoodPO();
			po.setCaixi(caixi);
			po.setCname(cname);
			po.setCprice(cprice);
			po.setLeix(leixi);
			po.setLirun(lirun);
			po.setCphoto(cphoto);
			int i;
			
			i=dao.addFood(po);
			if (i>0){
				response.sendRedirect("back/foodfind.jsp");
			}else{
				printtoString(response, "error");
			}	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SmartUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	
	protected void doDel(HttpServletRequest request, HttpServletResponse response,int cid) throws ServletException, IOException {
		SmartUpload su = new  SmartUpload();
		try {
			//su.initialize(pageContext);
			su.service(request, response);
			su.setTotalMaxFileSize(100000000);
			su.setAllowedFilesList("zip,rar");
			su.setDeniedFilesList("exe,bat,jsp,htm,html,,");
			su.upload();
			FoodPO po = new FoodPO();
			po.setCid(cid);
			//System.out.println("--------"+cid);
			int i;
			i=dao.del(po);
			if (i>0){
				response.sendRedirect("back/foodfind.jsp");
			}else{
				printtoString(response, "error");
			}	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SmartUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doUpdate(HttpServletRequest request, HttpServletResponse response,int cid) throws ServletException, IOException {
		SmartUpload su = new  SmartUpload();
		try {
			//su.initialize(pageContext);
			su.service(request, response);
			su.setTotalMaxFileSize(100000000);
			su.setAllowedFilesList("zip,rar");
			su.setDeniedFilesList("exe,bat,jsp,htm,html,,");
			su.upload();
			String caixi = su.getRequest().getParameter("caixi");
			String cname = su.getRequest().getParameter("cname");
			int cprice = Integer.parseInt(su.getRequest().getParameter("cprice"));
			String cphoto = su.getRequest().getParameter("cphoto");
			FoodPO po = new FoodPO();
			po.setCid(cid);
			po.setCaixi(caixi);
			po.setCname(cname);
			po.setCprice(cprice);
			po.setCphoto(cphoto);
			//System.out.println("--------");
			//System.out.println("---"+po.toString());
			int i;
			i=dao.update(po);
			if (i>0){
				response.sendRedirect("back/foodfind.jsp");
			}else{
				printtoString(response, "error");
			}	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SmartUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
