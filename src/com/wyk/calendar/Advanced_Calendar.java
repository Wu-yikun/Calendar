package com.wyk.calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
/**
 * @author 57715
 * 
 * 实现图形日历
 * 
 * 设计GUI界面的日历记事本。
 * 
 */
@SuppressWarnings("all")
class Advanced_Calendar implements ActionListener{
	private JFrame frame = null;
	private Container container = null;
	private JComboBox<Integer> year_ComboBox = null;
	private JComboBox<String> month_ComboBox = null;
	private JButton button[] = new JButton[42];
	private JLabel time = null;
	private JButton last_month = null;
	private JButton next_month = null;
	private JPanel part_I = null;
	private JPanel part_II = null;
	private JPanel part_III = null;
	private Font font = null;
	private int year=2016;
	private int month=1;
	private String days[] = new String[42];
	final String[] week_d = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sept","Oct","Nov","Dec"};
	
	public JFrame getFrame() {
		return frame;
	}

	public Advanced_Calendar() {
		frame = new JFrame("Java日历");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setBounds(550, 200, 400, 400);
		container = frame.getContentPane();
		container.setLayout(new BorderLayout());
		navigation_Bar();
		week_and_day();
		while (true) {
			try {
				Thread.sleep(1000);
				date();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void navigation_Bar() {
		part_I = new JPanel();
		year_ComboBox = new JComboBox<>();
		month_ComboBox = new JComboBox<>();
		JLabel year_label = new JLabel("年");
		JLabel month_label = new JLabel("月");
		last_month = new JButton("previous");
		next_month = new JButton("next");
		last_month.addActionListener(this);
		next_month.addActionListener(this);
		for (int i=year; i<=2050; i++) {
			year_ComboBox.addItem(i);
		}
		year_ComboBox.addActionListener(this);
		for (int i=month; i<=12; i++) {
			month_ComboBox.addItem(week_d[i-1]);
		}
		month_ComboBox.addActionListener(this);
		part_I.setLayout(new FlowLayout());
		part_I.add(year_ComboBox);
		part_I.add(year_label);
		part_I.add(month_ComboBox);
		part_I.add(month_label);
		part_I.add(last_month);
		part_I.add(next_month);
		part_I.setBackground(Color.white);
		container.add(part_I,BorderLayout.NORTH);
	}
	
	private void week_and_day() {
		part_II = new JPanel();
		final String[] week_D = {"日","一","二","三","四","五","六"};
		JButton[] weeks = new JButton[week_D.length];
		for(int i=0; i<weeks.length; i++) {
			weeks[i] = new JButton(week_D[i]);
			part_II.add(weeks[i]);
		}
		part_II.setLayout(new GridLayout(7,7));
		for(int i=0; i<button.length; i++) {
			button[i] = new JButton("-");
			button[i].setHorizontalAlignment(SwingConstants.CENTER);
			button[i].setBorder(new SoftBevelBorder(BevelBorder.RAISED));
			part_II.add(button[i]);
		}
		container.add(part_II,BorderLayout.CENTER);
	}
	
	private String[] day_on_month(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		String day_month[] = new String[42];
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DATE, 1);
		int alldays_month = calendar.getActualMaximum(Calendar.DATE);
		int first_of_week = calendar.get(Calendar.DAY_OF_WEEK);
		for(int i=first_of_week-2; i>=0; i--) {
			Calendar calendar1 = Calendar.getInstance();
			if(0==(month-1)) {
				month=12;
				calendar1.set(year-1, month-1, i-first_of_week+2);
			}else {
				calendar1.set(year, month-1, i-first_of_week+2);
			}
			day_month[i] = "#"+String.valueOf(calendar1.get(Calendar.DATE));
		}
		for(int i=first_of_week-1, day=1; i<first_of_week+alldays_month-1; i++) {
			day_month[i] = String.valueOf(day);
			day++;
		}
		for(int i=first_of_week+alldays_month-1,day=1; i<42; i++) {
			Calendar calendar2 = Calendar.getInstance();
			calendar2.set(Calendar.DATE,day);
			day_month[i] = "#"+String.valueOf(calendar2.get(Calendar.DATE));
			day++;
		}
		return day_month;
	}
	
	private int conclude(String arg) {
		for(int i=0; i<12; i++) {
			if(arg.equals(week_d[i])) {
				return i+1;
			}
		}
		return 0;
	}
	
	public void remake() {
		for(int i=0; i<button.length; i++) {
			button[i].setEnabled(true);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		remake();			//实现按钮全部重复,以防覆盖
		if(e.getSource()==next_month) {
			month=month+1;
			if(13==month) {
				month=1;
				year++;
			}
			month_ComboBox.setSelectedItem(week_d[month-1]);
			year_ComboBox.setSelectedItem(year);
			days = day_on_month(year,month);
			for(int i=0;i<42;i++) {
				if(days[i].matches("\\p{Alnum}+")) {
					button[i].setText(days[i]);
				}else {
					button[i].setText(days[i]);
					button[i].setEnabled(false);
				}
			}
		}
		else if(e.getSource()==last_month) {
			month=month-1;
			if(0==month) {
				month=12;
				year--;
			}
			month_ComboBox.setSelectedItem(week_d[month-1]);
			year_ComboBox.setSelectedItem(year);
			days = day_on_month(year,month);
			for(int i=0;i<42;i++) {
				if(days[i].matches("\\p{Alnum}+")) {
					button[i].setText(days[i]);
				}else {
					button[i].setText(days[i]);
					button[i].setEnabled(false);
				}
			}
		}
		else if(e.getSource()==year_ComboBox) {
			year = (Integer)year_ComboBox.getSelectedItem();
			days = day_on_month(year,month);
			for(int i=0;i<42;i++) {
				if(days[i].matches("\\p{Alnum}+")) {
					button[i].setText(days[i]);
				}else {
					button[i].setText(days[i]);
					button[i].setEnabled(false);
				}
			}
		}
		else if(e.getSource()==month_ComboBox) {
			month = conclude((String)month_ComboBox.getSelectedItem());
			days = day_on_month(year,month);
			for(int i=0;i<42;i++) {
				if(days[i].matches("\\p{Alnum}+")) {
					button[i].setText(days[i]);
					
				}else {
					button[i].setText(days[i]);
					button[i].setEnabled(false);
				}
			}
		}
	}
	
	private void date() {
		part_III = new JPanel();
		time = new JLabel();
		font = new Font("华文新魏",Font.BOLD,16);
		time.setFont(font);
		part_III.add(time);
		Calendar calendar = Calendar.getInstance();
		time.setText(calendar.getTime().toString());
		time.setHorizontalAlignment(SwingConstants.CENTER);
		part_III.setBackground(Color.white);
		container.add(part_III,BorderLayout.SOUTH);
	}
	public static void main(String[] args) {
		new Advanced_Calendar();
	}
}