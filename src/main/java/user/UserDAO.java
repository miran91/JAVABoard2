package user;

//import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    private Connection conn; // 자바와 데이터베이스 연결
    private PreparedStatement pstmt; // 쿼리문 대기 및 설정, 실행
    private ResultSet rs; // 결과값 받아오기

    // 기본 생성자
    // UserDAO가 실행되면 자동으로 생성되는 부분
    // 메소드마다 반복되는 코드를 이곳에 넣으면 코드가 간소화된다
    public UserDAO() {
        try {
            String dbURL = "jdbc:mariadb://localhost:3306/project01";
            String dbID = "root";
            String dbPassword = "rkdalfks1!";
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
            System.out.print("test");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 로그인
    public int login(String userID, String userPassword) {
        String sql = "SELECT userPassword From user WHERE userID = ?";
        try {
            pstmt = conn.prepareStatement(sql); // sql 쿼리문을 대기 시킨다
            pstmt.setString(1, userID); // 첫번째 '?'에 매개변수로 받아온 'userID'를 대입
            rs = pstmt.executeQuery(); // 쿼리를 실행한 결과 rs에 저장
            if(rs.next()) {
                if(rs.getString(1).equals(userPassword)) {
                    return 1; // 로그인 성공
                } else {
                    return 0; // 비번틀림
                }
            }

            return -1; // 아이디 없음
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -2; // 오류
    }

    // 가입
    public int join(User user) {
        String sql = "INSERT INTO user VALUES(?, ?, ?, ?, ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUserID());
            pstmt.setString(2, user.getUserPassword());
            pstmt.setString(3, user.getUserName());
            pstmt.setString(4, user.getUserGender());
            pstmt.setString(5, user.getUserEmail());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}


