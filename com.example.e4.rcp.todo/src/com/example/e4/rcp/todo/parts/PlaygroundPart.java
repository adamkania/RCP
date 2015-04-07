package com.example.e4.rcp.todo.parts;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.example.e4.bundleresourceloader.IBundleResourceLoader;

public class PlaygroundPart {

	@Inject
	private IBundleResourceLoader resourceLoader;
	private Text txtCity;

	@PostConstruct
	public void postConstruct(Composite parent) {
		parent.setLayout(new GridLayout(15, false));

		txtCity = new Text(parent, SWT.BORDER);
		txtCity.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				14, 1));

		Button btnSearch = new Button(parent, SWT.NONE);
		btnSearch.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		Browser browser = new Browser(parent, SWT.NONE);
		GridData gd_browser = new GridData(SWT.FILL, SWT.FILL, true, true, 15,
				1);
		gd_browser.widthHint = 440;
		gd_browser.heightHint = 253;
		browser.setLayoutData(gd_browser);

		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String city = txtCity.getText();
				if (city.isEmpty()) {
					return;
				}
				try {
					browser.setUrl("http://maps.google.com/maps?q="
							+ URLEncoder.encode(city, "UTF-8"));
				} catch (UnsupportedEncodingException ueEx) {
					ueEx.printStackTrace();
				}
			}
		});
		btnSearch.setText("Search");

	}

	@Focus
	public void onFocus() {
		txtCity.setFocus();
	}
}