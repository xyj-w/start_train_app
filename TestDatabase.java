import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestDatabase {
    public static void main(String[] args) {
        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC driver loaded successfully");
            
            // Establish database connection
            String url = "jdbc:mysql://localhost:3306/zhuru?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false";
            String username = "root";
            String password = "1234";
            
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                System.out.println("Database connection successful");
                
                // Query user table
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery("SELECT * FROM user")) {
                    System.out.println("User table data:");
                    System.out.println("ID | Height | Weight | BMI | Gender | Age | Training Intensity | BMR | TDEE | Training Calories | Rest Calories");
                    System.out.println("--------------------------------------------------------------------");
                    while (resultSet.next()) {
                        long id = resultSet.getLong("id");
                        double height = resultSet.getDouble("height");
                        double weight = resultSet.getDouble("weight");
                        double bmi = resultSet.getDouble("bmi");
                        String gender = resultSet.getString("gender");
                        int age = resultSet.getInt("age");
                        String trainingIntensity = resultSet.getString("training_intensity");
                        int bmr = resultSet.getInt("bmr");
                        int tdee = resultSet.getInt("tdee");
                        int trainCalories = resultSet.getInt("train_calories");
                        int restCalories = resultSet.getInt("rest_calories");
                        
                        System.out.printf("%d | %.1f | %.1f | %.1f | %s | %d | %s | %d | %d | %d | %d%n",
                                id, height, weight, bmi, gender, age, trainingIntensity, bmr, tdee, trainCalories, restCalories);
                    }
                }
                
                // Query nutrition goal table
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery("SELECT * FROM nutrition_goal")) {
                    System.out.println("\nNutrition goal table data:");
                    System.out.println("ID | User ID | Protein Ratio | Carbs Ratio | Fat Ratio");
                    System.out.println("----------------------------------------");
                    while (resultSet.next()) {
                        long id = resultSet.getLong("id");
                        long userId = resultSet.getLong("user_id");
                        int proteinRatio = resultSet.getInt("protein_ratio");
                        int carbsRatio = resultSet.getInt("carbs_ratio");
                        int fatRatio = resultSet.getInt("fat_ratio");
                        
                        System.out.printf("%d | %d | %d%% | %d%% | %d%%%n",
                                id, userId, proteinRatio, carbsRatio, fatRatio);
                    }
                }
                
                // Query training plan table
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM training_plan")) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        System.out.println("\nTraining plan table has " + count + " records");
                    }
                }
                
                // Query meal log table
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM meal_log")) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        System.out.println("Meal log table has " + count + " records");
                    }
                }
                
            } catch (Exception e) {
                System.err.println("Database operation failed: " + e.getMessage());
                e.printStackTrace();
            }
            
        } catch (Exception e) {
            System.err.println("Program execution failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}