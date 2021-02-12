# NWN_CElegans_VPC_model
This repo contains a NWN model of the C. Elegans VPC development model.



To run a simulation:
- Open the terminal
- In the scripts folder enter the folder of the condition you want to simulate.
- Run the simulation by entering the command: source launch_script.sh <number_of_simulations>
- The simulation creates a folder named outputs. The folder contains
- One file namede cells<number>.csv for each simulation
- One file named cells_unlabeled<number>.arff for each simulation 
- One file named fates.csv containing the fate predictions (every line is a simulation.)

N.B. Every time you run the simulation the output file is erased and recreated. Move your results outside if you want to keep them.

~~~~
.
├── README.md
├── results                                                         //Simulation results
├── scripts                                                         //Scripts running the simulations
└── src
    ├── model
    │   ├── collect_and_assemble.class
    │   ├── collect_and_assemble.java                               //Java class used within the fate manager to perform the classification 
    │   ├── lin12_gf                                                //Same organization as wt_model
    │   │   ├── fates_collection_lin12_gf.csv
    │   │   ├── VPC_CElegans_ac.rnw
    │   │   ├── VPC_CElegans_cell_building_blocks.rnw
    │   │   ├── VPC_CElegans_hyp7.rnw
    │   │   ├── VPC_CElegans_spatial_grid.rnw
    │   │   ├── VPC_CElegans_spatial_grid.sns
    │   │   └── VPC_CElegans_states_landscape.rnw
    │   ├── lin12_ko_model                                          //Same organization as wt_model
    │   │   ├── fates_collection_lin12_ko.csv
    │   │   ├── VPC_CElegans_ac.rnw
    │   │   ├── VPC_CElegans_cell_building_blocks.rnw
    │   │   ├── VPC_CElegans_hyp7.rnw
    │   │   ├── VPC_CElegans_spatial_grid.rnw
    │   │   ├── VPC_CElegans_spatial_grid.sns
    │   │   └── VPC_CElegans_states_landscape.rnw
    │   ├── lst_lf_dpy23_lf                                         //Same organization as wt_model
    │   │   ├── fates_collection_lstkodyp23ko.csv
    │   │   ├── VPC_CElegans_ac.rnw
    │   │   ├── VPC_CElegans_cell_building_blocks.rnw
    │   │   ├── VPC_CElegans_hyp7.rnw
    │   │   ├── VPC_CElegans_spatial_grid.rnw
    │   │   ├── VPC_CElegans_spatial_grid.sns
    │   │   └── VPC_CElegans_states_landscape.rnw
    │   ├── lst_lf_model                                            //Same organization as wt_model
    │   │   ├── fates_collection_lst_lf.csv
    │   │   ├── VPC_CElegans_ac.rnw
    │   │   ├── VPC_CElegans_cell_building_blocks.rnw
    │   │   ├── VPC_CElegans_hyp7.rnw
    │   │   ├── VPC_CElegans_spatial_grid.rnw
    │   │   ├── VPC_CElegans_spatial_grid.sns
    │   │   └── VPC_CElegans_states_landscape.rnw
    │   ├── vul_ko_model                                            //Same organization as wt_model
    │   │   ├── fates_collection_vul_ko.csv
    │   │   ├── VPC_CElegans_ac.rnw
    │   │   ├── VPC_CElegans_cell_building_blocks.rnw
    │   │   ├── VPC_CElegans_hyp7.rnw
    │   │   ├── VPC_CElegans_spatial_grid.rnw
    │   │   ├── VPC_CElegans_spatial_grid.sns
    │   │   └── VPC_CElegans_states_landscape.rnw
    │   └── wt_model                                                //The wild-type model
    │       ├── VPC_CElegans_ac.rnw                                 //The AC cell net-tocken 
    │       ├── VPC_CElegans_cell_building_blocks.rnw               //The Pnp net token
    │       ├── VPC_CElegans_hyp7.rnw                               //The Hyp7 cell net-tocken
    │       ├── VPC_CElegans_spatial_grid.rnw                       //The system-net
    │       ├── VPC_CElegans_spatial_grid.sns                       //The compiled model (.sns, Shadow Net System)
    │       └── VPC_CElegans_states_landscape.rnw                   //The Fates Manager (FM) net-token
    ├── renew2.5.1                                                  // Renew v2.5.1 jar required to run simulations
    ├── weka.jar                                                    // Weka jar required to implement the FM
    └── weka-src.jar                                                // Weka-src jar required to implement the FM
~~~~~
