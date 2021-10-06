for  i in `ls *.log`
do
	zip $i.zip $i
	echo $i
done
