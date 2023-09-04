package model.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.util.DBManager;

public class MemberDAO {
	private MemberDAO() {
	}

	private static MemberDAO instance = new MemberDAO();

	public static MemberDAO getInstance() {
		return instance;
	}

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	// 회원목록 (번호 ID 이름 전화번호 등록일자 최근접속일)
	public List<MemberDTO> memberList() {
		List<MemberDTO> list = new ArrayList<>();
		String query = "select * from tbl_member order by first_time desc";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setUserid(rs.getString("userid"));
				dto.setName(rs.getString("name"));
				dto.setTel(rs.getString("tel"));
				dto.setFirsttime(rs.getString("first_time"));
				dto.setLasttime(rs.getString("last_time"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}

	// 검색후 회원목록 (번호 ID 이름 전화번호 등록일자 최근접속일)
	public List<MemberDTO> memberList(String search, String key) {
		List<MemberDTO> list = new ArrayList<>();
		String query = "select * from tbl_member where " + search + " like ? order by first_time desc";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%" + key + "%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setUserid(rs.getString("userid"));
				dto.setName(rs.getString("name"));
				dto.setTel(rs.getString("tel"));
				dto.setFirsttime(rs.getString("first_time"));
				dto.setLasttime(rs.getString("last_time"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}

	// 페이지인덱싱 회원목록 (번호 ID 이름 전화번호 등록일자 최근접속일)
	public List<MemberDTO> memberList(int pagestart, int endpage) {
		List<MemberDTO> list = new ArrayList<>();
		String query = "select X.* from(\r\n" 
				+ "   	 select rownum rnum, A.* from(\r\n"
				+ "        select * from tbl_member order by first_time desc) A\r\n"
				+ "            where rownum <=?) X where X.rnum>=?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, endpage);
			pstmt.setInt(2, pagestart);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setUserid(rs.getString("userid"));
				dto.setName(rs.getString("name"));
				dto.setTel(rs.getString("tel"));
				dto.setFirsttime(rs.getString("first_time"));
				dto.setLasttime(rs.getString("last_time"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}

	// 페이지인덱싱 검색후 회원목록 (번호 ID 이름 전화번호 등록일자 최근접속일)
	public List<MemberDTO> memberList(String search, String key, int pagestart, int endpage) {
		List<MemberDTO> list = new ArrayList<>();
		String query = "select X.* from(\r\n" 
				+ "   	 select rownum rnum, A.* from(\r\n"
				+ "        select * from tbl_member order by first_time desc) A\r\n"
				+ "            where "+search+" like ? and rownum <=?) X where "+search+" like ? and X.rnum>=?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%" + key + "%");
			pstmt.setInt(2, endpage);
			pstmt.setString(3, "%" + key + "%");
			pstmt.setInt(4, pagestart);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setUserid(rs.getString("userid"));
				dto.setName(rs.getString("name"));
				dto.setTel(rs.getString("tel"));
				dto.setFirsttime(rs.getString("first_time"));
				dto.setLasttime(rs.getString("last_time"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}

	// 중복체크
	public int idcheck(String userid) {
		int row = 0;
		String query = "select count(*) from tbl_member where userid=?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				row = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}

	// 회원정보 가져오기
	public MemberDTO memberSelect(String userid) {
		MemberDTO dto = new MemberDTO();
		String query = "select * from tbl_member where userid=?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto.setName(rs.getString("name"));
				dto.setUserid(rs.getString("userid"));
				dto.setPasswd(rs.getString("passwd"));
				dto.setGubun(rs.getString("gubun"));
				dto.setZip(rs.getString("zip"));
				dto.setAddr1(rs.getString("addr1"));
				dto.setAddr2(rs.getString("addr2"));
				dto.setTel(rs.getString("tel"));
				dto.setEmail(rs.getString("email"));
				dto.setFavorite(rs.getString("favorite"));
				dto.setJob(rs.getString("job"));
				dto.setIntro(rs.getString("intro"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return dto;
	}
	// 회원정보 수정

	public int memberModify(MemberDTO dto) {
		int row = 0;
		String query = "update tbl_member set gubun=?,zip=?,addr1=?,addr2=?,tel=?,email=?,job=?,intro=?,favorite=? where userid=?";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, dto.getGubun());
			pstmt.setString(2, dto.getZip());
			pstmt.setString(3, dto.getAddr1());
			pstmt.setString(4, dto.getAddr2());
			pstmt.setString(5, dto.getTel());
			pstmt.setString(6, dto.getEmail());
			pstmt.setString(7, dto.getJob());
			pstmt.setString(8, dto.getIntro());
			pstmt.setString(9, dto.getFavorite());
			pstmt.setString(10, dto.getUserid());
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}

	// 회원정보 등록
	public int memberInsert(MemberDTO dto) {
		int row = 0;
		String query = "insert into tbl_member(name,userid,passwd,gubun,zip,addr1,addr2,tel,email,job,intro,favorite) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getUserid());
			pstmt.setString(3, dto.getPasswd());
			pstmt.setString(4, dto.getGubun());
			pstmt.setString(5, dto.getZip());
			pstmt.setString(6, dto.getAddr1());
			pstmt.setString(7, dto.getAddr2());
			pstmt.setString(8, dto.getTel());
			pstmt.setString(9, dto.getEmail());
			pstmt.setString(10, dto.getJob());
			pstmt.setString(11, dto.getIntro());
			pstmt.setString(12, dto.getFavorite());
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}
	//로그인 체크
	public int memberLogin(String userid, String passwd)  {

		String query = "select passwd from tbl_member where userid = ?";
		int row=0;

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,userid);		
			rs = pstmt.executeQuery();
			if(rs.next()){
				String dbpass = rs.getString("passwd");
				if(dbpass.equals(passwd)){  //로그인에 성공하면 최근접속일자 지정
					query = "update tbl_member set last_time = sysdate where userid = ?";
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1,userid);		
					pstmt.executeUpdate();
					row = 1;
				}else{ //비밀번호가 다른 경우
					row = 0;
				}
			}else{  //아이디가 없는 경우
				row = -1;
			}
		
		} catch(SQLException e)	{
			e.printStackTrace();
		} finally	{
			DBManager.close(conn, pstmt,rs);
		}
		return row;
	}
	//특정ID 검색
	public MemberDTO MemberSelect(String userid) {
		String query = "";
		MemberDTO dto = new MemberDTO();

		try {
			conn = DBManager.getConnection();
			query = "select * from tbl_member where userid = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,userid);		
			rs = pstmt.executeQuery();
			if(rs.next()){
				dto.setUserid(rs.getString("userid"));
				dto.setName(rs.getString("name"));
				dto.setPasswd(rs.getString("passwd"));	
				dto.setZip(rs.getString("zip"));	
				dto.setAddr1(rs.getString("addr1"));	
				dto.setAddr2(rs.getString("addr2"));	
				dto.setGubun(rs.getString("gubun"));
				dto.setTel(rs.getString("tel"));	
				dto.setEmail(rs.getString("email"));	
				dto.setJob(rs.getString("job"));	
				dto.setIntro(rs.getString("intro"));	
				dto.setFavorite(rs.getString("favorite"));	
				dto.setFirsttime(rs.getString("first_time"));	
				dto.setLasttime(rs.getString("last_time"));	
			}
		} catch(SQLException e)	{
			e.printStackTrace();
		} finally	{
			DBManager.close(conn, pstmt,rs);
		}
		return dto;
	}
}
