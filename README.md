Twitter-sentimental-analysis
------------------
------------------

This Program uses Mongodb, LingPipe tool kit and MapReduce to classify whether the tweet is positive or negative. 

I have collected public tweets when the Government Shutdown was about to begin in US because of Obamacare. I have stored over 15000 tweets on to mongodb. The program for storing public tweet that I have wrote can be found here
```
https://github.com/kailashjoshi/twitter-mongodb
```
The data that I have collected for mongodb can be downloaded from
```
https://dl.dropboxusercontent.com/u/91258003/Obama.zip
```

Pre-requisite for running this program
============================
```
1. Install Mongodb and start mongodb with the data downloaded from above
   For example ./mongod --dbpath data/db
2. Install Apache Hadoop cluster for running MapReduce Job and start the cluster
3. Install Spring Tool Suite because the program is written on Spring Data
4. Install Apache Maven
```
Steps for running the program
============================
1. Download the source code
2. Unzip the folder and open a java class Classify.java
3. Edit the path of classifier.txt file (Save and close the file)
4. Go to the download source folder from your terminal window
5. Run maven command " mvn clean compile package"
6. Open Spring Tool Suite and import the project
7. Run the project
