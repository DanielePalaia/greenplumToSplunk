# greenplumToSplunk

## Summary
This prototype is continuing the experiment done here: </br>
https://github.com/DanielePalaia/splunkExternalTables</br>
But in this case is doing the opposite test. From a Greenplum table this is storing in parallel logs on Splunk passing through a writable web external table.</br>
A .jar is provided (./target) </br>
This one is taking in input a csv file and storing it on splunk </br>
A bash script is encapsulating it. It catch piped input from Greenplum, save it on a temporary file and pass it to the .jar </br>
the script in then embedded on external writable web table definition </br>
</br>

## Prerequisites:
### 1. Generate the input Greenplum table:
**dashboard=# select * from services limit 2;**</br></br>
  id   | version | environment |     service_level     | company_id | top_service |               name                
-------+---------+-------------+-----------------------+------------+-------------+-----------------------------------
 10427 |       0 | Production  | DC - Business Premium |         24 | NO          | GSOC-LOCAL SECURITY INCIDENT (PT)
 10425 |       0 | Production  | DC - Business Premium |         26 | NO          | GSOC-LOCAL SECURITY INCIDENT (CZ)


### 2. Create a Greenplum external writable web table:
drop EXTERNAL TABLE log_output_writable_csv; </br>
CREATE WRITABLE EXTERNAL WEB TABLE log_output_writable_csv(id int, version text, environment text, service_level text, company_id int, top_service text, name text) EXECUTE '/home/gpadmin/splunk_input_data.sh' FORMAT 'CSV';

(you can find the script inside the script directory of the software) </br>

### 3. Create the linked script /home/gpadmin/splunk_input_data.sh
The linked script will receive the piped entries and send to the .jar to be sent to splunk 

if [ -p /dev/stdin ]; then </br>
        # If we want to read the input line by line </br>
        path="/home/gpadmin/" </br>
        fileName=$(cat /dev/urandom | tr -cd 'a-f0-9' | head -c 32) </br>
        filePath="$path$fileName" </br>
        while IFS= read line; do </br>
                echo "${line}" >> $filePath </br>
        done </br>
fi </br>
java -jar /home/gpadmin/greenplumToSplunk/target/splunk-0.0.1-SNAPSHOT.jar $filePath >> /home/gpadmin/prova </br>
rm $filePath </br> </br>

(you can find the script inside the script directory of the software) </br>

### 4. The software is written in Java so you need a JVM installed as well as Splunk
Java needs to be installed on every host of the Greenplum distributed system </br> 
In every segment host, you need also to create a .splunkrc  in your home directory specifying connection parameters like: </br>  

host=localhost </br> 
#Splunk admin port (default: 8089) </br> 
port=8089   </br> 
#Splunk username   
username=daniele   
#Splunk password   
password=XXXXXX   
#Access scheme (default: https)   
scheme=https  
#Splunk version number   
version=7.2.6   
 </br>

### 5. Copy the .jar
Copy the .jar in the /home/gpadmin folder of every host

## Running the software:
### 1. Running through psql </br>  

insert into log_output_writable_csv select * from services;</br>  

### 2. Search in splunk for the new created logs </br>  

## Compiling the software:

**If you wish to compile the software you can just mvn install on the root directory** </br>
**Jar file will be produced inside /target**
