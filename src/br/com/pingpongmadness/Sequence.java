package br.com.pingpongmadness;

import java.util.ArrayList;

import android.content.res.Resources;

public class Sequence {
	
	// #FEATURE: tipos de sequencia
	// SEQUENCE TYPES
    public static final int TYPE_NONE = 0;
    public static final int TYPE_SEQUENCE_001 = 1; // straight shots in a row (10)
    public static final int TYPE_SEQUENCE_002 = 2; // straight shots alternated (10)
    public static final int TYPE_SEQUENCE_003 = 3; // straight shots sequential (17)
    protected int sequenceType = TYPE_NONE;
    
    // SEQUENCE SHOTS
    public static ArrayList<Sequence.Shots> sequenceShots001 = null;
    public static ArrayList<Sequence.Shots> sequenceShots002 = null;
    public static ArrayList<Sequence.Shots> sequenceShots003 = null;
    public ArrayList<Sequence.Shots> sequenceAllShots = null;
	
	public Sequence(Resources res, int type) {
        switch (type) {
            case TYPE_SEQUENCE_001:
                sequenceAllShots = sequenceShots001;
                break;
            case TYPE_SEQUENCE_002:
                sequenceAllShots = sequenceShots002;
                break;
            case TYPE_SEQUENCE_003:
                sequenceAllShots = sequenceShots003;
                break;
            default:
                sequenceAllShots = sequenceShots001;
                break;
        }
        sequenceType = type;
    }
	
	 /*******************************************************************************************
	    *******************************************************************************************/

	    public static void loadAllShots(Resources res) {
	        sequenceShots001 = loadSequence(res, R.xml.sequence_001);
	        //sequenceShots002 = loadSequence(res, R.xml.sequence_002);
	        //sequenceShots003 = loadSequence(res, R.xml.sequence_003);
	    }
	    private static ArrayList<Sequence.Shots> loadSequence(Resources res, int content) {
	    	ArrayList<Sequence.Shots> mSequence = new ArrayList<Sequence.Shots>();
	    	
	    	return mSequence;
	    }
	/*******************************************************************************************
	    *******************************************************************************************/
	
	public static class Shots {
		
	}
}
