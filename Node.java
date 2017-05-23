package blockChain;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.graph.Network;
import repast.simphony.space.grid.Grid;
import repast.simphony.util.ContextUtils;

public class Node extends abstractAgent {
		
	private boolean bAlreadyProcessed;
	
	
	private int iTxId; //Cia uzdedame kokias transakcijas generuos agentas. PVz vienas generuos 1, kitas 2, kitas 3 ir t.t.
	
	public Node(String sName, int iNode, int iMiner, int iSize)//, int iBSize)//, boolean bTxMaker)//,ContinuousSpace<Object> space)//, double dFail, double dGenerateTx) //Po kazkiek tai step generuojame dar vienà agenta
	{
		super(sName, iNode, iMiner, iSize);
		
	
		bMiner = false;
		iTxId = 1; //Pagal nutylejima visada 1
					
		//bTxChanged = false;
		bAlreadyProcessed = false;
	
	}
		
	@ScheduledMethod(start=1, interval=1)
	public void step()
	{
		//iTxPool++;
		
		Context<Object> context = ContextUtils.getContext(this);
		Network<Object> net = (Network<Object>)context.getProjection("BlockChain network");
		
		Terminate(net);
		
		
		
		if(bTxChanged&&this.getiTinkloTrukdis() == 0&&this.iSize == 0)// && !bAlreadyProcessed)
		{
			for(Object obj:net.getAdjacent(this))//getPredecessors(this))
			{				
				/*if(((abstractAgent)obj).getiTinkloTrukdis() == 0) //ar tinklo trukdziu jau nera
				{*/	
					if(((abstractAgent)obj).getTxPool()<1) //Ar dar neprideta?
					{
						((abstractAgent)obj).setTxPool(this,iTxId);
						((abstractAgent)obj).setBTx(true); //Tada per anksti pradeda veikti kiti
						//((abstractAgent)obj).setiTinkloTrukdis(0);
					}
				/*}
				else
				{
					//((abstractAgent)obj).setiTinkloTrukdis(((abstractAgent)obj).getiTinkloTrukdis()-1); //Sumazinam tinklo trukdi vienetu //((abstractAgent)obj).getiTinkloTrukdis()-1
					int o = 2;// ((abstractAgent)obj).getiTinkloTrukdis();
					o--;
					
					((abstractAgent)obj).setiTinkloTrukdis(o);
					((abstractAgent)obj).setBTx(true);
				}*/
			}
			this.bTxChanged = false;
			this.bAlreadyProcessed = true;		
		}
		else if(this.getiTinkloTrukdis() > 0)
		{
			this.setiTinkloTrukdis(this.getiTinkloTrukdis()-1);
			//this.setiTinkloTrukdis(0);
			//this.bTxChanged = true;
			//this.bAlreadyProcessed = false;	
		}
		else if(this.iSize > 0)
			this.iSize--; //Sumazinam dydi vientu, kol jis is vis isnyks ir bus galima vykdyti skaiciavimus.
		
		//this.bTxChanged = false;
		//this.bAlreadyProcessed = true;
		this.iPeers = net.getDegree(this);
		
	
	}
	
	private void Terminate(Network<Object> net)
	{
		boolean bTerminate = true;
		boolean bCount50 = true;
		int iFullNodeCount = 0; //Kiek jau yra uzpildyta Node'u tx 
		ISchedule schedule = RunEnvironment.getInstance().getCurrentSchedule();
		
		/*if(bCount50) //Apskaiciuojam 50
		{
			for(Object obj:net.getNodes()) //Tikrinam ar dar yra tusciu, jei nera - stabdom simuliacija
			{
				
				if(((abstractAgent)obj).getTxPool()>0)  
				{
					iFullNodeCount++;	
					if(iFullNodeCount == ((abstractAgent)obj).getNodeCount()*0.5)
					{							
						((abstractAgent)obj).setPercents(50, iFullNodeCount+" "+schedule.getTickCount());
						bCount50 = false; 
						break;
					}
				}
			}
			
		}*/
		
		
		for(Object obj:net.getNodes()) //Tikrinam ar dar yra tusciu, jei nera - stabdom simuliacija
		{				
			if(((abstractAgent)obj).getTxPool()<1)  
			{
				if(bTerminate)
					bTerminate = false;
				//((abstractAgent)obj).setEnd();
				
				break;
				//if(iFullNodeCount == ((abstractAgent)obj).getNodeCount()*0.50)
					//((abstractAgent)obj).setPercents(50, iFullNodeCount+" "+schedule.getTickCount());				
			}
		}
		
		if(bTerminate)
			RunEnvironment.getInstance().endRun();	
	}	
			
	public void setTxId(int value)
	{
		this.iTxId = value;
	}
		
}
