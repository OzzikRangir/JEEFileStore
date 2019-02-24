/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ozz.ejb;

import com.ozz.webServices.jaxb.Document;
import com.ozz.webServices.jaxb.Files;
import com.ozz.webservices.FileWebService_Service;
//import com.ozz.webservices.FileWebService_Service;

import java.io.StringReader;
import java.util.List;
import javax.ejb.Stateful;
import javax.xml.bind.JAXB;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author Windows
 */
@Stateful
public class FileManagerBean implements FileManagerBeanLocal {

    @WebServiceRef(wsdlLocation = "META-INF/wsdl/localhost_27830/Projekt-backend/FileWebService.wsdl")
    private FileWebService_Service service;

    private int id;
    public String name;
    public String description;
    public byte[] code;

    @Override
    public boolean setDocument(String name, String description, byte[] code) {
        this.name = name;
        this.description = description;
        this.code = code;
        return upload(name, description, code);
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setPath(String path) {
        setUpPath(path);
    }

    @Override
    public Document[] getFilesStructure() {
        //System.out.println(getXmlString());
        Files file;
        List<Document> docs;
        try {
            file = JAXB.unmarshal(new StringReader(getXmlString()), Files.class);
            docs = file.getDocuments();
            return docs.toArray(new Document[docs.size()]);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean delete(int id) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        com.ozz.webservices.FileWebService port = service.getFileWebServicePort();
        return port.delete(id);
    }

    @Override
    public byte[] download(int id) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        com.ozz.webservices.FileWebService port = service.getFileWebServicePort();
        return port.download(id);
    }

    private String getXmlString() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        com.ozz.webservices.FileWebService port = service.getFileWebServicePort();
        return port.getXmlString();
    }

    private void setUpPath(java.lang.String path) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        com.ozz.webservices.FileWebService port = service.getFileWebServicePort();
        port.setUpPath(path);
    }

    private boolean upload(java.lang.String name, java.lang.String description, byte[] code) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        com.ozz.webservices.FileWebService port = service.getFileWebServicePort();
        return port.upload(name, description, code);
    }

}
