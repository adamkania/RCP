package com.example.e4.rcp.todo.contribute.processors;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuFactory;

import com.example.e4.rcp.todo.contribute.handlers.ExitHandlerWithCheck;

public class MenuProcessor {

	@Inject
	@Named("com.example.e4.rcp.todo.menu.file")
	private MMenu menu;

	@Execute
	public void execute() {
		if (menu != null && menu.getChildren() != null) {
			List<MMenuElement> list = new ArrayList<MMenuElement>();

			for (MMenuElement elem : menu.getChildren()) {
				System.out.println(elem);
				if (elem.getElementId() != null) {
					if (elem.getElementId().contains("exit")) {
						list.add(elem);
					}
				}
			}
			menu.getChildren().removeAll(list);

			MDirectMenuItem menuItem = MMenuFactory.INSTANCE
					.createDirectMenuItem();
			menuItem.setLabel("Another Exit");
			menuItem.setContributionURI("bundleclass://com.example.e4.rcp.todo.contribute/"
					+ ExitHandlerWithCheck.class.getName());
			
			menu.getChildren().add(menuItem);

		}
	}
}
