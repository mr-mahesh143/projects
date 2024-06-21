import java.sql.*;
import java.util.*;
import java.util.Random;
public class Online_Reservation_System{
    private static final int min=1000;
    private static final int max=9999;
    public static class User{
        private static String userName;
        private static String passWord;
        Scanner sc=new Scanner(System.in);
        String getUserName(){
            System.out.println("enter username : ");
            userName=sc.nextLine();
            return userName;
        }
        String getPassWord(){
            System.out.println("enter ur password :");
            passWord=sc.nextLine();
            return passWord;
        }
    }
    public static class Details{
        private int pnrNumber;
        private String passengerName;
        private int trainNumber;
        private String trainType;
        private String journerDate;
        private String from;
        private String to;
        Scanner sc=new Scanner(System.in);
        int getPnrNumber(){
            Random rd=new Random();
            pnrNumber=rd.nextInt(max)+min;
            return pnrNumber;
        }
        String getPassName(){
            System.out.println("enter passenger name :");
            passengerName=sc.nextLine();
            return passengerName;
        }
        int getTrainNumber(){
            System.out.println("enter train number :");
            trainNumber=sc.nextInt();
            sc.nextLine();
            return trainNumber;
        }
        String getTrainType(){
            System.out.println("enter Train type :");
            trainType=sc.nextLine();
            return trainType;
        } 
        String getJourneyDate(){
            System.out.println("enter date of journer in 'YYYY-DD-MM' fromat");
            journerDate=sc.nextLine();
            return journerDate;
        }  
        String getFrom(){
            System.out.println("enter FROM place :");
            from=sc.nextLine();
            return from;
        }  
        String getTo(){
            System.out.println("enter TO place : ");
            to=sc.nextLine();
            return to;
        }

    }
    public static void main(String[] args) throws Exception{
        Scanner sc=new Scanner(System.in);
        User u=new User();
        String userName=u.getUserName();
        String passWord=u.getPassWord();
        String url="jdbc:mysql://localhost:3306/mahesh";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection(url,userName,passWord);
        System.out.println("User connection granted!!\n");
        while(true){
            String InsertQuery="insert into reservations values (?, ?, ?, ?, ?, ?, ?)";
            String deleteQuery="DELETE FROM reservations WHERE pnr_number= ?";
            String showQuery="select * from reservations";
            System.out.println("Enter your choice :");
            System.out.println("1. Insert record.\n");
            System.out.println("2. Delete record.\n");
            System.out.println("3. Show record.\n");
            System.out.println("4. EXIT.\n");
            int choice=sc.nextInt();
            if(choice==1){
                Details dt=new Details();
                int pnr_number=dt.getPnrNumber();
                String passengerName=dt.getPassName();
                int trainNumber=dt.getTrainNumber();
                String trainType=dt.getTrainType();
                String journeyDate=dt.getJourneyDate();
                String fromPlace=dt.getFrom();
                String toPlace=dt.getTo();
                PreparedStatement ps=con.prepareStatement(InsertQuery);
                ps.setInt(1, pnr_number);
                ps.setString(2, passengerName);
                ps.setInt(3, trainNumber);
                ps.setString(4, trainType);
                ps.setString(5, journeyDate);
                ps.setString(6, fromPlace);;
                ps.setString(7, toPlace);
                int rowsAffected=ps.executeUpdate();
                if(rowsAffected>0){
                    System.out.println("Record added successfully");
                }
                else{
                    System.out.println("No records were added");
                }
            }
            else if(choice==2){
                System.out.println("enter the PNR number to delete the records :");
                int pnrNumber=sc.nextInt();
                PreparedStatement ps=con.prepareStatement(deleteQuery);
                ps.setInt(1, pnrNumber);
                int rowsAffected=ps.executeUpdate();
                if(rowsAffected>0){
                    System.out.println("Record Deleted successfully");
                }
                else{
                    System.out.println("No Records were deleted");
                }
            }
            else if(choice==3){
                PreparedStatement ps=con.prepareStatement(showQuery);
                ResultSet rs=ps.executeQuery();
                System.out.println("\nAll records are printing...\n");
                while(rs.next()){
                    String pnrNumber = rs.getString("pnr_number");
                    String passengerName = rs.getString("passengerName");
                    String trainNumber = rs.getString("trainNumber");
                    String trainType = rs.getString("trainType");
                    String journeyDate = rs.getString("journeyDate");
                    String fromPlace = rs.getString("fromPlace");
                    String toPlace = rs.getString("toPlace");

                    System.out.println("PNR Number: " + pnrNumber);
                    System.out.println("Passenger Name: " + passengerName);
                    System.out.println("Train Number: " + trainNumber);
                    System.out.println("class Type: " + trainType);
                    System.out.println("Journey Date: " + journeyDate);
                    System.out.println("From Location: " + fromPlace);
                    System.out.println("To Location: " + toPlace);
                    System.out.println("--------------");
                }
            }
            else if(choice==4){
                System.out.println("exiting...\n");
                break;
            }
            else{
                System.out.println("invalid choice!!!\n");
            }
        }
        sc.close();
    }

}