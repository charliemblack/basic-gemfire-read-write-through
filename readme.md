#Implementing The Read / Write through pattern With Apache Geode

This project contains the material I created in a youtube video.    In the video I walked though implementing the read and write portions of this pattern.

![alt text](images/patterns.png)

The read through pattern allows Geode to potentially lazy load data from a back end store.   In the video I used Postgres, but the back end store could be nearly any thing that one would like to cache.   

We can also combine the various storage patterns to enable Geode to satify the the applications requirements. 

Maybe we are caching for performance or we caching for cost.   The good example for caching for cost is mainframes.

## The Read Pattern

In the read pattern we are using Geode to cache results from the back end system.   In the below example we are using JDBC to retrive data from some data base.   

Once we have the result Geode uses that value and the provided key and stores it in the region the cache loader is associated with.

![alt text](images/read.png)



## The Write Pattern

In the write pattern we use Geode to write the result to the backend data source.   Once that has successfully been written Geode then commits the value to the regions associated to the cache writer.

![alt text](images/write.png)


# YouTube

https://youtu.be/jWDePZcjTH4    
