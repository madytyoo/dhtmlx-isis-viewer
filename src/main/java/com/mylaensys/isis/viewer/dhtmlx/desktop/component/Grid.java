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
import org.apache.isis.core.metamodel.facets.collections.modify.CollectionFacet;
import org.apache.isis.core.metamodel.facets.collections.modify.CollectionFacetUtils;
import org.apache.isis.core.metamodel.services.ServiceUtil;
import org.apache.isis.core.metamodel.spec.ActionType;
import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.metamodel.spec.feature.*;
import org.apache.isis.core.runtime.system.context.IsisContext;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

/**
 * dhtmlxGrid
 */
@XmlRootElement(name = "rows")
public class Grid extends AbstractComponent implements Component {
    @XmlElement(name="head")
    private GridHead head;
    @XmlElement(name="userdata")
    private List<GridUserData> userdata;
    @XmlElement(name="row")
    private List<GridRow> rows;

    public Grid() {
        this.head = new GridHead();
        this.rows = new ArrayList<GridRow>();
        this.userdata = new ArrayList<GridUserData>();
    }


    public GridUserData addUserData(String name,String value) {
        GridUserData userData = new GridUserData(name,value);
        this.userdata.add( userData );
        return userData;
    }

    public GridHeadColumn addColumn(String text,String width,String type,String align,String sort) {
        GridHeadColumn column = new GridHeadColumn(text,width,type,align,sort,null);
        this.head.addColumn(column);
        return column;
    }

    public GridHeadColumn addColumn(String text,String width,String type,String align,String sort,String format) {
        GridHeadColumn column = new GridHeadColumn(text,width,type,align,sort,format);
        this.head.addColumn(column);
        return column;
    }
    public GridRow addRow(String id)  {
        GridRow row = new GridRow(id);
        this.rows.add( row );
        return row;
    }

    public GridRow get(String id) throws ComponentRenderException {
        for(GridRow row : this.rows) {
            if( row.getId().compareTo( id ) == 0 ) {
                return row;
            }
        }
        throw new ComponentRenderException("Invalid row : " + id);
    }

    public GridHeadEvent addBeforeInitEvent(String command,String...params) {
        GridHeadEvent beforeInit = new GridHeadEvent(command);
        for (String param : params) {
            beforeInit.addParameter(param);
        }
        this.head.addBeforeInit(beforeInit);
        return beforeInit;
    }

    public GridHeadEvent addAfterInitEvent(String command,String...params) {
        GridHeadEvent afterInit = new GridHeadEvent(command);
        for (String param : params) {
            afterInit.addParameter(param);
        }
        this.head.addAfterInit(afterInit);
        return afterInit;
    }

