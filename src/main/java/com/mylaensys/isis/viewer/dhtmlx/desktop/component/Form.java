package com.mylaensys.isis.viewer.dhtmlx.desktop.component;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.mylaensys.isis.viewer.dhtmlx.AbstractComponent;
import com.mylaensys.isis.viewer.dhtmlx.Component;
import com.mylaensys.isis.viewer.dhtmlx.desktop.ComponentRenderException;
import com.mylaensys.isis.viewer.dhtmlx.servlet.utils.Utils;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.consent.Consent;
import org.apache.isis.core.metamodel.facets.describedas.DescribedAsFacet;
import org.apache.isis.core.metamodel.facets.mandatory.MandatoryFacet;
import org.apache.isis.core.metamodel.facets.multiline.MultiLineFacet;
import org.apache.isis.core.metamodel.facets.param.choices.ActionParameterChoicesFacet;
import org.apache.isis.core.metamodel.facets.properties.choices.PropertyChoicesFacet;
import org.apache.isis.core.metamodel.services.ServiceUtil;
import org.apache.isis.core.metamodel.spec.ActionType;
import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.metamodel.spec.feature.*;
import org.apache.isis.core.progmodel.facets.members.disabled.DisabledFacet;
import org.apache.isis.core.runtime.persistence.adapter.PojoAdapter;
import org.apache.isis.core.runtime.system.context.IsisContext;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * dhtmlxForm
 */
@XmlRootElement(name = "items")
public class Form extends AbstractComponent implements FormContent {
    private FormSettings settings;
    private List<FormItem> items;

    public Form() {
        this.settings = new FormSettings();
        this.items = new ArrayList<FormItem>();
    }

    @XmlElement(name="item")
    public FormSettings getSettings() {
        return settings;
    }


    public void setSettings(FormSettings settings) {
        this.settings = settings;
    }

    public FormItem addInput( String name, String label, String value ) {
        FormItem item = new FormItem( name, "input" , label , value );
        this.items.add( item );
        return item;
    }


    public FormItem addInput( String name, String label, String value , String info,String required) {
        FormItem item = new FormItem( name, "input" , label , value , info, required );
        this.items.add( item );
        return item;
    }

    public FormItem addTextArea( String name, String label, int rows, String value) {
        FormItem item = new FormItem( name, "input" , label , value );
        item.setRows( "" + rows );
        this.items.add( item );
        return item;
    }

    public FormItem addCheckBox( String name, String label, String value ) {
        FormItem item = new FormItem( name, "checkbox" , label , null );
        if( "true".equalsIgnoreCase( value ) ) {
            item.setChecked( value );
        }
        this.items.add( item );
        return item;
    }

    public FormItem addHidden( String name,  String value ) {
        FormItem item = new FormItem( name, "hidden" , null, value );
        this.items.add( item );
        return item;
    }

    public FormItem addButton( String name, String value ) {
        FormItem item = new FormItem( name, "button" , null , value );
        this.items.add( item );
        return item;
    }

    public FormItem addLabel( String name, String label ) {
        FormItem item = new FormItem( name, "label" , label, null);
        this.items.add( item );
        return item;
    }

    public FormItem addSelect( String name, String label ) {
        FormItem item = new FormItem( name, "combo" , label , null);
        this.items.add( item );
        return item;
    }

    public FormItem addSelect( String name, String label , String info, String required) {
        FormItem item = new FormItem( name, "combo" , label , null, info, required );
        this.items.add( item );
        return item;
    }

    public FormItem addContainer( String name, String label ,String info, String required ) {
        FormItem item = new FormItem( name, "container" , label , null, info , required );
        this.items.add( item );
        return item;
    }


    public FormItem addBlock(String name) {
        FormItem item = new FormItem( name, "block" , null , null);
        this.items.add( item );
        return item;
    }

    public FormItem addFieldSet(String name,String label) {
        FormItem item = new FormItem( name, "fieldset" , label , null);
        this.items.add( item );
        return item;
    }


    public FormItem addCalendar(String name, String label) {
        FormItem item = new FormItem( name, "calendar" , label , null);
        item.setDateFormat( "%d-%m-%Y" );
        this.items.add( item );
        return item;
    }

