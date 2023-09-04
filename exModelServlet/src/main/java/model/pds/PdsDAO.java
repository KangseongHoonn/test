package model.pds;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.board.BoardDTO;
import model.util.DBManager;

public class PdsDAO {
		// 싱글톤
		private PdsDAO() {
		}

		private static PdsDAO instance = new PdsDAO();// 자기자신의 객체를 생성

		public static PdsDAO getInstance() {
			return instance;
		}

		// DB 관련
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// 메소드 정의

		// 게시판 글 총수 계산
		public int PdsCount() {
			// 리턴타입
			int row = 0;
			// 쿼리
			String query = "select count(*) from tbl_pds";

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
			public int PdsCount(String search, String key) {
				// 리턴타입
				int row = 0;
				// 쿼리
				String query = "select count(*) from tbl_Pds where "+search+" like ?";
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
		
		public int PdsWrite(PdsDTO dto) {
			// 리턴타입
			int row = 0;
			// 쿼리
			String query = "INSERT INTO tbl_pds(idx,email,name, subject,contents, pass,filename) VALUES(tbl_pds_seq_idx.nextval,?,?,?,?,?,?)";

			try {
				conn = DBManager.getConnection();
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, dto.getEmail());
				pstmt.setString(2, dto.getName());
				pstmt.setString(3, dto.getSubject());
				pstmt.setString(4, dto.getContents());
				pstmt.setString(5, dto.getPass());
				pstmt.setString(6, dto.getFilename());
				row = pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DBManager.close(conn, pstmt);
			}
			return row;
		}
		
		public List<PdsDTO> PdsList() {
			// 리턴타입
			List<PdsDTO> list = new ArrayList<>();
			// 쿼리
			String query = "select * from tbl_pds order by idx desc";

			try {
				conn = DBManager.getConnection();
				pstmt = conn.prepareStatement(query);
				rs = pstmt.executeQuery();
				PdsDTO dto = null;
				while (rs.next()) {
					dto = new PdsDTO();
					dto.setIdx(rs.getInt("idx"));
					dto.setSubject(rs.getString("subject"));
					dto.setName(rs.getString("name"));
					dto.setEmail(rs.getString("email"));
					dto.setRegdate(rs.getString("regdate"));
					dto.setReadcnt(rs.getInt("readcnt"));
					dto.setFilename(rs.getString("filename"));
					list.add(dto);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DBManager.close(conn, pstmt, rs);
			}
			return list;
		}
		public List<PdsDTO> PdsList(int pagestart,int endpage) {
			// 리턴타입
			List<PdsDTO> list = new ArrayList<>();
			// 쿼리
			String query = "select X.* from(\r\n"
					+ "    select rownum rnum, A.* from(\r\n"
					+ "        select * from tbl_pds order by idx desc) A\r\n"
					+ "            where rownum <=?) X where X.rnum>=?";

			try {
				conn = DBManager.getConnection();
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, endpage);
				pstmt.setInt(2, pagestart);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					PdsDTO dto = new PdsDTO();
					dto.setIdx(rs.getInt("idx"));
					dto.setSubject(rs.getString("subject"));
					dto.setName(rs.getString("name"));
					dto.setEmail(rs.getString("email"));
					dto.setRegdate(rs.getString("regdate"));
					dto.setReadcnt(rs.getInt("readcnt"));
					dto.setFilename(rs.getString("filename"));
					list.add(dto);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DBManager.close(conn, pstmt, rs);
			}
			return list;
		}
		public List<PdsDTO> PdsList(String search,String key,int pagestart,int endpage) {
			// 리턴타입
			List<PdsDTO> list = new ArrayList<>();
			// 쿼리
			String query = "select X.* from(\r\n"
					+ "    select rownum rnum, A.* from(\r\n"
					+ "        select * from tbl_Pds order by idx desc) A\r\n"
					+ "            where "+search+" like ? and rownum <=?) X where "+search+" like ? and X.rnum>=?";

			try {
				conn = DBManager.getConnection();
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%"+key+"%");
				pstmt.setInt(2, endpage);
				pstmt.setString(3, "%"+key+"%");
				pstmt.setInt(4, pagestart);
				rs = pstmt.executeQuery();
				PdsDTO dto = null;
				while (rs.next()) {
					dto = new PdsDTO();
					dto.setIdx(rs.getInt("idx"));
					dto.setSubject(rs.getString("subject"));
					dto.setName(rs.getString("name"));
					dto.setRegdate(rs.getString("regdate"));
					dto.setEmail(rs.getString("email"));
					dto.setReadcnt(rs.getInt("readcnt"));
					dto.setFilename(rs.getString("filename"));
					list.add(dto);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DBManager.close(conn, pstmt, rs);
			}
			return list;
		}
}
