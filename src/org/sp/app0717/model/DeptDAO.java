package org.sp.app0717.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Dept테이블과 관련된 CRUD 전담객체를 정의한다
public class DeptDAO {
	String url="jdbc:oracle:thin:@localhost:1521:XE";
	String user="java";
	String pass="1234";
	
	public String[][] selectAll(){
		Connection con=null;//접속정보르 가진 객체 따라서 끊을수도있따.
		PreparedStatement pstmt=null;//쿼리문 수행객체
		ResultSet rs=null;//표를 표현한 객체
		String[][] data=null;
		
		//1)드라이버로드
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//2)접속
			con=DriverManager.getConnection(url,user,pass);
			if(con==null) {
				System.out.println("접속실패ㅜㅜ");
			}else {
				//3)쿼리수행
				pstmt=con.prepareStatement("select * from dept",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				rs=pstmt.executeQuery();
				rs.last();
				int total=rs.getRow();//마지막 레코드 번호 반환
				data=new String[total][3];
				
				rs.beforeFirst();//커서원상복귀
				int index=0;
				
				while(rs.next()){//커서 한칸 전진
				data[index][0]=Integer.toString(rs.getInt("deptno"));
				data[index][1]=rs.getString("dname");
				data[index][2]=rs.getString("loc");
				index++;
				}
				
				
				
			}
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
			if(rs!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
			if(rs!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		}
		
		
		return data;
	}
}
