package blockChain;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.engine.watcher.Watch;
import repast.simphony.engine.watcher.WatcherTriggerSchedule;
import repast.simphony.space.graph.Network;
import repast.simphony.util.ContextUtils;

public class Terminator 
{
		
	public Terminator()
	{
		
	}		
	
	public void run()
	{
		Context < Object > context = ContextUtils . getContext (this);
		Network < Object > net = ( Network < Object >) context.getProjection ("BlockChain network");
		
		boolean bTerminate = true; 
		
		for(Object obj:net.getNodes()) //Tikrinam ar dar yra tusciu, jei nera - stabdom simuliacija
		{				
			if(((Node)obj).getTxPool()<1)  
			{
				bTerminate = false;
				break;
			}
		}
		
		//if(bTerminate)
			RunEnvironment.getInstance().endRun();
	}
}
