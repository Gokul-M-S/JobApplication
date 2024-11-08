package com.example;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class index extends HttpServlet {
    public void init() throws ServletException{}

    public void doGet(HttpServletRequest request,HttpServletResponse response)
        throws ServletException,IOException{
            int num = Integer.parseInt((String)request.getParameter("num"));
            String res ="";
            JSONObject jsonObject = new JSONObject();
            if(num==1){
                jsonObject.put("url","http://localhost:8080/naaukri/registrationPage");
            }else if(num==2){
                jsonObject.put("url","http://localhost:8080/naaukri/loginPage");
            }
            response.setContentType("application/json");
            response.getWriter().write(jsonObject.toString());
    }
    public void destroy(){}
}