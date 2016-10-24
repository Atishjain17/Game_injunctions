### Input Code
import copy
import time
for w in range(100):
        print("***************************"+str(w))
        input_file_name=str(w)+'.in'
        ip='testcases\input\\'+input_file_name
        output_file_name='testcases\output\output'+str(w)+'.txt'
        f = open(output_file_name, "w")
        f.write(input_file_name+'\n')
        f.close()
        r = open('testcases\output\output_combined.txt','a')
        r.write('\n'+input_file_name+'\n')
        r.close()

        starttime= time.time()
        global opened
        opened=[]

        input_file = open(ip,'r')
        input = input_file.read()
        input_file.close()
        input = input.split('\n')

        #Assigining values
        # Board Size, Algo, My Symbol,Depth
        board_size = int(input[0])
        algo = input[1].upper()
        my_symbol = input[2].upper()
        depth = int(input[3])

        # Assigining board cell values
        board_cell_values_temp = []
        board_cell_values_line_no = board_size+4

        board_cell_values_temp.append('0'+' 0'*(board_size+1))
        for i in range(4, board_cell_values_line_no):
            board_cell_values_temp.append('0 '+input[i]+' 0')
        board_cell_values_temp.append('0'+' 0'*(board_size+1))

        board_cell_values = []
        for i in range(len(board_cell_values_temp)):
            board_cell_values.append(board_cell_values_temp[i].split(' '))

        for i in range(len(board_cell_values)):
            for j in range(len(board_cell_values[i])):
             board_cell_values[i][j]=int(board_cell_values[i][j])

        # Finished assigning board cell values

        # Assigining start board state
        start_board_temp =[]
        start_board_temp.append(' '*(board_size+2))
        for i in range(board_cell_values_line_no, board_cell_values_line_no+board_size):
            start_board_temp.append(' '+input[i].upper()+' ')
        start_board_temp.append(' '*(board_size+2))
        start_board = []
        for i in range(len(start_board_temp)):
            start_board.append(list(start_board_temp[i]))
        '''
        #for i in range(len(board_cell_values)):
        #    print(board_cell_values[i])
        #for i in range(len(start_board)):
        #    print(start_board[i])
        '''
        # Finished Assigning start board state
        # Input taken

        dot_count = 0
        for i in range(len(start_board)):
            for j in range(len(start_board[i])):
                if(start_board[i][j] == '.'):
                    dot_count=dot_count+1

        if(my_symbol=='X'):
            opp_symbol = 'O'
        else:
            opp_symbol = 'X'

        depth_to_go= min(depth,dot_count)
        ##

        ### Create a Node_Table Class
        class Node_State(object):
            """A class that makes a queue type table of nodes"""
            def __init__(self, board_state, move_type, move_pos,value):
                self.board_state = board_state
                self.move_type = move_type
                self.move_pos = move_pos
                self.value = value

            def description(self):
                return "My State is %s, move type is %s, mov position is %s. My cost is %d" % (self.board_state, self.move_type, self.move_pos, self.value)
        ### Node_Table Class Created

        class Alpha_Node_State(object):
            """A class that makes a queue type table of nodes"""
            def __init__(self, board_state, move_type, move_pos,value,alpha,beta):
                self.board_state = board_state
                self.move_type = move_type
                self.move_pos = move_pos
                self.value = value
                self.alpha = alpha
                self.beta = beta

            def description(self):
                return "My State is %s, move type is %s, mov position is %s. My cost is %d. Alpha is %d ,Beta is %d" % (self.board_state, self.move_type, self.move_pos, self.value,self.alpha,self.beta)
        ### Node_Table Class Created

        #Calculate value Function
        def calculate_value(board):
            temp_board= board
            x=0
            y=0
            for i in range(len(temp_board)):
                for j in range(len(temp_board[i])):
                    if temp_board[i][j] == my_symbol:
                        x=x + board_cell_values[i][j]
                    elif temp_board[i][j] == opp_symbol:
                        y=y + board_cell_values[i][j]
            value = x-y
            return value
        ##

        def minimax_decision(board_state):
            curr_depth = 0
            v=-10000
            curr_board=copy.deepcopy((board_state))
            if curr_depth == depth_to_go:
                value=calculate_value(curr_board)
                return value
            else:
                for q in range(1,(len(curr_board)-1)):
                    for w in range(1,(len(curr_board[q])-1)):
                        if(curr_board[q][w]=='.'):
                            curr_board1=copy.deepcopy(curr_board)
                            curr_board1[q][w]= my_symbol
                            v=min_value(curr_board1,curr_depth+1)
                            opened.append(Node_State(curr_board1,'Stake',str(q)+' '+str(w),v))
                for q in range(1,(len(curr_board)-1)):
                    for w in range(1,(len(curr_board[q])-1)):
                        flag=0
                        if(curr_board[q][w]=='.'):
                            curr_board1=copy.deepcopy(curr_board)
                            curr_board1[q][w]= my_symbol
                            if ((curr_board1[q+1][w]== my_symbol) or (curr_board1[q-1][w]==my_symbol) or (curr_board1[q][w+1]== my_symbol) or (curr_board1[q][w-1]==my_symbol)):
                                if curr_board1[q-1][w]== opp_symbol:
                                    curr_board1[q-1][w] = my_symbol
                                    flag=1
                                if curr_board1[q][w-1]== opp_symbol:
                                    curr_board1[q][w-1] = my_symbol
                                    flag=1
                                if curr_board1[q][w+1]== opp_symbol:
                                    curr_board1[q][w+1] = my_symbol
                                    flag=1
                                if curr_board1[q+1][w]== opp_symbol:
                                    curr_board1[q+1][w] = my_symbol
                                    flag=1
                                if(flag==1):
                                    v=min_value(curr_board1,curr_depth+1)
                                    opened.append(Node_State(curr_board1,'Raid',str(q)+' '+str(w),v))

                opened.sort(key=lambda x: x.value, reverse= True)
                return v

        def max_value(board,current_depth):
            #print('in maxima')
            curr_board=copy.deepcopy((board))
            curr_depth=copy.deepcopy(current_depth)
            if curr_depth == depth_to_go:
                value=calculate_value(curr_board)
                return value
            else:
                v = -10000
                for q in range(1,(len(curr_board)-1)):
                    for w in range(1,(len(curr_board[q])-1)):
                        if(curr_board[q][w]=='.'):
                            curr_board1=copy.deepcopy(curr_board)
                            curr_board1[q][w]= my_symbol
                            v=max(v,min_value(curr_board1,curr_depth+1))
                for q in range(1,(len(curr_board)-1)):
                    for w in range(1,(len(curr_board[q])-1)):
                        flag=0
                        if(curr_board[q][w]=='.'):
                            curr_board1=copy.deepcopy(curr_board)
                            curr_board1[q][w]= my_symbol
                            if ((curr_board1[q+1][w]== my_symbol) or (curr_board1[q-1][w]==my_symbol) or (curr_board1[q][w+1]== my_symbol) or (curr_board1[q][w-1]==my_symbol)):
                                if curr_board1[q-1][w]== opp_symbol:
                                    flag=1
                                    curr_board1[q-1][w] = my_symbol
                                if curr_board1[q][w-1]== opp_symbol:
                                    flag=1
                                    curr_board1[q][w-1] = my_symbol
                                if curr_board1[q][w+1]== opp_symbol:
                                    flag=1
                                    curr_board1[q][w+1] = my_symbol
                                if curr_board1[q+1][w]== opp_symbol:
                                    flag=1
                                    curr_board1[q+1][w] = my_symbol
                                if(flag==1):
                                    v=max(v,min_value(curr_board1,curr_depth+1))
                #print(curr_board)
                #print(v)
                #print('****')
                return v

        def min_value(board,current_depth):
            curr_board=copy.deepcopy((board))
            curr_depth=copy.deepcopy(current_depth)
            if curr_depth == depth_to_go:
                value=calculate_value(curr_board)
                return value
            else:
                v = 10000
                for q in range(1,(len(curr_board)-1)):
                    for w in range(1,(len(curr_board[q])-1)):
                        if(curr_board[q][w]=='.'):
                            curr_board1=copy.deepcopy(curr_board)
                            curr_board1[q][w]= opp_symbol
                            v=min(v,max_value(curr_board1,curr_depth+1))
                for q in range(1,(len(curr_board)-1)):
                    for w in range(1,(len(curr_board[q])-1)):
                        flag=0
                        if(curr_board[q][w]=='.'):
                            curr_board1=copy.deepcopy(curr_board)
                            curr_board1[q][w]= opp_symbol
                            if ((curr_board1[q+1][w]== opp_symbol) or (curr_board1[q-1][w]==opp_symbol) or (curr_board1[q][w+1]== opp_symbol) or (curr_board1[q][w-1]==opp_symbol)):
                                if curr_board1[q-1][w]== my_symbol:
                                    flag=1
                                    curr_board1[q-1][w] = opp_symbol
                                if curr_board1[q][w-1]== my_symbol:
                                    flag=1
                                    curr_board1[q][w-1] = opp_symbol
                                if curr_board1[q][w+1]== my_symbol:
                                    flag=1
                                    curr_board1[q][w+1] = opp_symbol
                                if curr_board1[q+1][w]== my_symbol:
                                    flag=1
                                    curr_board1[q+1][w] = opp_symbol
                                if(flag == 1):
                                    v=min(v,max_value(curr_board1,curr_depth+1))
                #print('Return best state')
                #print(curr_board)
                #print(v)
                #print('****')
                return v


        def alphabeta_decision(board_state):
            curr_depth = 0
            alpha= -10000
            beta = +10000
            v= -10000
            curr_board=copy.deepcopy((board_state))
            if curr_depth == depth_to_go:
                value=calculate_value(curr_board)
                return value
            else:
                for q in range(1,(len(curr_board)-1)):
                    for w in range(1,(len(curr_board[q])-1)):
                        if(curr_board[q][w]=='.'):
                            curr_board1=copy.deepcopy(curr_board)
                            curr_board1[q][w]= my_symbol
                            v1=min_beta_value(curr_board1,curr_depth+1,alpha,beta)
                            v=max(v,v1)
                            if(v>=beta):
                                return v
                            alpha= max(alpha,v)
                            opened.append(Alpha_Node_State(curr_board1,'Stake',str(q)+' '+str(w),v1,alpha,beta))
                for q in range(1,(len(curr_board)-1)):
                    for w in range(1,(len(curr_board[q])-1)):
                        flag=0
                        if(curr_board[q][w]=='.'):
                            curr_board1=copy.deepcopy(curr_board)
                            curr_board1[q][w]= my_symbol
                            if ((curr_board1[q+1][w]== my_symbol) or (curr_board1[q-1][w]==my_symbol) or (curr_board1[q][w+1]== my_symbol) or (curr_board1[q][w-1]==my_symbol)):
                                if curr_board1[q-1][w]== opp_symbol:
                                    curr_board1[q-1][w] = my_symbol
                                    flag=1
                                if curr_board1[q][w-1]== opp_symbol:
                                    curr_board1[q][w-1] = my_symbol
                                    flag=1
                                if curr_board1[q][w+1]== opp_symbol:
                                    curr_board1[q][w+1] = my_symbol
                                    flag=1
                                if curr_board1[q+1][w]== opp_symbol:
                                    curr_board1[q+1][w] = my_symbol
                                    flag=1
                                if(flag==1):
                                    v1=min_beta_value(curr_board1,curr_depth+1,alpha,beta)
                                    v=max(v,v1)
                                    if(v>=beta):
                                        return v
                                    alpha= max(alpha,v)
                                    opened.append(Alpha_Node_State(curr_board1,'Raid',str(q)+' '+str(w),v1,alpha,beta))

                opened.sort(key=lambda x: x.value, reverse= True)
                return v

        def max_alpha_value(board,current_depth,alpha,beta):
            #print('in maxima')
            curr_board=copy.deepcopy((board))
            curr_depth=copy.deepcopy(current_depth)
            if curr_depth == depth_to_go:
                value=calculate_value(curr_board)
                return value
            else:
                v = -10000
                for q in range(1,(len(curr_board)-1)):
                    for w in range(1,(len(curr_board[q])-1)):
                        if(curr_board[q][w]=='.'):
                            curr_board1=copy.deepcopy(curr_board)
                            curr_board1[q][w]= my_symbol
                            if ((curr_board1[q+1][w]== my_symbol) or (curr_board1[q-1][w]==my_symbol) or (curr_board1[q][w+1]== my_symbol) or (curr_board1[q][w-1]==my_symbol)):
                                if curr_board1[q-1][w]== opp_symbol:
                                    curr_board1[q-1][w] = my_symbol
                                if curr_board1[q][w-1]== opp_symbol:
                                    curr_board1[q][w-1] = my_symbol
                                if curr_board1[q][w+1]== opp_symbol:
                                    curr_board1[q][w+1] = my_symbol
                                if curr_board1[q+1][w]== opp_symbol:
                                    curr_board1[q+1][w] = my_symbol
                            v=max(v,min_beta_value(curr_board1,curr_depth+1,alpha,beta))
                            if(v>=beta):
                                #print('beta')
                                return v
                            alpha= max(alpha,v)
                return v
        '''
                            for q in range(1,(len(curr_board)-1)):
                    for w in range(1,(len(curr_board[q])-1)):
                        flag=0
                        if(curr_board[q][w]=='.'):
                            curr_board1=copy.deepcopy(curr_board)
                            curr_board1[q][w]= my_symbol
                            if ((curr_board1[q+1][w]== my_symbol) or (curr_board1[q-1][w]==my_symbol) or (curr_board1[q][w+1]== my_symbol) or (curr_board1[q][w-1]==my_symbol)):
                                if curr_board1[q-1][w]== opp_symbol:
                                    flag=1
                                    curr_board1[q-1][w] = my_symbol
                                if curr_board1[q][w-1]== opp_symbol:
                                    flag=1
                                    curr_board1[q][w-1] = my_symbol
                                if curr_board1[q][w+1]== opp_symbol:
                                    flag=1
                                    curr_board1[q][w+1] = my_symbol
                                if curr_board1[q+1][w]== opp_symbol:
                                    flag=1
                                    curr_board1[q+1][w] = my_symbol
                                if(flag==1):
                                    v=max(v,min_beta_value(curr_board1,curr_depth+1,alpha,beta))
                                    if(v>=beta):
                                        return v
                                    alpha= max(alpha,v)
        '''
        '''
          #print(curr_board)
                #print(v)
                #print('****')
        '''

        def min_beta_value(board,current_depth,alpha,beta):
            #print('in min')
            curr_board=copy.deepcopy((board))
            curr_depth=copy.deepcopy(current_depth)
            if curr_depth == depth_to_go:
                value=calculate_value(curr_board)
                return value
            else:
                v = 10000
                for q in range(1,(len(curr_board)-1)):
                    for w in range(1,(len(curr_board[q])-1)):
                        if(curr_board[q][w]=='.'):
                            curr_board1=copy.deepcopy(curr_board)
                            curr_board1[q][w]= opp_symbol
                            if ((curr_board1[q+1][w]== opp_symbol) or (curr_board1[q-1][w]==opp_symbol) or (curr_board1[q][w+1]== opp_symbol) or (curr_board1[q][w-1]==opp_symbol)):
                                if curr_board1[q-1][w]== my_symbol:
                                    curr_board1[q-1][w] = opp_symbol
                                if curr_board1[q][w-1]== my_symbol:
                                    curr_board1[q][w-1] = opp_symbol
                                if curr_board1[q][w+1]== my_symbol:
                                    curr_board1[q][w+1] = opp_symbol
                                if curr_board1[q+1][w]== my_symbol:
                                    curr_board1[q+1][w] = opp_symbol
                            v=min(v,max_alpha_value(curr_board1,curr_depth+1,alpha,beta))
                            if(v<=alpha):
                                #print('alpha')
                                return v
                            beta= min(beta,v)
                return v
        '''
                for q in range(1,(len(curr_board)-1)):
                    for w in range(1,(len(curr_board[q])-1)):
                        flag=0
                        if(curr_board[q][w]=='.'):
                            curr_board1=copy.deepcopy(curr_board)
                            curr_board1[q][w]= opp_symbol
                            if ((curr_board1[q+1][w]== opp_symbol) or (curr_board1[q-1][w]==opp_symbol) or (curr_board1[q][w+1]== opp_symbol) or (curr_board1[q][w-1]==opp_symbol)):
                                if curr_board1[q-1][w]== my_symbol:
                                    flag=1
                                    curr_board1[q-1][w] = opp_symbol
                                if curr_board1[q][w-1]== my_symbol:
                                    flag=1
                                    curr_board1[q][w-1] = opp_symbol
                                if curr_board1[q][w+1]== my_symbol:
                                    flag=1
                                    curr_board1[q][w+1] = opp_symbol
                                if curr_board1[q+1][w]== my_symbol:
                                    flag=1
                                    curr_board1[q+1][w] = opp_symbol
                                if(flag == 1):
                                    v=min(v,max_alpha_value(curr_board1,curr_depth+1,alpha,beta))
                                    if(v<=alpha):
                                        return v
                                    beta= min(beta,v)
        '''
        ''''
                #print('Return best state')
                #print(curr_board)
                #print(v)
                #print('****')
        '''

        def printtofile():
            #print("Printing To File")
            sol=opened.pop(0)
            print("***")
            posn=(sol.move_pos).split(' ')
            print(posn)
            print(sol.description())
            move= chr(64+int(posn[1]))+posn[0]+" "+sol.move_type
            print(move)
            sol_board=sol.board_state
            for i in range(1,(len(sol_board)-1)):
                for j in range(1,(len(sol_board[i])-1)):
                    print(sol_board[i][j],end='')
                print()
            f = open(output_file_name, "a")
            r = open("testcases\output\output_combined.txt", "a")
            f.write(move+"\n")
            r.write(move+"\n")
            for i in range(1,(len(sol_board)-1)):
                for j in range(1,(len(sol_board[i])-1)):
                    f.write(sol_board[i][j])
                    r.write(sol_board[i][j])
                f.write("\n")
                r.write("\n")
            f.close()
            r.close()

        ### Printtofile defined

        if(algo=='MINIMAX'):
            solution = minimax_decision(start_board)
            #print("Opened")
            #for i in range(len(opened)):
            #    print(opened[i].description())
            printtofile()
        elif(algo=='ALPHABETA'):
            solution = alphabeta_decision(start_board)
            #print("Opened")
            #for i in range(len(opened)):
            #    print(opened[i].description())
            printtofile()

        endtime=time.time()
        print(endtime-starttime)