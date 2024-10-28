package com.example;

import java.io.ByteArrayOutputStream; // Required for handling byte arrays
import java.io.IOException;
import java.io.InputStream; // Required for handling InputStream
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.MultipartConfig; // Required for multipart requests
import javax.servlet.http.Part;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@MultipartConfig
public class registration extends HttpServlet{
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASS = "postgres";
    private static final String resumePath = "D:\\gokul\\ResumeFiles";
    public void init() throws ServletException{}
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException,IOException{
            //Get the required Input
            String emailId = request.getParameter("emailId");
            String userName = request.getParameter("userName");
            String password  = request.getParameter("password");
            String phoneNumber = request.getParameter("phoneNumber");
            int userType = Integer.parseInt(request.getParameter("userType"));
            byte[] resumeBytes = null;
            String resumeName = null;
            int experience = 0;
            String companyName= null;
            String companyAddress = null;

            if(userType ==1){
                experience = Integer.parseInt(request.getParameter("experience"));


                // Get the pdf file from request
                Part resumePart = request.getPart("resume");
                resumeName = request.getParameter("resumeName");
                InputStream resumeInputStream = resumePart.getInputStream();
                if(resumePart!=null){

                    // converting to byte array 
                    resumeBytes = convertInputStreamToByteArray(resumeInputStream);
                    //storing it in file as pdf
                    String path = "D:\\gokul\\ResumeFiles\\"+resumeName;
                    try{
                        createPdf(path,resumeBytes);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    
                    
                }
                resumeInputStream.close();
            }else if(userType ==2){
                companyName = request.getParameter("companyName");
                companyAddress = request.getParameter("companyAddress");
            }

            response.setContentType("text/html");

            String sql = "INSERT INTO temp (resume) VALUES (?)";

            try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                // Set the byte array to the prepared statement
                pstmt.setBytes(1, resumeBytes); // 1 refers to the first parameter in the SQL statement

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    response.getWriter().write("Resume uploaded successfully");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().write("Error uploading resume: " + e.getMessage());
            }    
        
        }
    private static byte[] convertInputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, bytesRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }
    
    private static void createPdf (String path,byte[] resumeByte)
    throws Exception{
        try(OutputStream salida = (new FileOutputStream(path))){
            salida.write(resumeByte);
            salida.flush();
        }
    }
    
    public void destroy(){}
}

//need to change the resume name while saving the folder or it get overwrite in saving
