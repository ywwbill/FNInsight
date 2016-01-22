package fn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

import fn.bill.Bill;
import com.google.gson.Gson;

public class FNBillExtractor
{
	public ArrayList<Bill> bills;
	public Gson gson;
	
	public void readBills(String billFileName) throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader(billFileName));
		String line;
		while ((line=br.readLine())!=null)
		{
			bills.add(gson.fromJson(line, Bill.class));
		}
		br.close();
	}
	
	public void writeYesVote(String yesVoteFileName) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(yesVoteFileName));
		for (Bill bill : bills)
		{
			if (bill.votes!=null && bill.votes.chamber_lower!=null &&
					bill.votes.chamber_lower.yes_vote!=null && bill.votes.chamber_lower.yes_vote.length>0)
			{
				bw.write(bill.votes.chamber_lower.yes_vote[0]);
				for (int i=1; i<bill.votes.chamber_lower.yes_vote.length; i++)
				{
					bw.write("#"+bill.votes.chamber_lower.yes_vote[i]);
				}
			}
			bw.newLine();
		}
		bw.close();
	}
	
	public void writeDate(String dateFileName) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(dateFileName));
		for (Bill bill : bills)
		{
			if (bill.votes!=null && bill.votes.chamber_lower!=null)
			{
				bw.write(bill.votes.chamber_lower.date);
			}
			bw.newLine();
		}
		bw.close();
	}
	
	public void writeNoVote(String noVoteFileName) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(noVoteFileName));
		for (Bill bill : bills)
		{
			if (bill.votes!=null && bill.votes.chamber_lower!=null &&
					bill.votes.chamber_lower.no_vote!=null && bill.votes.chamber_lower.no_vote.length>0)
			{
				bw.write(bill.votes.chamber_lower.no_vote[0]);
				for (int i=1; i<bill.votes.chamber_lower.no_vote.length; i++)
				{
					bw.write("#"+bill.votes.chamber_lower.no_vote[i]);
				}
			}
			bw.newLine();
		}
		bw.close();
	}
	
	public void writePassed(String passedFileName) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(passedFileName));
		for (Bill bill : bills)
		{
			if (bill.votes!=null && bill.votes.chamber_lower!=null)
			{
				bw.write(bill.votes.chamber_lower.passed+"");
			}
			bw.newLine();
		}
		bw.close();
	}
	
	public void writeOtherVote(String otherVoteFileName) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(otherVoteFileName));
		for (Bill bill : bills)
		{
			if (bill.votes!=null && bill.votes.chamber_lower!=null &&
					bill.votes.chamber_lower.other_vote!=null && bill.votes.chamber_lower.other_vote.length>0)
			{
				bw.write(bill.votes.chamber_lower.other_vote[0]);
				for (int i=1; i<bill.votes.chamber_lower.other_vote.length; i++)
				{
					bw.write("#"+bill.votes.chamber_lower.other_vote[i]);
				}
			}
			bw.newLine();
		}
		bw.close();
	}
	
	public void writeDocument(String documentFileName) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(documentFileName));
		for (Bill bill : bills)
		{
			if (bill.documents!=null)
			{
				bw.write(bill.documents.id);
			}
			bw.newLine();
		}
		bw.close();
	}
	
	public void writeCommitteeUpper(String committeeUpperFileName) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(committeeUpperFileName));
		for (Bill bill : bills)
		{
			if (bill.committees!=null && bill.committees.committee_upper!=null)
			{
				bw.write(bill.committees.committee_upper);
			}
			bw.newLine();
		}
		bw.close();
	}
	
	public void writeCommitteeLower(String committeeLowerFileName) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(committeeLowerFileName));
		for (Bill bill : bills)
		{
			if (bill.committees!=null && bill.committees.committee_lower!=null)
			{
				bw.write(bill.committees.committee_lower);
			}
			bw.newLine();
		}
		bw.close();
	}
	
	public void writeDescription(String descriptionFileName) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(descriptionFileName));
		for (Bill bill : bills)
		{
			bw.write(bill.description);
			bw.newLine();
		}
		bw.close();
	}
	
	public void writeBillType(String billTypeFileName) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(billTypeFileName));
		for (Bill bill : bills)
		{
			bw.write(bill.bill_type);
			bw.newLine();
		}
		bw.close();
	}
	
	public void writeSponsor(String sponsorFileName) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(sponsorFileName));
		for (Bill bill : bills)
		{
			if (bill.sponsors!=null && bill.sponsors.length>0)
			{
				bw.write(bill.sponsors[0].party+"$"+bill.sponsors[0].type+"$"+bill.sponsors[0].name);
				for (int i=1; i<bill.sponsors.length; i++)
				{
					bw.write("#"+bill.sponsors[i].party+"$"+bill.sponsors[i].type+"$"+bill.sponsors[i].name);
				}
			}
			bw.newLine();
		}
		bw.close();
	}
	
	public void writeVetoed(String vetoedFileName) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(vetoedFileName));
		for (Bill bill : bills)
		{
			if (bill.actions!=null && bill.actions.vetoed!=null)
			{
				bw.write(bill.actions.vetoed);
			}
			bw.newLine();
		}
		bw.close();
	}
	
	public void writeResolutionPassed(String resolutionPassedFileName) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(resolutionPassedFileName));
		for (Bill bill : bills)
		{
			if (bill.actions!=null && bill.actions.resolution_passed!=null)
			{
				bw.write(bill.actions.resolution_passed);
			}
			bw.newLine();
		}
		bw.close();
	}
	
	public void writePassedLower(String passedLowerFileName) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(passedLowerFileName));
		for (Bill bill : bills)
		{
			if (bill.actions!=null && bill.actions.passed_lower!=null)
			{
				bw.write(bill.actions.passed_lower);
			}
			bw.newLine();
		}
		bw.close();
	}
	
	public void writeFailed(String failedFileName) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(failedFileName));
		for (Bill bill : bills)
		{
			if (bill.actions!=null && bill.actions.failed!=null)
			{
				bw.write(bill.actions.failed);
			}
			bw.newLine();
		}
		bw.close();
	}
	
	public void writePassedUpper(String passedUpperFileName) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(passedUpperFileName));
		for (Bill bill : bills)
		{
			if (bill.actions!=null && bill.actions.passed_upper!=null)
			{
				bw.write(bill.actions.passed_upper);
			}
			bw.newLine();
		}
		bw.close();
	}
	
	public void writeIntroduced(String introducedFileName) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(introducedFileName));
		for (Bill bill : bills)
		{
			if (bill.actions!=null && bill.actions.introduced!=null)
			{
				bw.write(bill.actions.introduced);
			}
			bw.newLine();
		}
		bw.close();
	}
	
	public void writeEnacted(String enactedFileName) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(enactedFileName));
		for (Bill bill : bills)
		{
			if (bill.actions!=null && bill.actions.enacted!=null)
			{
				bw.write(bill.actions.enacted);
			}
			bw.newLine();
		}
		bw.close();
	}
	
	public void writeChamber(String chamberFileName) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(chamberFileName));
		for (Bill bill : bills)
		{
			if (bill.chamber!=null)
			{
				bw.write(bill.chamber);
			}
			bw.newLine();
		}
		bw.close();
	}
	
	public void writeState(String stateFileName) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(stateFileName));
		for (Bill bill : bills)
		{
			if (bill.state!=null)
			{
				bw.write(bill.state);
			}
			bw.newLine();
		}
		bw.close();
	}
	
	public void writeSession(String sessionFileName) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(sessionFileName));
		for (Bill bill : bills)
		{
			if (bill.session!=null)
			{
				bw.write(bill.session);
			}
			bw.newLine();
		}
		bw.close();
	}
	
	public void writeID(String idFileName) throws Exception
	{
		BufferedWriter bw=new BufferedWriter(new FileWriter(idFileName));
		for (Bill bill : bills)
		{
			bw.write(bill.id+"");
			bw.newLine();
		}
		bw.close();
	}
	
	public FNBillExtractor()
	{
		bills=new ArrayList<Bill>();
		gson=new Gson();
	}
	
	public static void main(String args[]) throws Exception
	{
		FNBillExtractor extractor=new FNBillExtractor();
		extractor.readBills(FNCfg.rawDataFileName);
		extractor.writeYesVote(FNCfg.yesVoteFileName);
		extractor.writeDate(FNCfg.dateFileName);
		extractor.writeNoVote(FNCfg.noVoteFileName);
		extractor.writePassed(FNCfg.passedFileName);
		extractor.writeOtherVote(FNCfg.otherVoteFileName);
		extractor.writeDocument(FNCfg.documentFileName);
		extractor.writeCommitteeUpper(FNCfg.committeeUpperFileName);
		extractor.writeCommitteeLower(FNCfg.committeeLowerFileName);
		extractor.writeDescription(FNCfg.descriptionFileName);
		extractor.writeBillType(FNCfg.billTypeFileName);
		extractor.writeSponsor(FNCfg.sponsorFileName);
		extractor.writeVetoed(FNCfg.vetoedFileName);
		extractor.writeResolutionPassed(FNCfg.resolutionPassedFileName);
		extractor.writePassedLower(FNCfg.passedLowerFileName);
		extractor.writeFailed(FNCfg.failedFileName);
		extractor.writePassedUpper(FNCfg.passedUpperFileName);
		extractor.writeIntroduced(FNCfg.introducedFileName);
		extractor.writeEnacted(FNCfg.enactedFileName);
		extractor.writeChamber(FNCfg.chamberFileName);
		extractor.writeState(FNCfg.stateFileName);
		extractor.writeSession(FNCfg.sessionFileName);
		extractor.writeID(FNCfg.idFileName);
	}
}
