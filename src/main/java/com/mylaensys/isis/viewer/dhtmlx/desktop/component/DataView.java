package com.mylaensys.isis.viewer.dhtmlx.desktop.component;

import com.mylaensys.isis.viewer.dhtmlx.AbstractComponent;
import com.mylaensys.isis.viewer.dhtmlx.Component;
import com.mylaensys.isis.viewer.dhtmlx.desktop.ComponentRenderException;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.facets.collections.modify.CollectionFacet;
import org.apache.isis.core.metamodel.facets.collections.modify.CollectionFacetUtils;
import org.apache.isis.core.metamodel.services.ServiceUtil;
import org.apache.isis.core.metamodel.spec.ObjectSpecification;
import org.apache.isis.core.metamodel.spec.feature.ObjectAction;
import org.apache.isis.core.metamodel.spec.feature.ObjectAssociation;
import org.apache.isis.core.metamodel.spec.feature.ObjectAssociationFilters;
import org.apache.isis.core.runtime.system.context.IsisContext;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement(name = "data")
public class DataView extends AbstractComponent implements Component {
    @XmlElement(name="item")
    private List<DataViewItem> items;
    /**
     * Default constructor required by JAXB.
     */
    public DataView() {
        this.items = new ArrayList<DataViewItem>();
    }


    @Override
    public Component acceptObject(ObjectAdapter object,ObjectAction action, ObjectAdapter result) throws ComponentRenderException {
        final CollectionFacet facet = CollectionFacetUtils.getCollectionFacetFromSpec(result);


        int maxColumns = 10;
        boolean header = false;
        boolean hasIcon = false;
        boolean hasData = false;
        for (final ObjectAdapter rowAdapter : facet.iterable(result)) {
            IsisContext.getPersistenceSession().resolveImmediately(rowAdapter);
            String id = "?_c=widget&_type=widget&_object=" + ServiceUtil.id(object.getObject()) + "&_oid=" +
                    rowAdapter.getOid().enString(IsisContext.getOidMarshaller());

            DataViewItem item = new DataViewItem(id);
            final List<ObjectAssociation> fields = rowAdapter.getSpecification().getAssociations(ObjectAssociationFilters.WHEN_VISIBLE_IRRESPECTIVE_OF_WHERE);
            Map<String,String> data = new HashMap<String, String>();
            for(int i = 0; i < fields.size();i++ ) {
                ObjectSpecification specification = fields.get(i).getSpecification();
                if (fields.get(i).isOneToManyAssociation() ) {
                    continue;
                }
                String text = fields.get(i).getName();
                final ObjectAdapter o = fields.get(i).get(rowAdapter);

                data.put(fields.get(i).getId(),(o != null ? o.titleString() : "" ) );

            }


            item.setData(data);

            this.items.add(item);

        }




        return this;
    }

}
