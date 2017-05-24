	// GUI-related imports

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

	public class  MTAlgorithms extends Frame implements ActionListener
	{
		String[] description = new String[] {
				"This proram is made to test the differences of sorting 10,000,000",
				"items with multithreading and non multithreading through use of",
				"merge sort, quicksort and java parallel sort."
		};
		static int threshold = 1000;
		//static int[] threshold = {1000,10000,100000, 1000000};
		boolean arrayInitialized = false;
		int NDataItems = 10000000;
		int[] a = new int[NDataItems];
		
		int maximumSerial;
		int maximumParallel;
		
		long time[]  = new long[6];
		
		MenuItem miAbout,
		         miInitArr,
		         miSerialSort,
		         maSerialSort,
		         miMultiThreadedMergeSort,
		         maMultiThreadedMergeSort,
		         miMultiThreadedQuickSort,
		         maMultiThreadedQuickSort,
		         miJavaParallelSort,
		         maJavaParallelSort,
		         miRunAll;
		
		
		long start, 
					elapsedTimeSerialSort, elapsedTimeParallelMergeSort,
					elapsedTimeParallelQuickSort,elapsedTimeJavaParallelSort;
		String command = "";
			
		public static void main(String[] args)
		{
			Frame frame = new  MTAlgorithms();
			
			frame.setResizable(true);
			frame.setSize(800,500);
			frame.setVisible(true);
			
		}
		
			public  MTAlgorithms()
		{
			
			
			
			setTitle("Parallel Algorithms");
			
			
			// Create Menu Bar
		
			   			
			MenuBar mb = new MenuBar();
			setMenuBar(mb);
						
			Menu menu = new Menu("Operations");
			Menu MA = new Menu("Anylasis");
			
			// Add it to Menu Bar
						
			mb.add(menu);
			mb.add(MA);
			
			// Create Menu Items
			// Add action Listener 
			// Add to "File" Menu Group
			
			miAbout = new MenuItem("About");
			miAbout.addActionListener(this);
			menu.add(miAbout);
			
		    miInitArr = new MenuItem("Initialize Array");
			miInitArr.addActionListener(this);
			menu.add(miInitArr);
				
			miSerialSort = new MenuItem("Serial Sort");
			miSerialSort.addActionListener(this);
			miSerialSort.setEnabled(false);
			menu.add(miSerialSort);
			
			miMultiThreadedMergeSort = new MenuItem("MultiThreaded MergeSort");
			miMultiThreadedMergeSort.addActionListener(this);
			miMultiThreadedMergeSort.setEnabled(false);
			menu.add(miMultiThreadedMergeSort);
			
			maMultiThreadedMergeSort = new MenuItem("Analyze MultiThreaded MergeSort");
			maMultiThreadedMergeSort.addActionListener(this);
			maMultiThreadedMergeSort.setEnabled(false);
			MA.add(maMultiThreadedMergeSort);
						
			miMultiThreadedQuickSort = new MenuItem("MultiThreaded QuickSort");
			miMultiThreadedQuickSort.addActionListener(this);
			miMultiThreadedQuickSort.setEnabled(false);
			menu.add(miMultiThreadedQuickSort);
			
			maMultiThreadedQuickSort = new MenuItem("Analyze MultiThreaded QuickSort");
			maMultiThreadedQuickSort.addActionListener(this);
			maMultiThreadedQuickSort.setEnabled(false);
			MA.add(maMultiThreadedQuickSort);
			
			miJavaParallelSort = new MenuItem("Java Parallel Sort");
			miJavaParallelSort.addActionListener(this);
			miJavaParallelSort.setEnabled(false);
			menu.add(miJavaParallelSort);
			
			maJavaParallelSort = new MenuItem("Analyze Java Parallel Sort");
			maJavaParallelSort.addActionListener(this);
			maJavaParallelSort.setEnabled(false);
			MA.add(maJavaParallelSort);
			
			miRunAll = new MenuItem("Run All");
			miRunAll.addActionListener(this);
			miRunAll.setEnabled(false);
			menu.add(miRunAll);
			
			
			MenuItem miExit = new MenuItem("Exit");
			miExit.addActionListener(this);
			menu.add(miExit);
				
			// End program when window is closed
			
			WindowListener l = new WindowAdapter()
			{
							
				public void windowClosing(WindowEvent ev)
				{
					System.exit(0);
				}
				
				public void windowActivated(WindowEvent ev)
				{
					repaint();
				}
				
				public void windowStateChanged(WindowEvent ev)
				{
					repaint();
				}
			
			};
			
			ComponentListener k = new ComponentAdapter()
			{
				public void componentResized(ComponentEvent e) 
				{
	        		repaint();           
	    		}
			};
			
			// register listeners
				
			this.addWindowListener(l);
			this.addComponentListener(k);

		}
		
	//******************************************************************************
	//  called by windows manager whenever the application window performs an action
	//  (select a menu item, close, resize, ....
	//******************************************************************************

		public void actionPerformed (ActionEvent ev) 
			{
				// figure out which command was issued
				
				command = ev.getActionCommand();
				
				// take action accordingly
				
				if("About".equals(command))
				{
					repaint();
				}
				else
				if("Initialize Array".equals(command))
				{
					 InitializeArrays();
					 arrayInitialized = true;
			         miSerialSort.setEnabled(true);
			         miMultiThreadedMergeSort.setEnabled(true);
			         maMultiThreadedMergeSort.setEnabled(true);
			         miMultiThreadedQuickSort.setEnabled(true);
			         maMultiThreadedQuickSort.setEnabled(true);
			         miJavaParallelSort.setEnabled(true);
			         maJavaParallelSort.setEnabled(true);
			         miRunAll.setEnabled(true);
			         threshold = 1000;
					 repaint();
				}
				else
					
					if("Serial Sort".equals(command))
					{
						threshold = 1000;
						SerialSortCall();
						
					}
				else
					if("Analyze MultiThreaded MergeSort".equals(command))
					{
						threshold = 1000;
						for(int i = 0; i <= 5; i++)
						{
							ParallelMergeSortCall();
							time[i] = elapsedTimeParallelMergeSort;
							threshold = (ThresholdAdanvce(threshold));
						}
						
					}
				else
					if("Analyze MultiThreaded QuickSort".equals(command))
					{
						threshold = 1000;
						for(int i = 0; i <= 5; i++)
						{
							ParallelQuickSortCall();
							time[i] = elapsedTimeParallelQuickSort;
							threshold = (ThresholdAdanvce(threshold));
						}
						
					}
				else
					if("Analyze Java Parallel Sort".equals(command))
					{
						threshold = 1000;
						for(int i = 0; i <= 5; i++)
						{
							QuickSortCall();
							time[i] = elapsedTimeJavaParallelSort;
							threshold = (ThresholdAdanvce(threshold));
						}
						
					}
				else	
					if("MultiThreaded MergeSort".equals(command))
					{
						threshold = 1000;
						ParallelMergeSortCall();
					}
					else
						if("MultiThreaded QuickSort".equals(command))
						{
							threshold = 1000;
							ParallelQuickSortCall();
							
		
						}
				else
					if("Java Parallel Sort".equals(command))
					{
						threshold = 1000;
						QuickSortCall();
					}
				else
					if("Exit".equals(command))
					{
						System.exit(0);
					}
					else
						if("Run All".equals(command))
						{
							SerialSortCall();
							ParallelMergeSortCall();
							ParallelQuickSortCall();
							QuickSortCall();
						}
			}
	//********************************************************
	// called by repaint() to redraw the screen
	//********************************************************
			
			public void paint(Graphics g)
			{
				g.drawString(
					"Number of processors is "+Integer.toString( Runtime.getRuntime().availableProcessors() ),300,130);
				g.drawString("Number of Data Items = "+Integer.toString(NDataItems),300, 150);
			//	g.drawString("Threshold = "+Integer.toString(threshold),300, 170);
				
				if( "Serial Sort".equals(command ) 
						
					|| "MultiThreaded MergeSort".equals(command)
					|| "MultiThreaded QuickSort".equals(command)
					|| "Java Parallel Sort".equals(command))
					
				{
					g.drawString("Threshold = "+Integer.toString(threshold),300, 170);
					int x = 200;
					int y =200;
					
					g.drawString("Method",x,y-5);
					g.drawString("Elapsed Time", x+300, y-5);
					g.drawLine(x, y, x+400, y);
					if ("Serial Sort".equals(command))
					{
						g.drawString("Serial Sort (MergeSort)", x, y+15);
						g.drawString(""+ elapsedTimeSerialSort,x+300,y+15);
					}
					else
						if("MultiThreaded MergeSort".equals(command))
						{
							g.drawString("MultiThreaded MergeSort", x, y+15);
							g.drawString(""+ elapsedTimeParallelMergeSort,x+300,y+15);
						}
						else
							if("MultiThreaded QuickSort".equals(command))
							{
								g.drawString("MultiThreaded QuickSort", x, y+15);
								g.drawString(""+ elapsedTimeParallelQuickSort,x+300,y+15);
							}
							else
								if("Java Parallel Sort".equals(command))
								{
									g.drawString("Java Parallel Sort", x, y+15);
									g.drawString(""+ elapsedTimeJavaParallelSort,x+300,y+15);
								}
					
					
				}	
				
				else
					if("Run All".equals(command))
					{
						
								
						g.drawString("Threshold = "+Integer.toString(threshold),300, 170);
						int x = 200;
						int y =200;
						
						g.drawString("Method",x,y-5);
						g.drawString("Elapsed Time", x+300, y-5);
						g.drawLine(x, y, x+400, y);
						
						g.drawString("Serial Sort (MergeSort)", x, y+15);
						g.drawString(""+ elapsedTimeSerialSort,x+300,y+15);
						
						g.drawString("Parallel MergeSort", x, y+30);
						g.drawString(""+ elapsedTimeParallelMergeSort,x+300,y+30);
						
						g.drawString("Parallel QuickSort", x, y+45);
						g.drawString(""+ elapsedTimeParallelQuickSort,x+300,y+45);
						
						g.drawString("Java Parallel Sort", x, y+60);
						g.drawString(""+ elapsedTimeJavaParallelSort,x+300,y+60);
					}
				else
					if("Analyze MultiThreaded MergeSort".equals(command))
					{
						int x = 200;
						int y =210;
						threshold = 1000;
						g.drawString("MultiThreaded MergeSort", x+100, y-40);
						
						g.drawString("Threshold",x,y-5);
						g.drawString("Elapsed Time", x+300, y-5);
						g.drawLine(x, y, x+400, y);
						
						for(int i =0; i<6; i++)
						{
							g.drawString("" +time[i], x+300, y+15);
							g.drawString("" +threshold, x, y+15);
							y= y+20;
							threshold = (ThresholdAdanvce(threshold));
						}
					}
				else
					if("Analyze MultiThreaded QuickSort".equals(command))
					{
							int x = 200;
							int y =210;
							threshold = 1000;
							g.drawString("MultiThreaded QuickSort", x+100, y-40);
							
							g.drawString("Threshold",x,y-5);
							g.drawString("Elapsed Time", x+300, y-5);
							g.drawLine(x, y, x+400, y);
							
							for(int i =0; i<6; i++)
							{
								g.drawString("" +time[i], x+300, y+15);
								g.drawString("" +threshold, x, y+15);
								y= y+20;
								threshold = (ThresholdAdanvce(threshold));
							}
						}
					else
						if("Analyze Java Parallel Sort".equals(command))
						{
								int x = 200;
								int y =210;
								threshold = 1000;
								g.drawString("Java Parallel Sort", x+100, y-40);
								
								g.drawString("Threshold",x,y-5);
								g.drawString("Elapsed Time", x+300, y-5);
								g.drawLine(x, y, x+400, y);
								
								for(int i =0; i<6; i++)
								{
									g.drawString("" +time[i], x+300, y+15);
									g.drawString("" +threshold, x, y+15);
									y= y+20;
									threshold = (ThresholdAdanvce(threshold));
								}
							}
					
				else
					if("About".equals(command))
					{
						int x = 200;
						int y = 200;
						
						for(int i = 0; i < description.length; i++)
						{
							g.drawString(description[i], x, y);
							y = y +25;
						}
				}
				else
					if("Initialize Array".equals(command))
					{
						g.drawString("Array Initialized",200, 100);
					}	
				
			}

public void InitializeArrays ()
{
	maximumSerial=	maximumParallel = -1;
	
	start = /*elapsedTimeSerialMax = elapsedTimeParallelMax = */
				elapsedTimeSerialSort =  elapsedTimeParallelMergeSort = 
						elapsedTimeParallelQuickSort = elapsedTimeJavaParallelSort = 0;
	for (int i=0; i<a.length; i++)
		a[i] = (int) (Math.random()*400000000);
}
public boolean isSorted(int[] list)
{
	boolean sorted = true;
	int index = 0;
	while (sorted & index<list.length-1)
	{
		if (list[index] > list[index+1])
			sorted = false;
		else
			index++;	
	}
	return sorted;
}

public void SerialSortCall()
{
MergeSort k = new MergeSort();
int[] b = new int[a.length];
System.arraycopy(a, 0, b, 0, a.length);
start = System.currentTimeMillis();
k.mergeSort(b);
elapsedTimeSerialSort = System.currentTimeMillis() - start;
System.out.println("SerialSort(MergeSort)" + elapsedTimeSerialSort);
repaint();
}

public void ParallelMergeSortCall()
{
	// create a new array, copy original array to it
	int[] b = new int[a.length];
	System.arraycopy(a, 0, b, 0, a.length);
	start = System.currentTimeMillis();
	ParallelMergeSort.startMainTask(b,threshold);
	
	elapsedTimeParallelMergeSort = System.currentTimeMillis()-start;
	if (isSorted(b))
	{
		System.out.println("ParallelMergeSort:" + elapsedTimeParallelMergeSort);
		repaint();
	}
	else
		System.out.println("Array is not sorted ---- multiThreaded MergeSort");
}

public void ParallelQuickSortCall()
{
	int[] b = new int[a.length];
	System.arraycopy(a, 0, b, 0, a.length);
	start = System.currentTimeMillis();
	ParallelQuickSort.startMainTask(b,threshold);
	elapsedTimeParallelQuickSort = System.currentTimeMillis()-start;
	if (isSorted(b))
	{
		System.out.println("ParallelQuickSort:" + elapsedTimeParallelQuickSort);
		repaint();
	}
	else
		System.out.println("Array is not sorted ---- multiThreaded MergeSort");
}

public void QuickSortCall()
{
	int[] b = new int[a.length];
	System.arraycopy(a, 0, b, 0, a.length);
	start = System.currentTimeMillis();
	Arrays.parallelSort(b);
	elapsedTimeJavaParallelSort = System.currentTimeMillis()-start;
	System.out.println("QuickSort:" + elapsedTimeJavaParallelSort);
	repaint();
}
 public int ThresholdAdanvce(int threshold)
 {
	 if (threshold < 999999)
			threshold = threshold*10;
			else if (threshold < 1999999)
				threshold = threshold*2;
			else 
				threshold = threshold +500000;
	 return threshold;
 }


	}	


