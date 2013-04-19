/**
 *  ViewManager
 */
var debug = false;
var uuid = "";

function log(message) {
    if (debug) {
        console.log(message);
    }
}


function ViewManager(container, skin) {
    this._xml = "";
    this._skin = skin;
    this._notify = new Array();
    this._size = new Array();
    this._actions = new Object();
    this._message = "";
    this._containers = new Array();
    this._containers.push(container);

    this._contentManager = new ContentManager(this);
}
ViewManager.prototype = {
    render: function(xml) {
        this._xml = xml;
        this._contentManager.render(xml);
    },
    /**
     * returns the current container
     */
    currentContainer : function() {
        if (this._containers.length > 0) {
            return this._containers[this._containers.length - 1];
        }
        return undefined;
    },
    /**
     * returns the stack of containers
     */
    containers : function() {
        return this._containers;
    },
    /**
     * add a notification
     */
    addNotify : function(view) {
        if (!this.isNotified(view)) {
            console.log("adding " + view + " to notity list");
            this._notify.push(view);
        }
    },
    /**
     * remove a notification
     */
    removeNotify : function(view) {
        for (var i = 0; i < this._notify.length; i++) {
            if (this._notify[i] == view) {
                console.log("removing " + view + " to notity list");
                this._notify.splice(i, 1);
            }
        }
    },
    /**
     * remove a notification
     */
    isNotified : function(view) {
        for (var i = 0; i < this._notify.length; i++) {
            if (this._notify[i] == view) {
                return true;
            }
        }
        return false;
    },
    /**
     * updates the current view
     */
    updateCurrentView : function() {
        var view = this._containers[0].getActiveTab();
        console.log("updating view " + view);
        var label = this._containers[0].getLabel(view);
        viewManager.addOrOpenView(view, label, true);
    },
    /**
     * set the size
     */
    setSize: function(size) {
        this._size = size;
    },
    /**
     * set the size
     */
    getSize: function() {
        return this._size;
    },
    /**
     * set the message
     */
    setMessage: function(message) {
        this._message = message;
    },
    /**
     * get the message
     */
    getMessage : function() {
        var message = this._message;
        this._message = "";
        return message;
    },
    /**
     * add an action
     */
    addAction : function(id, form) {
        this._actions[id] = form;
        log("mapping action " + id + " to form " + form);
    },
    getAction : function(id) {
        return this._actions[id];
    },
    getSkin: function() {
        return this._skin;
    },
    notify : function(url) {
        var viewManager = this;
        var views = this._containers[0].getAllTabs();
        for (var i = 0; i < views.length; i++) {
            if (url.indexOf(views[i]) == 0) {
                var view = url.substring(0, views[i].length);
                viewManager.addNotify(view);
                var label = this._containers[0].getLabel(view);
                viewManager.addOrOpenView(view, label, true);

            } else if (views[i].indexOf("dhx") == 0) {
                viewManager.addNotify(views[i]);
            }
        }
        dhtmlxAjax.get("_ah/channel/notify?" + encodeURI("uuid=" + uuid + "&url=" + url), function(loader) {

        });
    },
    /**
     * returns the stack of containers
     */
    activateView : function(id) {
        var views = this._containers[0].setTabActive(id);
    },
    addOrOpenView : function(id, text, load) {

        var viewManager = this;
        var container = this.currentContainer();
        if (id.indexOf("prompt", id.length - "/prompt".length) !== -1) {
            // remove prompt so the win id matches the target url in form->_ref
            id = id.substring(0, id.indexOf("?prompt"));
            var window = dhxWins.createWindow(id, 9999, 9999, 450, 450);
            window.setText(text);
            var stack = viewManager.containers();
            var layout = window.attachLayout("1C", viewManager.getSkin());
            layout.cells("a").hideHeader();
            layout.progressOn();
            stack.push(layout.cells("a"));
            dhtmlxAjax.get(encodeURI(id), function(loader) {
                viewManager.render(loader.xmlDoc.responseText);
                /* after rendering remove the view from the stack */
                viewManager.containers().pop();
                layout.progressOff();
                var size = viewManager.getSize();
                if (size.length > 0) {
                    window.setDimension(size[0], size[1]);
                    window.setModal(true);
                    window.center();
                    viewManager.setSize(new Array());
                }

            });

        } else {

            if (!this.isOpenTab(id, container)) {
                container.addTab(id, text, "*");
                viewManager.addNotify(id);
                container.setTabActive(id);
            }
            if (load) {
                /* push the current container */
                var stack = viewManager.containers();
                var layout = container.cells(id).attachLayout("1C", viewManager.getSkin());
                layout.cells("a").hideHeader();
                layout.progressOn();
                stack.push(layout.cells("a"));
                dhtmlxAjax.get(encodeURI(id), function(loader) {
                    viewManager.render(loader.xmlDoc.responseText);
                    /* after rendering remove the view from the stack */
                    viewManager.containers().pop();
                    layout.progressOff();
                });
            }

            container.setLabel(id, text);
            //

        }
    },
    /**
     * Detects if the view is already open
     * @param id
     * @param tabbar
     */
    isOpenTab : function(id) {
        var tabs = this._containers[0].getAllTabs();
        for (var i = 0; i < tabs.length; i++) {
            if (tabs[i] == id) {

                return true;
            }
        }
        return false;
    },
    getXml : function() {
        return this._xml;
    }



}

