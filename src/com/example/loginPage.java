package com.example;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

public class loginPage extends HttpServlet{
    public void init() throws ServletException{}

    public void doGet(HttpServletRequest request,HttpServletResponse response)
        throws ServletException,IOException{
            request.getRequestDispatcher("/html/login.html").forward(request,response);
    }
    public void destroy(){}
}