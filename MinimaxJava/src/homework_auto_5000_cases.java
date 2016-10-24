import java.io.*;
import java.util.*;

class Node_State1
{
    char[][] board_state;
    String move_type;
    String move_pos;
    int value;
    int alpha;
    int beta;
    
    Node_State1(char board_state1[][],String move_type1, String move_pos1,int value1)
    {
        board_state = board_state1;
        move_type = move_type1;
        move_pos = move_pos1;
        value = value1;
    }
    
        Node_State1(char board_state1[][],String move_type1, String move_pos1,int value1,int alpha1,int beta1)
    {
        board_state = board_state1;
        move_type = move_type1;
        move_pos = move_pos1;
        value = value1;
        alpha=alpha1;
        beta=beta1;
    }
    
}

public class homework_auto_5000_cases{
	
	public static Scanner sc;
	public static int board_size;
	public static String algo;
	public static char my_symbol;
	public static int depth;
	public static int board_cell_values[][];
	public static char start_board[][];
	public static int depth_to_go;
	public static char opp_symbol;
	public static int new_board_size;
        public static ArrayList<Node_State1> opened;
        public static Node_State1 Solution_node;
        public static int qw=0;
	
	public static void readfile()
	{
		opened=new ArrayList<Node_State1>();
                board_size = Integer.parseInt(sc.next());
		algo = sc.next().toUpperCase();
		my_symbol = (sc.next().toUpperCase()).charAt(0);
		depth = Integer.parseInt(sc.next());
		System.out.println(board_size);
		System.out.println(algo);
		System.out.println(my_symbol);
		System.out.println(depth);
		new_board_size=board_size+2;
		board_cell_values = new int[new_board_size][new_board_size];
		start_board = new char[new_board_size][new_board_size];
		for(int i=1;i<(board_size+1);i++)
		{	for(int j=1;j<(board_size+1);j++)
				board_cell_values[i][j]= Integer.parseInt(sc.next());
		}
		for(int i=1;i<(board_size+1);i++)
		{	
			String line =(sc.next());
			for(int j=1;j<(board_size+1);j++)
				start_board[i][j]= line.charAt(j-1);
		}
		
		int dot_count = 0;
		for (int i=1; i<(board_size+1);i++)
		{
			for (int j=1; j<(board_size+1);j++)
				if(start_board[i][j] == '.')
					dot_count=dot_count+1;
		}
		depth_to_go=Math.min(depth, dot_count);
		
		if(my_symbol=='X')
		    opp_symbol = 'O';
		else
		    opp_symbol = 'X';
		
		//print start board and cell values
		for(int i=0;i<(new_board_size);i++)
		{
			System.out.println(Arrays.toString(board_cell_values[i]));
		}
		for(int i=0;i<(new_board_size);i++)
		{
			System.out.println(Arrays.toString(start_board[i]));
		}
	}
	
	public static char[][] board_copy(char board[][])
	{
		char temp_board[][]=new char[new_board_size][new_board_size];
		for(int i=0;i<new_board_size;i++)
			for(int j=0;j<new_board_size;j++)
				temp_board[i][j]=board[i][j];
		return temp_board;
		}
	
	public static int calculate_value(char board[][])
	{
		char[][] temp_board = board_copy(board);
		int x=0;
		int y=0;
		for(int i=1;i<(board_size+1);i++)
		{
			for(int j=1;j<(board_size+1);j++){
				if(temp_board[i][j]==my_symbol){
					x=x+board_cell_values[i][j];
				}
				else if(temp_board[i][j]==opp_symbol){
					y=y+board_cell_values[i][j];
				}
			}
		}
		return x-y;
	}

        public static void printtofile(){   
            try {
                FileWriter ran = new FileWriter("testcases//testcases-5000//output//output"+qw+".txt",false);
                FileWriter rand = new FileWriter("testcases//testcases-5000//output//output_combined.txt",true);
                System.out.println("***");
                String posn = Solution_node.move_pos;
                System.out.println(posn);
                String[] posin = posn.split(" ");
                String asd = Character.toString((char)(64+Integer.parseInt((posin[1]))));
                String move= asd+posin[0]+" "+Solution_node.move_type;
                System.out.println(move);
                ran.write(move);
                rand.write("\r\n");
                rand.write("input"+qw+".in");
                rand.write("\r\n");
                ran.write("\r\n");
                rand.write(move);
                rand.write("\r\n");
                char[][] sol_board=Solution_node.board_state;
                for (int i=1;i<(board_size+1);i++){
                    for (int j=1;j<(board_size+1);j++){
                        System.out.print(sol_board[i][j]);
                        ran.write(sol_board[i][j]);
                        rand.write(sol_board[i][j]);
                    }
                    System.out.println();
                    ran.write("\r\n");
                    rand.write("\r\n");
                }
                ran.close();
                rand.close();
            } catch (IOException ex) {  
            System.out.println("File Not Found - Exception "+ex);
            
            }
        }   
        
