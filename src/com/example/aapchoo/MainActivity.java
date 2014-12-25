package com.example.aapchoo;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	//Game Variables
	private int board_size=5;
	private int startx=-1,starty=-1,endx=-1,endy=-1;
	private int n1=-1,n2=-1,n3=-1,n4=-1;
	private int [][] state_holder;
	private int [][]visited;
    private int countZero;
    private int scoreP1;
    private int scoreP2;

	//Colors
    private int neighbourColor = Color.rgb(157,250,174);
    private int player1Color = Color.rgb(245,186,59);
    private int player2Color = Color.rgb(224,65,94);
    private int inactiveColor = Color.rgb(120,120,120);
    private int emptyColor = Color.rgb(255,255,255);
    private int canvasColor = Color.rgb(232,201,188);
  
    //Game Elements
    private GridView gv;
    private TextView tv1;
    private TextView tv2;
    private TextView result;
    
	private boolean neighbourFree(int x,int y)
    {
    	//c is the side of the square board game
    	//checks that the cell 'b' satisfies 4-neighbourhood with 'a' 
    	//System.out.print("yoo");
    	if((x>0 && state_holder[x-1][y]==0 )|| 
    	   (x<board_size-1 && state_holder[x+1][y]==0 )||
    	   (y>0 && state_holder[x][y-1]==0 )||
    	   (y<board_size-1 && state_holder[x][y+1]==0))
    		return true;
    	return false;
    }
    
	private boolean areNeighbours(int x1,int y1,int x2,int y2)
	{
		System.out.println(x1+":x1,"+y1+":y1,"+x2+":x2,"+y2+":y2");
		if((x1==x2-1 && y1==y2) || (x2==x1-1 && y1==y2) || (y1==y2-1 && x2==x1) || (y2==y1-1 && x2==x1) )
			return true;
		return false;
	}
	
	private void highlightNeighbours(int x,int y,int flag)
	{
		int color;
		if(flag==1)
			color=neighbourColor;
		else
			color=emptyColor;
		ImageView image;
		if(x>0 && state_holder[x-1][y]==0)
		{
			image=(ImageView)gv.getChildAt(board_size*(x-1)+y);
			image.setBackgroundColor(color);
			n1=board_size*(x-1)+y;
		}
		if(y>0 && state_holder[x][y-1]==0)
		{
			image=(ImageView)gv.getChildAt(board_size*(x)+y-1);
			image.setBackgroundColor(color);
			n2=board_size*(x)+y-1;
		}
		if(x<board_size-1 && state_holder[x+1][y]==0)
		{
			image=(ImageView)gv.getChildAt(board_size*(x+1)+y);
			image.setBackgroundColor(color);
			n3=board_size*(x+1)+y;
		}
		if(y<board_size-1 && state_holder[x][y+1]==0)
		{
			image=(ImageView)gv.getChildAt(board_size*(x)+y+1);
			image.setBackgroundColor(color);
			n4=board_size*(x)+y+1;
		}
	}
	
	private int dfs(int curx,int cury,int color)
	{
		if(curx<0 || curx==board_size || cury<0 || cury==board_size || visited[curx][cury]>0 || state_holder[curx][cury]!=color )
			return 0;
		visited[curx][cury]=1;
		return 1+dfs(curx+1,cury,color)+dfs(curx,cury+1,color)+dfs(curx-1,cury,color)+dfs(curx,cury-1,color);
	}
	
	private void updateScore()
	{
		//score for P1
		scoreP1=0;
		int color=1;
		visited=new int[board_size][];
		for(int i=0;i<board_size;i++)
			visited[i]=new int[board_size];
		for(int i=0;i<board_size;i++)
			for(int j=0;j<board_size;j++)
			{
				if(visited[i][j]==0 && state_holder[i][j]==color)
				{
					int tmp=dfs(i,j,color);
					scoreP1+=tmp*tmp;
				}
			}
		
		//score for P2
		color=2;
		scoreP2=0;
		for(int i=0;i<board_size;i++)
			for(int j=0;j<board_size;j++)
				visited[i][j]=0;
		
		for(int i=0;i<board_size;i++)
			for(int j=0;j<board_size;j++)
			{
				if(visited[i][j]==0 && state_holder[i][j]==color)
				{
					int tmp=dfs(i,j,color);
					scoreP2+=tmp*tmp;
				}
			}
		
	}
	private void init()
	{
        countZero=board_size*board_size;
        scoreP1=0;
        scoreP2=0;
        tv1.setText("0");
        tv2.setText("0");
        for(int i=0;i<board_size;i++)
        	for(int j=0;j<board_size;j++)
        	{
        		//ImageView im = (ImageView)gv.getChildAt(i*board_size+j);
        		//im.setBackgroundColor(emptyColor);
        		state_holder[i][j]=0;
        	}
        result.setText("");
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        
		
		super.onCreate(savedInstanceState);
        
		//Basic Initialisation(Permanent through all plays)
		setContentView(R.layout.activity_main);
        gv = (GridView)findViewById(R.id.gridview);
        tv1 = (TextView)findViewById(R.id.scorelabelp1);
        tv2 = (TextView)findViewById(R.id.scorelabelp2);
        result = (TextView)findViewById(R.id.result);
        
        state_holder=new int[board_size][];        
        for (int i=0;i<board_size;i++)
        	state_holder[i]=new int[board_size];
        
        gv.setBackgroundColor(canvasColor);
        tv1.setBackgroundColor(canvasColor);
        tv2.setBackgroundColor(canvasColor);
        findViewById(R.id.player1token).setBackgroundColor(player1Color);
        findViewById(R.id.player2token).setBackgroundColor(player2Color);
        //set the number of rows of the gridview ImageAdapter
        ImageAdapter image_adap = new ImageAdapter(this);
        image_adap.setCount(board_size*board_size);
        //setting the ImageAdapter which will supply the imageviews to the GridView
        gv.setAdapter(image_adap);
        
        //Initialisation of variables and allocation
        init();




        //setting the onClickHandler
        gv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,View v,int position,long id) {
            	
            	ImageView image = (ImageView)v;
            	int xgrid = position/board_size;
            	int ygrid = position%board_size;
            	
            	if(state_holder[xgrid][ygrid]==0 && countZero>0)
            	{
    				
            		if(startx==-1 && starty==-1 && neighbourFree(xgrid,ygrid))//first player
            		{
            			startx=xgrid;
            			starty=ygrid;
            			
            			state_holder[xgrid][ygrid]=1;
            			countZero--;

            			image.setBackgroundColor(player1Color);
            			highlightNeighbours(startx,starty,1);
            		}
            		else if(position==n1 || position==n2 || position==n3 || position==n4)//second player.
            		{
            			endx=xgrid;
            			endy=ygrid;
            			if(areNeighbours(startx,starty,endx,endy))
            			{
                			
            				state_holder[endx][endy]=2;
                			countZero--;
            				
                			highlightNeighbours(startx,starty,0);
                			n1=n2=n3=n4=-1;//make all neighbours null
            				image.setBackgroundColor(player2Color);
            				System.out.println("Second cellops: "+position);
                			//
                			startx=starty=endx=endy=-1;//2*1 tile successfully inserted.
            			}	
            		}
            		else//mark the cell inactive
            		{
            			if(neighbourFree(xgrid,ygrid))
            				//send some warning
            				;
            			else
            			{
            				state_holder[xgrid][ygrid]=-1;
            				countZero--;

            				image.setBackgroundColor(inactiveColor);

            			}
            		}
            		
                	System.out.println("Current::");
                	System.out.println(position);
                	
                	updateScore();
                	tv1.setText(""+scoreP1);
                	tv2.setText(""+scoreP2);
                	
                	if(countZero==0)
                	{
                		if(scoreP1>scoreP2)
                		{
                			result.setText("Yellow wins!, Tap to restart. ");
                		}
                		else if(scoreP1<scoreP2)
                		{
                			result.setText("Red wins!, Tap to restart. ");
                		}
                		else
                		{
                			result.setText("Its a tie! :P, Tap to restart. ");
                		}
                	}
            	}
            	
            	else if(countZero==0)
            	{
            		//end of the game!
            		init();
                    for(int i=0;i<board_size;i++)
                    	for(int j=0;j<board_size;j++)
                    	{
                    		ImageView im = (ImageView)gv.getChildAt(i*board_size+j);
                    		im.setBackgroundColor(emptyColor);
                    	}
            	}
            	//Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    
}
