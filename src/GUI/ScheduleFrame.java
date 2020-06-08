package GUI;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;

//import hffsp.model.HFFSPInstance;
//import hffsp.model.Schedule;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;


import GUI.ScheduleJComponent.Boton;
import Logic.Machine;
import Logic.Operation;
import Logic.QLearning;
import javafx.stage.FileChooser;

public class ScheduleFrame extends JFrame {
	
	private static final long serialVersionUID = 6561306520838117775L;
	
	Schedule bestSchedule;
	ScheduleJComponent scheduleJComponent;
	Instance problemInstance;
	private JFileChooser FileChooser = new JFileChooser(System.getProperty("user.dir"));
	
	public ScheduleFrame(Instance problemInstance, Schedule initialSchedule, String title,QLearning ql2) {
		this.problemInstance=problemInstance;
		this.setTitle(problemInstance.getName()+" | makespan: "+initialSchedule.getMakespan()+" " +title);
		scheduleJComponent = new ScheduleJComponent(initialSchedule, problemInstance);
		bestSchedule = initialSchedule;
	//	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //to close the main application
		//this.setSize(1200, 750);
		
		
		//new frame size
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();		
		//int l =  problemInstance.machines.length*100;
		setBounds((screenSize.width-1200)/2, (screenSize.height-350)/2, 1200, 350);
				
		JPanel jp = new javax.swing.JPanel();
		jp.setSize(200,250);       
		
       // final JButton validate = new JButton("  Validate   ");
//        final JButton undo = new JButton("    Undo     ");
        final JButton fix = new JButton("   Modify    ");
       // final JButton end_fix = new JButton("   End Fix   ");
        
        final JButton optimize = new JButton(" Right Shift ");
        final JButton ql = new JButton("  Q-Learning ");
//        JButton save_files = new JButton("Save Schedule");
        JButton save_schedule = new JButton("Save Gantt Chart");
        
        JButton new_orders = new JButton("Load New Orders");
        
       // final JButton perturbations = new JButton("Add Perturbation");
        
       // jp.add(validate);
        jp.add(fix);
       // jp.add(end_fix);
        jp.add(optimize);
        jp.add(ql);
//        jp.add(undo);
//        jp.add(save_files);
        jp.add(save_schedule); 
        
        jp.add(new_orders);
        
        this.add(jp,"South");
        optimize.setEnabled(false);
        ql.setEnabled(false);
        ql.setToolTipText("You must press the Modify button to activate this option");
        optimize.setToolTipText("You must press the Modify button to activate this option");
        final QLearning q = ql2;
        
        fix.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub				
				scheduleJComponent.fix = true;	
				scheduleJComponent.contPintar =1;
					//System.out.println("O3 ");
					//end_fix.setEnabled(true);
					fix.setEnabled(false);
				//	validate.setEnabled(false);
					optimize.setEnabled(true);
					optimize.setToolTipText("Right shifts all the necessary operations");
					if(q != null) {
						ql.setEnabled(true);
						ql.setToolTipText("Q-Learning Algorithm");
					}else
						ql.setToolTipText("You must load three files to activate this option");
					//optim = false;
					setResizable(false);			
			}
		});
        
        
        save_schedule.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFileChooser saveFileChooser = new JFileChooser(System.getProperty("user.dir"));
	        	saveFileChooser.setDialogTitle("Enter the name for the png image");
	        	FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .png files", "png"); 
	        	//saveFileChooser.addChoosableFileFilter(restrict);
	        	saveFileChooser.setFileFilter(restrict);
	        	saveFileChooser.setBorder(new LineBorder(Color.BLUE));

	        	BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
	        	Graphics2D graphics2D = image.createGraphics();
	        	paint(graphics2D);
	        	try {
		        	int returnVal = saveFileChooser.showSaveDialog(null);
		        	if (returnVal == JFileChooser.APPROVE_OPTION) {		
		        	    File fileC = saveFileChooser.getSelectedFile();
		        	    //ImageIO.write(image, "jpg", new File("test.jpg"));	        	    
		        	    
		        	  //Son estas dos líneas o solo la de abajo
		        	    String newName = fileC.getName() + ".png";
		        	    ImageIO.write(image, "png", new File(newName));
		        	    
		        	  //Es solo esta línea o las dos de arriba
		        	  //ImageIO.write(image, "png", fileC);
		        	}	
	        	} catch (IOException e1) {
	        	// TODO Auto-generated catch block
	        		e1.printStackTrace();
	        		}
				
				
			}
		});
        
        
