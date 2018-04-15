import java.sql.*;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);

    private static Connection con = null;

    public static void main(String[] args) {

        int input = 0;

        try
        {
            Class.forName("com.mysql.jdbc.Driver"); // ClassNotFoundException 해결, External Libraries에 mysql java connector를 import해야 함.
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/telephone", "root", "c00k!erun");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("DB 연결 실패. 프로그램 종료.");
            return;
        }

        System.out.println("Telephone Program");

        while (true)
        {
            System.out.println("1 - Add List");
            System.out.println("2 - Show List");
            System.out.println("3 - Delete List\n");

            System.out.println("0 - Turn Off\n");

            System.out.println("Choose Number -> ");

            input = sc.nextInt();

            Statement stmt;
            try
            {
                stmt = con.createStatement();
            }
            catch(Exception e)
            {
                System.out.println("[Error] "+e.toString());
                return;
            }

            ResultSet rs = null;

            String name;
            String phone;

            switch (input)
            {
                case 1:
                    //AddList();
                    System.out.println("Input Your name -> ");
                    name=sc.next(); // No nextLine
                    System.out.println("Input Your phone number (ex. 010-0000-9999) -> ");
                    phone=sc.next();

                    try
                    {
                        stmt.executeUpdate("insert into phonedata values('"+name+"', '"+phone+"')");
                        System.out.println("Insert Complete!");
                    }
                    catch (Exception e)
                    {
                        System.out.println("[Error] "+ e.toString());
                    }
                    break;
                case 2:
                    //ShowList();
                    try
                    {
                        rs = stmt.executeQuery("select * from phonedata");

                        while(rs.next())
                        {
                            System.out.print("  "+rs.getString(1));
                            System.out.print("  "+rs.getString(2));
                            System.out.println(" ");
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println("[Error] "+ e.toString());
                    }
                    break;
                case 3:
                    //DeleteList();
                    System.out.println("Delete by name");
                    System.out.println("Input Your name -> ");
                    name=sc.next();

                    try
                    {
                        rs = stmt.executeQuery("select name from phonedata where name = '"+ name +"'");

                        rs.next();

                        name = rs.getString(1);

                        System.out.println("Erase Data includes name = '"+name+"'.\nAre You Sure? [y / any key except y]");
                        String y = sc.next();

                        if(y.equals("y") || y.equals("Y"))
                        {
                            stmt.executeUpdate("delete from phonedata where name = '"+name+"'");
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println("[Error] "+ e.toString());
                    }
                    break;
                case 0:
                    System.out.println("Bye Bye");
                    break;
                default:
                    System.out.println("[Error] Wrong Number");
                    break;
            }
            if (input == 0) break;
        }
    }
}


