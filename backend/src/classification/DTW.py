from math import *
from sys import maxsize
import numpy as np
from matplotlib import pyplot as plt

maxINT=maxsize

def distance_euclid(a,b): #distance if a,b=[x,y,z] but it doesn't matter if a,b = [x,y,t] cause it's the same time, thanks to interpolation
    return sqrt((b[0]-a[0])**2+(b[1]-a[1])**2+(b[2]-a[2])**2)
    
def distance_tchebychev(a,b): #distance if a,b=[x,y,z] but it doesn't matter if a,b = [x,y,t] cause it's the same time, thanks to interpolation
    return max(abs(b[0]-a[0]), abs(b[1]-a[1]), abs(b[2]-a[2]))   
    
def distance(a,b):
    return distance_euclid(a,b)
    # return distance_tchebychev(a,b)
    
def DTW(chaine1, chaine2):
    dtw=[]
    for i in range(len(chaine1)):
        dtw.append([])
        for j in range(len(chaine2)):
            dtw[i].append(maxINT)
    
    # to avoid index problems        
    dtw[0][0] = distance(chaine1[0],chaine2[0])
    dtw[0][1] = distance(chaine1[0],chaine2[1])+dtw[0][0]
    dtw[1][0] = distance(chaine1[1],chaine2[0])+dtw[0][0]
    for i in range(1,len(chaine1)):
        for j in range(1,len(chaine2)):
            cost=distance(chaine1[i],chaine2[j])
            dtw[i][j]=cost + min(dtw[i-1][j], dtw[i][j-1], dtw[i-1][j-1])
    return dtw[len(chaine1)-1][len(chaine2)-1]   
   # return dtw    


# here are only [x,y,t] chains, but it's really easy to add z
Test1 = [[0,3,0],[1,3,1],[2,3,2],[3,3,3],[2,2,4],[1,1,5],[0,0,6],[1,0,7],[2,0,8],[3,0,9]]    #"Z" droit
Test2 = [[0,3,0],[1,3.5,0.9],[2,3.5,2],[3,3,2.9],[2,2,4.1],[1,1,5],[0,0,6.2],[1,0.5,6.9],[2,0.5,7.9],[3,0,9]] #"Z" courbé
Test3 = [[0,0,0],[0,1,1],[0,2,2],[0,3,3],[1,2,4],[2,1,5],[3,0,6],[3,1,7],[3,2,8],[3,3,9]]  #"N" droit 

# in order to show graphs, need of arraylists
def prog(chaine):
    resultX=[]
    resultY=[]
    for i in range(len(chaine)):
        resultX.append(chaine[i][0])
        resultY.append(chaine[i][1])
    chaineX=np.asarray(resultX)
    chaineY=np.asarray(resultY)
    return chaineX, chaineY


    
Test1Abs = prog(Test1)[0]
Test1Ord = prog(Test1)[1]    
Test2Abs = prog(Test2)[0]
Test2Ord = prog(Test2)[1] 
Test3Abs = prog(Test3)[0]
Test3Ord = prog(Test3)[1] 


# linear interpolation to define the chains at the same times
def interpolation(chaine1, chaine2):
    a=0
    t1=chaine2[0][2]
    t2=chaine2[1][2]
    newChaine=[]
    for i in range(len (chaine1)):
        t0=chaine1[i][2]
        while (t1 > t0 or t2 < t0):
            a=a+1
            t1 , t2= chaine2[a][2] , chaine2[a+1][2]
        if t1==t0 :
            newChaine.append([chaine2[a][0], chaine2[a][1], t0])
        elif t2==t0 :
            newChaine.append([chaine2[a+1][0], chaine2[a+1][1], t0])
        else :
           x=chaine2[a][0]+(chaine2[a+1][0]-chaine2[a][0])*(t0-t1)/(t2-t1)
           y=chaine2[a][1]+(chaine2[a+1][1]-chaine2[a][1])*(t0-t1)/(t2-t1)    
           newChaine.append([x,y,t0])
    return newChaine   
    
Test4 =interpolation(Test1, Test2)

Test4Abs = prog(Test4)[0]
Test4Ord = prog(Test4)[1]

plt.axis([-1,4,-1,4])
plt.plot(Test1Abs, Test1Ord, 'r', linewidth=2) # red
plt.plot(Test2Abs, Test2Ord, 'b', linewidth=2) # blue
plt.plot(Test3Abs, Test3Ord, 'g', linewidth=2) # green
plt.plot(Test4Abs, Test4Ord, 'k', linewidth=1) # black

plt.show()


                 
            
        
        