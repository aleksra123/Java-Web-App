package pt.isep.cms.modules.client.presenter;

import pt.isep.cms.modules.client.ModulesServiceAsync;
import pt.isep.cms.modules.client.event.AddModuleEvent;
import pt.isep.cms.modules.client.event.EditModuleEvent;
import pt.isep.cms.modules.shared.ModuleDetails;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.List;

public class ModulesPresenter implements Presenter {

	private List<ModuleDetails> moduleDetails;

	public interface Display {
		HasClickHandlers getAddButton();

		HasClickHandlers getDeleteButton();

		HasClickHandlers getList();

		void setData(List<String> data);

		int getClickedRow(ClickEvent event);

		List<Integer> getSelectedRows();

		Widget asWidget();
	}

	private final ModulesServiceAsync rpcService;
	private final HandlerManager eventBus;
	private final Display display;

	public ModulesPresenter(ModulesServiceAsync rpcService, HandlerManager eventBus, Display view) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = view;
	}

	public void bind() {
		display.getAddButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new AddModuleEvent());
			}
		});

		display.getDeleteButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				deleteSelectedModules();
			}
		});

		display.getList().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int selectedRow = display.getClickedRow(event);

				if (selectedRow >= 0) {
					String id = moduleDetails.get(selectedRow).getId();
					eventBus.fireEvent(new EditModuleEvent(id));
				}
			}
		});
	}

	public void go(final HasWidgets container) {
		bind();
		container.clear();
		container.add(display.asWidget());

		fetchModuleDetails();
	}

	public void sortModuleDetails() {

		// Yes, we could use a more optimized method of sorting, but the
		// point is to create a test case that helps illustrate the higher
		// level concepts used when creating MVP-based applications.
		//
		for (int i = 0; i < moduleDetails.size(); ++i) {
			for (int j = 0; j < moduleDetails.size() - 1; ++j) {
				if (moduleDetails.get(j).getDisplayName()
						.compareToIgnoreCase(moduleDetails.get(j + 1).getDisplayName()) >= 0) {
					ModuleDetails tmp = moduleDetails.get(j);
					moduleDetails.set(j, moduleDetails.get(j + 1));
					moduleDetails.set(j + 1, tmp);
				}
			}
		}
	}

	public void setModuleDetails(List<ModuleDetails> moduleDetails) {
		this.moduleDetails = moduleDetails;
	}

	public ModuleDetails getModuleDetail(int index) {
		return moduleDetails.get(index);
	}

	private void fetchModuleDetails() {
		rpcService.getModuleDetails(new AsyncCallback<ArrayList<ModuleDetails>>() {
			public void onSuccess(ArrayList<ModuleDetails> result) {
				moduleDetails = result;
				sortModuleDetails();
				List<String> data = new ArrayList<String>();

				for (int i = 0; i < result.size(); ++i) {
					data.add(moduleDetails.get(i).getDisplayName());
				}

				display.setData(data);
			}

			public void onFailure(Throwable caught) {
				Window.alert("Error fetching module details");
			}
		});
	}

	private void deleteSelectedModules() {
		List<Integer> selectedRows = display.getSelectedRows();
		ArrayList<String> ids = new ArrayList<String>();

		for (int i = 0; i < selectedRows.size(); ++i) {
			ids.add(moduleDetails.get(selectedRows.get(i)).getId());
		}

		rpcService.deleteModules(ids, new AsyncCallback<ArrayList<ModuleDetails>>() {
			public void onSuccess(ArrayList<ModuleDetails> result) {
				moduleDetails = result;
				sortModuleDetails();
				List<String> data = new ArrayList<String>();

				for (int i = 0; i < result.size(); ++i) {
					data.add(moduleDetails.get(i).getDisplayName());
				}

				display.setData(data);

			}

			public void onFailure(Throwable caught) {
				Window.alert("Error deleting selected modules");
			}
		});
	}
}