    @Override
    public Component acceptObject(ObjectAdapter object,ObjectAction action, ObjectAdapter result) throws ComponentRenderException {
        List<String> columns = parseColumnList();
        final CollectionFacet facet = CollectionFacetUtils.getCollectionFacetFromSpec(result);


        int maxColumns = 10;
        boolean header = false;
        boolean hasIcon = false;
        boolean hasData = false;
        String boundedObject = "";
        for (final ObjectAdapter rowAdapter : facet.iterable(result)) {

            IsisContext.getPersistenceSession().resolveImmediately(rowAdapter);
            String id = Utils.computeOid(rowAdapter.getOid().enString(IsisContext.getOidMarshaller()));

            boundedObject = rowAdapter.getSpecification().getFullIdentifier();

            GridRow gridRow = this.addRow( id );
            gridRow.addUserData("_title",rowAdapter.titleString());

            /* Adds the actions status */
            final List<ObjectAction> actions = rowAdapter.getSpecification().getObjectActions(Arrays.asList(ActionType.USER, ActionType.EXPLORATION, ActionType.PROTOTYPE), ObjectActionContainer.Contributed.EXCLUDED);
            for (ObjectAction objectAction : actions) {
                Consent isVisible = objectAction.isVisible(IsisContext.getAuthenticationSession(), rowAdapter, Where.OBJECT_FORMS);
                Consent isUsable = objectAction.isUsable(IsisContext.getAuthenticationSession(), rowAdapter, Where.OBJECT_FORMS);
                if( isVisible.isAllowed() ) {
                    if( isUsable.isVetoed() ) {
                        gridRow.addUserData( "dhx/objects/object/" +  boundedObject + ":" + objectAction.getId() , "false");
                    } else {
                        gridRow.addUserData( "dhx/objects/object/" +  boundedObject + ":" + objectAction.getId() , "true");
                    }
                    //this.addNewColumn( "nc_" + newColumn++ );
                }
            }




//            if( rowAdapter.getIconName() != null && rowAdapter.getIconName().length() > 0 ) {
//                this.get( id ).addCell( "/images/" + rowAdapter.getIconName() + ".jpg" );
//            }
//
//            if(!header) {
//                if( rowAdapter.getIconName() != null && rowAdapter.getIconName().length() > 0 ) {
//                    this.addColumn( "", "*" , "img", "", "");
//                }
//            }

            final List<ObjectAssociation> fields = rowAdapter.getSpecification().getAssociations(ObjectAssociationFilters.WHEN_VISIBLE_IRRESPECTIVE_OF_WHERE);
            final List<Integer> positions = getColumnOrdering(fields, columns);
            for(int i = 0; i < positions.size();i++ ) {
                int p = positions.get(i);
                ObjectSpecification specification = fields.get(p).getSpecification();
                if (fields.get(p).isOneToManyAssociation() ) {
                    continue;
                }
                /** A column list has been specified */
                if( columns.size() > 0 ) {
                    String column = fields.get(p).getId();
                    if( !columns.contains(column.toLowerCase()) ) {
                        continue;
                    }
                }
                if(!header) {

                    String text = fields.get(p).getName();
                    String type = typeToDHX( specification.getFullIdentifier() );
                    String align = alignToDHX( specification.getFullIdentifier() );
                    String sort = "str";
                    String format = formatToDHX( specification.getFullIdentifier() );
                    this.addColumn( text, "*" , type , align, sort, format );
                }
                final ObjectAdapter o = fields.get(p).get(rowAdapter);
                String javaType = fields.get(p).getSpecification().getFullIdentifier();
                if(  javaType.endsWith("Blob") ||  javaType.endsWith("BlobKey") ) {
                    String value = "";
                    if( o != null ) {
                        BlobKey blobKey = (BlobKey) o.getObject();
                        BlobInfoFactory blobInfoFactory = new BlobInfoFactory(DatastoreServiceFactory.getDatastoreService());
                        BlobInfo blobInfo = blobInfoFactory.loadBlobInfo(blobKey);
                        value = "<a href=\"javascript:showType('" + "dhx/objects/object/" +  boundedObject + "/" + id +"/property/" + fields.get(p).getId() + "')\">" + blobInfo.getContentType() + "</a>";
                        this.get( id ).addCell( toDHX( value, specification.getFullIdentifier()) );
                    }
                } else {
                    this.get( id ).addCell( toDHX(o != null ? o.getObject() : null, specification.getFullIdentifier()) );
                }
            }
            header = true;
            hasData = true;
        }


        if( !hasData ) {
            this.addColumn( action.getName(), "*" , "ro", "left", "");
            String id = "?_c=widget&_type=widget&_object=" + ServiceUtil.id(object.getObject()) + "&_oid=";
                this.addRow( id );
            this.get( id ).addCell( "No Data" );
        }


        //this.addUserData("object", ServiceUtil.id(object.getObject()) );

        this.addUserData("_ref", "dhx/objects/object/" +  boundedObject  + "/" );
        this.addAfterInitEvent("attachEvent", "onRowSelect", "onSelect");
        this.addAfterInitEvent("attachEvent", "onRowDblClicked", "attachView");


        return this;
    }

