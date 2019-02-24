/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ozz.webServices.jaxb;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;



public class JaxBController {


    public Files jaxbXMLToObject(String path) {
        try {
            File file = new File(path);
            JAXBContext context = JAXBContext.newInstance(Files.class);
            Unmarshaller un = context.createUnmarshaller();
            Files emp = (Files) un.unmarshal(file);
            return emp;
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean jaxbObjectToXML(Files baza,String path) {
        try {
            JAXBContext context = JAXBContext.newInstance(Files.class);
            Marshaller m = context.createMarshaller();
            //for pretty-print XML in JAXB
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // Write to System.out for debugging
            // m.marshal(baza, System.out);

            // Write to File
            m.marshal(baza, new File(path));
            return true;
        } catch (JAXBException e) {
            e.printStackTrace();
            return false;
        }
    }

}