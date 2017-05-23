package blockChain;

import repast.simphony.random.RandomHelper;

public abstract class abstractAgent 
{
	protected int iTxPool;
	private boolean bTxMaker; //Identifikuojame, kad bûtent ðis agentas generuos transakcijas
	protected boolean bMiner;
	protected String sName;
	
	protected int iPeers;
	protected boolean bTxChanged;
	private int iBSize; //BlockChain size
	private int iTrukdis; //Tinklo trukdis
	private int iPerformance; //Kiek laiko tikrina
	
	protected int iSize;
	
	private int iTinkloTrukdis;
	
	private int iNodeCount; //Kiekvienas saugo kiek is viso tinkle dalyviu (modeliavimo reikalams)

	
	private String s50; //Kada uzspildo 50%
	private String s75;
	private String s90;
	private String s99;
		
	private abstractAgent oGotFrom; //Is kur buvo gauta transakcija
	
	public abstractAgent(String sName, int iNode, int iMiner, int iSize)
	{
		this.sName = sName;
		iTxPool = 0; 
		iBSize = 0;
		bTxChanged = false;
		//bEnd = false;
		
		this.iSize = iSize; 
		
		this.iNodeCount = iNode;
		//this.iMinerCount = iMiner;
		s50 = "";
		s75 = "";
		
		iTrukdis = RandomHelper.nextIntFromTo(1,5);
		iPerformance = RandomHelper.nextIntFromTo(1,5);
	}
		
	public int getTxPool()
	{
		return iTxPool;
	}
	public String getName()
	{
		if(bTxMaker)
			return sName+" ! Tx: "+iTxPool+" Peers: "+iPeers+" "+bTxChanged+" 50: "+s50+" 75: "+s75+" Trk: "+iTinkloTrukdis;
		if(bMiner)
			return sName+" M Tx: "+iTxPool+" Peers: "+iPeers+" "+bTxChanged+" 50: "+s50+" 75: "+s75+" Trk: "+iTinkloTrukdis;
					
		return sName+" Tx: "+iTxPool+" Peers: "+iPeers+" "+bTxChanged+" 50: "+s50+" 75: "+s75+" Trk: "+iTinkloTrukdis;
	}
	public void setBTx(boolean bValue)
	{
		bTxChanged = bValue;
	}
	public void setTxMaker(boolean bValue) //Sitas generuos transakcijas
	{
		this.bTxMaker = bValue;		
	}	
	public boolean isMiner()
	{
		return bMiner;
	}
	void setTxPool(abstractAgent oSender, int iValue)
	{
		iTxPool=iValue;		
		this.oGotFrom = oSender;  
		
	}
	public int getBSize()
	{
		return iBSize;		
	}
	public void incBSize()
	{
		this.iBSize++;
	}
	public int getTrukdis()
	{
		return iTrukdis;
	}
	public int getPerformance()
	{
		return iPerformance;
	}
	public int getNodeCount()
	{
		return iNodeCount;
	}
	public String getPercents(int iValue)
	{
		switch (iValue)
		{
			case 50: return s50;
			case 75: return s75;
			case 90: return s90;
			case 99: return s99;
		}
		return s50;			
	}
	public void setPercents(int iCase, String sValue)
	{
		switch (iCase)
		{
			case 50: s50 =  sValue;
			case 75: s75 =  sValue;
			case 90: s90 =  sValue;
			case 99: s99 =  sValue;
		}					
	}

	public int getiTinkloTrukdis() 
	{
		return iTinkloTrukdis;
	}

	public void setiTinkloTrukdis(int iTinkloTrukdis) 
	{
		this.iTinkloTrukdis = iTinkloTrukdis;
	}
	
}
