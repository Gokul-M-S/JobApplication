package com.example;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
public class loginFilter implements Filter{
    
    public void init(FilterConfig filterconfig) throws ServletException{}

    
    public void doFilter(ServletRequest srequest,ServletResponse sresponse,FilterChain chain) 
        throws ServletException,IOException {
            HttpServletRequest request = (HttpServletRequest)srequest;
            HttpServletResponse response = (HttpServletResponse)sresponse;
            try{
                String username = request.getParameter("username");
                checkUserName(username);
                String password = request.getParameter("password");
                checkPassword(password);
               
                if(!(checkEmailId(username,request) || checkPhoneNumber(username,request))){
                    throw new Exception("Invalid Username !!");
                }

            }catch(Exception e){
                response.setContentType("text/html");
                response.getWriter().write(e.getMessage());
                return;
            }
            
            chain.doFilter(request,response);
    }

    public static void checkUserName(String userName) throws Exception {
        if (userName == null || userName.isEmpty()) {
            throw new Exception("UserName can't be empty !!");
        }
        if (userName.length() > 30) {
            throw new Exception("UserName can't be longer than 30 characters !!");
        }
    }

    public static void checkPassword(String password) throws Exception {
        if (password == null || password.isEmpty()) {
            throw new Exception("Password can't be empty !!");
        }
        if (password.length() > 30) {
            throw new Exception("Password can't be longer than 30 characters !!");
        }
    }

    public static boolean checkPhoneNumber(String phoneNumber,HttpServletRequest request) throws Exception{
        if(phoneNumber.length()!=10){
            return false;
        }
        for(int i=0;i<phoneNumber.length();i++){
            if(!Character.isDigit(phoneNumber.charAt(i))){
                return false;
            }
        }
        request.setAttribute("type","2");
        return true;
    }

    public boolean checkEmailId(String email,HttpServletRequest request) throws Exception {
        if (email == null || email.isEmpty()) {
           return false;
        }
        if (email.length() > 40) {
            return false;
        }
        boolean isContainAt = false;
        for(int i=0;i<email.length();i++){
            if(email.charAt(i)=='@'){
                isContainAt = true;
            }
        }
        if(!isContainAt){
            return false;    
        }
        request.setAttribute("type","1");
        return true;
    }
    
    public void destroy(){}
}
