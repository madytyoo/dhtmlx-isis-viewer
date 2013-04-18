package com.mylaensys.isis.viewer.dhtmlx.desktop.component;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GridHeadEvent {
    @XmlElement(name="call")
    private GridHeadEventCommand command;


    public GridHeadEvent() {
    }

    public GridHeadEvent(String command) {
        this.command = new GridHeadEventCommand(command);

    }

    public GridHeadEventCommand getCommand() {
        return command;
    }


    public void addParameter(String param) {
        this.command.addParameter( param );
    }
}
