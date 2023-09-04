package model.guest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GuestDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	/*
	 * public static Connection getConnection(){ Connection conn = null; try {
	 * Class.forName("oracle.jdbc.driver.OracleDriver"); conn =
	 * DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","track2_12"
	 * ,"1234"); }catch (Exception e) { e.printStackTrace(); } return conn; }
	 */
	private static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "track2_12", "1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	// 메소드 정의
	public void dbTest() {
		Connection conn = getConnection();
		System.out.println("conn : " + conn);
	}

	// 전체게시글 카운트 메소드
	public int guestCount() {
		// Connection conn = null;
		// PreparedStatement pstmt = null;
		// ResultSet rs = null;
		int row = 0;// 리턴타입
		String query = "select count(*) from tbl_guest";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				row = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return row;
	}

	// 검색기능 추가 전체게시글 카운트 메소드
	public int guestCount(String search, String key) {
		int row = 0;// 리턴타입
//			String query="select count(*) from tbl_guest where ? like ?";//불가능
		String query = "select count(*) from tbl_guest where " + search + " like ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%" + key + "%");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				row = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return row;
	}
	// 전체게시글 검색 반환(전체목록)

	public List<GuestDTO> guestList() {
		// Connection conn = null;
		// PreparedStatement pstmt = null;
		// ResultSet rs = null;
		List<GuestDTO> list = new ArrayList<>();// 리턴타입
		String query = "select * from tbl_guest order by idx desc";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				GuestDTO dto = new GuestDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setName(rs.getString("name"));
				dto.setSubject(rs.getString("subject"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setReadcnt(rs.getInt("readcnt"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	// 검색기능 추가 게시글 검색 반환(전체목록)

	public List<GuestDTO> guestList(String search, String key) {
		List<GuestDTO> list = new ArrayList<>();// 리턴타입
		String query = "select * from tbl_guest where " + search + " like ? order by idx desc";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%" + key + "%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				GuestDTO dto = new GuestDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setName(rs.getString("name"));
				dto.setSubject(rs.getString("subject"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setReadcnt(rs.getInt("readcnt"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	// 전체게시글 검색 반환(전체목록) - 페이징 처리 추가

	public List<GuestDTO> guestList(int pagestart, int endpage) {
		List<GuestDTO> list = new ArrayList<>();// 리턴타입
		String query = "select idx,name,subject,readcnt,regdate from\r\n"
				+ "    (select rownum rm, idx, name,subject,readcnt,regdate from tbl_guest where rownum<=? order by idx desc)\r\n"
				+ "        where rm>=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, endpage);
			pstmt.setInt(2, pagestart);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				GuestDTO dto = new GuestDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setName(rs.getString("name"));
				dto.setSubject(rs.getString("subject"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setReadcnt(rs.getInt("readcnt"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	// 전체게시글 검색 반환(전체목록) - (검색기능 + 페이징 처리 추가)

	public List<GuestDTO> guestList(String search,String key,int pagestart, int endpage) {
		List<GuestDTO> list = new ArrayList<>();// 리턴타입
		String query = "select idx,name,subject,readcnt,regdate from\r\n"
				+ "    (select rownum rm, idx, name,subject,readcnt,regdate from tbl_guest where "+search+" like ? and rownum<=? order by idx desc)\r\n"
				+ "        where "+search+" like ? and rm>=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%"+key+"%");
			pstmt.setInt(2, endpage);
			pstmt.setString(3, "%"+key+"%");
			pstmt.setInt(4, pagestart);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				GuestDTO dto = new GuestDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setName(rs.getString("name"));
				dto.setSubject(rs.getString("subject"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setReadcnt(rs.getInt("readcnt"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	// 조회수 증가 메소드
	public void guestHits(int idx) {
		// Connection conn = null;
		// PreparedStatement pstmt = null;
		// ResultSet rs = null;
		String query = "update tbl_guest set readcnt = readcnt+1 where idx=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 특정글 검색
	public GuestDTO guestSelect(int idx) {
		// Connection conn = null;
		// PreparedStatement pstmt = null;
		// ResultSet rs = null;
		GuestDTO dto = new GuestDTO();// 리턴타입
		String query = "select * from tbl_guest where idx=?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto.setIdx(rs.getInt("idx"));
				dto.setName(rs.getString("name"));
				dto.setContents(rs.getString("contents"));
				dto.setSubject(rs.getString("subject"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setReadcnt(rs.getInt("readcnt"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	// 글 등록
	public int guestWrite(GuestDTO dto) {
		// Connection conn = null;
		// PreparedStatement pstmt = null;
		// ResultSet rs = null;
		int row = 0;// 리턴타입
		String query = "insert into tbl_guest(idx,name,subject,contents) values(tbl_guest_seq_idx.nextval,?,?,?)";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getContents());
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return row;
	}

	// 게시물 수정
	public int guestModify(GuestDTO dto) {
		// Connection conn = null;
		// PreparedStatement pstmt = null;
		// ResultSet rs = null;
		String query = "update tbl_guest set name=?, subject=?,contents=? where idx=?";
		int row = 0;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getContents());
			pstmt.setInt(4, dto.getIdx());
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return row;
	}

	// 게시물 삭제
	public int guestDelete(int idx) {
		// Connection conn = null;
		// PreparedStatement pstmt = null;
		// ResultSet rs = null;
		String query = "delete from tbl_guest where idx=?";
		int row = 0;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, idx);
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return row;
	}
}
