package com.example.e4.rcp.todo.lifecycle;

import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import com.example.e4.rcp.todo.dialogs.PasswordDialog;

public class Manager {

	@PostContextCreate
	void postContextCreate(IApplicationContext appContext, Display display) {
		final Shell shell = new Shell(SWT.TOOL | SWT.NO_TRIM);
		PasswordDialog pDial = new PasswordDialog(shell);
		appContext.applicationRunning();

		setLocation(display, shell);

		if (pDial.open() != Window.OK) {
			System.exit(-1);
		}
	}

	private void setLocation(Display display, Shell shell) {
		Monitor monitor = display.getPrimaryMonitor();
		Rectangle monitorRect = monitor.getBounds();
		Rectangle shellRect = shell.getBounds();
		int x = monitorRect.x + (monitorRect.width - shellRect.width) / 2;
		int y = monitorRect.y + (monitorRect.height - shellRect.height) / 2;
		shell.setLocation(x, y);

	}

}
