html_clean()
{
export YURL="http://www.rosenheimwetter.de/tagesauswertungen.php"
export FILE="/tmp/yesterdaysRain.html"
export RAW="/tmp/raw.txt"
export PRETTY="/tmp/pretty.txt"
curl "$YURL" -H "Accept: application/json"  > $FILE
sed -e 's/<[^>]*>//g' $FILE | awk '$1=$1' | sed '1,11d' | sed '$d' > $RAW
#rm -f $FILE
}
clean_text()
{
COUNTER=1
echo "COMMA_SEPERATED_HEADERS_OF_TABLES" > $PRETTY
while read -r line
do
	if [ $COUNTER -le 6 ]
	then
	echo "$line" >> $PRETTY
	let COUNTER+=1
	else
	echo "$line"$'\n' >> $PRETTY
	let COUNTER=1
	fi
done < $RAW
}