/**
 *  ContentManager
 */
function ContentManager(_viewManager) {
    var handlers = {
        "layout" : new Layout(),
        "cell" : new LayoutCell(),
        "tabbar" : new Tabbar(),
        "tab" : new TabbarCell(),
        "toolbar": new Toolbar(),
        "grid":     new Grid(),
        "dataview": new DataView(),
        "form": new Form(),
        "view": new View()
    }

    /* Store a reference to the view Manager */
    var viewManager = _viewManager;
    /* Set up the sax parser */
    this._parser = sax.parser({strict:true});
    /* Open Tag Handler */
    this._parser.onopentag = function (node) {
        if (handlers[node.name] != undefined) {
            var container = viewManager.currentContainer();
            handlers[node.name].startRender(viewManager, container, node, this.position, viewManager.getXml());
        }
    }

    this._parser.onclosetag = function (node) {
        if (handlers[node] != undefined) {
            var container = viewManager.currentContainer();
            handlers[node].endRender(viewManager, container, node, this.position, viewManager.getXml());
        }
    }

}
ContentManager.prototype = {

    render: function(xml) {
        this._parser.write(xml).end();
    }
}


/**
 *  Layout Handler
 */
function Layout() {
}
Layout.prototype = {
    startRender: function(viewManager, container, node) {
        log("Layout start rendering" + container);

        var layout = container.attachLayout(node.attributes.pattern, viewManager.getSkin());
        viewManager.containers().push(layout);
    },
    endRender: function(viewManager, container, node) {
        log("Layout end rendering" + container);
        viewManager.containers().pop();
    }
}
/**
 *  Layout Cell Handler
 */
function LayoutCell() {
}
LayoutCell.prototype = {
    startRender: function(viewManager, container, node) {
        log("LayoutCell start rendering" + container);
        var cell = container.cells(node.attributes.id);
        if (node.attributes.text != undefined) {
            cell.setText(node.attributes.text);
        } else {
            cell.hideHeader();
        }
        if (node.attributes.width != undefined) {
            cell.setWidth(parseInt(node.attributes.width))
        }
        if (node.attributes.height != undefined) {
            cell.setHeight(parseInt(node.attributes.height))
        }
        viewManager.containers().push(cell);
    },
    endRender: function(viewManager, container, node) {
        log("LayoutCell end rendering" + container);
        viewManager.containers().pop();
    }


}
/**
 *  Toolbar Handler
 */
