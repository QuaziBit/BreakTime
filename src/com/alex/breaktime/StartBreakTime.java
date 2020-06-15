package com.alex.breaktime;

import java.awt.EventQueue;


public class StartBreakTime
{
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					WindowBreakTime window = new WindowBreakTime();
					window.getFrame().setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
}
