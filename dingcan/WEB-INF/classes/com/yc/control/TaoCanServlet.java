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
import com.yc.dao.TaoCanDAO;
import com.yc.po.FoodPO;
import com.yc.po.TaoCanPO;

/**
 * Servlet implementation class FoodServlet
 */
@WebServlet("/TaoCan.action")
public class TaoCanServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	TaoCanDAO dao = new TaoCanDAO();
    

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op =  request.getParameter("op");
		//System.out.println(op);
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
			//System.out.println("**********");
			int tid = Integer.parseInt(request.getParameter("tid"));
			doDel(request,response,tid);
		}
		if("update".equals(op)){
			int tid = Integer.parseInt(request.getParameter("tid"));
			doUpdate(request, response, tid);
		}
	}

	protected void doFindAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("********");
		try {
			//System.out.println("++++++++++++++++++");
			List<TaoCanPO> list = dao.findAll();
			//System.out.println(list);
			printtoString(response, list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void doFind(HttpServletRequest request, HttpServletResponse response){
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
			List<TaoCanPO> list = dao.findByPage(null, num, size);
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
			
			String tname = su.getRequest().getParameter("tname");
			String cname = su.getRequest().getParameter("cname");
			String tphoto = su.getRequest().getParameter("tphoto");
			int tprice = Integer.parseInt(su.getRequest().getParameter("tprice"));
			String caixi = su.getRequest().getParameter("caixi");
			String yanxi = su.getRequest().getParameter("yanxi");
			TaoCanPO po = new TaoCanPO();
			po.setTname(tname);
			po.setCname(cname);
			po.setTphoto(tphoto);
			po.setTprice(tprice);
			po.setCaixi(caixi);
			po.setYanxi(yanxi);
			int i;
			
			i=dao.addFood(po);
			if (i>0){
				response.sendRedirect("back/packagefind.jsp");
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
	
	
	protected void doDel(HttpServletRequest request, HttpServletResponse response,int tid) throws ServletException, IOException {
		SmartUpload su = new  SmartUpload();
		try {
			//su.initialize(pageContext);
			su.service(request, response);
			su.setTotalMaxFileSize(100000000);
			su.setAllowedFilesList("zip,rar");
			su.setDeniedFilesList("exe,bat,jsp,htm,html,,");
			su.upload();
			TaoCanPO po = new TaoCanPO();
			po.setTid(tid);
			//System.out.println("--------"+tid);
			int i;
			i=dao.del(po);
			if (i>0){
				response.sendRedirect("back/packagefind.jsp");
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
	protected void doUpdate(HttpServletRequest request, HttpServletResponse response,int tid) throws ServletException, IOException {
		SmartUpload su = new  SmartUpload();
		try {
			//su.initialize(pageContext);
			su.service(request, response);
			su.setTotalMaxFileSize(100000000);
			su.setAllowedFilesList("zip,rar");
			su.setDeniedFilesList("exe,bat,jsp,htm,html,,");
			su.upload();
			String tname = su.getRequest().getParameter("tname");
			String cname = su.getRequest().getParameter("cname");
			String tphoto = su.getRequest().getParameter("tphoto");
			int tprice = Integer.parseInt(su.getRequest().getParameter("tprice"));
			String caixi = su.getRequest().getParameter("caixi");
			String yanxi = su.getRequest().getParameter("yanxi");
			TaoCanPO po = new TaoCanPO();
			po.setTname(tname);
			po.setCname(cname);
			po.setTphoto(tphoto);
			po.setTprice(tprice);
			po.setCaixi(caixi);
			po.setYanxi(yanxi);
			po.setTid(tid);
			
			//System.out.println("--------");
			//System.out.println("---"+tid);
			int i;
			i=dao.update(po);
			if (i>0){
				response.sendRedirect("back/packagefind.jsp");
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
