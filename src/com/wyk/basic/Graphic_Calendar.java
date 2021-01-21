package com.wyk.basic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
/**
 * @author 57715
 * 
 * 实现图形日历
 * 
 */
class Window_Show implements ActionListener{
	private JFrame frame = null;
	private Container container = null;
	private JComboBox<Integer> year_ComboBox = null;
	private JComboBox<String> month_ComboBox = null;
	private JLabel label[] = new JLabel[42];
	private JLabel time = null;
	private JButton last_month = null;
	private JButton next_month = null;
	private JPanel part_I = null;
	private JPanel part_II = null;
	private JPanel part_III = null;
	private int year=2016;
	private int month=1;
	String days[] = new String[42];
	final String[] week_d = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sept","Oct","Nov","Dec"};
	public Window_Show() {
		frame = new JFrame("Java日历");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);		//窗口居中显示
		frame.setSize(new Dimension(400,400));
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
	
	public void navigation_Bar() {
		part_I = new JPanel();		//part_I处的面板
		year_ComboBox = new JComboBox<>();
		month_ComboBox = new JComboBox<>();
		JLabel year_label = new JLabel("年");
		JLabel month_label = new JLabel("月");
		last_month = new JButton("previous");
		next_month = new JButton("next");
		last_month.addActionListener(this);
		next_month.addActionListener(this);
		for (int i=year; i<=2025; i++) {
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
	
	public void week_and_day() {
		part_II = new JPanel();		//part_II处的面板
		final String[] week_D = {"日","一","二","三","四","五","六"};
		JButton[] weeks = new JButton[week_D.length];
		for(int i=0; i<weeks.length; i++) {
			weeks[i] = new JButton(week_D[i]);
			weeks[i].setBorder(new SoftBevelBorder(BevelBorder.RAISED));
			part_II.add(weeks[i]);
		}
		part_II.setLayout(new GridLayout(7,7));
		for(int i=0; i<label.length; i++) {
			label[i] = new JLabel("-");
			label[i].setHorizontalAlignment(SwingConstants.CENTER);
			label[i].setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
			part_II.add(label[i]);
		}
		container.add(part_II,BorderLayout.CENTER);
	}
	
	public String[] day_on_month(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		String day_month[] = new String[42];
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DATE, 1);
		int alldays_month = calendar.getActualMaximum(Calendar.DATE);
		int first_of_week = calendar.get(Calendar.DAY_OF_WEEK);
		for(int i=first_of_week-1, day=1; i<first_of_week+alldays_month-1; i++) {
			day_month[i] = String.valueOf(day);
			day++;
		}
		return day_month;
	}
	
	public int conclude(String arg) {
		for(int i=0; i<12; i++) {
			if(arg.equals(week_d[i])) {
				return i+1;
			}
		}
		return 0;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
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
				label[i].setText(days[i]);
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
				label[i].setText(days[i]);
			}
		}
		else if(e.getSource()==year_ComboBox) {
			year = (Integer)year_ComboBox.getSelectedItem();
			days = day_on_month(year,month);
			for(int i=0;i<42;i++) {
				label[i].setText(days[i]);
			}
		}
		else if(e.getSource()==month_ComboBox) {
			month = conclude((String)month_ComboBox.getSelectedItem());
			days = day_on_month(year,month);
			for(int i=0;i<42;i++) {
				label[i].setText(days[i]);
			}
		}
	}
	
	public void date() {
		part_III = new JPanel();
		time = new JLabel("totay");
		part_III.add(time);
		Calendar calendar = Calendar.getInstance();
		time.setText(calendar.getTime().toString());
		time.setHorizontalAlignment(SwingConstants.CENTER);
		part_III.setBackground(Color.white);
		container.add(part_III,BorderLayout.SOUTH);
	}
}

public class Graphic_Calendar {

	public static void main(String[] args) {
		new Window_Show();
	}
}