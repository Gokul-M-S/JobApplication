package com.example;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class registrationPage extends HttpServlet{
    
    public void init() throws ServletException{
        super.init();
    }
    public void doGet(HttpServletRequest request,HttpServletResponse response) 
        throws ServletException,IOException{
            request.getRequestDispatcher("/html/SignUp.html").forward(request,response);
    }
    public void destroy(){
        super.destroy();
    }
}