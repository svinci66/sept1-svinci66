package db;

import java.sql.*;

public class DBUtil {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    /**
     * �õ����ݿ�����
     */
    public void getConnection() throws SQLException {
        // ͨ��Config��ȡMySQL���ݿ�������Ϣ
        String driver = Config.getValue("driver");
        String url = Config.getValue("url");
        String user = Config.getValue("user");
        String pwd = Config.getValue("password");
        try {
            // ָ����������
            Class.forName(driver);
            // �������ݿ�����
            conn = DriverManager.getConnection(url, user, pwd);
        } catch (Exception e) {
            // ������ӹ��̳����쳣���׳��쳣��Ϣ
            throw new SQLException("�������������ʧ�ܣ�");
        }
    }

    /**
     * �ͷ���Դ
     */
    public void closeAll() {
        // ���rs���գ��ر�rs
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // ���pstmt���գ��ر�pstmt
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // ���conn���գ��ر�conn
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ִ��SQL��䣬���Խ��в�ѯ
     */
    public ResultSet executeQuery(String preparedSql, Object[] param) {
        // ����SQL,ִ��SQL
        try {
            // �õ�PreparedStatement����
            System.out.println(preparedSql);
            pstmt = conn.prepareStatement(preparedSql);
            System.out.println(pstmt);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    // ΪԤ����sql���ò���
                    pstmt.setObject(i + 1, param[i]);
                }
            }
            // ִ��SQL���
            System.out.println(pstmt);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            // ����SQLException�쳣
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * ִ��SQL��䣬���Խ�������ɾ���ĵĲ���������ִ�в�ѯ
     */
    public int executeUpdate(String preparedSql, Object[] param) {
        int num = 0;
        // ����SQL,ִ��SQL
        try {
            // �õ�PreparedStatement����
            pstmt = conn.prepareStatement(preparedSql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    // ΪԤ����sql���ò���
                    pstmt.setObject(i + 1, param[i]);
                }
            }
            // ִ��SQL���
            num = pstmt.executeUpdate();
        } catch (SQLException e) {
            // ����SQLException�쳣
            e.printStackTrace();
        }
        return num;
    }


    public static void main(String[] args) throws SQLException {
        DBUtil db = new DBUtil();
        db.getConnection();
    }

}
