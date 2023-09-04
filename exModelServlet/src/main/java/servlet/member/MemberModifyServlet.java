package servlet.member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.member.MemberDAO;
import model.member.MemberDTO;
import model.util.UserSHA256;

/**
 * Servlet implementation class MemberModifyServlet
 */
@WebServlet("/Member/userinfo_modify.do")
public class MemberModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberModifyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("userinfo_modify.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberDAO dao = MemberDAO.getInstance();
		MemberDTO dto = new MemberDTO();
		request.setCharacterEncoding("utf-8");
		
		dto.setName(request.getParameter("name"));
		dto.setUserid(request.getParameter("userid"));
		dto.setGubun(request.getParameter("gubun"));
		dto.setZip(request.getParameter("zip"));
		dto.setAddr1(request.getParameter("addr1"));
		dto.setAddr2(request.getParameter("addr2"));
		dto.setTel(request.getParameter("tel"));
		dto.setEmail(request.getParameter("email"));
		dto.setJob(request.getParameter("job"));
		dto.setIntro(request.getParameter("intro"));
		String favorite="";
		if(request.getParameterValues("fa")!=null){
			String fa[] = request.getParameterValues("fa");
			favorite = fa[0];
			for(int i=1; i<fa.length; i++){
				favorite = favorite +","+ fa[i];
			}
		}
		dto.setFavorite(favorite);
		int row = dao.memberModify(dto);
		
		request.setAttribute("row", row);
		request.setAttribute("dto", dto);
		
		RequestDispatcher rd = request.getRequestDispatcher("userinfo_modify_pro.jsp");
		rd.forward(request, response);
	}

}
