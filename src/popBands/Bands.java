package popBands;

/** Needed for BigInteger used to find next primes */
import java.math.*;

/**
 * The actual band list is a HashMap found in the main class (bandsTab).
 * This class contains methods and instance variables needed to manage bandsTab
 * @author theMitchells
 *
 */
public class Bands
{
    /** the last prime generated */
    static private int lastPrime = 0;

    /**
     * Find the bandId of the given band name. If no bandId is found, this is a new band,
     * creates a new id, updates the appropriate tables and returns that new id
     * 
     * @param bandName the name of the band
     * @return bandId the id associated with the band (a prime number)
     */
    static int getBandId(String bandName)
    {
        int tempId = ReportPopularPairs.shadowBandsTab.getOrDefault(bandName, -1);
        if (tempId < 0)
        {
            tempId = nextPrime();
            ReportPopularPairs.shadowBandsTab.put(bandName, tempId);
            ReportPopularPairs.bandsTab.put(lastPrime, bandName);
        }
        return tempId;
    }
    
    /**
     * Find the band name based off of the band id. If band id is not found, return an empty string.
     * 
     * @param bandId the band id we are interested in
     * @return bandName the band name of the band id we are interested in
     */
    static String getBandName(int bandId)
    {
        return ReportPopularPairs.bandsTab.getOrDefault(bandId, "");
    }
    
    /**
     * Generates the prime number after lastPrime and sets lastPrime to that number.
     * 
     * @return the next prime number
     */
    private static int nextPrime()
    {
        BigInteger b = new BigInteger(String.valueOf(lastPrime));
        lastPrime = Integer.parseInt(b.nextProbablePrime().toString());
        return lastPrime;
    }
}