	public static int minimax_decision(char board_state[][])
	{
	    int curr_depth = 0;
	    int v = -10000;
	    char[][] curr_board = board_copy(board_state);
	    if (curr_depth == depth_to_go){
	        int value=calculate_value(curr_board);
	        return value;
	    }   		
	    else{
	        for (int q=1;q<(board_size+1);q++)
	        {
	            for (int w=1;w<(board_size+1);w++)
	            {	
	                if(curr_board[q][w]=='.')
	                {
                            char[][] curr_board1=board_copy(curr_board);
	                    curr_board1[q][w]= my_symbol;
	                    v=min_value(curr_board1,curr_depth+1);
                            String ipos=Integer.toString(q)+' '+Integer.toString(w);
                            opened.add(new Node_State1(curr_board1,"Stake",ipos,v));
	                }
	            }
	        }
	        for (int q=1;q<(board_size+1);q++)
	        {
	            for (int w=1;w<(board_size+1);w++)
	            {	
	                int flag=0;
	                if(curr_board[q][w]=='.'){
	                    char[][] curr_board1=board_copy(curr_board);
	                    curr_board1[q][w]= my_symbol;
	                    if ((curr_board1[q+1][w]== my_symbol) || (curr_board1[q-1][w]==my_symbol) || (curr_board1[q][w+1]== my_symbol) || (curr_board1[q][w-1]==my_symbol))
	                    {
	                        if (curr_board1[q-1][w]== opp_symbol){
	                            curr_board1[q-1][w] = my_symbol;
	                            flag=1;
	                        }
	                        if (curr_board1[q][w-1]== opp_symbol){
	                            curr_board1[q][w-1] = my_symbol;
	                            flag=1;
	                        }
	                        if (curr_board1[q][w+1]== opp_symbol){
	                            curr_board1[q][w+1] = my_symbol;
	                            flag=1;
	                        }
	                        if (curr_board1[q+1][w]== opp_symbol){
	                            curr_board1[q+1][w] = my_symbol;
	                            flag=1;
	                        }
	                        if(flag==1){
	                            v=min_value(curr_board1,curr_depth+1);
	                            String ipos=Integer.toString(q)+' '+Integer.toString(w);
                                    opened.add(new Node_State1(curr_board1,"Raid",ipos,v));
	                        }
	                    }
	                }
	            }
	        }
                int max=opened.get(0).value;
                int max_pos=0;
                for(int k=0;k<opened.size();k++){
                    if(max < opened.get(k).value )
                    {
                        max=opened.get(k).value;
                        max_pos=k;
                    }
                }
                Solution_node= opened.get(max_pos);
	        //opened.sort(key=lambda x: x.value, reverse= True)
	        return v;
	}
        }
	
