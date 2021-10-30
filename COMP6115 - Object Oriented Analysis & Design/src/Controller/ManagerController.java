package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Model.Manager;
import View.DetailManagerView;
import View.Login;
import View.ManagerEmployeeView;

public class ManagerController implements ActionListener{
	
	
	private JButton btn_logout, btn_viewDetail, btn_employee;
	private Manager mng;
	private JTable table;
	private DefaultTableModel model_table;
	private JFrame frame;
	private JLabel selected_transaction;
	private int SelectedTransactionID = 0;
	public ManagerController(JButton btn_logout, JButton btn_viewDetail, JButton btn_employee, Manager mng,
			JTable table, DefaultTableModel model_table, JFrame frame, JLabel selected_transaction) {
		super();
		this.btn_logout = btn_logout;
		this.btn_viewDetail = btn_viewDetail;
		this.btn_employee = btn_employee;
		this.mng = mng;
		this.table = table;
		this.model_table = model_table;
		this.frame = frame;
		this.selected_transaction = selected_transaction;
		SelectItem();
	}
	
	private void SelectItem() {
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				SelectedTransactionID = (int) table.getValueAt(table.getSelectedRow(), 0);
				selected_transaction.setText("Selected Transaction [ ID ]: "+ SelectedTransactionID);
			}
		});
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btn_logout) {
			frame.setVisible(false);
			new Login();
		}else if(e.getSource() == btn_viewDetail){
			frame.setVisible(false);
			new DetailManagerView(mng, SelectedTransactionID);			
		}else if(e.getSource() == btn_employee) {
			frame.setVisible(false);
			new ManagerEmployeeView(mng);
		}
		
		
	}
	
	
}
