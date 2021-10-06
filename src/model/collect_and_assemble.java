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


      public class Cell{
        public int mpk1;
        public int lin12;
        
        public Cell() {
            this.mpk1 = 0;
            this.lin12 = 0;
        }
        
        
        public void reset() {
            this.mpk1 = 0;
            this.lin12= 0;
        }
      }

      //NEW
      
      //Vector of cells
      private Cell[] cells = new Cell[6];
      
      //This counter counts the number of collected measures in the cells array
      private int measureCount = 0;
      
      //This parameter is provided by the user and is the number of features collected
      private int samplesCount;
      
      //Two arrays containing the set of samples for the two markers
      private double[][] mpk1_test;
      private double[][] lin12_test;
      
      //This parameter defines the number of measure per sample
      private int sampleRate;
      
      private int testset_index=0;
      //The name of the output file
      private String fileName;
      
      // the name of the fates array
      private double[] fatesArray;
      // the name of the fates file
      private String fatesFileName;
      
    
     
      
        /**  
       * Creating one File Writer based on a buffer, that is unique for all cells
       *          
       */    
      private FileWriter fw;
      private BufferedWriter bw;
      private FileWriter test_set_fw;
      private BufferedWriter test_set_bw;
      
      
      
      public collect_and_assemble(){
      
      }
      

      public collect_and_assemble(int sampleRate, int samplesCount, String fileName) {
            mpk1_test = new double[6][samplesCount];
            lin12_test = new double [6][samplesCount];
            this.sampleRate = sampleRate;
            this.samplesCount = samplesCount;
            this.fileName = fileName;
            for (int i=0;i<6; i++) {
                cells[i]=new Cell();
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
      
      public void insert (int mpk1, int lin12, int cell) {
            
            cells[cell].mpk1+=mpk1;
            cells[cell].lin12+=lin12;

      }     
      
      
      public void testandsample () {
      
            measureCount++;

            if (measureCount > sampleRate && testset_index < this.samplesCount) {
                
                for (int i=0; i<6; i++) {
                    mpk1_test[i][testset_index] = cells[i].mpk1 / (double) measureCount;
                    lin12_test[i][testset_index] = cells[i].lin12 / (double) measureCount;
                    cells[i].reset();
                }
                
                testset_index++;
                measureCount = 0;
            }
      }     
      
    public boolean test() {
        if (testset_index == this.samplesCount) {
            return true;
        } else {
            return false;
        }
    }
 

   
        
     public void test_set_to_arff_file(){
        
        
            String head = String.format("@RELATION %s \n\n", this.fileName); 
			            
			//along simulation time, samples time points synchronously for each cell for both mpk1 and lin12, and store them into arrays.
			            
			//the number of time points to be collected in the arrays 
              
			for (int i=0; i < this.samplesCount; i++) {
			                    
			                    
			    head = head.concat(String.format("@ATTRIBUTE mpk1_t%d NUMERIC\n", i));
			                    
			                    
			 }
			                
			 for (int i=0; i < this.samplesCount; i++) {
			                
			     head = head.concat(String.format("@ATTRIBUTE lin12_t%d NUMERIC\n", i));
			                    
			                    
			 }
			 
			 head = head.concat("@ATTRIBUTE class  {Primary-fate,Secondary-fate,Tertiary-fate}\n");
			 
			 head = head.concat("\n\n@data\n");
            
            String test_set_to_string = "";
            
            for(int i=0; i<6; i++){
            
            
                for(int j=0; j < samplesCount; j++){
			        double s = mpk1_test[i][j];
			        test_set_to_string = test_set_to_string.concat(String.format("%s,", String.valueOf(s)));
			    }
			    
			    for(int j=0; j < samplesCount; j++){
			        double s = lin12_test[i][j];
			        test_set_to_string = test_set_to_string.concat(String.format("%s,", String.valueOf(s)));
			    }
			    
			    System.out.println(i);
			    
			    test_set_to_string = test_set_to_string.concat("?");
			    test_set_to_string = test_set_to_string.concat("\n");
			    
			    
			    //switch(i){
			    
                    
                    /*
                    //for lstdpy ko and lin12ko
                    case 0:
                    case 1:
                    case 5:
                    test_set_to_string = test_set_to_string.concat("Tertiary-fate\n");
                    break;
                    case 2:
                    case 4:
                    case 3:
                    test_set_to_string = test_set_to_string.concat("Primary-fate\n");
                    break;
                   
                    
                    
                    
                     //for wt
                    case 0:
                    case 1:
                    case 5:
                    test_set_to_string = test_set_to_string.concat("Tertiary-fate\n");
                    break;
                    case 2:
                    case 4:
                    test_set_to_string = test_set_to_string.concat("Secondary-fate\n");
                    break;
                    case 3:
                    test_set_to_string = test_set_to_string.concat("Primary-fate\n");
                    break;
                     */
        
			    //}
			    
			}
			
			

            
                try {
                
                    File test_set_file = new File(this.fileName);

		            // if file doesnt exists, then create it
			                                
	                if (!test_set_file.exists()) {
			            test_set_file.createNewFile();
		            }

                    // true = append file
		                                
	                test_set_fw = new FileWriter(test_set_file.getAbsoluteFile(), true);
	                test_set_bw = new BufferedWriter(test_set_fw);
	                
	                test_set_bw.write(head);
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
            
        public void write_to_outcome_file(Double[] fatesArray, String fatesFileName) {
                
                    //this.fatesArray = fatesArray;
                    //this.fatesFileName = fatesFileName;
                    
                    //System.out.println(fatesArray.getClass());
                    //System.out.println(fatesFileName);


                
                    try {
               			
                        String dat = System.currentTimeMillis()+"\t";
			    
                        for (int i = 0; i < fatesArray.length; i++) {		
		        
                            dat = dat.concat(String.format("%s\t", String.valueOf(fatesArray[i])));
		        
                        }
                
                        dat = dat.concat("\n");
                     
                        File file = new File(fatesFileName);
        
                        //if file doesnt exists, then create it
                        if (!file.exists()) {
                            file.createNewFile();
                        }

                         //true = append file
                        fw = new FileWriter(file.getAbsoluteFile(), true);
                        bw = new BufferedWriter(fw);
        
                        bw.write(dat);
        
                        System.out.println("Done");
        
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
