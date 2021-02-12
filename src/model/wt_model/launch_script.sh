#!/bin/bash

# $1 Renew Path
# $2 Output Folder
counter=31
while [ $counter -le 100 ]
do 
    java -Dde.renew.netPath=./launch_script.sh -jar /home/roberta/Documents/PhD/Tools/Renew/renew2.5/loader.jar script simulation_run
    
    mv ./cells.csv ./cells_$counter.csv
    
    mv ./cells_unlabeled.arff ./cells_unlabeled_$counter.arff
    ((counter++))
    
done
    
echo "all done"
