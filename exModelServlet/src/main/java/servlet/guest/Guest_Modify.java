package servlet.guest;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.guest.GuestDAO;
import model.guest.GuestDTO;

/**
 * Servlet implementation class Guest_modify
 */
@WebServlet("/Guest/guest_modify")
public class Guest_Modify extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Guest_Modify() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		request.setCharacterEncoding("utf-8");
		GuestDTO dto = new GuestDTO();
		GuestDAO dao = new GuestDAO();
		
		dto= dao.guestSelect(idx);
		
		request.setAttribute("dto", dto);
		
		RequestDispatcher rd = request.getRequestDispatcher("guest_modify.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		GuestDTO dto = new GuestDTO();
		GuestDAO dao = new GuestDAO();
		
		dto.setIdx(Integer.parseInt(request.getParameter("idx")));
		dto.setName(request.getParameter("name"));
		dto.setSubject(request.getParameter("subject"));
		dto.setContents(request.getParameter("contents"));
		
		int row = dao.guestModify(dto);
		
		if(row==1) {
			response.sendRedirect("/Guest/guest_list");
		}else {
			response.sendRedirect("/Guest/guest_modify");
		}
	}

}
