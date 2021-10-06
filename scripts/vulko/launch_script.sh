#!/bin/bash

# $1 number of simulations

rm -rf outputs
rm cell.csv
rm cells_unlabeled.arff
rm simulation.log
mkdir outputs

counter=1
while [ $counter -le $1 ]
do 
    java -Dde.renew.netPath=../../src/model/vulko_model/ -Dde.renew.classPath=../../src/weka.jar:../.. -jar ../../src/renew2.5.1/loader.jar script simulation_run 2> /dev/null
    
    mv ./cells.csv ./outputs/cells_$counter.csv
    mv ./cells_unlabeled.arff ./outputs/cells_unlabeled_$counter.arff
    mv ./simulation.log ./outputs/simulation_$counter.log
    #((counter++))
    counter=$((counter+1))
    cat ./fates_collection.csv >> ./outputs/fates.csv
    rm ./fates_collection.csv
done
    
echo "all done"