    /**
     * Return a grid for the specified collection
     * @param instance
     * @param collection
     * @return
     */
    @Override
    public Component acceptInstance(ObjectAdapter instance, String collection) throws ComponentRenderException {
        List<String> columns = parseColumnList();

        String boundedObject = "";
         final ObjectSpecification spec = instance.getSpecification();

            for(final OneToManyAssociation association : spec.getCollections()) {
                if(collection.equalsIgnoreCase( association.getId() ) ) {
                    final ObjectAdapter collectionAsAdapter = association.get(instance);

                    final CollectionFacet facet = collectionAsAdapter.getSpecification().getFacet(CollectionFacet.class);

                    final Iterator<ObjectAdapter> iterator = facet.iterator(collectionAsAdapter);
                    Boolean header = false;
                    while (iterator.hasNext()) {
                        final ObjectAdapter element = iterator.next();
                        boundedObject = element.getSpecification().getFullIdentifier();

                        String id = Utils.computeOid(element.getOid().enString(IsisContext.getOidMarshaller()));
                        GridRow gridRow = this.addRow( id );
                        gridRow.addUserData("_title",element.titleString());

                        /* Adds the actions status */
                        final List<ObjectAction> actions = element.getSpecification().getObjectActions(Arrays.asList(ActionType.USER, ActionType.EXPLORATION, ActionType.PROTOTYPE), ObjectActionContainer.Contributed.EXCLUDED);
                        for (ObjectAction objectAction : actions) {
                            Consent isVisible = objectAction.isVisible(IsisContext.getAuthenticationSession(), element, Where.OBJECT_FORMS);
                            Consent isUsable = objectAction.isUsable(IsisContext.getAuthenticationSession(), element, Where.OBJECT_FORMS);
                            if( isVisible.isAllowed() ) {
                                if( isUsable.isVetoed() ) {
                                    gridRow.addUserData( "dhx/objects/object/" +  boundedObject + ":" + objectAction.getId() , "false");
                                } else {
                                    gridRow.addUserData( "dhx/objects/object/" +  boundedObject + ":" + objectAction.getId() , "true");
                                }
                                //this.addNewColumn( "nc_" + newColumn++ );
                            }
                        }



                        final List<ObjectAssociation> fields = element.getSpecification().getAssociations(ObjectAssociationFilters.WHEN_VISIBLE_IRRESPECTIVE_OF_WHERE);
                        final List<Integer> positions = getColumnOrdering(fields, columns);
                        for(int i = 0; i < positions.size() ;i++ ) {
                            int p = positions.get(i);
                            ObjectSpecification specification = fields.get(p).getSpecification();
                            if (fields.get(p).isOneToManyAssociation() ) {
                                continue;
                            }
                            /** A column list has been specified */
                            if( columns.size() > 0 ) {
                                String column = fields.get(p).getId();
                                if( !columns.contains(column.toLowerCase()) ) {
                                    continue;
                                }
                            }

                            if(!header) {
                                String text = fields.get(p).getName();
                                String type = typeToDHX( specification.getFullIdentifier() );
                                String align = alignToDHX( specification.getFullIdentifier() );
                                String sort = "str";
                                String format = formatToDHX( specification.getFullIdentifier() );
                                this.addColumn( text, "*" , type , align, sort, format );

                            }
                            final ObjectAdapter o = fields.get(p).get(element);
                            String javaType = fields.get(p).getSpecification().getFullIdentifier();
                            if(  javaType.endsWith("Blob") ||  javaType.endsWith("BlobKey") ) {
                                String value = "";
                                if( o != null ) {
                                    BlobKey blobKey = (BlobKey) o.getObject();
                                    BlobInfoFactory blobInfoFactory = new BlobInfoFactory(DatastoreServiceFactory.getDatastoreService());
                                    BlobInfo blobInfo = blobInfoFactory.loadBlobInfo(blobKey);
                                    value = "<a href=\"javascript:showType('" + "dhx/objects/object/" +  boundedObject + "/" + id +"/property/" + fields.get(p).getId() + "')\">" + blobInfo.getContentType() + "</a>";
                                    this.get( id ).addCell( toDHX( value, specification.getFullIdentifier()) );
                                }
                            } else {
                                this.get( id ).addCell( toDHX(o != null ? o.getObject() : null, specification.getFullIdentifier()) );
                            }
                        }
                        header = true;


                    }
                }
            }
        this.addUserData("_ref", "dhx/objects/object/" +  boundedObject  + "/" );
        this.addAfterInitEvent("attachEvent", "onRowSelect", "onSelect");
        this.addAfterInitEvent("attachEvent", "onRowDblClicked", "attachView");

        return this;
    }

    private List<Integer> getColumnOrdering(List<ObjectAssociation> fields, List<String> columns) {
        List ordering = new ArrayList<Integer>();
        if(columns.size() > 0 ) {
            for(String column : columns) {
                int position = getPosition( fields, column );
                if( position != -1 ) {
                    ordering.add(position);
                }
            }

        } else {
            for(int i = 0; i < fields.size() ;i++ ) {
                ordering.add(i);
            }

        }
        return ordering;
    }

    private int getPosition(List<ObjectAssociation> fields,String column) {
        for(int i = 0; i < fields.size() ;i++ ) {
            if( fields.get(i).getId().equalsIgnoreCase( column ) ) {
                return i;
            }
        }
        return -1;

    }

    private ArrayList<String> parseColumnList() {
        ArrayList<String> list = new ArrayList<String>();
        if( paramters.containsKey("columns") ) {
            String columns = paramters.get("columns");
            StringTokenizer st = new StringTokenizer(columns , ",");
            while (st.hasMoreElements()) {
                String column = (String) st.nextElement();
                list.add(column);
            }
        }
        return list;
    }
}
