from math import *
from sys import maxsize
import numpy as np
from matplotlib import pyplot as plt

maxINT=maxsize

def distance_euclid(a,b):
    return sqrt((b[0]-a[0])**2+(b[1]-a[1])**2)
    
def distance_tchebychev(a,b):
    return max(abs(b[0]-a[0]), abs(b[1]-a[1]))   
    
def distance(a,b):
    return distance_euclid(a,b)
    # return distance_tchebychev(a,b)
    
def DTW(chaine1, chaine2):
    dtw=[]
    for i in range(len(chaine1)):
        dtw.append([])
        for j in range(len(chaine2)):
            dtw[i].append(maxINT)
    dtw[0][0] = distance(chaine1[0],chaine2[0])
    dtw[0][1] = distance(chaine1[0],chaine2[1])+dtw[0][0]
    dtw[1][0] = distance(chaine1[1],chaine2[0])+dtw[0][0]
    for i in range(1,len(chaine1)):
        for j in range(1,len(chaine2)):
            cost=distance(chaine1[i],chaine2[j])
            dtw[i][j]=cost + min(dtw[i-1][j], dtw[i][j-1], dtw[i-1][j-1])
    return dtw[len(chaine1)-1][len(chaine2)-1]   
   # return dtw    

Test1 = [[0,3],[1,3],[2,3],[3,3],[2,2],[1,1],[0,0],[1,0],[2,0],[3,0]]    #"Z" droit
Test2 = [[0,3],[1,3.5],[2,3.5],[3,3],[2,2],[1,1],[0,0],[1,0.5],[2,0.5],[3,0]] #"Z" courbé
Test3 = [[0,0],[0,1],[0,2],[0,3],[1,2],[2,1],[3,0],[3,1],[3,2],[3,3]]  #"N" droit 

#pour pouvoir tracer les graphes, renvoie des arraylists d'abscisses et d'ordonnées
def prog(chaine):
    resultX=[]
    resultY=[]
    for i in range(len(chaine)):
        resultX.append(chaine[i][0])
        resultY.append(chaine[i][1])
    chaineX=np.asarray(resultX)
    chaineY=np.asarray(resultY)
    return chaineX, chaineY

"""
    
Test1Abs = prog(Test1)[0]
Test1Ord = prog(Test1)[1]    
Test2Abs = prog(Test2)[0]
Test2Ord = prog(Test2)[1] 
Test3Abs = prog(Test3)[0]
Test3Ord = prog(Test3)[1] 


plt.axis([-1,4,-1,4])
plt.plot(Test1Abs, Test1Ord, 'r', linewidth=2) #rouge
plt.plot(Test2Abs, Test2Ord, 'b', linewidth=2) #bleu
plt.plot(Test3Abs, Test3Ord, 'g', linewidth=2) #vert

plt.show()

"""

#interpolation linéaire pour que les 2 chaînes aient des points définis aux mêmes temps
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
            newChaine.append([chaine2[a]])
        elif t2==t0 :
            newChaine.append([chaine2[a+1]])
        else :
           x=chaine2[a][0]+(chaine2[a+1][0]-chaine2[a][0])*(t0-t1)/(t2-t1)
           y=chaine2[a][1]+(chaine2[a+1][1]-chaine2[a][1])*(t0-t1)/(t2-t1)    
           newChaine.append([x,y,t0])
    return newChaine                
            
        
        