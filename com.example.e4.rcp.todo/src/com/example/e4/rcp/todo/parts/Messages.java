package com.example.e4.rcp.todo.parts;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.example.e4.rcp.todo.parts.messages"; //$NON-NLS-1$
	public static String PlaygroundPart_search;
	public static String TodoDetailsPart_description;
	public static String TodoDetailsPart_done;
	public static String TodoDetailsPart_dueDate;
	public static String TodoDetailsPart_question;
	public static String TodoDetailsPart_save;
	public static String TodoDetailsPart_summary;
	public static String TodoDetailsPart_tooltip;
	public static String TodoOverviewPart_0;
	public static String TodoOverviewPart_description;
	public static String TodoOverviewPart_filter;
	public static String TodoOverviewPart_load;
	public static String TodoOverviewPart_loading;
	public static String TodoOverviewPart_summary;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
