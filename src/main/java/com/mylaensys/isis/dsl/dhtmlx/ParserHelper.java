package com.mylaensys.isis.dsl.dhtmlx;

import com.mylaensys.isis.viewer.dhtmlx.desktop.ComponentRenderException;
import com.mylaensys.isis.viewer.dhtmlx.desktop.component.*;
import com.mylaensys.isis.viewer.dhtmlx.servlet.utils.Utils;
import org.antlr.v4.runtime.*;
import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.core.metamodel.services.ServiceUtil;
import org.apache.isis.core.metamodel.spec.feature.ObjectAction;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ParserHelper {
    private static int PROMPT = 1;
    private static int ACTION = 2;
    private static int INSTANCE = 3;

    private int mode;
    private String refPrefix;
    private Stack containers;
    private Object component;
    private Form form;
    /* Like a symbol table */
    private ObjectAdapter objectAdapter;
    private ObjectAction objectAction;
    private ObjectAdapter objectInstance;
    private List<ParserError> errors;

    private int index;


    private static final Logger LOG = Logger.getLogger(ParserHelper.class);

    public ParserHelper() {
        this.index = 0;
        this.containers = new Stack();
        this.errors = new ArrayList<ParserError>();
    }

    /**
     * Push on the stack
     *
     * @param component
     */
    private void push(Object component) {
        if (containers.empty()) {
            this.component = component;
        }
        containers.push(component);
    }

    /**
     * Pop from the stack
     */
    private Object pop() {
        if (!containers.empty()) {
            return containers.pop();
        }
        return component;
    }

    /**
     * Get current object on the stack
     *
     * @return the instance of the object on the stack
     */
    private Object getCurrent() {
        if (!containers.empty()) {
            return containers.peek();
        }
        return component;
    }

    /**
     * Set an attribute to the current object
     *
     * @param attribute name of the attribute
     * @param value     value of the attribute
     */
    public void setAttribute(Token attribute, String value) {
        Object current = getCurrent();
        if (current != null) {
            String name = attribute.getText();
            String setter = String.format("set%C%s", name.charAt(0), name.substring(1));
            LOG.debug("(" + attribute.getLine() + "," + attribute.getCharPositionInLine() + ") setting attribute : " + name + " = " + value + " to " + current);
            try {
                Method m = current.getClass().getMethod(setter, value.getClass());
                m.invoke(current, value);
            } catch (NoSuchMethodException ex) {
                LOG.info(current + " has no property " + name);
            } catch (InvocationTargetException e) {
                LOG.error(e.getMessage() + " invoking " + setter + " on " + current);
            } catch (IllegalAccessException e) {
                LOG.error(e.getMessage() + " invoking " + setter + " on " + current);
            }
        }
    }

    /**
     * Set String attribute
     * @param attribute
     * @param value
     */
    public void setStringAttribute(Token attribute, String value) {
        setAttribute( attribute,value.replaceAll("\"",""));
    }

    private boolean isPrompt() {
        return (this.mode == PROMPT);
    }
    private boolean isInstance() {
        return (this.mode == INSTANCE);
    }
    private boolean isAction() {
        return (this.mode == ACTION);
    }

    public List<ParserError> getErrors() {
        return errors;
    }

    /**
     * Print a list of values
     *
     * @param columns
     * @return a comma separated list of values;
     */
    private String toString(List<String> columns) {
        StringBuffer buffer = new StringBuffer();
        for (String column : columns) {
            buffer.append(column).append(",");
        }
        if (buffer.length() > 0) {
            return buffer.substring(0, buffer.length() - 1);
        }
        return buffer.toString();
    }


    /**
     * Create a layout container
     *
     * @param layout pattern of the layout
     */
    public void createLayout(Token layout) {
        String pattern = layout.getText();
        LOG.debug("(" + layout.getLine() + "," + layout.getCharPositionInLine() + ") creating Layout : " + pattern);
        push(new Layout(pattern));
    }

    /**
     * Add content to the container
     *
     * @param token token referring the closing bracket
     */
    public void addContent(Token token) {
        Object component = pop();
        if (!containers.isEmpty()) {
            ContainerCell cell = (ContainerCell) getCurrent();
            LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") adding : " + component + " to " + cell);
            if (component instanceof Layout) {
                cell.setLayout((Layout) component);
            }
            if (component instanceof Tabbar) {
                cell.setTabbar((Tabbar) component);
            }

        } else {
            LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") closing container : " + component);
        }

    }


    /**
     * Creates a cell
     *
     * @param cell id of the cell
     */
    public void createLayoutCell(Token cell) {
        String id = cell.getText();
        LOG.debug("(" + cell.getLine() + "," + cell.getCharPositionInLine() + ") creating layour cell : " + id);
        push(new LayoutCell(id));
    }

    /**
     * Add a cell to the current container
     *
     * @param token
     */

    public void addLayoutCell(Token token) {
        LayoutCell cell = (LayoutCell) pop();
        Layout layout = (Layout) getCurrent();
        layout.getCells().add(cell);
        LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") adding cell : " + cell + " to " + layout);
    }

    /**
     * Creates a toolbar
     *
     * @param token place holder for line numbers
     */
    public void createToolbar(Token token) {
        LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") creating a toolbar");
        push(new Toolbar());
    }

    /**
     * Add a toolbar to the current container
     *
     * @param token
     */
    public void addToolbar(Token token) {
        Toolbar toolbar = (Toolbar) pop();
        ContainerCell cell = (ContainerCell) getCurrent();
        LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") adding toolbar " + toolbar + " to " + cell);
        cell.setToolbar(toolbar);
    }

    /**
     * Adds an item to the toolbar
     *
     * @param token
     */
    public void addToolbarItem(Token token) {
        Toolbar toolbar = (Toolbar) getCurrent();

        switch (token.getType()) {
            case dhtmlxParser.ID:
                if( isInstance() ) {
                    String name = token.getText();
                    try {
                        FormItem action = this.form.getAction(name);
                        toolbar.addButton( action.getName() , action.getValue() );
                        toolbar.get( action.getName() ).setAction("invokeAction");
                        if( action.isDisabled() ) {
                            toolbar.get( action.getName() ).setEnabled("false");
                        }
                        LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") adding action " + action.getName() + " item to " + toolbar);
                    } catch (ComponentRenderException e) {
                        LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") the object does not have a " + name + " action ");
                        errors.add( new ParserError( token.getLine(),token.getCharPositionInLine() , " the object does not have a '" + name + "' action " ));
                    }
                }
                break;

            /* Not supported by DHTMLX,  */
            case dhtmlxParser.SPACER:
                LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") adding SPACER item to " + toolbar);
                toolbar.addSeparator("spacer_" + (index++));
                break;

            case dhtmlxParser.SEPARATOR:
                LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") adding SEPARATOR item to " + toolbar);
                toolbar.addSeparator("sep_" + (index++));
                break;

            case dhtmlxParser.STRING:
                LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") adding TEXT item to " + toolbar);
                toolbar.addText("txt_" + (index++), token.getText().replaceAll("\"", ""));
                break;

        }
