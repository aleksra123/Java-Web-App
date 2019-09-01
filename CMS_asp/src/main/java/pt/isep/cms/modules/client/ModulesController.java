package pt.isep.cms.modules.client;

import pt.isep.cms.client.ShowcaseConstants;

import pt.isep.cms.modules.client.event.AddModuleEvent;
import pt.isep.cms.modules.client.event.AddModuleEventHandler;
import pt.isep.cms.modules.client.event.ModuleUpdatedEvent;
import pt.isep.cms.modules.client.event.ModuleUpdatedEventHandler;
import pt.isep.cms.modules.client.event.EditModuleEvent;
import pt.isep.cms.modules.client.event.EditModuleEventHandler;
import pt.isep.cms.modules.client.event.EditModuleCancelledEvent;
import pt.isep.cms.modules.client.event.EditModuleCancelledEventHandler;
import pt.isep.cms.modules.client.presenter.ModulesPresenter;
import pt.isep.cms.modules.client.presenter.EditModulePresenter;
import pt.isep.cms.modules.client.presenter.Presenter;
import pt.isep.cms.modules.client.view.ModulesView;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.ui.HasWidgets;

import pt.isep.cms.modules.client.view.ModulesDialog;

public class ModulesController implements Presenter { // (ATB) No history at this level, ValueChangeHandler<String> {
	private final HandlerManager eventBus;
	private final ModulesServiceAsync rpcService;
	private HasWidgets container;

	public static interface CwConstants extends Constants {
	}

	/**
	 * An instance of the constants.
	 */
	private final CwConstants constants;
	private final ShowcaseConstants globalConstants;

	public ModulesController(ModulesServiceAsync rpcService, HandlerManager eventBus, ShowcaseConstants constants) {
		this.eventBus = eventBus;
		this.rpcService = rpcService;
		this.constants = constants;
		this.globalConstants = constants;

		bind();
	}

	private void bind() {
		// (ATB) No History at this level
		// History.addValueChangeHandler(this);

		eventBus.addHandler(AddModuleEvent.TYPE, new AddModuleEventHandler() {
			public void onAddModule(AddModuleEvent event) {
				doAddNewModule();
			}
		});

		eventBus.addHandler(EditModuleEvent.TYPE, new EditModuleEventHandler() {
			public void onEditModule(EditModuleEvent event) {
				doEditModule(event.getId());
			}
		});

		eventBus.addHandler(EditModuleCancelledEvent.TYPE, new EditModuleCancelledEventHandler() {
			public void onEditModuleCancelled(EditModuleCancelledEvent event) {
				doEditModuleCancelled();
			}
		});

		eventBus.addHandler(ModuleUpdatedEvent.TYPE, new ModuleUpdatedEventHandler() {
			public void onModuleUpdated(ModuleUpdatedEvent event) {
				doModuleUpdated();
			}
		});
	}

	private void doAddNewModule() {
		// Lets use the presenter to display a dialog...
		Presenter presenter = new EditModulePresenter(rpcService, eventBus, new ModulesDialog(globalConstants, ModulesDialog.Type.ADD));
		presenter.go(container);

	}

	private void doEditModule(String id) {
		Presenter presenter = new EditModulePresenter(rpcService, eventBus, new ModulesDialog(globalConstants, ModulesDialog.Type.UPDATE), id);
		presenter.go(container);
	}

	private void doEditModuleCancelled() {
		// Nothing to update...
	}

	private void doModuleUpdated() {
		// (ATB) Update the list of modules...
		Presenter presenter = new ModulesPresenter(rpcService, eventBus, new ModulesView());
		presenter.go(container);
	}

	public void go(final HasWidgets container) {
		this.container = container;

		Presenter presenter = new ModulesPresenter(rpcService, eventBus, new ModulesView());
		presenter.go(container);
	}

}
