package fn;

import java.io.File;

import cfg.Cfg;

public class FNCfg
{
	public static String dataPath=Cfg.dataPath;
	
	public static String rawPath=dataPath+"raw"+File.separator;
	public static String tempPath=dataPath+"temp"+File.separator;
	public static String itemsPath=dataPath+"items"+File.separator;
	public static String in1Path=dataPath+"insight1"+File.separator;
	
	public static String rawDataFileName=rawPath+"data.json";
	
	public static String yesVoteFileName=itemsPath+"votes-chamber_lower-yes_vote";
	public static String dateFileName=itemsPath+"votes-chamber_lower-date";
	public static String noVoteFileName=itemsPath+"votes-chamber_lower-no_vote";
	public static String passedFileName=itemsPath+"votes-chamber_lower-passed";
	public static String otherVoteFileName=itemsPath+"votes-chamber_lower-other_vote";
	public static String documentFileName=itemsPath+"document";
	public static String committeeUpperFileName=itemsPath+"committee-committee_upper";
	public static String committeeLowerFileName=itemsPath+"committee-committee_lower";
	public static String descriptionFileName=itemsPath+"description";
	public static String billTypeFileName=itemsPath+"bill_type";
	public static String sponsorFileName=itemsPath+"sponsors";
	public static String vetoedFileName=itemsPath+"actions-vetoed";
	public static String resolutionPassedFileName=itemsPath+"actions-resolution_passed";
	public static String passedLowerFileName=itemsPath+"actions-passed_lower";
	public static String failedFileName=itemsPath+"actions-failed";
	public static String passedUpperFileName=itemsPath+"actions-passed_upper";
	public static String introducedFileName=itemsPath+"actions-introduced";
	public static String enactedFileName=itemsPath+"actions-enacted";
	public static String chamberFileName=itemsPath+"chamber";
	public static String stateFileName=itemsPath+"state";
	public static String sessionFileName=itemsPath+"session";
	public static String idFileName=itemsPath+"id";
	
	public static String in1CorpusFileName=in1Path+"document";
	public static String in1LabelFileName=in1Path+"label";
	public static String in1VocabFileName=in1Path+"vocab";
}
