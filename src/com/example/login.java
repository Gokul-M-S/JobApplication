package com.example;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import java.sql.DriverManager;
import java.io.IOError;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class login extends HttpServlet{
    
    private Database database = Database.getInstance();

    public void init(){}
    public void doPost(HttpServletRequest request,HttpServletResponse response) 
        throws ServletException,IOException{
            String res = "";
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String type =(String) request.getAttribute("type");
            try(Connection conn = DriverManager.getConnection(database.getUrl(), database.getUser(), database.getPass())){
                 String query = "Select * from userinformation where ";
                 if(type.equals("1")){
                     query+="emailid = '";
                 }else if(type.equals("2")){
                     query+="phonenumber = '";
                 }
                 query+=username+"';";
                 Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery(query);
                 if(rs.next()){
                    res="login";
                 }
            }catch(Exception e){
                res = e.getMessage();
            }
            response.setContentType("text/html");
            response.getWriter().write(res);
    }
    public void destroy(){}
}
