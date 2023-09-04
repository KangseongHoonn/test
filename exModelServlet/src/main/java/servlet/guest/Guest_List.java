package servlet.guest;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.guest.GuestDAO;
import model.guest.GuestDTO;
import model.util.PageIndex;

/**
 * Servlet implementation class Guest_List
 */
@WebServlet("/Guest/guest_list")
public class Guest_List extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Guest_List() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		GuestDAO dao = new GuestDAO();
		
		String search="", key="";
		String url="guest_list.jsp", addtag="";
		int totcount = 0; //총 글수
		List<GuestDTO> list = null;  
		
		if(request.getParameter("key")!=null){//검색을 한 경우
			key = request.getParameter("key");
			search = request.getParameter("search");
			
			totcount = dao.guestCount(search, key);
			
		}else{
			totcount = dao.guestCount();
		}
		//페이지 처리
		int nowpage=1;//현재 페이지
		int maxlist=10;//페이지당 글수
		int totpage=1;//총페이지
		
		if(totcount % maxlist == 0)
			totpage = totcount / maxlist;
		else
			totpage = totcount / maxlist + 1;
		if(totpage==0) totpage=1;
		if(request.getParameter("page")!=null){
			nowpage=Integer.parseInt(request.getParameter("page"));
		}
		
		int pagestart = (nowpage-1) * maxlist +1;
		int endpage = nowpage * maxlist;
		int listcount = totcount-((nowpage-1)*maxlist);//가상번호 출력용
		
		// 게시글 목록 불러오기
		if(key.equals("")){
			list = dao.guestList(pagestart, endpage);
		}else{
			list = dao.guestList(search, key, pagestart, endpage);
		}
		
		//페이지 인덱싱 처리
		String pageSkip = "";
		if(key.equals("")){
			pageSkip=PageIndex.pageList(nowpage, totpage, url, addtag);
		}else{
			pageSkip=PageIndex.pageListHan(nowpage, totpage, url, search, key);
		}
		
		request.setAttribute("totcount", totcount);
		request.setAttribute("page", nowpage);
		request.setAttribute("listcount", listcount);
		request.setAttribute("list", list);
		request.setAttribute("pageSkip", pageSkip);
		request.setAttribute("key", key);
		request.setAttribute("search", search);
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("guest_list.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
