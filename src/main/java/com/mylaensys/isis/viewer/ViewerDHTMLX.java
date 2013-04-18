package com.mylaensys.isis.viewer;


import com.mylaensys.isis.viewer.dhtmlx.desktop.ViewerCode;
import com.mylaensys.isis.viewer.dhtmlx.desktop.ViewerObject;
import com.mylaensys.isis.viewer.dhtmlx.desktop.ViewerServiceDesktop;
import com.mylaensys.isis.viewer.dhtmlx.desktop.ViewerSkin;
import com.mylaensys.isis.viewer.dhtmlx.touch.ViewerServiceTouch;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class ViewerDHTMLX extends Application {
    private Set<Object> singletons = new HashSet<Object>();

	public ViewerDHTMLX() {
		singletons.add(new ViewerServiceDesktop());
		singletons.add(new ViewerServiceTouch());
		singletons.add(new ViewerObject());
		singletons.add(new ViewerCode());
		singletons.add(new ViewerSkin());

	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
