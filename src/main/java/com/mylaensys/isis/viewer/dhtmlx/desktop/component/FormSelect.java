package com.mylaensys.isis.viewer.dhtmlx.desktop.component;


import com.mylaensys.isis.viewer.dhtmlx.AbstractComponent;
import com.mylaensys.isis.viewer.dhtmlx.Component;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.facets.collections.modify.CollectionFacet;
import org.apache.isis.core.metamodel.facets.collections.modify.CollectionFacetUtils;
import org.apache.isis.core.runtime.system.context.IsisContext;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "complete")
public class FormSelect extends AbstractComponent implements Component {
    @XmlElement(name = "option")
    private List<FormSelectOption> items;


    public FormSelect() {
        this.items = new ArrayList<FormSelectOption>();
    }


    public List<FormSelectOption> getItems() {
        return items;
    }

    public Component acceptObject(ObjectAdapter object, ObjectAdapter result)  {
        final CollectionFacet facet = CollectionFacetUtils.getCollectionFacetFromSpec(result);

        boolean header = false;
        for (final ObjectAdapter rowAdapter : facet.iterable(result)) {
            if(rowAdapter != null) {
                IsisContext.getPersistenceSession().resolveImmediately(rowAdapter);
                // Must fix this
                String label = rowAdapter.titleString() != null ? rowAdapter.titleString() : "";
                String id = rowAdapter.getOid().toString();
                this.addOption( id , label );
                header = true;
            }
        }


        return this;
    }

    private void addOption(String id, String label) {
        FormSelectOption option = new FormSelectOption(id,label);
        this.items.add( option );
    }

}
