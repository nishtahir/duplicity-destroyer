package com.wolfden.java.duplicitydestroyer;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class DuplicityDestroyer {

	private Shell shell;
	private Text text;
	private Table table;
	private TableColumn tblclmnName;
	private TableColumn tblclmnSize;
	private TableColumn tblclmnDateModified;
	private TableColumn tblclmnPath;
	private Button btnNewButton_1;
	private Button btnNewButton_2;
	private Combo combo;

	File[] filesInDir;

	public DuplicityDestroyer() {
		Display display = new Display();
		shell = new Shell(display);

		initComponents(shell);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();

	}

	public static void main(String[] args) {
		new DuplicityDestroyer();
	}

	private void initComponents(final Shell shell) {
		shell.setLayout(new GridLayout(4, false));

		Label lblPath = new Label(shell, SWT.NONE);
		lblPath.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		lblPath.setText("Path");

		text = new Text(shell, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		combo = new Combo(shell, SWT.NONE);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1));

		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(shell, SWT.OPEN);

				// Set the initial filter path according
				// to anything they've selected or typed in
				dialog.setFilterPath(text.getText());

				// Change the title bar text
				// dlg.setText("SWT's DirectoryDialog");

				// Customizable message displayed in the dialog
				// dlg.setMessage("Select a directory");

				// Calling open() will open and run the dialog.
				// It will return the selected directory, or
				// null if user cancels
				String dir = dialog.open();
				if (dir != null) {
					// Set the text box to the new selection
					text.setText(dir);
					File mFile = new File(dir);
					updateTable(FileUtils.getFilesFromDir(mFile));
				}

			}
		});
		btnNewButton.setText("Browse...");

		table = new Table(shell, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		tblclmnName = new TableColumn(table, SWT.NONE);
		tblclmnName.setWidth(200);
		tblclmnName.setText("Name");

		tblclmnSize = new TableColumn(table, SWT.NONE);
		tblclmnSize.setWidth(100);
		tblclmnSize.setText("Size");

		tblclmnDateModified = new TableColumn(table, SWT.NONE);
		tblclmnDateModified.setWidth(100);
		tblclmnDateModified.setText("Date Modified");

		tblclmnPath = new TableColumn(table, SWT.NONE);
		tblclmnPath.setWidth(400);
		tblclmnPath.setText("Path");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);

		btnNewButton_2 = new Button(shell, SWT.NONE);
		btnNewButton_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		btnNewButton_2.setText("New Button");

		btnNewButton_1 = new Button(shell, SWT.NONE);
		btnNewButton_1.setText("New Button");

		table.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				String string = event.detail == SWT.CHECK ? "Checked"
						: "Selected";
				System.out.println(event.item + " " + string);
			}
		});
	}

	private void updateTable(File[] files) {
		for (File file : files) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, file.getName());
			item.setText(1, String.valueOf(file.length()));
			item.setText(2, String.valueOf(file.lastModified()));
			item.setText(3, String.valueOf(file.getPath()));
		}
	}

}
