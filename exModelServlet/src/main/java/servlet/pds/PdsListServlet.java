package servlet.pds;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.pds.PdsDAO;
import model.pds.PdsDTO;
import model.util.PageIndex;

/**
 * Servlet implementation class PdsListServlet
 */
@WebServlet("/Pds/pds_list.do")
public class PdsListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PdsListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PdsDAO dao = PdsDAO.getInstance();
		
		String search = ""; 
		String key = "";
		String url = "/Pds/pds_list.do";
		
		int totcount = 0; 
		
		if(request.getParameter("key")!=null) {
			key=request.getParameter("key");
			search=request.getParameter("search");
			totcount=dao.PdsCount(search, key);
		}else {
			totcount=dao.PdsCount();
		}
		int nowpage=1;
		int maxlist=10;
		int totpage=1;
		
		if(totcount%maxlist==0) {
			totpage=totcount/maxlist;
		}else {
			totpage=totcount/maxlist+1;
		}
		
		if(request.getParameter("page")!=null) {
			nowpage=Integer.parseInt(request.getParameter("page"));
		}
		
		int startpage = (nowpage-1)*maxlist +1;
		int endpage = nowpage*maxlist;
		int listcount = totcount-((nowpage-1)*maxlist);
		
		List<PdsDTO> list = null;
		if(key.equals("")) {
			list = dao.PdsList(startpage, endpage);
		}else {
			list= dao.PdsList(search, key, startpage, endpage);
		}
		String pageSkip ="";
		if(key.equals("")) {
			pageSkip = PageIndex.pageList(nowpage, totpage, url, "");
		}else {
			pageSkip = PageIndex.pageListHan(nowpage, totpage, url, pageSkip, key);
		}
		request.setAttribute("page", nowpage);
		request.setAttribute("totpage", totpage);
		request.setAttribute("totcount", totcount);
		request.setAttribute("listcount", listcount);
		request.setAttribute("list", list);
		request.setAttribute("pageSkip", pageSkip);
		request.setAttribute("search", search);
		request.setAttribute("key", key);
		
		RequestDispatcher rd = request.getRequestDispatcher("pds_list.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

}