//        save_files.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				// TODO Auto-generated method stub
//				JFileChooser saveFileChooser = new JFileChooser(System.getProperty("user.dir"));
//	        	saveFileChooser.setDialogTitle("Enter the name for the text file");
//	        	FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt"); 
//	        	//saveFileChooser.addChoosableFileFilter(restrict);
//	        	saveFileChooser.setFileFilter(restrict);
//	        	saveFileChooser.setBorder(new LineBorder(Color.BLUE));
//	        	
//	        	try {
//		        	int returnVal = saveFileChooser.showSaveDialog(null);
//		        	if (returnVal == JFileChooser.APPROVE_OPTION) {		
//		        	    File fileB = saveFileChooser.getSelectedFile();
//		        	    //File fileC = 
//		        	    PrintWriter pw = new PrintWriter(fileB);
//		    			pw.println(0.0);
//		    			pw.flush();
//		    			
//		    			pw.close();	
//		        	    
//		        	}	
//	        	} catch (IOException e1) {
//	        	// TODO Auto-generated catch block
//	        		e1.printStackTrace();
//	        		}
//
//
//			}
//		});
        
        
        new_orders.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
	        	FileNameExtensionFilter filter = new FileNameExtensionFilter("DZN files", "dzn");
				FileChooser.setFileFilter(filter);
				FileChooser.setMultiSelectionEnabled(true);
				        	
	        	int returnVal = FileChooser.showOpenDialog(null);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {		
				    File fileB = FileChooser.getSelectedFile();	    
				}
				String newTime = JOptionPane.showInputDialog(null, "Enter the start time for the new orders", 
															"Information", JOptionPane.INFORMATION_MESSAGE);
			}
		});
        
        optimize.addActionListener(new ActionListener() {
        	        	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (scheduleJComponent.newTimeOperationFix != -1) {
					scheduleJComponent.shiftRight();
					ScheduleFrame.this.setTitle(ScheduleFrame.this.problemInstance.getName()+" | makespan: "+scheduleJComponent.timeHorizon);
					fix.setEnabled(true);
					scheduleJComponent.newTimeOperationFix = -1;
					//validate.setEnabled(false);
					optimize.setEnabled(false);	
					ql.setEnabled(false);	
					setResizable(true);
				}else {
					JOptionPane.showMessageDialog(ScheduleFrame.this, "You should fix one operation", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				
			}
        });
        
        
        
        ql.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (scheduleJComponent.newTimeOperationFix != -1) {
					//scheduleJComponent.shiftRight();
					try {
						
						routeQl(q,ScheduleFrame.this.bestSchedule,scheduleJComponent.operationFix);
						q.Machines[scheduleJComponent.operationFix.getMachine()].timeReSchedule = scheduleJComponent.operationFix.getStart_time()+scheduleJComponent.newTimeOperationFix;
						Operation op = q.Jobs[scheduleJComponent.operationFix.getJob()].operations.get(scheduleJComponent.operationFix.getOperation());
						op.end_time = op.initial_time + scheduleJComponent.newTimeOperationFix;
						op.proc_time = scheduleJComponent.newTimeOperationFix;
						//ArrayList <Operation> opNoModify = new ArrayList<Operation>();
						startTimes(q,scheduleJComponent.operationFix);
						
					//	String job_operation_machine = ""+scheduleJComponent.operationFix.getJob() + scheduleJComponent.operationFix.getOperation()+scheduleJComponent.operationFix.getMachine();
						q.ExecuteReSchedule();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ScheduleFrame.this.setTitle(ScheduleFrame.this.problemInstance.getName()+" | makespan: "+scheduleJComponent.timeHorizon);
					fix.setEnabled(true);
					//scheduleJComponent.newTimeOperationFix = -1;
					//validate.setEnabled(false);
					//optimize.setEnabled(false);	
					ql.setEnabled(true);	
					setResizable(true);
				}else {
					JOptionPane.showMessageDialog(ScheduleFrame.this, "You should fix one operation", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				
			}

			private void startTimes(QLearning q, Boton operationFix) {
				// TODO Auto-generated method stub
				ArrayList <Operation> opNoModify = new ArrayList<Operation>();
				for (int i = 0; i < q.Jobs.length; i++) {
					for (int j = 0; j < q.Jobs[i].operations.size(); j++) {
						if (q.Jobs[i].operations.get(j).initial_time < operationFix.getFinal_time()) {
							opNoModify.add(q.Jobs[i].operations.get(j));
						}else {
							//si no es la op fija y si tiene back to back 
							if (q.Jobs[i].operations.get(j).back2back_before != -1 && !(i == operationFix.getJob() && j == operationFix.getOperation())) {
								//si la operacion empieza despues de q acabe la op fija y su back to back empieza antes que acabe la fija 
								if (q.Jobs[i].operations.get(j).initial_time >= operationFix.getFinal_time() && q.Jobs[i].operations.get(j-1).initial_time < operationFix.getFinal_time()) {
									//quito la op del array
									opNoModify.remove(opNoModify.size()-1);
								}
							}
						}
						
						//agregarla si es la operation fija para q no se modifique
						if (i == operationFix.getJob() && j == operationFix.getOperation()) {
							opNoModify.add(q.Jobs[i].operations.get(j));
						}
						
						//si la fija tiene una operation que tiene q ir back to back con ella
						if (q.Jobs[i].operations.get(j).back2back_before != -1 && (i == operationFix.getJob() && operationFix.getOperation() == q.Jobs[i].operations.get(j).back2back_before)) {
							q.Jobs[i].operations.get(j).initial_time = operationFix.getStart_time() + scheduleJComponent.newTimeOperationFix;
							q.Jobs[i].operations.get(j).end_time = q.Jobs[i].operations.get(j).initial_time + q.Jobs[i].operations.get(j).proc_time;
							opNoModify.add(q.Jobs[i].operations.get(j));
						//	System.out.println("get back ");
						}
					}
				}
				
				//start times
				for (int i = 0; i < opNoModify.size(); i++) {
					//System.out.println(" job "+opNoModify.get(i).GetJob()+" op "+opNoModify.get(i).GetID()+" name "+ opNoModify.get(i).name+" Ma "+opNoModify.get(i).Ma);
					//time of machines
					if(opNoModify.get(i).end_time > q.Machines[opNoModify.get(i).Ma].timeReSchedule)
						q.Machines[opNoModify.get(i).Ma].timeReSchedule = opNoModify.get(i).end_time;
					
					//time of zones
					 String job_operation_machine = ""+opNoModify.get(i).GetJob()+opNoModify.get(i).GetID()+opNoModify.get(i).Ma;
					for (int j = 0; j < q.zone.length; j++) {
						//si ocupa la zona y el tiempo actual de la zona es menor q el final de la operation 
						if (q.zone[j].job_operation_occupied.get(job_operation_machine).equals(true) && q.zone[j].timeReScheduleZone < opNoModify.get(i).end_time) {
							q.zone[j].timeReScheduleZone = opNoModify.get(i).end_time;
						}
					}
					
					//operation para empezar re-schedule
					q.Jobs[opNoModify.get(i).GetJob()].opStart = opNoModify.get(i).GetID()+1;
					q.Jobs[opNoModify.get(i).GetJob()].temp_endtime = opNoModify.get(i).end_time;
				}
			}
        });
               
		this.add(scheduleJComponent);
		this.setVisible(true);
	}
	
	
    public void saveSchedule(String msg) throws IOException {
        scheduleJComponent.saveSchedule(problemInstance.getName() + "-" + msg);
    }
    
    public void routeQl(QLearning ql, Schedule initialSchedule, Boton operationFix) throws IOException {
        	
    	for (OperationAllocation operAlloc : initialSchedule.getAllocations()) {
    		Operation op = ql.Jobs[operAlloc.getOperation().getiJob()].operations.get(operAlloc.getOperation().getId());
	          op.Ma = operAlloc.getMachine().getId();//number of machine      
	          op.M = ql.Machines[operAlloc.getMachine().getId()]; //object class Machine   
	          
	          for (int i = 0; i < op.machines.length; i++) { //search position of the machine selected into array machines
				if (op.machines[i] == op.Ma+1) {
					op.index_Ma = i;
					op.proc_time = op.times[i];
				}
			}
	          
	         // System.out.println("job "+op.GetJob()+" op "+op.GetID()+" name "+op.name+" t_initial "+op.initial_time+" Ma "+op.Ma+" proc time "+op.proc_time);
	       }    	
    	 
    }
	
}
