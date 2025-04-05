package todoList;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class TodoListUserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONObject jsonResponse = new JSONObject();
        response.setContentType("application/json");

        String username = request.getParameter("username");
        String password = request.getParameter("password");


        String path = request.getServletPath();


        try {

            switch (path) {
                case "/login":
                    if (username != null && password != null) {
                        login(username, password, jsonResponse);
                    } else {
                        jsonResponse.put("message", "login Parameter Missing!!!");
                    }
                    break;

                case "/register":
                    if (username != null && password != null) {
                        register(username, password, jsonResponse);
                    } else {
                        jsonResponse.put("message", "Register Parameter Missing!!!");
                    }

                    break;

                default:
                    jsonResponse.put("status", "error");
                    jsonResponse.put("message", "Invalid request");


            }
        } catch (Exception e) {
            jsonResponse.put("Status", "Exception occurs :" + e.getMessage());
        }


        response.getWriter().write(jsonResponse.toString());
        response.getWriter().flush();
    }


    private static void register(String username, String password, JSONObject jsonResponse) throws SQLException {
        if (username != null && password != null) {

            int regResult = UserManager.register(username, password);

            if (regResult == 1)
                jsonResponse.put("status", "Registration successful");
            else if (regResult == 2)
                jsonResponse.put("status", "Username already exists");
            else if (regResult == 3)
                jsonResponse.put("status", "Registration failed");
        } else {
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "Username and password required");
        }
    }

    private void login(String username, String password, JSONObject jsonResponse) throws Exception {
        if (username != null && password != null) {
            int result = UserManager.login(username, password);
            if (result == 1) {
                jsonResponse.put("status", "Login successful");

                String SEC_KEY = "iAmIronMan";

                SecretKey secretKey = new SecretKeySpec(SEC_KEY.getBytes(), "AES");

                String token = JSONtoken.generateToken(username);

                String encryptedToken = EncryptDecrypt.encrypt(token, secretKey);


                jsonResponse.put("token", encryptedToken);


            } else if (result == 2)
                jsonResponse.put("status", "Password Incorrect");
            else if (result == 3)
                jsonResponse.put("status", "User not found");
        } else {
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "Username and password required");
        }
    }
}
