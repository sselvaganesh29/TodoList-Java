package todoList.login;

import todoList.database.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager
{

    public static final UserManager INSTANCE = new UserManager();

    private UserManager(){

    }

    public static int register(String userName,String password) throws SQLException {
        String sql = "INSERT INTO users (username, password ) VALUES (?, ?)";

        try (Connection conn = DBconnection.getDbConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userName );
            ps.setString(2, password);


            ps.executeUpdate();
            return 1; //if Registration successful

        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                return 2; // if Username already exists
            }
            throw e;
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    public static int login(String userName,String password) throws SQLException {

        String sql = "SELECT password FROM users WHERE username = ?";
        try( Connection conn = DBconnection.getDbConnection();
             PreparedStatement ps = conn.prepareStatement(sql) )
        {
            ps.setString(1,userName );


            try ( ResultSet resultSet = ps.executeQuery() )
            {
                if (resultSet.next()) {
                    String realPassword = resultSet.getString("password");

                    if ( realPassword.equals(password) )
                    {
                        return 1;//if login successful
                    } else
                    {
                        return 2;//if username ok but password wrong
                    }

                }
            }


        }
        catch (SQLException e)
        {
          throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return 3;// if user doesn't exist

    }

    public static int getIdByUserName(String userName) throws Exception {
        String qry = "SELECT id FROM users WHERE username = ?";

        try ( Connection con = DBconnection.getDbConnection();
               PreparedStatement preparedStatement = con.prepareStatement(qry))
        {
            preparedStatement.setString(1,userName);

            ResultSet set = preparedStatement.executeQuery();

            if ( set.next() )
            {
                return set.getInt("id");
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        return -1;
    }


    public static boolean isValidUser(int id) throws SQLException {

        String qry = "SELECT * FROM users WHERE id = ?";

        try ( Connection con = DBconnection.getDbConnection();
              PreparedStatement preparedStatement = con.prepareStatement(qry))
        {
            preparedStatement.setInt(1,id);

            ResultSet set = preparedStatement.executeQuery();

            if ( set.next() )
            {
                return true;
            }
        }
        catch (Exception e)
        {
            throw e;
        }
        return false;
    }




}
