/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ozz.webServices;

import com.ozz.webServices.jaxb.Document;
import com.ozz.webServices.jaxb.Files;
import com.ozz.webServices.jaxb.JaxBController;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.Oneway;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Windows
 */
@WebService(serviceName = "FileWebService")
public class FileWebService {

    String path;
    String pathXml;
    JaxBController xmlParser = new JaxBController();

    /**
     * Web service operation
     */
    @WebMethod(operationName = "download")
    public byte[] download(@WebParam(name = "id") int id) {
        try {
            //TODO write your implementation code here:
            Files files = xmlParser.jaxbXMLToObject(pathXml);
            List<Document> docs = files.getDocuments();

            for (Document doc : docs) {
                if (doc.getId() == id) {
                    return java.nio.file.Files.readAllBytes(new File(path + "/files/" +doc.getPath()).toPath());
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(FileWebService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "upload")
    public boolean upload(@WebParam(name = "name") String name, @WebParam(name = "description") String description, @WebParam(name = "code") byte[] code) {
        //TODO write your implementation code here:
        Files files=new Files();
        try{
         files = xmlParser.jaxbXMLToObject(pathXml);
        }
        catch(Exception ex){
            
        }
        List<Document> docs=new LinkedList<Document>();
        int id;
        try{
         docs= files.getDocuments();
        
        
        id=docs.size();
        }catch(NullPointerException ex){
        id=0;
        docs=new LinkedList<Document>();
        }
        Document doc = new Document();
        doc.setDocument(id+1,name + ".doc", description);
        File file = new File(path + "/files/" + name + ".doc");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            System.out.println(path + "/files/" + name + ".doc");
            fos.write(code);
        } catch (IOException ex) {
            Logger.getLogger(FileWebService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        docs.add(doc);
        files.setDocuments(docs);
        xmlParser.jaxbObjectToXML(files, pathXml);

        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "delete")
    public boolean delete(@WebParam(name = "id") int id) {
        //TODO write your implementation code here:
        Files files = xmlParser.jaxbXMLToObject(pathXml);
        List<Document> docs = files.getDocuments();
        System.out.println(id);
        for (Document doc : docs) {
            if (doc.getId() == id) {
                docs.remove(doc);
                for (int i = 0; i < docs.size(); i++) {
                    docs.get(i).setId(i + 1);
                }
                files.setDocuments(docs);
                if (xmlParser.jaxbObjectToXML(files, pathXml)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "setUpPath")
    @Oneway
    public void setUpPath(@WebParam(name = "path") String path) {
        this.path = path;
        this.pathXml = path + "/files.xml";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getXmlString")
    public String getXmlString() {
        try {
            //TODO write your implementation code here:
            return new String(java.nio.file.Files.readAllBytes(Paths.get(pathXml)));
        } catch (IOException ex) {
            Logger.getLogger(FileWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

}
