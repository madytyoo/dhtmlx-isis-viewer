package com.mylaensys.isis.viewer.dhtmlx.desktop.component;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@XmlRootElement(name = "row")
public class GridRow {
    @XmlElement(name="cell")
    private List<GridRowCell> cells;
    @XmlAttribute
    private String id;
    @XmlElement(name="userdata")
    private List<GridUserData> userdata;


    public GridRow() {
    }

    public GridRow(String id) {
        this.id = id;
        this.cells = new ArrayList<GridRowCell>();
        this.userdata= new ArrayList<GridUserData>();
    }

    public String getId() {
        return id;
    }

    public GridRowCell addCell(String value) {
        GridRowCell cell = new GridRowCell(value);
        cells.add( cell );
        return cell;
    }

     public GridUserData addUserData(String name,String value) {
        GridUserData userData = new GridUserData(name,value);
        this.userdata.add( userData );
        return userData;
    }
}
