package com.zhuru.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * SQL执行器，用于运行SQL脚本文件
 */
public class SqlExecutor {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/zhuru?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    public static void main(String[] args) {
        System.out.println("开始执行SQL脚本...");
        
        // SQL脚本文件路径
        String sqlFilePath = "E:/WorkSpace/start_train_app/test_data.sql";
        
        try {
            // 加载JDBC驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC驱动加载成功");
            
            // 建立数据库连接
            try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
                System.out.println("数据库连接成功");
                
                // 读取SQL脚本文件
                List<String> sqlStatements = readSqlFile(sqlFilePath);
                System.out.println("SQL脚本读取成功，共包含 " + sqlStatements.size() + " 条SQL语句");
                
                // 执行SQL语句
                executeSqlStatements(connection, sqlStatements);
                
                System.out.println("SQL脚本执行完成！");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC驱动加载失败: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("数据库操作失败: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("SQL文件读取失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 读取SQL脚本文件，将SQL语句分割成单独的语句
     */
    private static List<String> readSqlFile(String filePath) throws IOException {
        List<String> sqlStatements = new ArrayList<>();
        StringBuilder currentStatement = new StringBuilder();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                
                // 跳过空行和注释行
                if (line.isEmpty() || line.startsWith("--")) {
                    continue;
                }
                
                // 添加当前行到当前SQL语句
                currentStatement.append(line);
                
                // 如果当前行以分号结束，则将当前SQL语句添加到列表中
                if (line.endsWith(";")) {
                    sqlStatements.add(currentStatement.toString());
                    currentStatement.setLength(0); // 清空当前SQL语句
                }
            }
        }
        
        // 处理最后一条没有分号结尾的SQL语句
        if (currentStatement.length() > 0) {
            sqlStatements.add(currentStatement.toString());
        }
        
        return sqlStatements;
    }

    /**
     * 执行SQL语句列表
     */
    private static void executeSqlStatements(Connection connection, List<String> sqlStatements) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            for (int i = 0; i < sqlStatements.size(); i++) {
                String sql = sqlStatements.get(i);
                System.out.println("执行SQL语句 " + (i + 1) + ": " + sql.substring(0, Math.min(50, sql.length())) + "...");
                statement.executeUpdate(sql);
            }
        }
    }
}
