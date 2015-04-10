package com.example.e4.rcp.todo.contribute.dialogs;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.e4.ui.services.IServiceConstants;

public class ExitDialog extends Dialog {

	@Inject
	public ExitDialog(@Named(IServiceConstants.ACTIVE_SHELL) Shell shell) {
		super(shell);
	}
	
	@Override
	protected Control createDialogArea(Composite parent){
		
		Label label = new Label(parent, SWT.NONE);
		label.setText("Closing this application may result in data loss. Are you sure ?");
		
		return parent;
	}

}
