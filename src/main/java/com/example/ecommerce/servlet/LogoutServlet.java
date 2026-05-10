package com.example.ecommerce.servlet; import javax.servlet.annotation.*; import javax.servlet.http.*; import java.io.IOException;
@WebServlet("/logout") public class LogoutServlet extends HttpServlet{protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws IOException{req.getSession().invalidate();resp.sendRedirect(req.getContextPath()+"/home");}}
