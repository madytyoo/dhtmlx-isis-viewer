<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<c:if test="${skin == null}" >
    <c:set var="skin" value="terrace" scope="session"/>
</c:if>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>Apache Isis Viewer DHTMLX</title>
    <link rel="icon" href="images/favicon.ico" type="image/x-icon"/>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <c:choose>
        <c:when test="${skin == 'terrace'}" >
    <link href="/skin/<c:out value="${skin}"/>/dhtmlx.css" rel="stylesheet" type="text/css"/>
    <script src="/skin/<c:out value="${skin}"/>/dhtmlx.js" charset="utf-8" type="text/javascript"></script>
        </c:when>
        <c:otherwise>
        <link href="/skin/<c:out value="${skin}"/>/dhtmlx.css" rel="stylesheet" type="text/css"/>
        <link href="/skin/<c:out value="${skin}"/>/dhtmlx_custom.css" rel="stylesheet" type="text/css"/>
        <script src="/skin/<c:out value="${skin}"/>/dhtmlx.js" charset="utf-8" type="text/javascript"></script>
        </c:otherwise>
    </c:choose>
    <link href="message_growl_dark.css" rel="stylesheet" type="text/css"/>
    <link href="stylesheet.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="dhtmlxform_item_container.js" charset="utf-8"></script>
    <script type="text/javascript" src="dhtmlxform_item_upload.js" charset="utf-8"></script>
    <script type="text/javascript" src="swfobject.js" charset="utf-8"></script>
    <script type="text/javascript" src="sax.js" charset="utf-8"></script>
    <script type="text/javascript" src="dhtmlx-isis-viewer.js" charset="utf-8"></script>
    <script src="ace/ace.js" type="text/javascript" charset="utf-8"></script>
    

</head>

<div id="welcome" style="margin: 80px;display:none">
    <div style="float:left"><img src="/images/Apache-Isis.png" alt=""></div>
    <div style="float:left;padding:10px;text-align:center">This is a single-class domain application, configured to run with Isis' DHTMLX Viewer on the Google App Engine.<br><br>
        For more details, see the <a href="http://isis.apache.org/" target="_blank">Isis website</a>.</div>
</div>

<body>
<div id="main">
    <textarea id="type_todoitem" style="display:none;">
        <div class="todoitem">
            <div class="todoitem_img" style="float:left;background-image:url('images/{obj.complete}.png')"></div>
            <div class="body" style="width:350px;">
            <div class="nm">{obj.description}</div>
            <div class="desc">{obj.category}</div>
        </div>
    </textarea>

</div>

<script type="text/javascript">
    var dhxWins,viewManager;
    function initialize() {
    var path = '<c:out value="${skin}"/>';
    var skin = (path == "terrace" ? "dhx_terrace" : path == "grass" ? "dhx_web" : "dhx_skyblue");
    dhtmlx.skin = skin;
    dhtmlx.image_path = "/skin/" + path + "/imgs/";
    dhxWins = new dhtmlXWindows();
    dhxWins.setSkin(dhtmlx.skin);
    dhxWins.enableAutoViewport(true);
    dhxWins.setImagePath(dhtmlx.image_path);

    dhxWins.attachEvent("onClose", function(id) {
        if (id.getId() == "dsl") {            
            var code = document.getElementById('code');
            code.parentNode.removeChild(code);            
        }
        return true;
    });


    var main = dhxLayout = new dhtmlXLayoutObject(document.body, "1C");
    mainToolbar = main.cells("a").attachToolbar();
    mainStatus = main.cells("a").attachStatusBar();
    mainStatus.setText("<div><img width='46' height='30' src='/images/logoSmall.png' style='float:left; padding-right:20px' title='Mylaensys' alt='Mylaensys Cloud Computing' />&copy; 2013 <a href='http://www.mylaensys.com' target='_blank'>mylaensys.com</a> all right reserved<div>")


    mainToolbar.setIconsPath(dhtmlx.image_path);
    mainToolbar.attachEvent("onXLE", function() {
        var s = "";
        mainToolbar.forEachItem(function(id) {
            if ("separator" == mainToolbar.getType(id)) {
                s = id;
            }
        });
        var pos = mainToolbar.getPosition(s);
        mainToolbar.addSpacer(s, pos);
        var id = String(new Date().getTime());
        var opts = Array(Array("_skin_terrace", 'obj', 'Terrace'),
            Array("_skin_clouds", 'obj', 'Classic Cloud'),
            Array("_skin_grass", 'obj', 'Web Grass'));
        mainToolbar.addButtonSelect("_skin", pos + 1, "Change Skin", opts, null, null);
        mainToolbar.addSeparator("skin_s", pos + 2);
        mainToolbar.addButton("_dsl", pos + 3, "View DSL", null, null);


    });

    mainToolbar.loadXML("/dhx/services?" + encodeURI(new Date().getTime()));


    mainContent = main.cells("a").attachTabbar();
    mainContent.setImagePath(dhtmlx.image_path);
    mainContent.enableTabCloseButton(true);
    mainContent.addTab("a1", "Welcome", "*");
    mainContent.setContent("a1", "welcome");
    mainContent.setTabActive("a1");

    viewManager = new ViewManager(mainContent, skin);
    mainContent.attachEvent("onSelect", activateView);

    mainToolbar.attachEvent("onClick", function(id) {
        var toolbar = this;
        if (id == "_dsl") {
            showDSL(id);
            return true;
        }
        if (id.indexOf("_skin_") == 0) {
            var skin = id.substring(6);
            changeSkin(skin);
            return true;
        }

        toolbar.forEachItem(function(itemId) {
            if (toolbar.getType(itemId) == "buttonSelect") {
                toolbar.forEachListOption(itemId, function(listId) {
                    if (id == listId) {
                        //alert( toolbar.getListOptionText(itemId,listId) );
                        viewManager.addOrOpenView(id, toolbar.getListOptionText(itemId, listId), false);
                        viewManager.activateView(id);
                    }
                });
            }
        });
    });

}


    dhtmlxEvent(window, 'load', initialize);
</script>
</body>
</html>