function Toolbar() {
    this._start = 0;
}
Toolbar.prototype = {
    startRender: function(viewManager, container, node, start, xml) {
        log("Toolbar start rendering" + container);
        this._start = start - 9;

    },
    endRender: function(viewManager, container, node, end, xml) {
        log("Toolbar end rendering" + container);
        var toolbar = container.attachToolbar();
        toolbar.setIconsPath(dhtmlx.image_path);
        /* Take the advantage from ready to use 'loadXMLString' */
        toolbar.loadXMLString(xml.substring(this._start, end), function() {
            toolbar.forEachItem(function(id) {
                if ("separator" == toolbar.getType(id) && id.indexOf("spacer") == 0) {
                    var pos = toolbar.getPosition(id);
                    console.log("got a spacer at " + pos);
                    toolbar.addSpacer(id, pos + 1);
                }
            });
        });
    }
}

/**
 * Tabbar Handler
 */
function Tabbar() {
    this._active = "";
}
Tabbar.prototype = {
    startRender: function(viewManager, container, node) {
        log("Tabbar start rendering" + container);
        this._active = "";
        var tabbar = container.attachTabbar();
        tabbar.setImagePath(dhtmlx.image_path);
        if (node.attributes.active != undefined) {
            this._active = node.attributes.active;
        }

        viewManager.containers().push(tabbar);
    },
    endRender: function(viewManager, container, node) {
        log("Tabbar end rendering" + container);
        var tabbar = viewManager.containers().pop();

        var tabs = tabbar.getAllTabs();
        tabbar.setTabActive(tabs[0]);
        for (var i = 0; i < tabs.length; i++) {
            if (this._active == tabs[i]) {
                tabbar.setTabActive(tabs[i]);
            }
        }
    }
}

/**
 *  Tabbar Cell Handler
 */
function TabbarCell() {
}
TabbarCell.prototype = {
    startRender: function(viewManager, container, node) {
        log("TabbarCell start rendering" + container);
        container.addTab(node.attributes.id, node.attributes.id);
        var cell = container.cells(node.attributes.id);
        if (node.attributes.width != undefined) {
            cell.setWidth(parseInt(node.attributes.width))
        }
        if (node.attributes.height != undefined) {
            cell.setHeight(parseInt(node.attributes.height))
        }
        viewManager.containers().push(cell);
    },
    endRender: function(viewManager, container, node) {
        log("TabbarCell end rendering" + container);
        viewManager.containers().pop();
    }


}


/**
 *  Grid Handler
 */
function Grid() {
}
Grid.prototype = {
    startRender: function(viewManager, container, node) {
        log("Grid start rendering" + container);

        //var wrapper = container.attachLayout("1C");
        //wrapper.cells("a").hideHeader();
        //var grid = wrapper.cells("a").attachGrid();
        var grid = container.attachGrid();
        grid.setSkin(viewManager.getSkin());

        grid.setImagePath(dhtmlx.image_path);
        grid.attachEvent("onXLS", function() {
            try {
                container.progressOn();
            }
            catch(err) {
                log(" sorry no progress here")
            }
        });
        grid.attachEvent("onXLE", function() {
            try {
                container.progressOff();
            }
            catch(err) {
                log(" sorry no progress here")
            }
            if (this.getRowsNum() > 0) {
                // todo in the row is no data do not select
                this.selectRow(0, true);
                var url = this.getUserData("", "_ref") + this.getSelectedId() + "/actions";

                var menu = new dhtmlXMenuObject();
                menu.setIconsPath(dhtmlx.image_path);
                menu.loadXML(url + "?" + new Date().getTime());
                menu.attachEvent("onClick", function(id, row, col) {
                    var url = id.substring(0, id.indexOf(":"));
                    var row = row.substring(0, row.lastIndexOf("_"));
                    var action = id.substring(id.indexOf(":") + 1);
                    console.log("clicked " + id + " on " + row);
                    console.log("target: " + url + "/" + row + "/actions/" + action + "/invoke");

                    var target = url + "/" + row + "/actions/" + action + "/invoke";
                    dhtmlxAjax.post(target, "", function(loader) {
                        if (loader.xmlDoc.status == 200) {
                            viewManager.notify(url + "/" + row);
                            viewManager.updateCurrentView();
                        } else {
                            alert("Error: " + loader.xmlDoc.status)
                        }
                    });
                    return true;
                });

                menu.renderAsContextMenu();
                this.enableContextMenu(menu);
                this.attachEvent("onBeforeContextMenu", function(id) {
                    var grid = this;
                    /* Selects the row */
                    grid.selectRowById(id);
                    /* Gets the row id */
                    console.log("clicked " + id);
                    menu.forEachItem(function(item) {
                        var status = grid.getUserData(id, item);
                        console.log("item " + item + " " + status);
                        if (status == "true") {
                            menu.setItemEnabled(item);
                        } else {
                            menu.setItemDisabled(item);
                        }
                    });

                    return true;
                });
            }
        });

        grid.loadXML(node.attributes.ref + "&" + new Date().getTime());

    },
    endRender: function(viewManager, container, node) {
        log("Grid end rendering" + container);
    }
}


