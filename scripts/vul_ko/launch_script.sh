#!/bin/bash

# $1 number of simulations

rm -rf outpus
mkdir outputs

counter=1
while [ $counter -le 100 ]
do 
    java -Dde.renew.netPath=../../src/model/vul_ko_model/ -Dde.renew.classPath=../../src/weka.jar:../.. -jar ../../src/renew2.5.1/loader.jar script simulation_run
    
    mv ./cells.csv ./outputs/cells_$counter.csv
    mv ./cells_unlabeled.arff ./outputs/cells_unlabeled_$counter.arff
    ((counter++))
    cat ./fates_collection.csv >> ./outputs/fates.csv
    rm ./fates_collection.csv
done
    
echo "all done"
