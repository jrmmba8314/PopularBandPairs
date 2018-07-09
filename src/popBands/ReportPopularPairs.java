package popBands;

//** needed for reading from file */
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;

/** Needed for HashMaps */
import java.util.HashMap;

/**
 * The main controlling class of PopularBands
 *
 * @author John Mitchell
 * @version July 9, 2018
 */
class ReportPopularPairs
{
    /**
     * the name of the file from where to get the data
     */
    static String fileName = "input (2).txt";
    
    /**
     * the name of the file where to save the output
     */
    static String outFileName = "output.txt";
    
    /**
     * The number of times a band pair is liked in order to be included in the final report
     */
    static int likedTimes = 50;
    
    /**
     * The table containing the list of Bands and their id 
     * Integer - unique id for band. Prime Number 
     * String - a unique band name
     */
    static HashMap<Integer, String> bandsTab     = new HashMap<Integer, String>();
    /**
     * This is the same as bandsTab but the name and id are reversed. This shadow table is maintained in improve lookup of band id given band name
     */
    static HashMap<String, Integer> shadowBandsTab     = new HashMap<String, Integer>();

    /** 
     * The table containing the list of which users like which bands.
     * Integer - unique id for each user
     * bandIds - Array of bandIds user likes. At the most this is 50
     */
    static HashMap<Integer, UserLikes> userLikesTab = new HashMap<Integer, UserLikes>();
    /** keeps track of last user id used*/
    static private int lastUserid;
    
    /**
     * The table containing a listing of all pairs of bands liked including how many users have liked them.
     * Integer - SemiPrime number of the two bandId prime numbers multiplied
     * Integer - the one bandIds of a pair of bands liked by a single user
     * Integer - the two bandIds of a pair of bands liked by a single user
     * Integer - how many users of liked this pair of bands
     */
    static HashMap<Long, LikedBandPairs> likedBandPairsTab = new HashMap<Long, LikedBandPairs>();

    /**
     * Main controlling method for the project.
     * 1) Read the data and insert into appropriate tables
     * 2) Find and count pairs of liked user bands
     * 3) Report pairs of liked bands
     * 
     * @param args none used
     */
    public static void main(String args[])
    {
        System.out.println("Started");
        
        System.out.println("*** Reading Data");
        getTheData();
        System.out.println("*** number of users  = " + lastUserid);
        System.out.println("*** Number of bands  = " + bandsTab.size());

        System.out.println("");
        
        System.out.println("+++ Finding Pairs");
        countBandPairLikes();
        
        System.out.println("");
        
        System.out.println("^^^ Write out results");
        printOutPairs(likedTimes);
        
        System.out.println("");
        
        System.out.println("Ended");
    };
    
    /**
     * Reads in each line of band preferences. The userId and Band Name (actually bandId) pair are added to the UserLikes table
     * Each new link of band preferences is for a new user
     */
    private static void getTheData()
    {
        Scanner fileScanner = null;
        
        try
        {
            fileScanner = new Scanner(new File(fileName));
            while (fileScanner.hasNextLine())
            {
                lastUserid++; // this means we have a new user
                Scanner lineScanner = new Scanner(fileScanner.nextLine());
                
                lineScanner.useDelimiter(",");
                UserLikes myUserLikes = new UserLikes();;
                int index = 0;
                while (lineScanner.hasNext()) 
                {
                    String bandName = lineScanner.next();
                    myUserLikes.myBands[index] = Bands.getBandId(bandName);
                    index++;
                }
                lineScanner.close();
                userLikesTab.put(lastUserid, myUserLikes);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            fileScanner.close();
        }
    }

    /**
     * Loops through UserLikes and counts pairs of liked bands
     */
    private static void countBandPairLikes()
    {
        for (int userId=1; userId <= lastUserid; userId++)
        {
            int[] myBands = userLikesTab.get(userId).myBands;
            
            for (int band1=0; band1 < myBands.length; band1++)
            {   
                if (myBands[band1] > 0)
                {
                    for (int band2=band1 + 1; band2 < myBands.length; band2++)
                    {
                        if (myBands[band2] > 0)
                        {
                            long semiPrime = (long) myBands[band1] * (long) myBands[band2];
                            
                            LikedBandPairs myLikedBandPair = likedBandPairsTab.get(semiPrime);
                            if (myLikedBandPair == null)
                            {
                                myLikedBandPair = new LikedBandPairs(myBands[band1], myBands[band2]);
                                likedBandPairsTab.put(semiPrime, myLikedBandPair);
                            }
                            else
                            {
                                myLikedBandPair.incrementFreq();
                                likedBandPairsTab.replace(semiPrime, myLikedBandPair);
                            }
                            // replace(K key, V value)                            
                        }
                    } // looping through band2
                } // band1 > 0
            } // looping through band1
        } // looping through users
    } // countBandPairLikes
    
    /**
     * Saves to a file the band names in pairs that are liked more than n times.
     * 
     * @param n print only pairs that are liked n or more times
     */
    private static void printOutPairs(int n) 
    {
        FileWriter writer = null;
        
        try
        {
            writer = new FileWriter(outFileName);
            
            for (HashMap.Entry<Long, LikedBandPairs> pair : likedBandPairsTab.entrySet())
            {
                LikedBandPairs likedBandPair = pair.getValue();
                if (likedBandPair.getFreq() >= n)
                {
                    writer.append(likedBandPair.getFirstBandName() + "," + likedBandPair.getSecondBandName());
                    writer.append('\n');
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                writer.flush();
                writer.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
} // class ending
