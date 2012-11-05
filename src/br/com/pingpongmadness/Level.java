package br.com.pingpongmadness;

import java.util.ArrayList;

import android.content.res.Resources;

public class Level {
	
	// SEQUENCE TYPES
    public static final int TYPE_NONE = 0;
    public static final int TYPE_TUTORIAL_001 = 1; // sequences 1,2,3 (37)
    protected int levelType = TYPE_NONE;
	
    // LEVEL MODES
    public static final int MODE_NONE = 0;
    public static final int MODE_NORMAL = 1;
    public static final int MODE_ENDLESS = 2;
    protected int levelMode = MODE_NONE;
    
    // MOVEMENT PATH
    public ArrayList<Level.Sequences> allLevel = null;
    private boolean isLevelLoaded = false;
    
	public Level(Resources resources, int type) {
        levelType = type;

        if (isLevelLoaded == false) {
            allLevel = loadLevel(resources, getLevelList(type));
            if (allLevel != null && allLevel.size() > 0) {
                if (allLevel.get(0).getSequenceType() != 0) {
                    levelMode = MODE_NORMAL;
                }
                else {
                    levelMode = MODE_ENDLESS;
                }
            }
            isLevelLoaded = true;
        }
    }
	
	 private int getLevelList(int type) {
	        switch (type) {
	            case TYPE_TUTORIAL_001:
	                return R.xml.level_001;
	            default:
	                return R.xml.level_001;
	        }
	    }
	
	 protected ArrayList<Level.Sequences> loadLevel(Resources resources, int content) {
		 ArrayList<Level.Sequences> mSequence = new ArrayList<Level.Sequences>();
		 
		 // TODO: copiar restante do tutorial
		 
		 return mSequence;
	 }
	 
	/*******************************************************************************************
     *******************************************************************************************/

    public class Sequences {
        private int sequenceType = 0;
        private int sequenceStart = 0;
        private int sequenceEnd = 0;
        private int sequenceSeed = 0;
        private int sequenceAcceleration = 0;

        public Sequences(int type) {
            sequenceType = type;
        }

        public Sequences(int start, int end, int seed, int acceleration) {
            sequenceStart = start;
            sequenceEnd = end;
            sequenceSeed = seed;
            sequenceAcceleration = acceleration;
        }

        public int getSequenceType() {
            return sequenceType;
        }

        public int getSequenceStart() {
            return sequenceStart;
        }

        public int getSequenceEnd() {
            return sequenceEnd;
        }

        public int getSequenceSeed() {
            return sequenceSeed;
        }

        public int getSequenceAcceleration() {
            return sequenceAcceleration;
        }

    }
}
