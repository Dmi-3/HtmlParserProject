# Html Parser Project
This application provides functional for calculating of uniq words in a html page or a local text file.

## Guide
#### Configuration
In order to correctly program working, you need to configure the configuration file "<b>config.properties</b>" 
that should be in a root of program. (If you use app.exe file, the config file should be in the same folder)

##### Example of "config.properties"

```
# Set of delimiters that define splitting text
delimiters =  [' ', ',', '.', '!', '?', '"', ';', ':', '[', ']', '(', ')','\\n', '\\r', '\\t']

# If this true, after download page, it will be parsed to text user see, else service will be working only with html
onlyTextFromHtml = true

# Path to file where downloaded and processing page will be saved
parsedPageFilePath = downloadPage.txt

# Path to file where result of calculated uniq word will be stored
resultFilePath = result.txt
```
#### Application functions
##### Process URL page
This function allow you to process any web page, but make sure you have an internet connection.

<b>Example of a correct</b>: https://www.simbirsoft.com
##### Process file
This function allow you to process local file (encoding UTF-8) on your computer.

##### Result of program
The result of successfully worked program is a set of uniq word/count pairs like below:

```
...
Press - 1
private - 1
Rest - 26
for - 15
Deploy - 1
Businesses - 1
content - 1
Kris - 2
related - 3
Electron - 1
Left - 2
We - 3
International - 1
Streaming - 1
Topics - 1
lowercase - 2
...
``` 

You can also store this result in file on you computer. Path to this file you specify in config file by "<b>resultFilePath</b>" property 