	public static int max_value(char board[][],int current_depth)
        {
            char[][] curr_board = board_copy(board);
            int curr_depth = current_depth;
            if (curr_depth == depth_to_go){
                int value=calculate_value(curr_board);
                return value;  
            }
            else{
                int v = -10000;
	        for (int q=1;q<(board_size+1);q++)
	        {
	            for (int w=1;w<(board_size+1);w++)
	            {	
	                if(curr_board[q][w]=='.')
	                {
                            char[][] curr_board1=board_copy(curr_board);
	                    curr_board1[q][w]= my_symbol;
	                    v=Math.max(v,min_value(curr_board1,curr_depth+1));
	                }
	            }
	        }
                for (int q=1;q<(board_size+1);q++)
	        {
	            for (int w=1;w<(board_size+1);w++)
	            {	
	                int flag=0;
	                if(curr_board[q][w]=='.'){
	                    char[][] curr_board1=board_copy(curr_board);
	                    curr_board1[q][w]= my_symbol;
	                    if ((curr_board1[q+1][w]== my_symbol) || (curr_board1[q-1][w]==my_symbol) || (curr_board1[q][w+1]== my_symbol) || (curr_board1[q][w-1]==my_symbol))
	                    {
	                        if (curr_board1[q-1][w]== opp_symbol){
	                            curr_board1[q-1][w] = my_symbol;
	                            flag=1;
	                        }
	                        if (curr_board1[q][w-1]== opp_symbol){
	                            curr_board1[q][w-1] = my_symbol;
	                            flag=1;
	                        }
	                        if (curr_board1[q][w+1]== opp_symbol){
	                            curr_board1[q][w+1] = my_symbol;
	                            flag=1;
	                        }
	                        if (curr_board1[q+1][w]== opp_symbol){
	                            curr_board1[q+1][w] = my_symbol;
	                            flag=1;
	                        }
	                        if(flag==1){
	                            v=Math.max(v,min_value(curr_board1,curr_depth+1));
	                        }
	                    }
	                }
	            }
                }
                return v;
            }
        }

	public static int min_value(char board[][],int current_depth)
        {
            char[][] curr_board = board_copy(board);
            int curr_depth = current_depth;
            if (curr_depth == depth_to_go){
                int value=calculate_value(curr_board);
                return value;  
            }
            else{
                int v = 10000;
	        for (int q=1;q<(board_size+1);q++)
	        {
	            for (int w=1;w<(board_size+1);w++)
	            {	
	                if(curr_board[q][w]=='.')
	                {
                            char[][] curr_board1=board_copy(curr_board);
	                    curr_board1[q][w]= opp_symbol;
	                    v=Math.min(v,max_value(curr_board1,curr_depth+1));
	                }
	            }
	        }
                for (int q=1;q<(board_size+1);q++)
	        {
	            for (int w=1;w<(board_size+1);w++)
	            {	
	                int flag=0;
	                if(curr_board[q][w]=='.'){
	                    char[][] curr_board1=board_copy(curr_board);
	                    curr_board1[q][w]= opp_symbol;
	                    if ((curr_board1[q+1][w]== opp_symbol) || (curr_board1[q-1][w]==opp_symbol) || (curr_board1[q][w+1]== opp_symbol) || (curr_board1[q][w-1]==opp_symbol))
	                    {
	                        if (curr_board1[q-1][w]== my_symbol){
	                            curr_board1[q-1][w] = opp_symbol;
	                            flag=1;
	                        }
	                        if (curr_board1[q][w-1]== my_symbol){
	                            curr_board1[q][w-1] = opp_symbol;
	                            flag=1;
	                        }
	                        if (curr_board1[q][w+1]== my_symbol){
	                            curr_board1[q][w+1] = opp_symbol;
	                            flag=1;
	                        }
	                        if (curr_board1[q+1][w]== my_symbol){
	                            curr_board1[q+1][w] = opp_symbol;
	                            flag=1;
	                        }
	                        if(flag==1){
	                            v=Math.min(v,max_value(curr_board1,curr_depth+1));
	                        }
	                    }
	                }
	            }
                }
                return v;
            }
            

        }
       