    public FormItem addCalendar(String name, String label,String value) {
        FormItem item = new FormItem( name, "calendar" , label , value);
        item.setDateFormat( "%d-%m-%Y" );
        this.items.add( item );
        return item;
    }

    public FormItem addCalendar(String name, String label,String value,String info,String required) {
        FormItem item = new FormItem( name, "calendar" , label , value, info, required);
        item.setDateFormat( "%d-%m-%Y" );
        this.items.add( item );
        return item;
    }

    public FormItem addNewColumn( String name ) {
        FormItem item = new FormItem( name , "newcolumn" , null , null );
        this.items.add( item );
        return item;
    }

    private FormItem addUpload(String name, String label, String value, String ref) {
        FormItem item = new FormItem( name , "upload" , null , null );

        item.setMode("html5");
        item.setAutoStart("true");
        if( value.length() > 0 ) {
            item.setTitleText( "<a href=\"javascript:showType('" + ref + "property/" + name + "')\">" + value + "</a>"  );
        }
        item.setAutoStart("true");
        item.set_swfLogs("enabled");
        item.setInputWidth("300");
        item.setInputHeight("90");
        String url = ref + "upload/" + name;
        //item.setUrl(ref + "upload/" + name);
        item.setUrl( url );
        item.setSwfPath("/uploader.swf");
        item.setSwfUrl( url );

        this.items.add(item);

        return item;
    }



    public FormItem get(String id) throws ComponentRenderException {
        for(FormItem item : this.items) {
            if( item.getName().compareTo( id ) == 0 ) {
                return item;
            }
        }
        throw new ComponentRenderException("Invalid item : " + id);
    }

    public FormItem getAction(String id) throws ComponentRenderException {
        /* The actions are in a actions label */
        FormItem actions = this.get("actions");
        for(FormItem item : actions.getItems() ) {
            if( /*item.getName().startsWith( id )*/
                    (item.getName().compareTo( id ) == 0 || item.getName().compareTo( id + "?prompt" ) == 0)
                    && item.getType().compareTo( "button" ) == 0) {
                return item;
            }
        }
        throw new ComponentRenderException("Invalid item : " + id);
    }

    public FormItem getProperty(String id) throws ComponentRenderException {
        for(FormItem item : this.items) {
            if( item.getName().compareTo( id ) == 0  && item.getType().compareTo( "button" ) != 0) {
                return item;
            }
        }
        throw new ComponentRenderException("Invalid item : " + id);
    }

    @XmlElement(name="item")
    public List<FormItem> getItems() {
        return items;
    }