/**
 *  Dataview Handler
 */
function DataView() {
}
DataView.prototype = {
    startRender: function(viewManager, container, node) {
        log("Dataview start rendering" + container);
        if (node.attributes.template != undefined) {
            var wrapper = container.attachLayout("1C");
            wrapper.cells("a").hideHeader();
            var config = {
                type:{
                    template:node.attributes.template,
                    css:"todoitem",
                    width:577,
                    height:137
                }
            };
            var dataview = wrapper.cells("a").attachDataView(config);

            dataview.load(node.attributes.ref + "&" + new Date().getTime());

        }


    },
    endRender: function(viewManager, container, node) {
        log("Dataview end rendering" + container);
    }
}

/**
 *  Form Handler
 */
function Form() {
    this._start = 0;
}
Form.prototype = {
    startRender: function(viewManager, container, node, start, xml) {
        log("Form start rendering" + container);
        this._start = start;


    },

    endRender: function(viewManager, container, node, end, xml) {
        log("Form end rendering" + container);


        //var wrapper = container.attachLayout("1C");
        //wrapper.cells("a").hideHeader();
        //var form = wrapper.cells("a").attachForm();
        var form = container.attachForm();
        form.setSkin(viewManager.getSkin());

        form.loadStructString("<items>" + xml.substring(this._start, end - 7) + "</items>", function() {
            viewManager.setSize(new Array(form.cont.childNodes[0].offsetWidth + 40, form.cont.childNodes[0].offsetHeight + 80));

            var lock = form.getItemValue("_prompt") == null ? true : false;
            var disabled = "";
            //console.log("form width :" + form.cont.offsetWidth)
            form.forEachItem(function(id) {
                console.log("form item " + id + " -> ");
                var type = form.getItemType(id);
                /* save the list of disabled items */
                if (!form.isItemEnabled(id)) {
                    disabled += id + ",";
                }
                if (type == "hidden") {
                    viewManager.addAction(form.getItemValue(id), form);
                } else {
                    if (lock) {
                        var type = form.getItemType(id);
                        if (type != "block" && type != "fieldset") {
                            form.disableItem(id);
                        }
                    }

                }

            });
            console.log("disabled " + disabled);
            form.setItemValue("_disabled", disabled);
            form.enableItem("edit");
            form.attachEvent("onButtonClick", function(action) {
                invokeAction(action);
            });
            form.attachEvent("onValidateError", function (input, value, result) {
                form.setItemLabel("_message", "<span class='warning'><p>Please verify the data entered</p></span>");
                return true;
            });
            form.attachEvent("onUploadComplete", function(count) {
                console.log("upload complete");
            });


        });

    }
}

/**
 *  Form Handler
 */
function View() {
}
View.prototype = {
    startRender: function(viewManager, container, node) {
        log("View start rendering" + container);
        viewManager.addOrOpenView(node.attributes.id, node.attributes.text, false);

    },

    endRender: function(viewManager, container, node) {
        log("View  end rendering" + container);
    }
}


/**
 * Grid row select handler
 * @param id
 */
function onSelect(id) {
    return true;
}
function attachView(id) {
    var ref = this.getUserData("", "_ref");
    var title = this.getUserData(id, "_title");
    viewManager.addOrOpenView(ref + id, title, false);


    return true;
}

