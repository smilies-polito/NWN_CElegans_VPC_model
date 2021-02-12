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


<code>
.
├── README.md
├── results
├── scripts
│   └── wt
│       ├── cells_fated_v3.arff
│       ├── launch_script.sh
│       └── simulation_run
└── src
    ├── model
    │   ├── collect_and_assemble.class
    │   ├── collect_and_assemble.java
    │   ├── lin12_gf
    │   │   ├── fates_collection_lin12_gf.csv
    │   │   ├── VPC_CElegans_ac.rnw
    │   │   ├── VPC_CElegans_cell_building_blocks.rnw
    │   │   ├── VPC_CElegans_hyp7.rnw
    │   │   ├── VPC_CElegans_spatial_grid.rnw
    │   │   ├── VPC_CElegans_spatial_grid.sns
    │   │   └── VPC_CElegans_states_landscape.rnw
    │   ├── lin12_ko_model
    │   │   ├── fates_collection_lin12_ko.csv
    │   │   ├── VPC_CElegans_ac.rnw
    │   │   ├── VPC_CElegans_cell_building_blocks.rnw
    │   │   ├── VPC_CElegans_hyp7.rnw
    │   │   ├── VPC_CElegans_spatial_grid.rnw
    │   │   ├── VPC_CElegans_spatial_grid.sns
    │   │   └── VPC_CElegans_states_landscape.rnw
    │   ├── lst_lf_dpy23_lf
    │   │   ├── fates_collection_lstkodyp23ko.csv
    │   │   ├── VPC_CElegans_ac.rnw
    │   │   ├── VPC_CElegans_cell_building_blocks.rnw
    │   │   ├── VPC_CElegans_hyp7.rnw
    │   │   ├── VPC_CElegans_spatial_grid.rnw
    │   │   ├── VPC_CElegans_spatial_grid.sns
    │   │   └── VPC_CElegans_states_landscape.rnw
    │   ├── lst_lf_model
    │   │   ├── fates_collection_lst_lf.csv
    │   │   ├── VPC_CElegans_ac.rnw
    │   │   ├── VPC_CElegans_cell_building_blocks.rnw
    │   │   ├── VPC_CElegans_hyp7.rnw
    │   │   ├── VPC_CElegans_spatial_grid.rnw
    │   │   ├── VPC_CElegans_spatial_grid.sns
    │   │   └── VPC_CElegans_states_landscape.rnw
    │   ├── VPC_specification_example
    │   ├── vul_ko_model
    │   │   ├── fates_collection_vul_ko.csv
    │   │   ├── VPC_CElegans_ac.rnw
    │   │   ├── VPC_CElegans_cell_building_blocks.rnw
    │   │   ├── VPC_CElegans_hyp7.rnw
    │   │   ├── VPC_CElegans_spatial_grid.rnw
    │   │   ├── VPC_CElegans_spatial_grid.sns
    │   │   └── VPC_CElegans_states_landscape.rnw
    │   └── wt_model
    │       ├── cells.csv
    │       ├── cells_unlabeled.arff
    │       ├── fates_collection_wt.csv
    │       ├── launch_script.sh
    │       ├── simulation_run
    │       ├── VPC_CElegans_ac.rnw
    │       ├── VPC_CElegans_cell_building_blocks.rnw
    │       ├── VPC_CElegans_hyp7.rnw
    │       ├── VPC_CElegans_spatial_grid.rnw
    │       ├── VPC_CElegans_spatial_grid.sns
    │       ├── VPC_CElegans_states_landscape.aut
    │       └── VPC_CElegans_states_landscape.rnw
    ├── renew2.5.1
    │ 
    ├── src
    │   └── model
    ├── weka.jar
    └── weka-src.jar
</code>
