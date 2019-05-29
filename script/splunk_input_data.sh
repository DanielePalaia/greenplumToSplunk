#test=($(< /dev/stdin));
if [ -p /dev/stdin ]; then
        # If we want to read the input line by line
	path="/home/gpadmin/"
	fileName=$(cat /dev/urandom | tr -cd 'a-f0-9' | head -c 32)
	filePath="$path$fileName"
        while IFS= read line; do
                echo "${line}" >> $filePath 
        done
fi
java -jar /home/gpadmin/greenplumToSplunk/target/splunk-0.0.1-SNAPSHOT.jar $filePath >> /home/gpadmin/prova
rm $filePath 
