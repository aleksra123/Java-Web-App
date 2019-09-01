/*
 * Copyright 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package pt.isep.cms.students.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.Constants;
import pt.isep.cms.client.ShowcaseConstants;
import pt.isep.cms.students.client.presenter.EditStudentPresenter;
import pt.isep.cms.students.server.StudentsServiceImpl;
import pt.isep.cms.modules.client.ModulesService;
import pt.isep.cms.modules.client.ModulesServiceAsync;
import pt.isep.cms.modules.server.ModulesServiceImpl;
import pt.isep.cms.modules.shared.Module;
import pt.isep.cms.modules.shared.ModuleDetails;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import pt.isep.cms.students.server.StudentsServiceImpl;

/**
 * Dialog Box for Adding and Updating students.
 */
public class StudentsDialog implements EditStudentPresenter.Display {


	public enum Type {
		ADD,
		UPDATE
	}

	/**
	 * The constants used in this Content Widget.
	 */
	public static interface CwConstants extends Constants {

		String cwAddStudentDialogCaption();

		String cwUpdateStudentDialogCaption();

//		String cwDialogBoxClose();
//
//		String cwDialogBoxDescription();
//
//		String cwDialogBoxDetails();
//
//		String cwDialogBoxItem();
//
//		String cwDialogBoxListBoxInfo();
//
//		String cwDialogBoxMakeTransparent();
//
//		String cwDialogBoxName();
//
//		String cwDialogBoxShowButton();
	}

	/**
	 * An instance of the constants.
	 */
	private final CwConstants constants;
	private final ShowcaseConstants globalConstants;



	// Widgets
	private final TextBox FirstName;
	private final TextBox LastName;
	private final TextBox Gender;
	private final TextBox BirthDate;
	private final TextBox AddToClass;
	private final Button listButton;

	private final Button saveButton;
	private final Button cancelButton;

	private final FlexTable detailsTable;

	FlexTable itemlist;
	PopupPanel dropdown;
	ScrollPanel scroller;


	List<String> items = new ArrayList<String>();
	String value = null;
	int selectedIndex;


	ModulesServiceAsync modulesService = (ModulesServiceAsync) GWT.create(ModulesService.class);

	public void menuCommandGetModuleDetails() {

		modulesService.getModuleDetails( new AsyncCallback<ArrayList<ModuleDetails>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println("FAILURE");
			}

			@Override
			public void onSuccess(ArrayList<ModuleDetails> result) {
				// TODO Auto-generated method stub

				for(ModuleDetails module: result) {
					addItem(module.getId()+ " "+ module.getDisplayName());
				}

			}


		});
	}

	private void initDetailsTable() {
		detailsTable.setWidget(0, 0, new Label("FirstName"));
		detailsTable.setWidget(0, 1, FirstName);
		detailsTable.setWidget(1, 0, new Label("LastName"));
		detailsTable.setWidget(1, 1, LastName);
		detailsTable.setWidget(2, 0, new Label("Gender"));
		detailsTable.setWidget(2, 1, Gender);
		detailsTable.setWidget(3, 0, new Label("Birthday"));
		detailsTable.setWidget(3, 1, BirthDate);
		detailsTable.setWidget(4, 0, new Label("Add to Class"));
		detailsTable.setWidget(4, 1, AddToClass);
		detailsTable.setWidget(4, 2, listButton);
		listButton.addClickHandler(show);
		FirstName.setFocus(true);

	}

	DecoratorPanel contentDetailsDecorator;
	final DialogBox dialogBox;

	/**
	 * Constructor.
	 *
	 * @param constants
	 *            the constants
	 */
	public StudentsDialog(ShowcaseConstants constants, Type type) {
		// super(constants.cwDialogBoxName(), constants.cwDialogBoxDescription());

		this.constants = constants;
		this.globalConstants = constants;

		// Init the widgets of the dialog
		contentDetailsDecorator = new DecoratorPanel();
		contentDetailsDecorator.setWidth("30em"); // em = size of current font
		// initWidget(contentDetailsDecorator);

		VerticalPanel contentDetailsPanel = new VerticalPanel();
		contentDetailsPanel.setWidth("100%");


		// Create the Students list
		//

		itemlist = new FlexTable();
	    dropdown = new PopupPanel(true);
	    scroller = new ScrollPanel();
	    scroller.add(itemlist);
	    dropdown.add(scroller);

		detailsTable = new FlexTable();
		detailsTable.setCellSpacing(0);
		detailsTable.setWidth("100%");
		detailsTable.addStyleName("Students-ListContainer");
		detailsTable.getColumnFormatter().addStyleName(1, "add-Student-input");
		FirstName = new TextBox();
		LastName = new TextBox();
		Gender = new TextBox();
		BirthDate = new TextBox();
		AddToClass = new TextBox();

		listButton = new Button("Select Class");

		AddToClass.setText("0");

		menuCommandGetModuleDetails();

		initDetailsTable();

		contentDetailsPanel.add(detailsTable);

		HorizontalPanel menuPanel = new HorizontalPanel();
		saveButton = new Button("Save");
		cancelButton = new Button("Cancel");
		menuPanel.add(saveButton);
		menuPanel.add(cancelButton);
		contentDetailsPanel.add(menuPanel);
		contentDetailsDecorator.add(contentDetailsPanel);

		dialogBox = new DialogBox();
		dialogBox.ensureDebugId("cwDialogBox");
		if (type==Type.ADD)
			dialogBox.setText(constants.cwAddStudentDialogCaption());
		else
			dialogBox.setText(constants.cwUpdateStudentDialogCaption());

		dialogBox.add(contentDetailsDecorator);

		dialogBox.setGlassEnabled(true);
		dialogBox.setAnimationEnabled(true);
	}
