package com.example;

import javax.servlet.http.HttpServlet;

import java.io.IOError;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

public class logout extends HttpServlet{
    public void init() throws ServletException{}

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        response.setContentType("application/json");
        JSONObject obj = new JSONObject();
        obj.put("success","logout");
        obj.put("url","http://localhost:8080/naaukri");
        response.getWriter().write(obj.toString());
    }

    public void destroy(){}
}
