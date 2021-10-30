package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import Model.Manager;
import View.ManagerView;

public class DetailManagerController implements ActionListener{
	private JFrame frame;
	private JButton btn_back;
	private Manager mng;
	
	public DetailManagerController(JFrame frame, JButton btn_back, Manager mng) {
		super();
		this.frame = frame;
		this.btn_back = btn_back;
		this.mng = mng;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btn_back) {
			frame.setVisible(false);
			new ManagerView(mng);
		}
	}
	
	
}