//
	public void displayDialog() {
		// Create the dialog box
		// final DialogBox dialogBox = createDialogBox();

		dialogBox.center();
		dialogBox.show();
	}
	public ClickHandler show = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            if ( dropdown.isShowing() ) {
                dropdown.hide();
            } else {
                dropdown.setPopupPosition(detailsTable.getAbsoluteLeft() + 160, detailsTable.getAbsoluteTop() + detailsTable.getOffsetHeight());
                for (int i = 0; i < items.size(); i++) {
                    MenuItem menuItem = new MenuItem(items.get(i));
                    itemlist.setWidget(i, 0, menuItem);
                }
                dropdown.show();
            }

        }
    };
		public String getValue() {
        return value;
    }
    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int index) {
        if (index >= 0 && index < items.size() ) {
            value = items.get(index);
            HTML html = new HTML(value);
            html.addStyleName("current-item");
            detailsTable.setWidget(0, 0, html);
            selectedIndex = index;
        }

    }
    	// Database Connection here
		public void addItem(String item) {
			 items.add(item);
			 if ( items.size() == 1 ) {
					 value = item;
					 HTML currentValue = new HTML(value);
					 currentValue.addStyleName("current-item");
			 }
	 }

		public class MenuItem extends Composite {
        public MenuItem (String item) {
            final String itemvalue = item;
            HTML html = new HTML(item);
            initWidget(html);
            html.addStyleName("dropdown-item");
            html.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    value = itemvalue;
                    selectedIndex = items.indexOf(value);
                    HTML html = new HTML(itemvalue);
                    html.addStyleName("current-item");
                    AddToClass.setText(value);
                    dropdown.hide();

                }
            });
        }
    }

	@Override
	public HasClickHandlers getSaveButton() {
		// TODO Auto-generated method stub
		return saveButton;
		// return null;
	}

	@Override
	public HasClickHandlers getCancelButton() {
		// TODO Auto-generated method stub
		return cancelButton;
		// return null;
	}

	@Override
	public HasValue<String> getFirstName() {
		// TODO Auto-generated method stub
		return FirstName;
		// return null;
	}

	@Override
	public HasValue<String> getLastName() {
		// TODO Auto-generated method stub
		return LastName;
		// return null;
	}

	@Override
	public HasValue<String> getGender() {
		// TODO Auto-generated method stub
		return Gender;
		// return null;
	}

	@Override
	public HasValue<String> getBirthDate() {
		// TODO Auto-generated method stub
		return BirthDate;
		// return null;
	}

	@Override
	public HasValue<String> getAddToClass() {
		// TODO Auto-generated method stub
		return AddToClass;
		// return null;
	}


	@Override
	public void show() {
		// TODO Auto-generated method stub
		// return null;
		displayDialog();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		// return null;
		dialogBox.hide();
	}


}