        public static int alphabeta_decision(char board_state[][])
	{
	    int curr_depth = 0;
	    int v = -10000;
            int alpha = -100000;
            int beta = 100000;
	    char[][] curr_board = board_copy(board_state);
	    if (curr_depth == depth_to_go){
	        int value=calculate_value(curr_board);
	        return value;
	    }   		
	    else{
	        for (int q=1;q<(board_size+1);q++)
	        {
	            for (int w=1;w<(board_size+1);w++)
	            {	
	                if(curr_board[q][w]=='.')
	                {
                            int v1;
                            char[][] curr_board1=board_copy(curr_board);
	                    curr_board1[q][w]= my_symbol;
	                    v1=min_beta_value(curr_board1,curr_depth+1,alpha,beta);
                            v=Math.max(v,v1);
                            if(v>=beta){
                                return v;
                            }
                            alpha = Math.max(alpha, v);
                            String ipos=Integer.toString(q)+' '+Integer.toString(w);
                            opened.add(new Node_State1(curr_board1,"Stake",ipos,v1,alpha,beta));
	                }
	            }
	        }
	        for (int q=1;q<(board_size+1);q++)
	        {
	            for (int w=1;w<(board_size+1);w++)
	            {	
	                int flag=0;
	                if(curr_board[q][w]=='.'){
	                    char[][] curr_board1=board_copy(curr_board);
	                    curr_board1[q][w]= my_symbol;
	                    if ((curr_board1[q+1][w]== my_symbol) || (curr_board1[q-1][w]==my_symbol) || (curr_board1[q][w+1]== my_symbol) || (curr_board1[q][w-1]==my_symbol))
	                    {
	                        if (curr_board1[q-1][w]== opp_symbol){
	                            curr_board1[q-1][w] = my_symbol;
	                            flag=1;
	                        }
	                        if (curr_board1[q][w-1]== opp_symbol){
	                            curr_board1[q][w-1] = my_symbol;
	                            flag=1;
	                        }
	                        if (curr_board1[q][w+1]== opp_symbol){
	                            curr_board1[q][w+1] = my_symbol;
	                            flag=1;
	                        }
	                        if (curr_board1[q+1][w]== opp_symbol){
	                            curr_board1[q+1][w] = my_symbol;
	                            flag=1;
	                        }
	                        if(flag==1){
	                            int v1;
                                    v1=min_beta_value(curr_board1,curr_depth+1,alpha,beta);
                                    v=Math.max(v,v1);
                                    if(v>=beta){
                                        return v;
                                    }
                                    alpha = Math.max(alpha, v);
	                            String ipos=Integer.toString(q)+' '+Integer.toString(w);
                                    opened.add(new Node_State1(curr_board1,"Raid",ipos,v1,alpha,beta));
	                        }
	                    }
	                }
	            }
	        }
                int max=opened.get(0).value;
                int max_pos=0;
                for(int k=0;k<opened.size();k++){
                    if(max < opened.get(k).value )
                    {
                        max=opened.get(k).value;
                        max_pos=k;
                    }
                }
                Solution_node= opened.get(max_pos);
	        //opened.sort(key=lambda x: x.value, reverse= True)
	        return v;
	}
        }
        
       	public static int max_alpha_value(char board[][],int current_depth, int alpha, int beta)
        {
            char[][] curr_board = board_copy(board);
            int curr_depth = current_depth;
            if (curr_depth == depth_to_go){
                int value=calculate_value(curr_board);
                return value;  
            }
            else{
                int v = -10000;
	        for (int q=1;q<(board_size+1);q++)
	        {
	            for (int w=1;w<(board_size+1);w++)
	            {	
	                if(curr_board[q][w]=='.')
	                {
                            char[][] curr_board1=board_copy(curr_board);
	                    curr_board1[q][w]= my_symbol;
                            if ((curr_board1[q+1][w]== my_symbol) || (curr_board1[q-1][w]==my_symbol) || (curr_board1[q][w+1]== my_symbol) || (curr_board1[q][w-1]==my_symbol))
	                    {
	                        if (curr_board1[q-1][w]== opp_symbol){
	                            curr_board1[q-1][w] = my_symbol;
	                        }
	                        if (curr_board1[q][w-1]== opp_symbol){
	                            curr_board1[q][w-1] = my_symbol;
	                        }
	                        if (curr_board1[q][w+1]== opp_symbol){
	                            curr_board1[q][w+1] = my_symbol;
	                        }
	                        if (curr_board1[q+1][w]== opp_symbol){
	                            curr_board1[q+1][w] = my_symbol;
	                        }
	                    }
	                    v=Math.max(v,min_beta_value(curr_board1,curr_depth+1,alpha,beta));
                            if(v>=beta){
                                return v;
                            }
                            alpha=Math.max(alpha,v);
                        }
	            }
	        }
                for (int q=1;q<(board_size+1);q++)
	        {
	            for (int w=1;w<(board_size+1);w++)
	            {	
	                int flag=0;
	                if(curr_board[q][w]=='.'){
	                    char[][] curr_board1=board_copy(curr_board);
	                    curr_board1[q][w]= my_symbol;
	                    if ((curr_board1[q+1][w]== my_symbol) || (curr_board1[q-1][w]==my_symbol) || (curr_board1[q][w+1]== my_symbol) || (curr_board1[q][w-1]==my_symbol))
                            {
                                if ((curr_board1[q+1][w]== opp_symbol) || (curr_board1[q-1][w]==opp_symbol) || (curr_board1[q][w+1]== opp_symbol) || (curr_board1[q][w-1]==opp_symbol)){
                                    flag=1;
                                }
	                        if(flag==1){
                                    v=Math.max(v,min_beta_value(curr_board1,curr_depth+1,alpha,beta));
                                    if(v>=beta){
                                        return v;
                                    }
                                alpha=Math.max(alpha,v);
	                        }
	                    }
	                }
	            }
                }
                return v;
            }
        }
 
