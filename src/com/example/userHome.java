package com.example;

import javax.servlet.http.HttpServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class userHome extends HttpServlet{
    
    public void init() throws ServletException{}

    public void doGet(HttpServletRequest request,HttpServletResponse response) 
        throws ServletException,IOException{
            request.getRequestDispatcher("/html/user.html").forward(request,response);
        }
    
    public void destroy(){}
}
