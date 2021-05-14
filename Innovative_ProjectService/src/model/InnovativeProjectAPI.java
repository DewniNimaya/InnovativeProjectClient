package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




@WebServlet("/InnovativeProjectAPI")
public class InnovativeProjectAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	InnovativeProject iproject = new InnovativeProject();
	String stsMsg = "";   
   
    public InnovativeProjectAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		stsMsg = iproject.uploadProject(request.getParameter("projectName"), request.getParameter("projectPrice"),
				"abc.com" ,request.getParameter("projectType"), request.getParameter("projectDescription"),request.getParameter("quantity"));
		doGet(request, response);
	}

	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map paras = getParasMap(request);
		
		stsMsg = iproject.updateInnovativeProject(paras.get("hidItemIDSave").toString(),paras.get("projectName").toString(), paras.get("projectPrice").toString(),
				"abc.com" ,paras.get("projectType").toString(), paras.get("projectDescription").toString(),paras.get("quantity").toString());
		response.getWriter().write(stsMsg);
	}

	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map paras = getParasMap(request);
		stsMsg = iproject.removeInnnovativeProjects(paras.get("hidItemIDDelete").toString());
		response.getWriter().write(stsMsg);
	}
	
	// Convert request parameters to a Map
		private static Map getParasMap(HttpServletRequest request) {
			Map<String, String> map = new HashMap<String, String>();
			try {
				Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
				String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
				scanner.close();
				String[] params = queryString.split("&");
				for (String param : params) {
					String[] p = param.split("=");
					map.put(p[0], p[1]);
				}
			} catch (Exception e) {
			}
			return map;
		}


}
