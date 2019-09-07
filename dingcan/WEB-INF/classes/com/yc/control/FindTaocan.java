package com.yc.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.dao.TaoCanDAO;
import com.yc.po.TaoCanPO;

/**
 * Servlet implementation class FindTaocan
 */
@WebServlet("/taocan.action")
public class FindTaocan extends BaseServlet {
	private static final long serialVersionUID = 1L;
	TaoCanDAO dao =new TaoCanDAO();

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		String yanxi = request.getParameter("yanxi");
		if ("findYanXi".equals(op) && yanxi!=null){
			doFindYanxi(request,response,yanxi);
		}
	}

	protected void doFindYanxi(HttpServletRequest request, HttpServletResponse response,String yanxi) throws ServletException, IOException {
		TaoCanPO po =new TaoCanPO();
		po.setYanxi(yanxi);
		try {
			List<TaoCanPO> list= dao.findYanXI(po);
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