function invokeAction(action) {
    var form = viewManager.getAction(action);
    var url = form.getItemValue("_ref");

    if (action == "edit") {
        var list = form.getItemValue("_disabled");
        var disabled = list.split(",");

        form.forEachItem(function(id) {
            if (inArray(disabled, id) == -1) {
                form.enableItem(id);
            }
        })
        form.disableItem("edit");
        return;
    }
    if (action == "cancel") {
        if (dhxWins.window(url) != null) {
            dhxWins.window(url).close();
            form.unload();
        } else {
            form.forEachItem(function(id) {
                var type = form.getItemType(id);
                if (type != "block" && type != "fieldset") {
                    form.disableItem(id);
                }
            });
            form.enableItem("edit");
        }

    } else if (action.indexOf("prompt", action.length - "/prompt".length) !== -1) {
        url = url + "/actions/" + action;
        log("inoking action " + action + " target URL" + url);
        viewManager.addOrOpenView(url, "", false);

    } else {
        var oid = form.getItemValue("_oid");
        var target = url;
        /* The action specifies an oid, it referes to an object */
        if (action != "ok") {
            target = target + "/actions/" + action + "/invoke";
        } else {
            if (oid == null) {
                target = target + "/invoke";
            }
        }
        log("inoking action " + action + " target URL" + url);
        form.setItemLabel("_message", "<span class='info'><p>Saving...</p></span>");
        form.send(target, function(loader, response) {
            if (loader.xmlDoc.status == 200) {
                form.setItemLabel("_message", "<span class='success'><p>Record Saved</p></span>");
                setTimeout(function() {
                    viewManager.render(loader.xmlDoc.responseText);
                    viewManager.notify(url);
                    if (dhxWins.window(url) != null) {
                        dhxWins.window(url).close();
                        form.unload();
                    }
                }, 1000);
            } else {
                var errorTag = loader.doXPath("/errors/error");
                for (var i = 0; i < errorTag.length; i++) {
                    var error = tagToObject(errorTag[i]);
                    form.setItemLabel("_message", "<span class='error'><p>Error : " + error.message + "</p></span>");
                    return;
                }
            }
        });
    }
}

function activateView(id) {
    console.log("onSelect", id);
    if (viewManager.isNotified(id)) {
        var label = mainContent.getLabel(id);
        viewManager.addOrOpenView(id, label, true);
        viewManager.removeNotify(id);
    }
    return true;

}

function changeSkin(id) {
    dhtmlxAjax.get("dhx/skin/" + id, function(loader) {
        //alert(loader.xmlDoc.responseText);
        location.href = "/";
    });
}

function tagToObject(tag) {
    var obj = {};
    var d = tag.attributes;
    if (d && d.length) {
        for (var i = 0; i < d.length; i++)
            obj[d[i].name] = d[i].value;
    }
    return obj;
}

function showType(url) {
    //alert("showType: " + url);
    var window = dhxWins.createWindow(url, 0, 0, 400, 420);
    window.center();
    window.setText("Please Wait ...");
    var layout = window.attachLayout("1C", dhtmlx.skin);
    layout.cells('a').hideHeader();
    window.progressOn();
    layout.progressOn();
    layout.attachEvent("onContentLoaded", function() {
        window.setText("Attachment");
        layout.progressOff();
        window.progressOff();
    });

    layout.cells('a').attachURL(url);
    //window.attachHTMLString("<div style='padding: 10px'><img height='380' width='380' src='" + url +"'></img></div>");
}

function inArray(array, elem) {
    var len = array.length;
    for (var i = 0; i < len; i++) {
        if (array[i] == elem) {
            return i;
        }
    }
    return -1;
}

