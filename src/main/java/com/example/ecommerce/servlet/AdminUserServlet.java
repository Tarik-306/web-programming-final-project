package com.example.ecommerce.servlet;
import com.example.ecommerce.dao.UserDAO; import javax.servlet.*; import javax.servlet.annotation.*; import javax.servlet.http.*; import java.io.IOException;
@WebServlet("/admin/users") public class AdminUserServlet extends HttpServlet{protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{try{req.setAttribute("users",new UserDAO().findAll());req.getRequestDispatcher("/WEB-INF/views/admin/users.jsp").forward(req,resp);}catch(Exception e){throw new ServletException(e);}}}
