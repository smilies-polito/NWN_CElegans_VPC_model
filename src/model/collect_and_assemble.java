package src.model;
import java.util.concurrent.Callable;
import java.util.NoSuchElementException; 
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.Set;
import java.util.Collection;
import java.util.Arrays;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

import de.renew.engine.simulator.SimulationThreadPool;
import de.renew.net.NetInstance;
import de.renew.net.Place;
import de.renew.net.PlaceInstance;
import de.renew.engine.searchqueue.SearchQueue;

 
 /**  
  * Thread safe fixed size circular buffers implementation. Backed by an array.  
  *   
  * @author brad  feat. roberta feat. anonymous
  */  
 
public class collect_and_assemble {  
          
      // accessible data storages  
      private Integer[][][] data;
      private Integer[][] insertLocation;
      private Integer[][] sizebuf;
      
      private Integer[][] mpk1_test;
      private Integer[][] lin12_test;
      public double[][] test_set;
      public double[] avgs_array = new double[2];
      
      
      private int bufferSize;
      
        /**  
       * Creating one File Writer based on a buffer, that is unique for all cells
       *          
       */    
     
      private FileWriter fw;
      private BufferedWriter bw;
      private FileWriter test_set_fw;
      private BufferedWriter test_set_bw;
      
      // sampling rate for the test set creation
      
      
      //public int sampling_counter=0;
      //public int counter;
      private int counterA;
      private int counterB;
      private String dat;
      private String test_set_to_string;
      public double[][] test_arrays;
      private double avg1;
      private double avg2;
      private int half_N;
      private double sum1;
      private double sum2;
      private double[] avgs;
      public int Nt; //numner of time points after which the simulation tracks reach a sort of steady state, i. e. point after wich it makes sense to perform classifications. 
      public double sampling_rate;
      
      //checkpoints
      public boolean insert_for_a_start=false;
      public boolean test_set_isset=false;
      public boolean test_set_isbuilt=false;
      public boolean arff_file_isset=false;
      public String generic_check;
      public int j=0;
      public int i=0;
      public double[] outcomes_array = new double[6];
      public double outcome;
      public double[] outcomes;
      
      // 
      

      /**  
       * Creating two circular buffers with the specified uniform size for each pnp cell - one for mpk1 and the other for lin12.  
       *   
       * @param bufferSize  
       *      - the maximum size of the buffers , unique for all pnp cells      
       */  
      
      public void create_buffers(int bsize) {
            
            this.bufferSize=bsize;
            insertLocation = new Integer[6][2];
            sizebuf = new Integer[6][2];
            
            
            data = (Integer[][][]) new Integer[6][2][this.bufferSize];
            
            for (int i=0; i<6; i++) {
                insertLocation[i][0] = 0;
                insertLocation[i][1] = 0;
                sizebuf[i][0] = 0;
                sizebuf[i][1] = 0;
            }  
      }  
      
      
      /**  
       * Separately and independently for each cell, this group of methods
       * inserts items at the end of the queues. If a queue is full, the oldest  
       * value will be removed and head of the queue will become the second oldest  
       * value.  
       *   
       * @param item  
       *      - the item to be inserted  
       */  
      
      public void insert (int item1, int item2, int cell) {
            
            //counting "insert" events for sampling 
            
            
            counterA++;
            
            data[cell][0][insertLocation[cell][0]]=item1;
            data[cell][1][insertLocation[cell][1]]=item2;
            
            insertLocation[cell][0] = (insertLocation[cell][0]+1) % bufferSize;
            insertLocation[cell][1] = (insertLocation[cell][1]+1) % bufferSize;
            
            if (sizebuf[cell][0] < bufferSize) {
                sizebuf[cell][0]++;
            }
            
            if (sizebuf[cell][1] < bufferSize) {
                sizebuf[cell][1]++;
            }
            
            if (insert_for_a_start==false){
            
                insert_for_a_start=true;    
            }
     
      }     
      
      public void insert_outcomes (double outcome) {
            
            
            if (this.i < this.outcomes_array.length) {
		        
		            this.outcomes_array[i] = outcome;
		            i++;
		            
		    }else{
		    
		        i=0;
		        
		    }
            
            
            
     
      }     
      
      public double[] average_new (int cell) {
            
          //avgs_array = {0.0, 0.0};
          
          if (insert_for_a_start == true) {
            
                sum1 = 0.0;
                sum2 = 0.0;
                
                for (int i = 0; i < this.bufferSize; i++) {
                    sum1 += data[cell][0][i];
                }
                
                for (int i = 0; i < this.bufferSize; i++) {
                    sum2 += data[cell][1][i];
                }     
                
                double avg1 = sum1 / this.bufferSize;
                double avg2 = sum2 / this.bufferSize;
                
                
                
                avgs_array[0] = avg1;
                avgs_array[1] = avg2;
                
                
          }
          
          return avgs_array;      
      }
      
