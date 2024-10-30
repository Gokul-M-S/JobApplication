package com.example;

import java.sql.Statement;
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
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Logger;

@MultipartConfig
public class registration extends HttpServlet{
   
    private static final String resumePath = "D:\\gokul\\ResumeFiles";
    public void init() throws ServletException{}
    
    private Database database = new Database();
    private String returnResponse = "";
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException,IOException{
            Logger logger = Logger.getLogger(registration.class.getName());
            logger.info("Enter the registration class");
            try{
                Connection conn = DriverManager.getConnection(database.getUrl(), database.getUser(), database.getPass());
                //Get the required Input
                String emailId          = request.getParameter("emailId");
                String userName         = request.getParameter("userName");
                String password         = request.getParameter("password");
                String phoneNumber      = request.getParameter("phoneNumber");
                int userType            = Integer.parseInt(request.getParameter("userType"));
                byte[] resumeBytes      = null;
                String resumeName       = null;
                String resumePath       = null;
                int experience          = 0;
                String companyName      = null;
                String companyAddress   = null;
                int userId              = 0;
                int years               = 0;

                logger.info("Inserting into userinformation:");
                logger.info("Email ID: " + emailId);
                logger.info("Phone Number: " + phoneNumber);
                logger.info("Password: " + password);
                logger.info("User Type: " + userType);
                
                if(userType ==1){
                    experience = Integer.parseInt(request.getParameter("experience"));
                }else if(userType ==2){
                    companyName = request.getParameter("companyName");
                    companyAddress = request.getParameter("companyAddress");
                }


                response.setContentType("text/html");
                logger.info("Before insertion in userinformation");
                // we will provide a insertion here to databse
                String query = "";
                // Declare outside the try block
                Statement st = null;
                try {
                    st = conn.createStatement();
                    
                    query = "INSERT INTO userinformation (emailid, phonenumber, password, usertype)\n"
                            +"VALUES ('"+ emailId+"','"+phoneNumber+"','"+password+"',"+userType+");";
                    int inserted = st.executeUpdate(query);
                    if (inserted == 0) {
                        throw new Exception("Error occurred in insertion!");
                    }
                } catch (SQLException e) {
                    throw new Exception("SQL error: " + e.getMessage());
                } catch (Exception e) {
                    throw new Exception("Error: " + e.getMessage());
                } finally {
                    if (st != null) {
                        try {
                            st.close(); // Close the PreparedStatement
                        } catch (SQLException e) {
                           throw new Exception("Error occured in closing the connection in database !!");
                        }
                    }
                }

                
                //Getting the userId
                logger.info("getting userId through emailid");
                
                try {
                    st = conn.createStatement();
                    query = "SELECT * from userInformation where emailid = '"+emailId+"';";
                    ResultSet rs = st.executeQuery(query);
                    if (rs.next()){
                        userId = Integer.parseInt(rs.getString("userid"));
                    }
                } catch (SQLException e) {
                    throw new Exception("SQL error: " + e.getMessage());
                } catch (Exception e) {
                    throw new Exception("Error: " + e.getMessage());
                } finally {
                    if (st != null) {
                        try {
                            st.close(); // Close the PreparedStatement
                        } catch (SQLException e) {
                           throw new Exception("Error occured in closing the connection in database !!");
                        }
                    }
                }
                logger.info("userId = "+userId);
                logger.info("Inserting into exprience");
                if(userType ==1){
                    // adding exprience to the table
                    
                    try {
                        st = conn.createStatement();
                        query = "Insert into exprience(userid,exprience,years) \n" 
                                +"Values("+userId+","+experience+","+years+");";
                        int inserted = st.executeUpdate(query);
                        if (inserted == 0) {
                            throw new Exception("Error occurred in insertion!");
                        }
                    } catch (SQLException e) {
                        throw new Exception("SQL error: " + e.getMessage());
                    } catch (Exception e) {
                        throw new Exception("Error: " + e.getMessage());
                    } finally {
                        if (st != null) {
                            try {
                                st.close(); // Close the PreparedStatement
                            } catch (SQLException e) {
                               throw new Exception("Error occured in closing the connection in database !!");
                            }
                        }
                    }
                    logger.info("adding resume to folder");
                    //add resume to the folder
                    // Get the pdf file from request
                    Part resumePart = request.getPart("resume");
                    resumeName      = request.getParameter("resumeName");
                    InputStream resumeInputStream = resumePart.getInputStream();
                    
                    if(resumePart!=null){

                        // converting to byte array 
                        resumeBytes = convertInputStreamToByteArray(resumeInputStream);
                        
                        //storing it in file as pdf
                        resumePath = "D:\\gokul\\ResumeFiles\\"+userId+".pdf";
                        
                        try{
                            createPdf(resumePath,resumeBytes);
                        }catch(Exception e){
                            throw new Exception(e.getMessage());
                        }
                    }
                    resumeInputStream.close();
                    
                    //resume path
                    logger.info("adding resume path to folder");
                    try {
                        st = conn.createStatement();
                        query = "Insert into resume(userid,resumepath)\n "
                                +"Values("+userId+",'"+resumePath+"');";
                       
                        int inserted = st.executeUpdate(query);
                        if (inserted == 0) {
                            throw new Exception("Error occurred in insertion!");
                        }
                    } catch (SQLException e) {
                        throw new Exception("SQL error: " + e.getMessage());
                    } catch (Exception e) {
                        throw new Exception("Error: " + e.getMessage());
                    } finally {
                        if (st != null) {
                            try {
                                st.close(); // Close the PreparedStatement
                            } catch (SQLException e) {
                               throw new Exception("Error occured in closing the connection in database !!");
                            }
                        }
                    }
                    //adding username
                    
                    try {
                        st = conn.createStatement();
                        query = "Insert into username(userid,username)\n"+
                                "Values("+userId+",'"+userName+"');";
                        
                        int inserted = st.executeUpdate(query);
                        if (inserted == 0) {
                            throw new Exception("Error occurred in insertion!");
                        }
                    } catch (SQLException e) {
                        throw new Exception("SQL error: " + e.getMessage());
                    } catch (Exception e) {
                        throw new Exception("Error: " + e.getMessage());
                    } finally {
                        if (st != null) {
                            try {
                                st.close(); // Close the PreparedStatement
                            } catch (SQLException e) {
                               throw new Exception("Error occured in closing the connection in database !!");
                            }
                        }
                    }
                    
                
                }else if(userType ==2){
                    //adding company name and  address
                    st = conn.createStatement();
                    query = "Insert into hrtable(userid,companyname,companyaddress)\n "
                            +"Values("+userId+",'"+companyName+"','"+companyAddress+"');";
                    try {
                        
                        int inserted = st.executeUpdate(query);
                        if (inserted == 0) {
                            throw new Exception("Error occurred in insertion!");
                        }
                    } catch (SQLException e) {
                        throw new Exception("SQL error: " + e.getMessage());
                    } catch (Exception e) {
                        throw new Exception("Error: " + e.getMessage());
                    } finally {
                        if (st != null) {
                            try {
                                st.close(); // Close the PreparedStatement
                            } catch (SQLException e) {
                               throw new Exception("Error occured in closing the connection in database !!");
                            }
                        }
                    }
                }
                returnResponse = "Registered Successfully";

            }catch(Exception e){
                logger.severe("Caught in exception");
                returnResponse = e.getMessage();
            }
            response.setContentType("text/html");
            response.getWriter().write(returnResponse);
        
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
