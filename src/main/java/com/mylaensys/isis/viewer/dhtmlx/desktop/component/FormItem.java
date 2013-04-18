package com.mylaensys.isis.viewer.dhtmlx.desktop.component;

import com.mylaensys.isis.viewer.dhtmlx.desktop.ComponentRenderException;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "item")
public class FormItem implements FormContent {
    private String name;
    private String type;
    private String label;
    private String labelWidth;
    private String labelHeight;
    private String className;
    private String value;
    private String width;
    private String info;
    private String required;
    private String connector;
    private String filtering;
    private String validate;
    private String disabled;
    private boolean disabledStatus = false;
    private String inputWidth;
    private String inputHeight;
    private String dateFormat;
    private String checked;
    private String rows;
    private FormNote note;
    private String url;

    private String mode;
    private String _swfLogs;
    private String swfPath;
    private String swfUrl;
    private String autoStart;
    private String titleText;



    @XmlElement(name = "item")
    private List<FormItem> items;
    @XmlElement(name = "option")
    private List<FormItemOption> options;
    @XmlElement(name = "userdata")
    private List<FormUserData> userdata;

    public FormItem() {
    }

    public FormItem(String name, String type, String label, String value) {
        this.name = name;
        this.type = type;
        this.label = label;
        this.value = value;
        this.items = new ArrayList<FormItem>();
        this.options = new ArrayList<FormItemOption>();
        this.userdata = new ArrayList<FormUserData>();
    }

    public FormItem(String name, String type, String label, String value,String info,String required) {
        this.name = name;
        this.type = type;
        this.label = label;
        this.value = value;
        this.info = info;
        this.required = required;
        this.items = new ArrayList<FormItem>();
        this.options = new ArrayList<FormItemOption>();
        this.userdata = new ArrayList<FormUserData>();
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    @XmlAttribute
    public String getType() {
        return type;
    }

    @XmlAttribute
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @XmlAttribute
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @XmlAttribute
    public String getFiltering() {
        return filtering;
    }

    public void setFiltering(String filtering) {
        this.filtering = filtering;
    }

    @XmlAttribute
    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
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
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @XmlAttribute
    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    @XmlAttribute
    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    @XmlAttribute
    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }

    @XmlAttribute
    public String getDisabled() {
        return disabled;
    }

    public boolean isDisabled() {
        return disabledStatus;
    }

    public void setDisabled(Boolean disabled) {
        this.disabledStatus = disabled;
        this.disabled = disabled.toString();
    }

    @XmlAttribute
    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @XmlAttribute
    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    @XmlAttribute
    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    @XmlElement
    public FormNote getNote() {
        return note;
    }

    public void setNote(FormNote note) {
        this.note = note;
    }

    @XmlAttribute
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @XmlAttribute
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @XmlAttribute
    public String get_swfLogs() {
        return _swfLogs;
    }

    public void set_swfLogs(String _swfLogs) {
        this._swfLogs = _swfLogs;
    }

    @XmlAttribute
    public String getSwfPath() {
        return swfPath;
    }

    public void setSwfPath(String swfPath) {
        this.swfPath = swfPath;
    }

    @XmlAttribute
    public String getSwfUrl() {
        return swfUrl;
    }

    public void setSwfUrl(String swfUrl) {
        this.swfUrl = swfUrl;
    }

    @XmlAttribute
    public String getAutoStart() {
        return autoStart;
    }

    public void setAutoStart(String autoStart) {
        this.autoStart = autoStart;
    }

    @XmlAttribute
    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public List<FormItem> getItems() {
        return items;
    }

    public FormItem get(String id) throws ComponentRenderException {
        for(FormItem item : this.items) {
            if( item.getName().compareTo( id ) == 0 ) {
                return item;
            }
        }
        throw new ComponentRenderException("Invalid item : " + id);
    }

    public void addButton( String name, String value ) {
        FormItem item = new FormItem( name, "button" , null , value );
        item.setWidth("90");
        this.items.add( item );
    }

    public void addNewColumn( ) {
        FormItem item = new FormItem( "sep", "newcolumn" , null , null );
        this.items.add( item );
    }

    public void addInput( String name, String label, String value ) {
        FormItem item = new FormItem( name, "input" , label , value );
        this.items.add( item );
    }


    public void addOption(String label, String value) {
        FormItemOption option  = new FormItemOption( label , value );
        this.options.add( option );
    }

    public void addOption(String label, String value,Boolean  selected) {
        FormItemOption option  = new FormItemOption( label , value, selected );
        this.options.add( option );
    }

    public void addUserData(String name,String value) {
        FormUserData userData = new FormUserData(name,value);
        this.userdata.add( userData );
    }

    public FormItem addContainer( String name, String label ,String info, String required ) {
        FormItem item = new FormItem( name, "container" , label , null, info , required );
        this.items.add( item );
        return item;
    }
}
