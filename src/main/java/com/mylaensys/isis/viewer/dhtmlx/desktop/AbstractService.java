package com.mylaensys.isis.viewer.dhtmlx.desktop;

import com.mylaensys.isis.viewer.dhtmlx.ComponentFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AbstractService {

    protected ComponentFactory factory = new ComponentFactoryDesktop();

    protected String loadDSL(String object, String action, HttpServletRequest request) {
        BufferedReader br = null;
        StringBuffer code = new StringBuffer();
        String name = object == null ? "" : action == null ?  "" : object + "." + action;

        HttpSession session = request.getSession();
        //String filename = "WEB-INF/" + object + "." + action + ".dsl";

		try {
            if( name.length() > 0 ) {
                String filename = "WEB-INF/" + name + ".dsl";
                if( session.getAttribute( filename ) != null ) {
                    return (String)session.getAttribute( filename );
                }

                File f = new File(filename);
                if(f.exists()) {
                    String line;
                    br = new BufferedReader(new FileReader(filename));
                    while ((line = br.readLine()) != null) {
                        code.append( line ).append("\n\r");
                    }
                }
                session.setAttribute(filename, code.toString());
            }

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
        return code.toString();
    }

    protected void saveDSL(String object, String action,String code, HttpServletRequest request) {
        String name = object == null ? "" : action == null ?  "" : object + "." + action;

        HttpSession session = request.getSession();
        String filename = "src/main/webapp/WEB-INF/" + name + ".dsl";
        session.setAttribute(filename, code );

    }
}
