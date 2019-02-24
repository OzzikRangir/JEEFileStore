/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ozz.ejb;

import com.ozz.webServices.jaxb.Document;
import javax.ejb.Local;

/**
 *
 * @author Windows
 */
@Local
public interface FileManagerBeanLocal {
    
    public boolean delete(int id);

    public byte[] download(int id);

    public Document[] getFilesStructure();

    public void setPath(String path);

    public int getId();

    public void setId(int id);

    public boolean setDocument(String name, String description, byte[] code);
}
