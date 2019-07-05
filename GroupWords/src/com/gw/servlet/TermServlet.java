package com.gw.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gw.service.TermSerice;

/**
 * 组词的Servlet
 */
public class TermServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String g_effectChar = "";//存放有效字符
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//获取session中method的值
		String method = request.getParameter("method");
		
		//根据不同的method值执行不同的函数
		if("randchar".equals(method)){
			RandChar(request,response);
		} else if("inputchar".equals(method)){
			InputChar(request,response);
		} else if("sortchar".equals(method)){
			SortChar(request,response);
		}
	}

	/**
	 * 手动输入
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void InputChar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		g_effectChar = request.getParameter("effectchar");
		
		SortChar(request, response);
	}

	/**
	 * 获取随机的汉字
	 * @param request
	 * @param response 
	 * @throws ServletException 
	 * @throws IOException 
	 */
	private void RandChar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int size = Integer.parseInt(request.getParameter("sizeselect"));
		
		//声明Servlet对象，调用TermServlet中的方法
		TermSerice termSerice = new TermSerice();
		
		g_effectChar = "";
		
		//加载原始汉字语料
		termSerice.LoadChar(request, response);

		for (int i=0; i<size; i++) {
			g_effectChar += "\0";
		}
		
		//加载有效词语
		g_effectChar = termSerice.Extract(g_effectChar.toCharArray());
		
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(g_effectChar);
	}

	/**
	 * 随机产生的数据进行排序
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void SortChar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<String> list = new ArrayList<String>();
		
		TermSerice termSerice = new TermSerice();
		
		list = termSerice.SortChar(request, response, g_effectChar);
		
		//将集合加入request域中
		request.setAttribute("list", list);
		request.setAttribute("listtwosize", Integer.parseInt(list.get(list.size()-3)));
		request.setAttribute("listthreesize", Integer.parseInt(list.get(list.size()-2)));
		request.setAttribute("listfoursize", Integer.parseInt(list.get(list.size()-1)));
		
		//重新加载页面
		request.getRequestDispatcher("/pages/termcontent.jsp").forward(request, response);
		
		list.clear();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