    @Override
    public Component acceptObject(ObjectAdapter object, ObjectAction action) throws ComponentRenderException {
        final List<ObjectActionParameter> parameters = action.getParameters();
        final ObjectAdapter[] defaultParameterValues = action.getDefaults(object);


        String ref = "dhx/services/" + object.getSpecification().getFullIdentifier() + "/actions/" + action.getId() + "/invoke";
        this.addHidden("_ref", ref );

        this.addHidden("_prompt", "");
        this.addLabel("_message", "&#160;");
        this.get( "_message" ).setLabelWidth("auto");
        this.get( "_message" ).setLabelHeight("30");
        for (int i = 0; i < parameters.size(); i++) {
            ObjectActionParameter parameter = parameters.get(i);
            ObjectSpecification specification = parameter.getSpecification();

            String name = parameter.getId();


            if( defaultParameterValues[i] == null ) {
                 if( parameter.getFacet(ActionParameterChoicesFacet.class) != null ) {
                    this.addSelect(name,parameter.getName());
                    ObjectAdapter[][] optionsMap = action.getChoices(object);
                    ObjectAdapter[] options = optionsMap[i];
                    for (int j = 0; j < options.length; j++) {
                        ObjectAdapter option = options[j];
                        String oid = option.getOid() != null ? Utils.computeOid(option.getOid().enString(IsisContext.getOidMarshaller())) : option.titleString();
                        this.get(name).addOption(  option.titleString() ,oid  );
                    }
                    this.get(name).addOption(  "" , "" , true );
                    this.get(name).setFiltering("true");

                } else if( action.isContributed() && parameter.isObject() ) {
                    this.addSelect(name,parameter.getName());
                    /* How to know the object ? */
                    this.get(name ).setConnector( "dhtmlx?_c=" + Component.SELECT + "&_object=" + ServiceUtil.id(parameter) + "&_action=" + action.getId() + "&_type=method");

                } else  {
                    if( specification.getFullIdentifier().endsWith("Date") ) {
                        this.addCalendar(name, parameter.getName());
                    } else {
                        this.addInput(name, parameter.getName(), "");
                    }
                }

            } else {
                if( parameter.getFacet(ActionParameterChoicesFacet.class) != null ) {
                    this.addSelect(name,parameter.getName());
                    ObjectAdapter[][] optionsMap = action.getChoices(object);
                    ObjectAdapter[] options = optionsMap[i];
                    for (int j = 0; j < options.length; j++) {
                        ObjectAdapter option = options[j];
                        this.get(name).addOption( option.titleString() , option.getOid().enString(IsisContext.getOidMarshaller()) );
                    }

                } else {
                    this.addInput(name, parameter.getName(), defaultParameterValues[i].titleString());
                }
            }
                if( parameter.getFacet(DisabledFacet.class) != null ) {
                    DisabledFacet facet = parameter.getFacet(DisabledFacet.class);
                    this.get(parameter.getId()).setDisabled(true);
                } else {
                    if( parameter.getFacet(MandatoryFacet.class) != null ) {
                        this.get(parameter.getId()).setRequired("true");
                        this.get(parameter.getId()).setValidate("NotEmpty");
                    }
                    if( parameter.getFacet(DescribedAsFacet.class) != null ) {
                        DescribedAsFacet facet = parameter.getFacet(DescribedAsFacet.class);
                        if( facet.value().length() > 0 ) {
                            //this.get(parameter.getId()).setNote( new FormNote( facet.value() ));
                        }
                    }

                }

        }

        FormItem container = this.addLabel("actions", "&nbsp;");
        container.addButton("ok", "Ok");
        this.addHidden( "_" + "ok" , "ok" );
        container.addNewColumn();
        container.addButton("cancel","Cancel");
        this.addHidden( "_" + "cancel" , "cancel" );



        // this.addButton("Cancel","cancel");

        return this;
    }