        public static int min_beta_value(char board[][],int current_depth, int alpha, int beta)
        {
            char[][] curr_board = board_copy(board);
            int curr_depth = current_depth;
            if (curr_depth == depth_to_go){
                int value=calculate_value(curr_board);
                return value;  
            }
            else{
                int v = 10000;
	        for (int q=1;q<(board_size+1);q++)
	        {
	            for (int w=1;w<(board_size+1);w++)
	            {	
	                if(curr_board[q][w]=='.')
	                {
                            char[][] curr_board1=board_copy(curr_board);
	                    curr_board1[q][w]= opp_symbol;
                            if ((curr_board1[q+1][w]== opp_symbol) || (curr_board1[q-1][w]==opp_symbol) || (curr_board1[q][w+1]== opp_symbol) || (curr_board1[q][w-1]==opp_symbol))
	                    {
	                        if (curr_board1[q-1][w]== my_symbol){
	                            curr_board1[q-1][w] = opp_symbol;
	                        }
	                        if (curr_board1[q][w-1]== my_symbol){
	                            curr_board1[q][w-1] = opp_symbol;
	                        }
	                        if (curr_board1[q][w+1]== my_symbol){
	                            curr_board1[q][w+1] = opp_symbol;
	                        }
	                        if (curr_board1[q+1][w]== my_symbol){
	                            curr_board1[q+1][w] = opp_symbol;
	                        }
	                    }
	                    v=Math.min(v,max_alpha_value(curr_board1,curr_depth+1,alpha,beta));
                            if(v<=alpha){
                                return v;
                            }
                            beta=Math.min(beta,v);
                        }
	            }
	        }
                for (int q=1;q<(board_size+1);q++)
	        {
	            for (int w=1;w<(board_size+1);w++)
	            {	
	                int flag=0;
	                if(curr_board[q][w]=='.'){
	                    char[][] curr_board1=board_copy(curr_board);
	                    curr_board1[q][w]= opp_symbol;
	                    if ((curr_board1[q+1][w]== opp_symbol) || (curr_board1[q-1][w]==opp_symbol) || (curr_board1[q][w+1]== opp_symbol) || (curr_board1[q][w-1]==opp_symbol))
                            {
                                if ((curr_board1[q+1][w]== my_symbol) || (curr_board1[q-1][w]==my_symbol) || (curr_board1[q][w+1]== my_symbol) || (curr_board1[q][w-1]==my_symbol)){
                                    flag=1;
                                }
	                        if(flag==1){
                                    v=Math.min(v,max_alpha_value(curr_board1,curr_depth+1,alpha,beta));
                                    if(v<=alpha){
                                        return v;
                                    }
                                beta=Math.min(beta,v);
	                        }
	                    }
	                }
	            }
                }
                return v;
            }
        }
 
        
	public static void main(String args[])
	{
		for(;qw<5000;qw++)
                { 
		try {
		FileReader fr= new FileReader("testcases//testcases-5000//input//"+qw+".in"); 
		sc = new Scanner (fr); 
		readfile();
		fr.close();
		}
		catch (Exception e) {
	            System.err.println("Unable to find the file"+e);
	    }
		
		int value=calculate_value(start_board);
		System.out.println(value);
		for(int i=0;i<(board_size+2);i++)
		{
			//System.out.println(Arrays.toString(start_board[i]));
		}
                System.out.println(algo);
                if(algo.equalsIgnoreCase("MINIMAX")){
                int solution = minimax_decision(start_board);
                printtofile();
                }
                else if(algo.equalsIgnoreCase("ALPHABETA")){
                int solution = alphabeta_decision(start_board);
                printtofile();
                }
                else 
                System.out.println("Incorrect Algo");
        }}
}	