/* Initialize the channel */
function initializeChannel() {
    uuid = computeUUID();
    dhtmlxAjax.get("/_ah/channel/initialize?uuid=" + encodeURI(uuid), function(loader) {
        if (loader.xmlDoc.responseText != null) {
            openChannel(loader.xmlDoc.responseText);
        }
    });
}
/* Open the channel */
function openChannel(token) {
    var channel = new goog.appengine.Channel(token);
    var handler = {
        'onopen': function() {
        },
        'onmessage': onMessage,
        'onerror': function() {
        },
        'onclose': function() {
        }
    };
    var socket = channel.open(handler);
    socket.onopen = function() {
    };
    socket.onmessage = onMessage;
    dhtmlx.message({
        text:"<strong>&nbsp;SAMCRO Ready<strong>",
        expire:1000
    });

}

/* Process the message */
function onMessage(message) {
    //alert("yep : " + message.data );
    var view = viewManager._containers[0].getActiveTab();
    var label = viewManager._containers[0].getLabel(view);
    if (view.indexOf("dhx/") == 0) {
        viewManager.addOrOpenView(view, label, true);
        dhtmlx.message({
            text:"<strong>&nbsp;Data has been updated<strong>",
            expire:1000
        });
        var views = viewManager._containers[0].getAllTabs();
        for (var i = 0; i < views.length; i++) {
            if (view != views[i]) {
                viewManager.addNotify(view);
            }
        }
    }
}


function computeUUID() {
    var d = new Date().getTime();
    var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = (d + Math.random() * 16) % 16 | 0;
        d = Math.floor(d / 16);
        return (c == 'x' ? r : (r & 0x7 | 0x8)).toString(16);
    });
    return uuid;
}

function updateCode(id) {
    var code = editor.getValue();
    codeLayout.cells("a").progressOn();
    codeStatus.setText("Compiling...")
    var view = dhxWins.window("dsl").getText();
    view = view.replace(/dhx\//g, '');

    dhtmlxAjax.post("/dhx/code/" + view, encodeURI("code=" + code), function(response) {
        var annotations = new Array();

        editor.getSession().clearAnnotations();
        var errors = response.doXPath("/errors/error");
        for (var i = 0; i < errors.length; i++) {
            var error = tagToObject(errors[i]);
            annotations.push({
                row: error.line - 1,
                column: error.column,
                text: error.message,
                type: "error"
            });

        }
        editor.getSession().setAnnotations(annotations);
        if (annotations.length > 0) {
            codeStatus.setText("<div style='color:red'>Compilation Error</div>")
        } else {
            codeStatus.setText("Compilation Successful");
            var view = dhxWins.window("dsl").getText();
            var label = viewManager._containers[0].getLabel(view);
            viewManager.addOrOpenView(view, label, true);

        }
        codeLayout.cells("a").progressOff();

    });
}

function showDSL(id) {
    var view = viewManager.currentContainer().getActiveTab();
    if (view != null && view != "a1") {
        var window = dhxWins.createWindow("dsl", 0, 0, 650, 450);
        window.setText(view);
        window.center();
        codeLayout = window.attachLayout("1C", dhtmlx.skin);
        codeLayout.cells("a").hideHeader();
        var toolbar = codeLayout.cells("a").attachToolbar();
        toolbar.loadXML("/toolbar_code.xml?" + new Date().getTime());
        toolbar.setIconsPath(dhtmlx.image_path);
        codeStatus = codeLayout.cells("a").attachStatusBar();


        var div = document.createElement('div');
        document.body.appendChild(div);
        div.id = "code";
        div.style.width = '100%';
        div.style.height = '100%';

        /* Remember attach the div BEFORE create  the Editor */
        codeLayout.cells("a").attachObject(div);
        editor = ace.edit(div);
        editor.setTheme("ace/theme/eclipse");
        editor.getSession().setMode("ace/mode/java");
        codeLayout.cells("a").progressOn();
        codeStatus.setText("Loading...")

        view = view.replace(/dhx\//g, '');
        dhtmlxAjax.get("/dhx/code/" + view + "?" + new Date().getTime(), function(loader) {
            editor.setValue(loader.xmlDoc.responseText);
            editor.clearSelection();
            codeLayout.cells("a").progressOff();
            codeStatus.setText("Code loaded")
        });

    } else {
        alert("Please open a view");
    }

}

function getImageStyle(obj) {
    return "background-image:url(" + obj.img + ");";
}

