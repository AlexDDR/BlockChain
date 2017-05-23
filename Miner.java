package blockChain;
import java.lang.Object;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.engine.schedule.Schedule;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.space.graph.Network;
import repast.simphony.util.ContextUtils;

public class Miner extends abstractAgent
{
	
	int iTxInBlock;
	
	public Miner(String sName, int iBlockSize, int iTxDenom, int iNode, int iMiner, int iSize)
	{
		
		super(sName, iNode, iMiner, iSize);
		bMiner = true;
				
		int iTxInBlock = iBlockSize * iTxDenom;
		
		ISchedule schedule = RunEnvironment.getInstance().getCurrentSchedule();
		ScheduleParameters params = ScheduleParameters.createRepeating(1,1) ; 
        schedule.schedule(params, this, "step"); 
	}
	public void step()
	{
		/*Context<Object> context = ContextUtils.getContext(this);
		Network<Object> net = (Network<Object>)context.getProjection("BlockChain network");
		if(mine())
		{ //Informuoti visus apie pasikeitimus
			
			int iBSizeTemp = getBSize();
			for(Object obj:net.getAdjacent(this))//getPredecessors(this))
			{				
				if(((abstractAgent)obj).getBSize()<iBSizeTemp) //Ar minerio BlockChain dydis skiriasi nuo kaimynu?
				{
					((abstractAgent)obj).incBSize();;
					//((abstractAgent)obj).setBTx(true); //Tada per anksti pradeda veikti kiti
				}
			}
		}*/
		
	}
	public boolean mine()
	{		
		if(iTxPool >= iTxInBlock) //Prisikaupe pakankamai tx pool'e
		{
			this.iTxPool -= iTxInBlock; //Sumazinam transakciju skaiciu  tomis kiek idedame i bloka.
			incBSize(); //BlockChain dydis padideja vienu
			
			return true;
		}
		return false;
	}
		
}
