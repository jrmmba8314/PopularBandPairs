package popBands;

/**
 * The numbers of users that liked a band pair
 *
 * @author John Mitchell (jrmmba@outlook.com)
 * @version July 9, 2018
 */
class LikedBandPairs
{
    /** One of the band liked */
    private int bandId1;
    /** Another of the bands liked */
    private int bandId2;
    /** Number of users who liked the band pair */
    private int freq;

    /**
     * Constructor for a pair of liked bands. Sets frequency to 1
     * @param bandId1 band id of a liked band
     * @param bandId2 band id of the other liked band
     */
    public LikedBandPairs(int bandId1, int bandId2)
    {
        this.bandId1 = bandId1;
        this.bandId2 = bandId2;
        this.freq = 1;
    }
    
    public void incrementFreq()
    {
        this.freq++;
    }
    
    /**
     * Returns the frequency this pair has been liked
     * 
     * @return the number of times this band pair has been selected
     */
    
    public int getFreq()
    {
        return this.freq;        
    }
    
    /**
     * Returns the band name of one of the bands liked in the pair. This is the other band name. Not the one from getSecondBandName.
     * 
     * @return the band name of one of the bands
     */
    public String getFirstBandName()
    {
        return Bands.getBandName(this.bandId1);
    }

    /**
     * Returns the band name of band liked in the pair. This is the other band name. Not the one from getFirstBandName. 
     * 
     * @return the band name of one of the bands
     */
    public String getSecondBandName()
    {
        return Bands.getBandName(this.bandId2);
    }
}
