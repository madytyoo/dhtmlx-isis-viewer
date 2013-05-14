/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package fixture.todo;

import dom.todo.ToDoItem;
import dom.todo.ToDoItem.Category;
import dom.todo.ToDoItems;
import org.apache.isis.applib.fixtures.AbstractFixture;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ToDoItemsFixture extends AbstractFixture {

    @Override
    public void install() {

        removeAllToDosForCurrentUser();

        createToDoItemForCurrentUser("Buy milk", Category.Domestic, daysFromToday(0));
        createToDoItemForCurrentUser("Buy stamps", Category.Domestic, daysFromToday(0));
        createToDoItemForCurrentUser("Pick up laundry", Category.Other, daysFromToday(6));
        createToDoItemForCurrentUser("Write blog post", Category.Professional, null);
        ToDoItem workOnIsis = createToDoItemForCurrentUser("Work on Isis Viewer", Category.Professional, daysFromToday(14));


        createToDoItemForCurrentUser("Buy food", Category.Domestic, true, daysFromToday(-2));
        createToDoItemForCurrentUser("Write to Dan", Category.Professional, null,workOnIsis);

        getContainer().flush();
    }



    // {{ helpers
    private void removeAllToDosForCurrentUser() {
        
        final List<ToDoItem> allToDos = toDoItems.allToDos();
        for (final ToDoItem toDoItem : allToDos) {
            getContainer().remove(toDoItem);
        }
    }

    private void removeAllToDosFor(String user) {
        final List<ToDoItem> items = allMatches(ToDoItem.class, ToDoItem.thoseOwnedBy(user));
        for (final ToDoItem toDoItem : items) {
            getContainer().remove(toDoItem);
        }
    }

    private ToDoItem createToDoItemForCurrentUser(final String description, final Category category, final Date dueBy) {
        return toDoItems.newToDo(description, category, dueBy);
    }

    private ToDoItem createToDoItemForCurrentUser(final String description, final Category category, Boolean status,final Date dueBy) {
        return toDoItems.newToDo(description, category, status , dueBy);
    }

    private ToDoItem createToDoItemForUser(final String description, final Category category, String user, final Date dueBy) {
        return toDoItems.newToDo(description, category, user, dueBy);
    }
    private ToDoItem createToDoItemForCurrentUser(final String description, final Category category, final Date dueBy, ToDoItem dependency) {
        return toDoItems.newToDo(description, category, dueBy,dependency);
    }


    private static Date daysFromToday(final int i) {
        final Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, i); //minus number would decrement the days
        return cal.getTime();
    }

    // }}

    // {{ injected: ToDoItems
    private ToDoItems toDoItems;

    public void setToDoItems(final ToDoItems toDoItems) {
        this.toDoItems = toDoItems;
    }
    // }}
    

}