package todoList.task;


import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import todoList.login.EncryptDecrypt;
import todoList.login.JSONtoken;
import todoList.login.UserManager;
import org.json.JSONObject;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;

public class TodoListFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }




    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,FilterChain filterchain) throws IOException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String SEC_KEY = "iAmIronManWithAwesomeSuit1234567";


        SecretKey SECRET_KEY = new SecretKeySpec(SEC_KEY.getBytes(), "AES");

        JSONObject jsonResponse = new JSONObject();

        String encryptedToken = httpServletRequest.getHeader("encryptedToken");

        String path = httpServletRequest.getServletPath();


        if (encryptedToken == null || encryptedToken.isEmpty()) {
            jsonResponse.put("error", "Missing or invalid encryptedToken header");
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            httpServletResponse.getWriter().write(jsonResponse.toString());
            httpServletResponse.getWriter().flush();
            return;
        }







        try {

            String token = EncryptDecrypt.decrypt(encryptedToken, SECRET_KEY);

            String userName = JSONtoken.getUsername(token);

            int userId = UserManager.getIdByUserName(userName);


            switch (path) {

                case "/getAllTask":
                case "/getByStatus":
                case "/getByDate":
                case "/getByPriorities":
                case "/getByDueDate":
                case "/getByTag":
                case "/addTask":

                if ( UserManager.isValidUser(userId) ) {

                    httpServletRequest.setAttribute("userId", userId);

                    filterchain.doFilter(servletRequest, servletResponse);

                    return;
                }
                break;

                case "/addDueDate":
                case "/addDescription":
                case "/addTag":
                case "/updateDueDate":
                case "/updateDescription":
                case "/updateTask":
                case "/updateStatus":
                case "/updatePriority":
                case "/deleteTask":

                    int taskId = Integer.parseInt(httpServletRequest.getParameter("taskId"));

                    httpServletRequest.setAttribute("userId", userId);

                    int realUserId = TodoListManager.getUserIdByTaskId(taskId);

                    if ( realUserId == userId )
                    {
                        filterchain.doFilter(servletRequest, servletResponse);
                        return;
                    }
                    else
                    {
                        jsonResponse.put("status","This task is not relevant to you!!!");
                    }
                    break;

                case "/updateTag":
                case "/deleteTag":

                    int tagId = Integer.parseInt(httpServletRequest.getParameter("tagId"));

                    httpServletRequest.setAttribute("userId", userId);

                    int trueUserId = TodoListManager.getUserIdByTagId(tagId);

                    if ( trueUserId == userId )
                    {
                        filterchain.doFilter(servletRequest, servletResponse);
                        return;
                    }
                    else
                    {
                        jsonResponse.put("status","This tag  is not relevant to you!!!");
                    }
                    break;


            }



        }
        catch (ExpiredJwtException e)
        {
            jsonResponse.put("error", "Login time expired please Login again!!!");
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
        }
        catch (Exception e)
        {
                jsonResponse.put("Error message",e.getMessage());
        }

        httpServletResponse.getWriter().write(jsonResponse.toString());
        httpServletResponse.getWriter().flush();

    }

    @Override
    public void destroy() {
    }











}
