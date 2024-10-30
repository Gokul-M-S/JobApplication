package com.example;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class registrationFilter implements Filter {
    public void init(FilterConfig fConfig) throws ServletException {}
    
    private Database database = new Database();
    public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain chain) throws IOException, ServletException {
        System.out.println("Entered Registration Filter class!!");
        Logger logger = Logger.getLogger(registrationFilter.class.getName());
        logger.info("Enter the filter class");
        HttpServletRequest request = (HttpServletRequest) srequest; 
        HttpServletResponse response= (HttpServletResponse) sresponse;
        String errorMessage = "";
        boolean hasError = false;
        try(Connection conn = DriverManager.getConnection(database.getUrl(),database.getUser(),database.getPass())){
            // Validate Email Id
            String emailId = request.getParameter("emailId");
            checkEmailId(emailId);
            String sql;
            Statement pstmt;
            ResultSet rs;  
            try{
                sql = "Select * from userinformation where emailid ='"+emailId+"';";
                pstmt = conn.createStatement();
                rs = pstmt.executeQuery(sql);
                if(rs.next()){
                    throw new Exception("Email Id can't be same !!");
                }
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            } 
            // Validate UserName
            String userName = request.getParameter("userName");
            checkUserName(userName);
            

            //Validate Password
            String password  = request.getParameter("password");
            checkPassword(password);
            

            //Validate PhoneNumber
            String phoneNumber = request.getParameter("phoneNumber");
            checkPhoneNumber(phoneNumber);
            try{
                sql = "Select * from userinformation where phoneNumber ='"+phoneNumber+"';";
                pstmt = conn.createStatement();
                rs = pstmt.executeQuery(sql);
                if(rs.next()){
                    throw new Exception("Phone Number can't be same !!");
                }
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            } 
            //Validate UserType either jobseeker or hr
            int userType = 0;
            try{
                userType = Integer.parseInt(request.getParameter("userType"));
            }catch(Exception e){
                throw new Exception("User Type can be 1 or 2 !!");
            }
           
            if(userType != 1 && userType != 2){
                throw new Exception("User Type can be 1 or 2 !!");
            }
            if(userType ==1){
                Part resumePart = request.getPart("resume");
                if(resumePart == null || resumePart.getSize()==0){
                    throw new Exception("Resume can't be empty !!");
                }
                String resumeName = request.getParameter("resumeName");
                checkResumeName(resumeName);

                int experience = 0;
                try{
                    experience = Integer.parseInt(request.getParameter("experience"));
                    if(!(experience ==1 || experience==2)){
                        throw new Exception();
                    }
                }catch(Exception e){
                    throw new Exception("Exprience can be from 1 or 2 !!");
                }
            }else if(userType ==2){
                String companyName= request.getParameter("companyName");
                String companyAddress = request.getParameter("companyAddress");
                checkCompanyName(companyName);
                checkCompanyAddress(companyAddress);
            }
        }catch(Exception e){
                response.setContentType("text/html");
                response.getWriter().write(e.getMessage());
                return; // Stop further processing
            }

        // Continue with the filter chain if no errors
        chain.doFilter(srequest,sresponse);

    }


    public void checkEmailId(String email) throws Exception {
        if (email == null || email.isEmpty()) {
            throw new Exception("Email Id can't be empty !!");
        }
        if (email.length() > 40) {
            throw new Exception("Email Id can't be longer than 40 characters !!");
        }
    }

    public void checkUserName(String userName) throws Exception {
        if (userName == null || userName.isEmpty()) {
            throw new Exception("UserName can't be empty !!");
        }
        if (userName.length() > 30) {
            throw new Exception("UserName can't be longer than 30 characters !!");
        }
    }
 
    public void checkPassword(String password) throws Exception {
        if (password == null || password.isEmpty()) {
            throw new Exception("Password can't be empty !!");
        }
        if (password.length() > 30) {
            throw new Exception("Password can't be longer than 30 characters !!");
        }
        /* 
        
        //Password Pattern Check
        boolean captialLetter = false;
        boolean smallLetter = false;
        boolean number = false;
        boolean alphaNum = false;
        for(int i=0;i<password.length();i++){
            char ch = password.charAt(i);
            if(Character.isLowerCase(ch)){
                smallLetter = true;
                continue;
            }
            if(Character.isUpperCase(ch)){
                smallLetter = true;
                continue;
            }
            if(Character.isDigit(ch)){
                number = true;
                continue;
            }
            if(ch!=' '){
                alphaNum = true;
            }
        }
        if(!captialLetter){
            throw new Exception("Password should contain atleast one Captial Letter !!");
        }
        if(!smallLetter){
            throw new Exception("Password should contain atleast one Small Letter !!");
        }
        if(!number){
            throw new Exception("Password should contain atleast one Number !!");
        }
        if(!alphaNum){
            throw new Exception("Password should contain atleast one Special Character !!");
        }
        
        */
    }
    
    public void checkPhoneNumber(String phoneNumber) throws Exception{
        if(phoneNumber.length()!=10){
            throw new Exception("Phone number should have 10 digits !!");
        }
        for(int i=0;i<phoneNumber.length();i++){
            if(!Character.isDigit(phoneNumber.charAt(i))){
                throw new Exception("Phone Number should contain only digits !!");
            }
        }
    }

    public void checkResumeName(String resumeName) throws Exception {
        if (resumeName == null || resumeName.isEmpty()) {
            throw new Exception("Resume Name can't be empty !!");
        }
        if (resumeName.length() > 30) {
            throw new Exception("Resume Name can't be longer than 30 characters !!");
        }
    }

    public void checkCompanyName(String companyName) throws Exception {
        if (companyName == null || companyName.isEmpty()) {
            throw new Exception("Company Name can't be empty !!");
        }
        if (companyName.length() > 40) {
            throw new Exception("Company Name can't be longer than 40 characters !!");
        }
    }

    public void checkCompanyAddress(String companyAddress) throws Exception {
        if (companyAddress == null || companyAddress.isEmpty()) {
            throw new Exception("Company Address can't be empty !!");
        }
        if (companyAddress.length() > 50) {
            throw new Exception("Company Address can't be longer than 50 characters !!");
        }
    }
    
    public void destroy() {}
}
