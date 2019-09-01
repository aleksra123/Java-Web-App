package pt.isep.cms.modules.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.Window;
import pt.isep.cms.modules.client.ModulesServiceAsync;
import pt.isep.cms.modules.client.event.ModuleUpdatedEvent;
import pt.isep.cms.modules.client.event.EditModuleCancelledEvent;
import pt.isep.cms.modules.shared.Module;

public class EditModulePresenter implements Presenter {
	public interface Display {
		HasClickHandlers getSaveButton();

		HasClickHandlers getCancelButton();

		HasValue<String> getName();
		
		HasValue<String> getDescription();
		
		HasValue<String> getEnrolled_Students(); //Enrolled Students

		

		void show();

		void hide();
	}

	private Module module;
	private final ModulesServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;

	public EditModulePresenter(ModulesServiceAsync rpcService, HandlerManager eventBus, Display display) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.module = new Module();
		this.display = display;
		bind();
	}

	public EditModulePresenter(ModulesServiceAsync rpcService, HandlerManager eventBus, Display display, String id) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = display;
		bind();

		rpcService.getModule(id, new AsyncCallback<Module>() {
			public void onSuccess(Module result) {
				module = result;
				EditModulePresenter.this.display.getName().setValue(module.getName());
				EditModulePresenter.this.display.getDescription().setValue(module.getDescription());
				EditModulePresenter.this.display.getEnrolled_Students().setValue(module.getEnrolled_Students());
			}

			public void onFailure(Throwable caught) {
				Window.alert("Error retrieving module");
			}
		});

	}

	public void bind() {
		this.display.getSaveButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doSave();
				display.hide();
			}
		});

		this.display.getCancelButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				display.hide();
				eventBus.fireEvent(new EditModuleCancelledEvent());
			}
		});
	}

	public void go(final HasWidgets container) {
		display.show();
	}

	private void doSave() {
		module.setName(display.getName().getValue());
		module.setDescription(display.getDescription().getValue());
		module.setEnrolled_Students(display.getEnrolled_Students().getValue());


		

		if (module.getId() == null) {
			// Adding new module
			rpcService.addModule(module, new AsyncCallback<Module>() {
				public void onSuccess(Module result) {
					eventBus.fireEvent(new ModuleUpdatedEvent(result));
				}

				public void onFailure(Throwable caught) {
					Window.alert("Error adding module");
				}
			});
		} else {
			// updating existing module
			rpcService.updateModule(module, new AsyncCallback<Module>() {
				public void onSuccess(Module result) {
					eventBus.fireEvent(new ModuleUpdatedEvent(result));
				}

				public void onFailure(Throwable caught) {
					Window.alert("Error updating module");
				}
			});
		}
	}

}
