package com.mylaensys.isis.viewer.dhtmlx.desktop.component;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "call")
public class GridHeadEventCommand {
    @XmlAttribute
    private String command;
    @XmlElement(name="param")
    private List<GridHeadEventParam> parameters;

    public GridHeadEventCommand() {

    }

    public GridHeadEventCommand(String command) {
        this.command = command;
        this.parameters = new ArrayList<GridHeadEventParam>();
    }

    public GridHeadEventParam addParameter(String value) {
        GridHeadEventParam param = new GridHeadEventParam(value);
        this.parameters.add(param);
        return param;
    }

    public String getCommand() {
        return command;
    }

    public List<GridHeadEventParam> getParameters() {
        return parameters;
    }
}