      public double[] average (int cell) {
            
          //avgs_array = {0.0, 0.0};
          
          if (insert_for_a_start == true) {
            
                sum1 = 0.0;
                sum2 = 0.0;
                
                for (int i = 0; i < sizebuf[cell][0]; i++) {
                    sum1 += data[cell][0][i];
                }
                
                for (int i = 0; i < sizebuf[cell][1]; i++) {
                    sum2 += data[cell][1][i];
                }     
                
                double avg1 = sum1 / sizebuf[cell][0];
                double avg2 = sum2 / sizebuf[cell][1];
                
                
                
                avgs_array[0] = avg1;
                avgs_array[1] = avg2;
                
                
          }
          
          return avgs_array;      
      }
         
          
      /**  
       * Appends to a file a timestamp, and for P3p, P4p, P5p, P6p, P7p, P8p cells: current avg of data arrays
       *   
       *  
       */    
        
      public void write_to_file(String filename) {
      
            if (insert_for_a_start == true) {
            
                
                try {
               			
			        String dat = System.currentTimeMillis()+"";
			    
			        for(int i=0; i<6; i++){
			        
			            avgs = average(i);
			        
			            //dat+="\t" + avgs[0] + avgs[1];
			            
			            dat = dat.concat(String.format("\t%s", String.valueOf(avgs[0])));
			            dat = dat.concat(String.format("\t%s", String.valueOf(avgs[1])));
			        }
			    
			        dat = dat.concat("\n");
        
                
                     
			        File file = new File(filename);
        
			        // if file doesnt exists, then create it
			        if (!file.exists()) {
			        	file.createNewFile();
		        	}

			        // true = append file
			        fw = new FileWriter(file.getAbsoluteFile(), true);
			        bw = new BufferedWriter(fw);
        
			        bw.write(dat);
        
			        //System.out.println("Done");
        
		        } catch (IOException e) {
        
			        e.printStackTrace();
        
		        } finally {
        
			        try {
        
			        	if (bw != null){
			        		bw.close();}
        
			        	if (fw != null){
			        		fw.close();}
        
			        } catch (IOException ex) {
        
			        	ex.printStackTrace();}
			        	
			        }
           	    
            }
      } 
      
      
      public void write_to_outcome_file(String filename) {
      
            //if (insert_for_a_start == true) {
            //this.outcomes_array = outcomes;
                
                try {
               			
			        String dat = System.currentTimeMillis()+"\t";
			    
			        for (int i = 0; i < this.outcomes_array.length; i++) {		
		        
		                dat = dat.concat(String.format("%s\t", String.valueOf(this.outcomes_array[i])));
		        
                    }
                
                    dat = dat.concat("\n");
                     
                
                     
			        File file = new File(filename);
        
			        // if file doesnt exists, then create it
			        if (!file.exists()) {
			        	file.createNewFile();
		        	}

			        // true = append file
			        fw = new FileWriter(file.getAbsoluteFile(), true);
			        bw = new BufferedWriter(fw);
        
			        bw.write(dat);
        
			        //System.out.println("Done");
        
		        } catch (IOException e) {
        
			        e.printStackTrace();
        
		        } finally {
        
			        try {
        
			        	if (bw != null){
			        		bw.close();}
        
			        	if (fw != null){
			        		fw.close();}
        
			        } catch (IOException ex) {
        
			        	ex.printStackTrace();}
			        	
			        }
			        
			    
           	    
            
      } 
				   
          
             /**  
            * Appends to a file averaged values from the ring buffers, in a structure allowing for subsequent classification in weka.
            * In a first place, the file is structured as a matrix (array of arrays) in which each row corresponds to a Pnp cell. So
            * we have 6 rows. Each of them is divided in two parts: one for the temporal series of mpk1 values, the other for lin12 values.
            * 
            *  
            */     
            
