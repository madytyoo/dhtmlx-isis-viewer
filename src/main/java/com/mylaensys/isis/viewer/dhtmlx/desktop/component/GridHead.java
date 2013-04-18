package com.mylaensys.isis.viewer.dhtmlx.desktop.component;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@XmlRootElement(name = "head")
public class GridHead {
    @XmlElement(name="column")
    private List<GridHeadColumn> columns;
    @XmlElement(name="beforeInit")
    private GridHeadEvent beforeInit;
    @XmlElement(name="afterInit")
    private List<GridHeadEvent> afterInit;

    public GridHead() {
        this.columns = new ArrayList<GridHeadColumn>();
        this.afterInit = new ArrayList<GridHeadEvent>();
    }

    public void addColumn(GridHeadColumn column) {
        this.columns.add( column );
    }

    public void addBeforeInit(GridHeadEvent beforeInit) {
        this.beforeInit = beforeInit;
    }

    public void addAfterInit(GridHeadEvent afterInit) {
        this.afterInit.add( afterInit );
    }

    public GridHeadEvent getBeforeInit() {
        return beforeInit;
    }

    public List<GridHeadEvent> getAfterInit() {
        return afterInit;
    }

}
