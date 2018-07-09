# PopularBandPairs

This was done as part of software engineering interview...

The assignment is:

The attached text file contains the favorite musical artists of 1000 users from Some Popular Music Review Website. Each line is a list of up to 50 artists, formatted as follows:

Radiohead,Pulp,Morrissey,Delays,Stereophonics,Blur,Suede,Sleeper,The La's,Super Furry Animals,Iggy Pop\n
Band of Horses,Smashing Pumpkins,The Velvet Underground,Radiohead,The Decemberists,Morrissey,Television\n

Write a program that, using this file as input, produces an output file containing a list of pairs of artists which appear TOGETHER in at least fifty different lists. For example, in the above sample, Radiohead and Morrissey appear together twice, but every other pair appears only once. Your solution should be a cvs, with each row being a pair. For example:

Morrissey,Radiohead\n

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Optimization Notes:

* File coding needing to be UTF-8. JVM requires argument -Dfile.encoding=UTF-8
* Band names are converted to band ids. Processing strings is expensive. Strings are converted to integers. All processing is done on the integers. For display the integer is translated back to its string band name.
* Also show use a shadow table to improve look up of band id given band name. The performance gained is worth the extra memory and processing used.
* Hashmaps are used to represent tables. Hashmaps allow for unique ids assigned by the programmer similar to database tables. Hashmaps are also limited in size only by memory and provide efficient id based storing and retrieving of data.
* For UserLike I used an array. This requires possible looping through empty cells where users liked less than 50 bands. However, the trade off in speed of inserting, retrieving from an arraylist made this extra looping trival.

Algorithm
Create three tables (hashmap)

Bands ->  : int    unique id called bandId Prime Number
            string bandName
         
UserLikes -> int userId
             array of bandIds user likes
             
PairsLiked -> int unique id called semiprime
              int band 1
              int band 2
              int freq
             
1) Read in each line from the file inserting the user id and bandId into the UserLikes table.
   If band name is not yet in Bands table, add it. bandIds are prime numbers.
   
2) Loop through each user
      A) Loop through each combination of bands that user likes
      B) create semiprime of band id
      c) check semiprime in list of existing pairs. Add combination to list either by adding a new combination or incrementing frequency of an existing combination
      
3) Save to file only those pairs that appear N times.

Assumptions

* Since each user can list up to 50 bands and we have 1,000 users, we have a possibility of 50,000 unique bands
* Each user will like a band only once
* Band names are case sensitive so REM and Rem are two separate bands
* Different spellings of band names counts as a new band so REM and R.E.M. are two separate bands
* Order to the band names in the pairs is not matter.
