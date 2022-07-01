html_clean()
{
curl -s "URL_TO_BE_HIT" > table.html
sed -e 's/<[^>]*>//g' table.html | awk '$1=$1' | sed '1,11d' | sed '$d' | sed '$d' > dump.txt
rm -f table.html
}
