package com.mylaensys.isis.viewer.dhtmlx.desktop.component;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "item")
public class FormSettings {
    private String type;
    private String position;
    private String labelAlign;
    private String labelWidth;
    private String labelHeight;
    private String inputWidth;
    private String inputHeight;
    private String noteWidth;

    public FormSettings() {
        this.type = "settings";
        this.position = "label-left";
        this.labelAlign = "left";
        this.labelWidth = "100";
        this.inputWidth = "auto";
        this.noteWidth = "200";
    }

    @XmlAttribute
    public String getType() {
        return type;
    }

    @XmlAttribute
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @XmlAttribute
    public String getLabelAlign() {
        return labelAlign;
    }

    public void setLabelAlign(String labelAlign) {
        this.labelAlign = labelAlign;
    }

    @XmlAttribute
    public String getLabelWidth() {
        return labelWidth;
    }

    public void setLabelWidth(String labelWidth) {
        this.labelWidth = labelWidth;
    }

    @XmlAttribute
    public String getLabelHeight() {
        return labelHeight;
    }

    public void setLabelHeight(String labelHeight) {
        this.labelHeight = labelHeight;
    }

    @XmlAttribute
    public String getInputWidth() {
        return inputWidth;
    }

    public void setInputWidth(String inputWidth) {
        this.inputWidth = inputWidth;
    }

    @XmlAttribute
    public String getInputHeight() {
        return inputHeight;
    }

    public void setInputHeight(String inputHeight) {
        this.inputHeight = inputHeight;
    }

    @XmlAttribute
    public String getNoteWidth() {
        return noteWidth;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("FormSettings" );

        return buffer.toString();
    }
}