//        } catch (InvalidObjectException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (InvalidActionException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }


    }

    /**
     * Creates a status bar
     *
     * @param token place holder for line numbers
     */
    public void createStatusBar(Token token) {
        LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") creating a status bar");
        push(new StatusBar());
    }

    /**
     * Add a status to the current container
     *
     * @param token
     */
    public void addStatusBar(Token token) {
        StatusBar status = (StatusBar) pop();
        ContainerCell cell = (ContainerCell) getCurrent();
        LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") adding toolbar " + status + " to " + cell);
    }

    /**
     * Creates a tabbar
     *
     * @param token
     */

    public void createTabbar(Token token) {
        LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") creating a tabbar");
        push(new Tabbar());
    }

    /**
     * Creates a tab
     *
     * @param token
     */
    public void createTabCell(Token token) {
        String id = token.getText();
        LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") creating a tab cell " + id);
        push(new TabbarCell(id));
    }

    /**
     * Creates a tab
     *
     * @param token
     */
    public void addTabCell(Token token) {
        TabbarCell tab = (TabbarCell) pop();
        Tabbar tabbar = (Tabbar) getCurrent();
        tabbar.getTabs().add(tab);
        LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") adding cell : " + tab + " to " + tabbar);
    }

    /**
     * @param columns
     */
    public void createGrid(Token token, List<String> columns) {
        LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") creating a grid [" + toString(columns) + "]");
        push(new GridReference(toString(columns)));

    }


    public void createDataView(Token token) {
        String template = token.getText().replaceAll("\"", "");
        LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") creating a dataview");
        push(new DataViewReference(template));
    }


    public void addCollection(Token token, String actionOrProperty) {
        Object collection = pop();
        String url = "";
        if (getCurrent() instanceof ContainerCell) {
            if (actionOrProperty == null) {
                if(isAction()) {
                    url = this.refPrefix + ServiceUtil.id(objectAdapter.getObject()) + "/actions/" + this.objectAction.getId() + "/invoke?";
                }
                //url = "services/" + this.object + "/actions/" + this.action + "/invoke?";
            } else {
                if( actionOrProperty.indexOf("()") > -1 ) {

                } else {
                    String oid = Utils.computeOid(this.objectInstance.getOid().enString(IsisContext.getOidMarshaller()));
                    url = this.refPrefix + "object/" + objectInstance.getSpecification().getFullIdentifier() + "/" + oid + "/collections/" + actionOrProperty + "?";
                }
            }


            ContainerCell cell = (ContainerCell) getCurrent();
            LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") adding a " + collection + " to " + cell);
            if (collection instanceof GridReference) {
                GridReference gridCollection = (GridReference) collection;
                gridCollection.setRef(url);
                cell.setGridCellection(gridCollection);
            }
            if (collection instanceof DataViewReference) {
                LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") adding a " + collection + " to " + cell);
                DataViewReference dataViewCollection = (DataViewReference) collection;
                dataViewCollection.setRef(url);
                cell.setDataViewCollection(dataViewCollection);
            }

        } else if (getCurrent() instanceof ContainerForm) {
            ContainerForm cell = (ContainerForm) getCurrent();
            LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") adding a " + collection + " to " + cell);
        }

    }

    // todo remove
    public void addPrompt(Token token) {
        //FormReference prompt = new FormReference();
        //prompt.setRef( "services/" + this.object + "/actions/" + this.action  );
        //ContainerCell cell = (ContainerCell) getCurrent();
        //LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") adding a " + prompt + " to " + cell);
        // cell.setPrompt(prompt);
    }


    public void createSettings(Token token) {
        LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") creating form settings ");
        push(new FormSettings());
    }

    public void addSettings(Token token) {
        FormSettings settings = (FormSettings) pop();
        Form form = (Form) getCurrent();
        form.setSettings(settings);
        LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") adding settings to " + form);
    }

    public void createBlock(Token token, Token label) {
        String name = "block_" + (index++);

        Object current = getCurrent();
        if (current instanceof Form) {
            FormItem block = label == null ? new FormItem(name, "block", null, null) : new FormItem(name, "fieldset", label.getText().replaceAll("\"", ""), null);
            LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") creating a  " + block + " " + name);
            push(block);
        } else {
            if (isPrompt()) {
                /* Creates the actual form */
                Form form = new Form();
                /* Create the hidden field with the target url to invoke */
                form.addHidden("_ref", this.refPrefix + ServiceUtil.id(objectAdapter.getObject()) + "/actions/" + this.objectAction.getId() );

                LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") creating a form " + form);
                /* The form renders the command button within a actions label */
                try {
                    /* Create the message area */
                    form.addHidden("_prompt","");
                    form.addLabel("_message","&#160;");
                    form.get( "_message" ).setLabelWidth("auto");
                    form.get( "_message" ).setLabelHeight("30");

                    FormItem actions = this.form.get("actions");
                    for(FormItem item : actions.getItems()) {
                        if(item.getType().equalsIgnoreCase("button")) {
                            form.addHidden( "_" + item.getName() , item.getName() );
                        }
                    }
                } catch (ComponentRenderException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                push(form);

            }
            if (isInstance()) {
                /* Creates the actual form */
                Form form = new Form();
                /* Create the hidden field with the target url to invoke */
                //form.addHidden("_ref", this.refPrefix + ServiceUtil.id(objectAdapter.getObject()) + "/actions/" + this.objectAction.getId() + "/invoke");
                String oid = Utils.computeOid(objectInstance.getOid().enString(IsisContext.getOidMarshaller()));
                form.addHidden( "_oid" , oid );
                /* Place holder to save the status */
                form.addHidden( "_disabled" , "" );
                form.addHidden("_ref", this.refPrefix + "object/" + objectInstance.getSpecification().getFullIdentifier() + "/" + oid  );
                /** add some hidden for action reference */
                try {
                    FormItem actions = this.form.get("actions");
                    for(FormItem item : actions.getItems()) {
                        if(item.getType().equalsIgnoreCase("button")) {
                            form.addHidden( "_" + item.getName() , item.getName() );
                        }
                    }
                } catch (ComponentRenderException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") creating a form " + form);
                push(form);

            }


        }

    }

    public void addBlock(Token token) {
        Object current = getCurrent();

        if (current instanceof Form) {
            Form form = (Form) pop();
            Object container = getCurrent();
            /* the form is with in a container */
            if( container instanceof  ContainerCell) {
                ((ContainerCell)(container)).setForm(form);
                LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") adding form to " + container);
            } else {
                /* Used to wrap a form without container in <form></form> */
                this.component = new ContainerForm(form);
            }

        }

        if (current instanceof FormItem) {
            FormItem block = (FormItem) pop();
            FormContent container = (FormContent ) getCurrent();
            container.getItems().add(block);
            LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") adding block to " + container);
        }

    }

    public void addFormWidget(int line,int column,String name) {
        FormContent  container = (FormContent ) getCurrent();
        name = name.replaceAll(";", "");
        /* detects property/action */
        boolean message = false;


            if (name.indexOf("()") > -1) {
                try {
                    name = name.substring(0, name.indexOf("()"));
                    FormItem property = this.form.getAction(name);
                    container.getItems().add(property);
                } catch (ComponentRenderException e) {
                    // todo ad to error list
                    LOG.debug("Form " + container + " does not have " + name + " action");
                    errors.add( new ParserError( line, column , " the object does not have a '" + name + "' action" ));
                }
            } else {
                try {
                    FormItem property = this.form.getProperty(name);
                    container.getItems().add(property);
                } catch (ComponentRenderException e) {
                    // todo ad to error list
                    LOG.debug("Form " + container + " does not have " + name + " action");
                    errors.add( new ParserError( line, column, " the object does not have a '" + name + "' property" ));
                }
            }
        LOG.debug("(" + ") adding widget " + name + " to " + container);

    }

    public void addFormCollection(Token token, String name, List<String> columns) {
        ContainerForm container = (ContainerForm) getCurrent();
        LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") adding collection to " + container);
    }

    public void addNewColumn(Token token) {
        FormContent  container = (FormContent) getCurrent();
        String name = "sep_" + (index++);
        container.getItems().add(new FormItem(name, "newcolumn", null, null));
        LOG.debug("(" + token.getLine() + "," + token.getCharPositionInLine() + ") adding new column to " + container);
    }

    Object parse(String code) throws Exception {
        CharStream input = new ANTLRInputStream(code);
        dhtmlxLexer lexer = new dhtmlxLexer(input);
        TokenStream tokens = new CommonTokenStream(lexer);
        dhtmlxParser parser = new dhtmlxParser(tokens);

        ParserErrorListener errorListener = new ParserErrorListener(this);
        parser.addErrorListener(errorListener);

        parser.helper = this;
        parser.dhtmlx().view();

        return component;
    }

    /**
     * Parse the DSL for a prompt
     * Render a user interface based on the action paramters
     *
     * @param code
     * @param object
     * @param action
     */
    public Object parseForServicePrompt(String code, String object, String action) throws Exception {
        this.mode = PROMPT;
        this.refPrefix = "dhx/services/";
        this.objectAdapter = Utils.findService(object);
        this.objectAction = Utils.findAction(objectAdapter, action);
        /* Creates the form from the paramters */
        this.form = (Form) new Form().acceptObject(this.objectAdapter, this.objectAction);

        return this.parse(code);
    }


    public Object parseForServiceAction(String code, String object, String action) throws Exception {
        this.mode = ACTION;
        this.refPrefix = "dhx/services/";
        this.objectAdapter = Utils.findService(object);
        this.objectAction = Utils.findAction(objectAdapter, action);
        return this.parse(code);
    }

    public Object parseForObjectInstance(String code, ObjectAdapter instance) throws Exception {
        this.mode = INSTANCE;
        this.refPrefix = "dhx/objects/";
        this.objectInstance = instance;
        /* Creates the form from the instance object*/
        this.form = (Form) new Form().acceptInstance(this.objectInstance);


        return this.parse(code);
    }

}