    @Override
    public Component acceptInstance(ObjectAdapter result) throws ComponentRenderException {
        if( result == null ) {
            this.addLabel("", "No Data");
            return this;
        }
        final ObjectSpecification specification = result.getSpecification();
        final List<ObjectAssociation> visibleFields = specification.getAssociations(ObjectAssociationFilters.dynamicallyVisible(IsisContext.getAuthenticationSession(), result, Where.NOWHERE));

        String oid = Utils.computeOid(result.getOid().enString(IsisContext.getOidMarshaller()));
        String ref = "dhx/objects/object/" + specification.getFullIdentifier() + "/" + oid + "/" ;
        this.addHidden("_oid",  oid );
        this.addHidden("_ref",  ref );
        this.addHidden("_disabled",  "" );

        this.addLabel("_message","&#160;");
        this.get( "_message" ).setLabelWidth("auto");
        this.get( "_message" ).setLabelHeight("30");


        for (int i = 0; i < visibleFields.size(); i++) {
            final ObjectAssociation field = visibleFields.get(i);
            final ObjectAdapter associatedObject = field.get(result);
            Consent isVisible = field.isVisible(IsisContext.getAuthenticationSession(), result, Where.OBJECT_FORMS);
            Consent isUsable  = field.isVisible(IsisContext.getAuthenticationSession(), result, Where.OBJECT_FORMS);


            IsisContext.getPersistenceSession().resolveField(result, field);

            if( field.getFacet(PropertyChoicesFacet.class) != null ) {

                PropertyChoicesFacet facet = field.getFacet(PropertyChoicesFacet.class);
                this.addSelect(field.getId(), field.getName());
                Object[] options = field.getChoices(result);

                final String value = associatedObject != null ? associatedObject.titleString() : "";
                for (int j = 0; j < options.length; j++) {
                    PojoAdapter option = (PojoAdapter)options[j];
                    if( option.titleString().equalsIgnoreCase(value) ) {
                        this.get(field.getId()).addOption( option.titleString() , option.titleString() , true );
                    } else {
                        this.get(field.getId()).addOption( option.titleString() , option.titleString());
                    }
                }
            } else if (field.getSpecification().isParseable() && isVisible.isAllowed() )  {

                final String value = associatedObject != null ? associatedObject.titleString() : "";
                String javaType = field.getSpecification().getFullIdentifier();
                if(  javaType.endsWith("boolean") ) {
                    String bool = toDHX( associatedObject.getObject() , javaType  );
                    this.addCheckBox(field.getId(), field.getName(), bool );
                } else if(  javaType.endsWith("Date") ) {
                    String date = toDHX( associatedObject != null ? associatedObject.getObject(): null  , javaType  );
                    this.addCalendar(field.getId(), field.getName(), date );
                } else {
                    if( field.getFacet(MultiLineFacet.class) != null ) {
                        MultiLineFacet facet = field.getFacet(MultiLineFacet.class);
                        if( facet.numberOfLines() != 1) {
                            this.addTextArea(field.getId(), field.getName(), facet.numberOfLines(), value);
                        } else {
                            this.addInput( field.getId() , field.getName() , value );
                        }
                    }
                }
                if( isUsable.isVetoed() ) {
                    this.get(field.getId()).setDisabled(true);
                }

            } else if (field.isOneToOneAssociation()) {
                String javaType = field.getSpecification().getFullIdentifier();
                if(  javaType.endsWith("Blob") ||  javaType.endsWith("BlobKey") ) {
                    String value = "";
                    if( associatedObject != null ) {
                        BlobKey blobKey = (BlobKey) associatedObject.getObject();
                        BlobInfoFactory blobInfoFactory = new BlobInfoFactory(DatastoreServiceFactory.getDatastoreService());
                        BlobInfo blobInfo = blobInfoFactory.loadBlobInfo(blobKey);
                        value = blobInfo.getContentType();
                    }

                    this.addUpload(field.getId(), field.getName(), value , ref);
                }

                //this.addSelect(field.getId() ,field.getName());
                //this.addInput( field.getId() , field.getName() , associatedObject != null ? associatedObject.titleString() : "" );
            } else if (field.isOneToManyAssociation()) {
                this.addContainer(field.getId(), field.getName(), "true", "true");
            }

            if( field.getFacet(DisabledFacet.class) != null ) {
                DisabledFacet facet = field.getFacet(DisabledFacet.class);
                    this.get(field.getId()).setDisabled(true);
            } else {
                if( field.isMandatory() ) {
                    this.get(field.getId()).setRequired("true");
                    this.get(field.getId()).setValidate("NotEmpty");
                }
                if( field.getFacet(DescribedAsFacet.class) != null ) {
                    DescribedAsFacet facet = field.getFacet(DescribedAsFacet.class);
                    if( facet.value().length() > 0 ) {
                        //this.get(field.getId()).setNote( new FormNote( facet.value() ));
                    }
                }

            }

        }

        int count = 0;
        FormItem container = this.addLabel("actions", "&nbsp;");
        container.addButton("edit", "Edit");
        this.addHidden( "_" + "ok" , "ok" );
        container.addButton("ok", "Ok");
        this.addHidden( "_" + "ok" , "ok" );
        container.addNewColumn();
        container.addButton("cancel","Cancel");
        this.addHidden( "_" + "cancel" , "cancel" );

        final List<ObjectAction> actions = specification.getObjectActions(Arrays.asList(ActionType.USER, ActionType.EXPLORATION, ActionType.PROTOTYPE), ObjectActionContainer.Contributed.INCLUDED);
        for (ObjectAction action : actions) {
            Consent isVisible = action.isVisible(IsisContext.getAuthenticationSession(), result, Where.OBJECT_FORMS);
            Consent isUsable = action.isUsable(IsisContext.getAuthenticationSession(), result, Where.OBJECT_FORMS);
            if( isVisible.isAllowed() ) {
                String target = "";
                if( action.getParameterCount() > 0 ) {
                    target = action.getId() + "?prompt";
                } else {
                    target = action.getId();
                }
                container.addButton(target, action.getName() );

                if( isUsable.isVetoed() ) {
                    this.getAction( target ).setDisabled(true);
                }
                //this.addNewColumn( "nc_" + newColumn++ );
            }
        }
        return this;
    }


    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Form " );

        return buffer.toString();
    }
}