      public void write_to_test_set(int d, int Nt) {

            	        
	        if (insert_for_a_start == false) { 
	        
	            //que fare?
	            
	             
	            
	        } else if (insert_for_a_start == true) { 
		                
                                         
		          try {
		                    
                        if (test_set_isbuilt == false) {
        
                            
                            test_set = build_test_set(d, Nt);
			                
			                test_set_isbuilt = true;
                            
           		        } else if (test_set_isbuilt == true) {
                        
                            if ((counterA / 6) >= sampling_rate && j < d) {
                                
                                        
                                counterA = 0;
                                    
                                      
                                for (int i = 0; i < 6; i++) {
			                                
                                    
                                    
                    	            avgs = average(i);
			                                    
	    	                        test_set[i][this.j] = avgs[0];
			                                    
	    	                        test_set[i][this.j+d] = avgs[1];
	    	                        
	    	                        
			                                
		                        }    
		                        
		                        this.j++;    
		                    }     
                             
                                
           		        }        
         
		          
		            } catch (Exception e) {

			            e.printStackTrace();

		            }
		            
            }		        
      
      }
      

        
        
        public void set_up_arff_test_set(String output_filename, int n_of_attributes){
        
            String head = String.format("@RELATION %s \n\n", output_filename); 
			            
			//along simulation time, samples time points synchronously for each cell for both mpk1 and lin12, and store them into arrays.
			            
			//the number of time points to be collected in the arrays 
	
			                
			for (int i=0; i < n_of_attributes; i++) {
			                    
			                    
			    head = head.concat(String.format("@ATTRIBUTE mpk1_t%d NUMERIC\n", i));
			                    
			                    
			 }
			                
			 for (int i=0; i < n_of_attributes; i++) {
			                
			     head = head.concat(String.format("@ATTRIBUTE lin12_t%d NUMERIC\n", i));
			                    
			                    
			 }
			 
			 head = head.concat("@ATTRIBUTE class  {Primary-fate,Secondary-fate,Tertiary-fate}\n");
			 
			 head = head.concat("\n\n@data\n");
			                
			 try {
            
                File test_set_file = new File(output_filename);

		        // if file doesnt exists, then create it
			                            
	            if (!test_set_file.exists()) {
			        test_set_file.createNewFile();
		        }

                // true = append file
		                            
	            test_set_fw = new FileWriter(test_set_file.getAbsoluteFile(), true);
	            test_set_bw = new BufferedWriter(test_set_fw);
	                        
	            test_set_bw.write(head);

		    
		    } catch (Exception e) {

			            e.printStackTrace();

		            } finally {

			            try {

				            if (test_set_bw != null){
					            test_set_bw.close();}

				            if (test_set_fw != null){
					            test_set_fw.close();}

			            } catch (IOException ex) {

				            ex.printStackTrace();
				                
			            }
			                
			                
		            }
		            
		        test_set_isset = true;

        
        }
        
        public double[][] build_test_set(int d, int Nt){
        
        
        //defining sampling rate, corresponding to Nt/d. We hypothesize Nt is around 1000, according to Bonzanni's assumptions.
        //But Nt is to be considered a parameter to identify empirically observing simulations and in particular
        //when tracks reach a steady state in simulations. 
        //And, it must include any known delays significant to the process evolution as well. 
                            
        
            sampling_rate = Nt / d;
                            
			test_arrays = new double[6][(2*d)+1];
			
			return test_arrays;
        
        }
        
        public void test_set_to_arff_file(String filename, double[][] test_set, int d){
            
            String test_set_to_string = "";
            
            for(int i=0; i<6; i++){
            
            
                for(int j=0; j < (2*d); j++){
			            
			        double s = test_set[i][j];
			        
			        test_set_to_string = test_set_to_string.concat(String.format("%s,", String.valueOf(s)));
			    
			    }
			    
			    System.out.println(i);
			    
			    test_set_to_string = test_set_to_string.concat("?");
			    test_set_to_string = test_set_to_string.concat("\n");
			    
			}
			
			arff_file_isset = true;
            
            
            if (arff_file_isset == true) {
            
                try {
                
                    File test_set_file = new File(filename);

		            // if file doesnt exists, then create it
			                                
	                if (!test_set_file.exists()) {
			            test_set_file.createNewFile();
		            }

                    // true = append file
		                                
	                test_set_fw = new FileWriter(test_set_file.getAbsoluteFile(), true);
	                test_set_bw = new BufferedWriter(test_set_fw);
	                            
	                test_set_bw.write(test_set_to_string);

		            //analogous of "System.out.println("Done")"
			                                
	                //test_set_isbuilt = true;
			                            
		        } catch (Exception e) {

			                e.printStackTrace();

		        } finally {

			        try {

				        if (test_set_bw != null){
					        test_set_bw.flush();
					        test_set_bw.close();}

				        if (test_set_fw != null){
					        test_set_fw.close();}

			        } catch (IOException ex) {

				        ex.printStackTrace();
				                    
			        }
                
		       }

            }
        }    
       
}  
