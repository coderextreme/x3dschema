while read value
do
	if [[ "$value" =~ "value" ]]
	then
		read message
		if [[ "$message" =~ "message: should be equal to one of the allowed values" ]]
		then
			read file
			while ! [[ "$file" =~ "file" ]]
			do
				read file
			done
			echo $value
			echo $message
			echo $file
			echo -e "\r\n"
		fi
	fi
done
