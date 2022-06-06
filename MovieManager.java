import java.sql.*;
import java.util.Scanner;

public class MovieManager {
    public static void main(String[] args) throws SQLException {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/MovieManager", "root", "");
                PreparedStatement pstmtInsert = conn.prepareStatement(
                        "insert into Movies values (?, ?, ?, ?, ?)");
                PreparedStatement pstmtUpdate = conn.prepareStatement(
                        "update Movies set maphim = ? where tenphim = ?");
                PreparedStatement pstmtDelete = conn.prepareStatement(
                        "delete from Movies where tenphim = ?");
                PreparedStatement pstmtSelect = conn.prepareStatement(
                        "select * from Movies where tenphim = ?");
                PreparedStatement pstmtUpdate2 = conn.prepareStatement(
                        "update Movies set date = ? where tenphim = ?")
        ) {
            conn.setAutoCommit(false);
            try {
                int type;
                int type2;
                String maPhim;
                String tenPhim;
                String  date;
                String daoDien;
                int thoiGian;
                Scanner ip = new Scanner(System.in);
                do {
                    System.out.println("What do you wanna do: ");
                    System.out.println("1.Add\t\t2.Update\t\t3.Delete\t\t4.Search");
                    type = ip.nextInt();
                    if (type == 1 ){
                        System.out.println("=== Add Movie ===");
                        ip.nextLine();
                        System.out.println("\nEnter Id: ");
                        maPhim = ip.nextLine();
                        System.out.println("\nEnter Name: ");
                        tenPhim = ip.nextLine();
                        System.out.println("\nEnter Date:  ");
                        date = ip.nextLine();
                        System.out.println("\nEnter Director: ");
                        daoDien = ip.nextLine();
                        System.out.println("\nEnter Time: ");
                        thoiGian = ip.nextInt();
                        pstmtInsert.setString(1, maPhim);
                        pstmtInsert.setString(2, tenPhim);
                        pstmtInsert.setString(3, date);
                        pstmtInsert.setString(4, daoDien);
                        pstmtInsert.setInt   (5, thoiGian);
                        pstmtInsert.executeUpdate();
                        System.out.println("SUCCESS!");
                    }
                    else if (type == 2){
                        System.out.println("=== Update Movie ===");
                        ip.nextLine();
                        System.out.println("Enter movie's name: ");
                        tenPhim = ip.nextLine();
                        System.out.println("\nFix:");
                        System.out.println("1.Id\t\t2.Date");
                        type2 = ip.nextInt();
                        if (type2 == 1){
                            System.out.println("== Fix Id == ");
                            ip.nextLine();
                            System.out.println("Enter Id: ");
                            maPhim = ip.nextLine();
                            pstmtUpdate.setString(1, maPhim);
                            pstmtUpdate.setString(2, tenPhim);
                            pstmtUpdate.executeUpdate();
                        }
                        if (type2 == 2){
                            System.out.println("== Fix Date ==");
                            ip.nextLine();
                            System.out.println("Date: ");
                            date = ip.nextLine();
                            pstmtUpdate2.setString(1,date);
                            pstmtUpdate2.setString(2,tenPhim);
                            pstmtUpdate2.executeUpdate();
                        }
                    }
                    else if (type == 3){
                        System.out.println("=== Delete Movie === ");
                        ip.nextLine();
                        System.out.println("\nEnter movie's name: ");
                        tenPhim = ip.nextLine();
                        pstmtDelete.setString(1, tenPhim);
                        pstmtDelete.executeUpdate();
                    }
                    else if (type == 4){
                        System.out.println("=== Search movie ===");
                        ip.nextLine();
                        System.out.println("\nEnter movie's name: ");
                        tenPhim = ip.nextLine();
                        pstmtSelect.setString(1, tenPhim);
                        ResultSet rset = pstmtSelect.executeQuery();
                        while (rset.next()){
                            System.out.println(rset.getString("Id") + ", "
                                    + rset.getString("Name") + ", "
                                    + rset.getString("Date") + ", "
                                    + rset.getString("Director") + ", "
                                    + rset.getInt("Time"));
                        }
                    }
                    conn.commit();
                    System.out.println("Continue: ");
                    System.out.println("5.Y\t\t6.N");
                    type = ip.nextInt();
                    if (type == 6){
                        break;
                    }
                }while (type != 1 || type != 2 || type != 3 || type != 4 || type != 5);

            } catch (SQLException ex) {
                System.out.println("Wrong Information");
                conn.rollback();
                ex.printStackTrace();
            }
        }
    }
}