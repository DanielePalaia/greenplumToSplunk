drop EXTERNAL TABLE log_output_writable_csv;
CREATE WRITABLE EXTERNAL WEB TABLE log_output_writable_csv(id int, version text, environment text, service_level text, company_id int, top_service text, name text) EXECUTE '/home/gpadmin/splunk_input_data.sh' FORMAT 'CSV'; 
