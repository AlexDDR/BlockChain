package blockChain;

import java.util.ArrayList;

import com.lowagie.text.List;

import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactory;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.graph.*;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.RandomCartesianAdder;
import repast.simphony.space.graph.Network;
import repast.simphony.util.ContextUtils;

public class BlockChainBuilder implements ContextBuilder<Object> {

	@Override
	public Context<Object> build(Context<Object> context) 
	{
		context.setId("blockChain");
		
		final int iNodeCount = 2000; 
		
		final int iStartingMiners = 0;
		final int iRandom = 500;	
		
		final int iStep = 50;
		final int iDelta = 100;
		
		final int iBlockSize = 1;
		final int iTxDenom = 5; //Kai blokas 1 tai jame telpa 5 tx, kai 2 tai 10, kai 3 tai 15 ir pan
		
		final int iSize = 5600; //duomenu strukturos (tx ar bloko) dydis. Pagal tai bus praleidziami zingsniai 700 = 500KB
				
		int iTxId = 2;
		
		ArrayList<Integer> iRandomNumbers = new ArrayList<Integer>() ;
		ArrayList<Integer> iRandomMinerNumbers = new ArrayList<Integer>() ;
		 
				
		iRandomNumbers.add(iRandom); //Trukdziams
		
		
		for(int i = 0; i < iStartingMiners; i++)
		{
			int iRandomMiner = RandomHelper.nextIntFromTo(0,iNodeCount-1);
			
			while(iRandomNumbers.contains(iRandomMiner)||iRandomMinerNumbers.contains(iRandomMiner)) //Kad neparinktu dvieju vienodu Node'u ir Mineriu
				iRandomMiner = RandomHelper.nextIntFromTo(0,iNodeCount-1);
			
			iRandomMinerNumbers.add(iRandomMiner);						
		}
						
		int iTrkValue = 1;
		//for (int i = 0; i < iNodeCount; i++) 
		for (int i = 0; i <= iRandom; i++) //generuojam pirma dali su trukdziais
		{
			abstractAgent agent;
			
			//int iTempStep = iStep;
			//int iTempDelta = iDelta;
			
			
			if(iRandomMinerNumbers.contains(i))
			{
				agent = new Miner("Miner-" + i,iBlockSize, iTxDenom, iNodeCount, iStartingMiners, iSize);
				
			}			
			else
			{
				agent = new Node("Agent-" + i,iNodeCount, iStartingMiners, iSize);//, space);
				
				//if(i%iStep == 0) //Padidiname zingsniu
				if(i%iStep == 0)
					iTrkValue = iDelta;
				
				agent.setiTinkloTrukdis(iTrkValue);
				
				if(iRandomNumbers.contains(i))
				{
					agent.setBTx(true);
					agent.setTxMaker(true);
					((Node)agent).setTxId(iTxId);
					iTxId++;
				}
			}
						
			context.add(agent);	
		}
		
		iTrkValue = 1;
		for (int i = 1+iRandom; i < iNodeCount; i++) //Generuojam kita dali su trukdziais
		{
			abstractAgent agent;
				
		
			agent = new Node("Agent-" + i,iNodeCount, iStartingMiners, iSize);//, space);
			
			if(i%iStep == 0)
				iTrkValue += iDelta;
			
			agent.setiTinkloTrukdis(iTrkValue);
							
			context.add(agent);	
		}
							
		NetworkGenerator gen = new WattsBetaSmallWorldGenerator(0, 4, false);
		NetworkBuilder builder = new NetworkBuilder("BlockChain network", context, true);
		builder.setGenerator(gen);
		builder.buildNetwork();
				
		return context;
	}
	
}
