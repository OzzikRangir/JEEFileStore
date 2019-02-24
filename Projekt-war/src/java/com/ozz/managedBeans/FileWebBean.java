/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ozz.managedBeans;

import com.ozz.ejb.FileManagerBeanLocal;
import com.ozz.webServices.jaxb.Document;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

/**
 *
 * @author Windows
 */
@Named(value = "fileWebBean")
@SessionScoped
public class FileWebBean implements Serializable {

    @EJB
    private FileManagerBeanLocal fileManagerBean;

    int id;
    Document[] documents;
    private Part file;

    /**
     * Creates a new instance of FileWebBean
     */
    public FileWebBean() {
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public String addData(String name, String description) {
        try {
            InputStream input = null;
            input = file.getInputStream();
            int size = input.available();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[size];
            try {
                for (int length = 0; (length = input.read(buffer)) > 0;) {
                    output.write(buffer, 0, length);
                }
                if (fileManagerBean.setDocument(name, description, buffer)) {
                    return "/upload.jsp";
                }
            } catch (IOException | NullPointerException ex) {

            }

        } catch (IOException | NullPointerException ex) {

        }
        return "";
    }

    public int getId() {
        id = fileManagerBean.getId();
        return id;
    }

    public void setId(int id) {
        fileManagerBean.setId(id);
        this.id = id;
    }

    public void downloadFile() throws IOException {
        byte[] code = fileManagerBean.download(getId());
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        String name = "example.doc";
        for (Document doc : getDocuments()) {
            if (doc.getId() == getId()) {
                name = doc.getPath();
                System.out.println(name);

            }
        }
        // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.
        ec.responseReset();
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
        java.io.OutputStream output = ec.getResponseOutputStream();
        output.write(code);
        fc.responseComplete();
        // Important! Otherwise JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.
    }

    public Document getCurrent() {
        for (Document doc : getDocuments()) {
            if (doc.getId() == getId()) {
                return doc;
            }
        }
        return null;
    }

    public Document[] getDocuments() {
        try{
        return (Document[]) fileManagerBean.getFilesStructure();
        }catch(NullPointerException ex){
               return null; 
                }
            
        }
    

    public void setDocuments(Document[] documents) {
        this.documents = documents;
    }

    public boolean delete() {
        System.out.println(getId());
        return fileManagerBean.delete(getId());
    }

    public String getPath() throws MalformedURLException {
        //ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        fileManagerBean.setPath("d:/server/");
        return "";
    }

}
