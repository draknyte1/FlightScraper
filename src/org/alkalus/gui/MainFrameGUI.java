package org.alkalus.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.GridLayout;
import javax.swing.JCheckBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.alkalus.gui.listeners.CloseButtonListener;
import org.alkalus.gui.listeners.RunButtonListener;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.event.ChangeEvent;
import javax.swing.JSeparator;
import javax.swing.JFormattedTextField;
import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class MainFrameGUI {

	JFrame frame_Grand_Sky_Scraper;
	JFormattedTextField field_Text_Departure_Code;
	JFormattedTextField field_Text_Arrival_Code;
	JFormattedTextField field_Integer_Day_Start_Scan;
	JFormattedTextField field_Integer_Day_Finish_Scan;
	JComboBox combo_Month_Start;
	JComboBox combo_Month_Finish;
	JComboBox combo_Year_Start;
	JComboBox combo_Year_Finish;
	boolean isUsingExtendedDates = false;
	private JLabel label_Info_AreWeReady;
	private JTextArea textbox_System_Log;
	private JCheckBox stopRunningIfTimeOut;
	private JCheckBox checkbox_Info_ExtendedSearch;
	private JProgressBar progressBar;

	private static final String[] MONTHS = new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	private static final String[] YEARS = new String[] {"2018", "2019", "2020"};

	private boolean mLockGUI = false;
	public synchronized void toggleLockGUI() {		
		if (mLockGUI) {
			field_Text_Departure_Code.setEditable(true);
			field_Text_Arrival_Code.setEditable(true);
			field_Integer_Day_Start_Scan.setEditable(true);
			field_Integer_Day_Finish_Scan.setEditable(true);
			combo_Month_Start.setEnabled(true);
			combo_Month_Finish.setEnabled(true);
			combo_Year_Start.setEnabled(true);
			combo_Year_Finish.setEnabled(true);
			checkbox_Info_ExtendedSearch.setEnabled(true);
			mLockGUI = false;			
		}
		else {
			field_Text_Departure_Code.setEnabled(false);
			field_Text_Arrival_Code.setEnabled(false);
			field_Integer_Day_Start_Scan.setEnabled(false);
			field_Integer_Day_Finish_Scan.setEnabled(false);
			combo_Month_Start.setEnabled(false);
			combo_Year_Start.setEnabled(false);
			if (combo_Month_Finish.isEnabled() == true) combo_Month_Finish.setEnabled(false);
			if (combo_Year_Finish.isEnabled() == true) combo_Year_Finish.setEnabled(false);
			checkbox_Info_ExtendedSearch.setEnabled(false);
			mLockGUI = true;
		}	
	}

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrameGUI window = new MainFrameGUI();
					window.frame_Grand_Sky_Scraper.setVisible(true);
					if (window.frame_Grand_Sky_Scraper.isVisible()) {
					      //FlightScraper.mIsMainFrameVisible = true;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	 
	}*/

	/**
	 * Create the application.
	 */
	public MainFrameGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private synchronized void initialize() {
		frame_Grand_Sky_Scraper = new JFrame();
		frame_Grand_Sky_Scraper.setTitle("Alkalus' Grand Sky Scraper");
		frame_Grand_Sky_Scraper.setBounds(100, 100, 620, 400);
		frame_Grand_Sky_Scraper.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame_Grand_Sky_Scraper.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new RunButtonListener());
		panel.add(btnRun);

		progressBar = new JProgressBar();
		progressBar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				if (arg0 != null) {
					if (arg0.getPropertyName().toLowerCase().contains("value")) {
						progressBar.repaint();
					}
				}
			}
		});
		progressBar.setForeground(Color.ORANGE);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		panel.add(progressBar);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new CloseButtonListener());
		panel.add(btnExit);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame_Grand_Sky_Scraper.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Main", null, panel_1, null);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JSeparator separator_3 = new JSeparator();
		GridBagConstraints gbc_separator_3 = new GridBagConstraints();
		gbc_separator_3.insets = new Insets(0, 0, 5, 5);
		gbc_separator_3.gridx = 3;
		gbc_separator_3.gridy = 0;
		panel_1.add(separator_3, gbc_separator_3);

		JLabel SCPAING_TOP = new JLabel("");
		GridBagConstraints gbc_SCPAING_TOP = new GridBagConstraints();
		gbc_SCPAING_TOP.insets = new Insets(0, 0, 5, 5);
		gbc_SCPAING_TOP.gridx = 2;
		gbc_SCPAING_TOP.gridy = 1;
		panel_1.add(SCPAING_TOP, gbc_SCPAING_TOP);

		JSeparator separator_4 = new JSeparator();
		GridBagConstraints gbc_separator_4 = new GridBagConstraints();
		gbc_separator_4.insets = new Insets(0, 0, 5, 5);
		gbc_separator_4.gridx = 0;
		gbc_separator_4.gridy = 2;
		panel_1.add(separator_4, gbc_separator_4);

		JLabel SPACING_LEFT = new JLabel("");
		GridBagConstraints gbc_SPACING_LEFT = new GridBagConstraints();
		gbc_SPACING_LEFT.insets = new Insets(0, 0, 5, 5);
		gbc_SPACING_LEFT.anchor = GridBagConstraints.EAST;
		gbc_SPACING_LEFT.gridx = 1;
		gbc_SPACING_LEFT.gridy = 2;
		panel_1.add(SPACING_LEFT, gbc_SPACING_LEFT);

		field_Text_Departure_Code = new JFormattedTextField();
		field_Text_Departure_Code.setHorizontalAlignment(SwingConstants.CENTER);
		field_Text_Departure_Code.setText("MKY");
		GridBagConstraints gbc_txtMky = new GridBagConstraints();
		gbc_txtMky.insets = new Insets(0, 0, 5, 5);
		gbc_txtMky.anchor = GridBagConstraints.EAST;
		gbc_txtMky.gridx = 2;
		gbc_txtMky.gridy = 2;
		panel_1.add(field_Text_Departure_Code, gbc_txtMky);
		field_Text_Departure_Code.setColumns(10);

		JLabel lblDepartureCityCode = new JLabel("  Departure city code (MKY)  ");
		GridBagConstraints gbc_lblDepartureCityCode = new GridBagConstraints();
		gbc_lblDepartureCityCode.anchor = GridBagConstraints.WEST;
		gbc_lblDepartureCityCode.insets = new Insets(0, 0, 5, 5);
		gbc_lblDepartureCityCode.gridx = 3;
		gbc_lblDepartureCityCode.gridy = 2;
		panel_1.add(lblDepartureCityCode, gbc_lblDepartureCityCode);

		field_Text_Arrival_Code = new JFormattedTextField();
		field_Text_Arrival_Code.setHorizontalAlignment(SwingConstants.CENTER);
		field_Text_Arrival_Code.setText("BNE");
		GridBagConstraints gbc_txtBne = new GridBagConstraints();
		gbc_txtBne.anchor = GridBagConstraints.BELOW_BASELINE;
		gbc_txtBne.insets = new Insets(0, 0, 5, 5);
		gbc_txtBne.gridx = 2;
		gbc_txtBne.gridy = 3;
		panel_1.add(field_Text_Arrival_Code, gbc_txtBne);
		field_Text_Arrival_Code.setColumns(10);

		JLabel lblArrivalCityCode = new JLabel("  Arrival city code (BNE)  ");
		GridBagConstraints gbc_lblArrivalCityCode = new GridBagConstraints();
		gbc_lblArrivalCityCode.insets = new Insets(0, 0, 5, 5);
		gbc_lblArrivalCityCode.anchor = GridBagConstraints.WEST;
		gbc_lblArrivalCityCode.gridx = 3;
		gbc_lblArrivalCityCode.gridy = 3;
		panel_1.add(lblArrivalCityCode, gbc_lblArrivalCityCode);

		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 3;
		gbc_separator.gridy = 4;
		panel_1.add(separator, gbc_separator);

		JLabel lblDepartureDate = new JLabel("Departure/Start Date");
		GridBagConstraints gbc_lblDepartureDate = new GridBagConstraints();
		gbc_lblDepartureDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblDepartureDate.gridx = 3;
		gbc_lblDepartureDate.gridy = 5;
		panel_1.add(lblDepartureDate, gbc_lblDepartureDate);

		JLabel lblDay = new JLabel("Day");
		GridBagConstraints gbc_lblDay = new GridBagConstraints();
		gbc_lblDay.insets = new Insets(0, 0, 5, 5);
		gbc_lblDay.gridx = 2;
		gbc_lblDay.gridy = 6;
		panel_1.add(lblDay, gbc_lblDay);

		JLabel lblMonth = new JLabel("Month");
		GridBagConstraints gbc_lblMonth = new GridBagConstraints();
		gbc_lblMonth.insets = new Insets(0, 0, 5, 5);
		gbc_lblMonth.gridx = 3;
		gbc_lblMonth.gridy = 6;
		panel_1.add(lblMonth, gbc_lblMonth);

		JLabel lblYear = new JLabel("  Year  ");
		GridBagConstraints gbc_lblYear = new GridBagConstraints();
		gbc_lblYear.insets = new Insets(0, 0, 5, 5);
		gbc_lblYear.gridx = 4;
		gbc_lblYear.gridy = 6;
		panel_1.add(lblYear, gbc_lblYear);

		field_Integer_Day_Start_Scan = new JFormattedTextField();
		field_Integer_Day_Start_Scan.setText("1");
		field_Integer_Day_Start_Scan.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 7;
		panel_1.add(field_Integer_Day_Start_Scan, gbc_textField);
		field_Integer_Day_Start_Scan.setColumns(10);

		combo_Month_Start = new JComboBox();
		combo_Month_Start.setModel(new DefaultComboBoxModel(MONTHS));
		GridBagConstraints gbc_combo_Month_Start = new GridBagConstraints();
		gbc_combo_Month_Start.fill = GridBagConstraints.HORIZONTAL;
		gbc_combo_Month_Start.insets = new Insets(0, 0, 5, 5);
		gbc_combo_Month_Start.gridx = 3;
		gbc_combo_Month_Start.gridy = 7;
		panel_1.add(combo_Month_Start, gbc_combo_Month_Start);

		combo_Year_Start = new JComboBox();
		combo_Year_Start.setModel(new DefaultComboBoxModel(YEARS));
		GridBagConstraints gbc_combo_Year_Start = new GridBagConstraints();
		gbc_combo_Year_Start.fill = GridBagConstraints.HORIZONTAL;
		gbc_combo_Year_Start.insets = new Insets(0, 0, 5, 5);
		gbc_combo_Year_Start.gridx = 4;
		gbc_combo_Year_Start.gridy = 7;
		panel_1.add(combo_Year_Start, gbc_combo_Year_Start);

		JSeparator separator_1 = new JSeparator();
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.insets = new Insets(0, 0, 5, 5);
		gbc_separator_1.gridx = 3;
		gbc_separator_1.gridy = 8;
		panel_1.add(separator_1, gbc_separator_1);

		JLabel lblFinishDate = new JLabel("Finish Date");
		GridBagConstraints gbc_lblFinishDate = new GridBagConstraints();
		gbc_lblFinishDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblFinishDate.gridx = 3;
		gbc_lblFinishDate.gridy = 9;
		panel_1.add(lblFinishDate, gbc_lblFinishDate);

		JLabel lblDay_1 = new JLabel("Day");
		GridBagConstraints gbc_lblDay_1 = new GridBagConstraints();
		gbc_lblDay_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblDay_1.gridx = 2;
		gbc_lblDay_1.gridy = 10;
		panel_1.add(lblDay_1, gbc_lblDay_1);

		JLabel lblMonth_1 = new JLabel("Month");
		GridBagConstraints gbc_lblMonth_1 = new GridBagConstraints();
		gbc_lblMonth_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblMonth_1.gridx = 3;
		gbc_lblMonth_1.gridy = 10;
		panel_1.add(lblMonth_1, gbc_lblMonth_1);

		JLabel lblYear_1 = new JLabel("Year");
		GridBagConstraints gbc_lblYear_1 = new GridBagConstraints();
		gbc_lblYear_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblYear_1.gridx = 4;
		gbc_lblYear_1.gridy = 10;
		panel_1.add(lblYear_1, gbc_lblYear_1);

		field_Integer_Day_Finish_Scan = new JFormattedTextField();
		field_Integer_Day_Finish_Scan.setText("1");
		field_Integer_Day_Finish_Scan.setEnabled(false);
		field_Integer_Day_Finish_Scan.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 11;
		panel_1.add(field_Integer_Day_Finish_Scan, gbc_textField_1);
		field_Integer_Day_Finish_Scan.setColumns(10);

		combo_Month_Finish = new JComboBox();
		combo_Month_Finish.setEnabled(false);
		combo_Month_Finish.setModel(new DefaultComboBoxModel(MONTHS));
		GridBagConstraints gbc_combo_Month_Finish = new GridBagConstraints();
		gbc_combo_Month_Finish.insets = new Insets(0, 0, 5, 5);
		gbc_combo_Month_Finish.fill = GridBagConstraints.HORIZONTAL;
		gbc_combo_Month_Finish.gridx = 3;
		gbc_combo_Month_Finish.gridy = 11;
		panel_1.add(combo_Month_Finish, gbc_combo_Month_Finish);

		combo_Year_Finish = new JComboBox();
		combo_Year_Finish.setEnabled(false);
		combo_Year_Finish.setModel(new DefaultComboBoxModel(YEARS));
		GridBagConstraints gbc_combo_Year_Finish = new GridBagConstraints();
		gbc_combo_Year_Finish.insets = new Insets(0, 0, 5, 5);
		gbc_combo_Year_Finish.fill = GridBagConstraints.HORIZONTAL;
		gbc_combo_Year_Finish.gridx = 4;
		gbc_combo_Year_Finish.gridy = 11;
		panel_1.add(combo_Year_Finish, gbc_combo_Year_Finish);

		/*
		 * Check if using Extended scan mode
		 */
		checkbox_Info_ExtendedSearch = new JCheckBox("Using Date Range?");
		checkbox_Info_ExtendedSearch.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				/*
				 * Check if we are ready
				 */
				if (checkbox_Info_ExtendedSearch.isSelected()) {
					isUsingExtendedDates = true;
				}
				else {
					isUsingExtendedDates = false;					
				} 

				if (!mLockGUI) {
					if (!isUsingExtendedDates) {
						field_Integer_Day_Finish_Scan.setEnabled(false);
						combo_Month_Finish.setEnabled(false);
						combo_Year_Finish.setEnabled(false);
					}
					else {
						field_Integer_Day_Finish_Scan.setEnabled(true);
						combo_Month_Finish.setEnabled(true);
						combo_Year_Finish.setEnabled(true);
					}
				}
				else {
					field_Integer_Day_Finish_Scan.setEnabled(false);
					combo_Month_Finish.setEnabled(false);
					combo_Year_Finish.setEnabled(false);					
				}
			}
		});
		GridBagConstraints gbc_chckbxUsingDateRange = new GridBagConstraints();
		gbc_chckbxUsingDateRange.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxUsingDateRange.gridx = 5;
		gbc_chckbxUsingDateRange.gridy = 11;
		panel_1.add(checkbox_Info_ExtendedSearch, gbc_chckbxUsingDateRange);

		JSeparator separator_2 = new JSeparator();
		GridBagConstraints gbc_separator_2 = new GridBagConstraints();
		gbc_separator_2.insets = new Insets(0, 0, 5, 5);
		gbc_separator_2.gridx = 3;
		gbc_separator_2.gridy = 12;
		panel_1.add(separator_2, gbc_separator_2);

		JLabel lblReady = new JLabel("Ready?");
		GridBagConstraints gbc_lblReady = new GridBagConstraints();
		gbc_lblReady.anchor = GridBagConstraints.EAST;
		gbc_lblReady.insets = new Insets(0, 0, 0, 5);
		gbc_lblReady.gridx = 3;
		gbc_lblReady.gridy = 13;
		panel_1.add(lblReady, gbc_lblReady);

		String aReady = " " + MainFrame.mReady;
		aReady.toUpperCase();
		label_Info_AreWeReady = new JLabel(aReady);
		GridBagConstraints gbc_mLabelReadyState = new GridBagConstraints();
		gbc_mLabelReadyState.anchor = GridBagConstraints.WEST;
		gbc_mLabelReadyState.insets = new Insets(0, 0, 0, 5);
		gbc_mLabelReadyState.gridx = 4;
		gbc_mLabelReadyState.gridy = 13;
		panel_1.add(label_Info_AreWeReady, gbc_mLabelReadyState);

		JScrollPane scrollPane = new JScrollPane();
		tabbedPane.addTab("Log", null, scrollPane, null);

		textbox_System_Log = new JTextArea();
		textbox_System_Log.setRows(8);
		textbox_System_Log.setEditable(false);
		textbox_System_Log.setText("SYSTEM LOG");
		scrollPane.setViewportView(textbox_System_Log);

		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Settings", null, panel_2, null);
		panel_2.setLayout(new GridLayout(1, 0, 0, 0));

		stopRunningIfTimeOut = new JCheckBox("Stop running if program runs for too long?");
		panel_2.add(stopRunningIfTimeOut);

		/*
		 * Document Listeners
		 */

		// Depature Field
		DocumentListener listener_Field_Departure_Code = new DocumentListener() {
			public void changedUpdate(DocumentEvent documentEvent) {
				printIt(documentEvent);
			}

			public void insertUpdate(DocumentEvent documentEvent) {
				printIt(documentEvent);
			}

			public void removeUpdate(DocumentEvent documentEvent) {
				printIt(documentEvent);
			}

			private void printIt(DocumentEvent documentEvent) {
				DocumentEvent.EventType type = documentEvent.getType();
				String typeString = null;
				if (type.equals(DocumentEvent.EventType.CHANGE)) {
					typeString = "Change";
				} else if (type.equals(DocumentEvent.EventType.INSERT)) {
					typeString = "Insert";
				} else if (type.equals(DocumentEvent.EventType.REMOVE)) {
					typeString = "Remove";
				}
				// System.out.print("Type : " + typeString);
				Document source = documentEvent.getDocument();
				int length = source.getLength();
				// System.out.println("Length: " + length);
			}
		};
		getField_Text_Departure_Code().getDocument().addDocumentListener(listener_Field_Departure_Code);

		// Arrival Field
		DocumentListener listener_Field_Arrival_Code = new DocumentListener() {
			public void changedUpdate(DocumentEvent documentEvent) {
				printIt(documentEvent);
			}

			public void insertUpdate(DocumentEvent documentEvent) {
				printIt(documentEvent);
			}

			public void removeUpdate(DocumentEvent documentEvent) {
				printIt(documentEvent);
			}

			private void printIt(DocumentEvent documentEvent) {
				DocumentEvent.EventType type = documentEvent.getType();

				int a1 = documentEvent.getLength();
				if (a1 != 3) {
					return;
				}

				/*
				 * String a0 = arg0.getPropertyName(); Object a1 = arg0.getOldValue(); Object a2
				 * = arg0.getNewValue();
				 * 
				 * if (field_Text_Departure_Code.getText().length() > 0) {
				 * field_Text_Departure_Code.setText(Utils.removeNumeralsFromString(
				 * field_Text_Departure_Code.getText()));
				 * field_Text_Departure_Code.setText(field_Text_Departure_Code.getText().
				 * toUpperCase()); if (field_Text_Departure_Code.getText().length() == 3) {
				 * field_Text_Departure_Code.setBackground(Color.green); } else {
				 * field_Text_Departure_Code.setSelectedTextColor(Color.red); } if
				 * (field_Text_Departure_Code.getText().length() > 3) {
				 * field_Text_Departure_Code.setText(field_Text_Departure_Code.getText().
				 * substring(0, 2)); } }
				 */

				String typeString = null;
				if (type.equals(DocumentEvent.EventType.CHANGE)) {
					typeString = "Change";
				} else if (type.equals(DocumentEvent.EventType.INSERT)) {
					typeString = "Insert";
				} else if (type.equals(DocumentEvent.EventType.REMOVE)) {
					typeString = "Remove";
				}
				// System.out.print("Type : " + typeString);
				Document source = documentEvent.getDocument();
				int length = source.getLength();
				// System.out.println("Length: " + length);
			}
		};
		getField_Text_Arrival_Code().getDocument().addDocumentListener(listener_Field_Arrival_Code);

	}

	public synchronized final JFormattedTextField getField_Text_Departure_Code() {
		return field_Text_Departure_Code;
	}

	public synchronized final void setField_Text_Departure_Code(JFormattedTextField field_Text_Departure_Code) {
		this.field_Text_Departure_Code = field_Text_Departure_Code;
	}

	public synchronized final JFormattedTextField getField_Text_Arrival_Code() {
		return field_Text_Arrival_Code;
	}

	public synchronized final void setField_Text_Arrival_Code(JFormattedTextField field_Text_Arrival_Code) {
		this.field_Text_Arrival_Code = field_Text_Arrival_Code;
	}

	public synchronized final JFormattedTextField getField_Integer_Day_Start_Scan() {
		return field_Integer_Day_Start_Scan;
	}

	public synchronized final void setField_Integer_Day_Start_Scan(JFormattedTextField field_Integer_Day_Start_Scan) {
		this.field_Integer_Day_Start_Scan = field_Integer_Day_Start_Scan;
	}

	public synchronized final JFormattedTextField getField_Integer_Day_Finish_Scan() {
		return field_Integer_Day_Finish_Scan;
	}

	public synchronized final void setField_Integer_Day_Finish_Scan(JFormattedTextField field_Integer_Day_Finish_Scan) {
		this.field_Integer_Day_Finish_Scan = field_Integer_Day_Finish_Scan;
	}

	public synchronized final JComboBox getCombo_Month_Start() {
		return combo_Month_Start;
	}

	public synchronized final JComboBox getCombo_Month_Finish() {
		return combo_Month_Finish;
	}

	public synchronized final JComboBox getCombo_Year_Start() {
		return combo_Year_Start;
	}

	public synchronized final JComboBox getCombo_Year_Finish() {
		return combo_Year_Finish;
	}

	public synchronized final boolean isUsingExtendedDates() {
		return isUsingExtendedDates;
	}

	public synchronized final JLabel getLabel_Info_AreWeReady() {
		return label_Info_AreWeReady;
	}

	public synchronized final void setLabel_Info_AreWeReady(JLabel label_Info_AreWeReady) {
		this.label_Info_AreWeReady = label_Info_AreWeReady;
	}

	public synchronized final JTextArea getTextbox_System_Log() {
		return textbox_System_Log;
	}

	public synchronized final void setTextbox_System_Log(JTextArea textbox_System_Log) {
		this.textbox_System_Log = textbox_System_Log;
	}

	public synchronized final JCheckBox getStopRunningIfTimeOut() {
		return stopRunningIfTimeOut;
	}

	public synchronized final boolean isGuiLocked() {
		return mLockGUI;
	}

	public synchronized final JProgressBar getProgressBar() {
		return progressBar;
	}

	public synchronized final void setProgressBar(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}

}
