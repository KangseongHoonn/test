package model.board;

import java.sql.*;
import java.util.*;

import model.util.DBManager;

public class BoardDAO {
	// 싱글톤
	private BoardDAO() {
	}

	private static BoardDAO instance = new BoardDAO();// 자기자신의 객체를 생성

	public static BoardDAO getInstance() {
		return instance;
	}

	// DB 관련
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	// 메소드 정의

	// 게시판 글 총수 계산
	public int boardCount() {
		// 리턴타입
		int row = 0;
		// 쿼리
		String query = "select count(*) from tbl_board";

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
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
	// 검색후 게시판 글 총수 계산
		public int boardCount(String search, String key) {
			// 리턴타입
			int row = 0;
			// 쿼리
			String query = "select count(*) from tbl_board where "+search+" like ?";
//			String query = "select count(*) from tbl_board where "+search+" like ? '%" + key +"%'";
			try {
				conn = DBManager.getConnection();
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%"+key+"%");
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

	// 게시판 전체 글목록 가져오기
	public List<BoardDTO> boardList() {
		// 리턴타입
		List<BoardDTO> list = new ArrayList<>();
		// 쿼리
		String query = "select * from tbl_board order by idx desc";

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			BoardDTO dto = null;
			while (rs.next()) {
				dto = new BoardDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setSubject(rs.getString("subject"));
				dto.setName(rs.getString("name"));
				dto.setEmail(rs.getString("email"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setReadcnt(rs.getInt("readcnt"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}
	// 검색기능 추가 게시판 전체 글목록 가져오기
			public List<BoardDTO> boardList(String search, String key) {
				// 리턴타입
				List<BoardDTO> list = new ArrayList<>();
				// 쿼리
				String query = "select * from tbl_board where "+search+" like ? order by idx desc";			

				try {
					conn = DBManager.getConnection();
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1, "%" + key + "%");
					rs = pstmt.executeQuery();
					BoardDTO dto = null;
					while (rs.next()) {
						dto = new BoardDTO();
						dto.setIdx(rs.getInt("idx"));
						dto.setSubject(rs.getString("subject"));
						dto.setEmail(rs.getString("email"));
						dto.setName(rs.getString("name"));
						dto.setRegdate(rs.getString("regdate"));
						dto.setReadcnt(rs.getInt("readcnt"));
						list.add(dto);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					DBManager.close(conn, pstmt, rs);
				}
				return list;
			}
	// 게시판 글목록 가져오기(페이지 인덱싱)
		public List<BoardDTO> boardList(int pagestart,int endpage) {
			// 리턴타입
			List<BoardDTO> list = new ArrayList<>();
			// 쿼리
			String query = "select X.* from(\r\n"
					+ "    select rownum rnum, A.* from(\r\n"
					+ "        select * from tbl_board order by idx desc) A\r\n"
					+ "            where rownum <=?) X where X.rnum>=?";

			try {
				conn = DBManager.getConnection();
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, endpage);
				pstmt.setInt(2, pagestart);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					BoardDTO dto = new BoardDTO();
					dto.setIdx(rs.getInt("idx"));
					dto.setSubject(rs.getString("subject"));
					dto.setName(rs.getString("name"));
					dto.setEmail(rs.getString("email"));
					dto.setRegdate(rs.getString("regdate"));
					dto.setReadcnt(rs.getInt("readcnt"));
					list.add(dto);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DBManager.close(conn, pstmt, rs);
			}
			return list;
		}
		//게시판 글목록 가져오기(페이지 인덱싱+ 검색기능 추가)
		public List<BoardDTO> boardList(String search,String key,int pagestart,int endpage) {
			// 리턴타입
			List<BoardDTO> list = new ArrayList<>();
			// 쿼리
			String query = "select X.* from(\r\n"
					+ "    select rownum rnum, A.* from(\r\n"
					+ "        select * from tbl_board order by idx desc) A\r\n"
					+ "            where "+search+" like ? and rownum <=?) X where "+search+" like ? and X.rnum>=?";

			try {
				conn = DBManager.getConnection();
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%"+key+"%");
				pstmt.setInt(2, endpage);
				pstmt.setString(3, "%"+key+"%");
				pstmt.setInt(4, pagestart);
				rs = pstmt.executeQuery();
				BoardDTO dto = null;
				while (rs.next()) {
					dto = new BoardDTO();
					dto.setIdx(rs.getInt("idx"));
					dto.setSubject(rs.getString("subject"));
					dto.setName(rs.getString("name"));
					dto.setRegdate(rs.getString("regdate"));
					dto.setEmail(rs.getString("email"));
					dto.setReadcnt(rs.getInt("readcnt"));
					list.add(dto);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DBManager.close(conn, pstmt, rs);
			}
			return list;
		}
	

	// 게시판 글쓰기
	public int boardWrite(BoardDTO dto) {
		// 리턴타입
		int row = 0;
		// 쿼리
		String query = "INSERT INTO tbl_board(idx,email,name, subject,contents, pass) VALUES(tbl_board_seq_idx.nextval,?,?,?,?,?)";

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, dto.getEmail());
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getSubject());
			pstmt.setString(4, dto.getContents());
			pstmt.setString(5, dto.getPass());
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		return row;
	}

	// 글내용 가져오기
	public BoardDTO boardView(int idx) {
		// 리턴타입
		BoardDTO dto = new BoardDTO();
		// 쿼리
		String query = "select * from tbl_board where idx=?";

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto.setIdx(rs.getInt("idx"));
				dto.setSubject(rs.getString("subject"));
				dto.setName(rs.getString("name"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setReadcnt(rs.getInt("readcnt"));
				dto.setContents(rs.getString("contents"));
				dto.setPass(rs.getString("pass"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return dto;
	}

	// 조회수 증가
	public void boardReadcnt(int idx) {
		// 쿼리
		String query = "update tbl_board set readcnt=readcnt+1 where idx=?";

		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
	}

	// 글 수정
	public int boardUpdate(BoardDTO dto) {
		// 쿼리
		String query = "update tbl_board set email=?, subject=?, contents=? where idx=?";
		int row=0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, dto.getEmail());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getContents());
			pstmt.setInt(4, dto.getIdx());
			row=pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}return row;
	}
	//글삭제
	public int boardDelete(int idx,String pass) {
		// 쿼리
		String query = "delete from tbl_board where idx=? and pass=?";
		int row=0;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, idx);
			pstmt.setString(2, pass);
			row=pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}return row;
	}
}
