import pandas as pd
import numpy as np
import sys

def rsi_lambda(x):
    le=x.size
    t=np.cumsum(np.diff(x))
    big=[]
    small=[]
    for v in t:
        if v>0 :
            big.append(v)
        elif v<0 :
            small.append(v)
    emau=np.array(big).mean()
    if np.isnan(emau):
        emau=0
    emad=abs(np.array(small).mean())
    if np.isnan(emad):
        rs=sys.float_info.max
    elif True :
        rs=emau/emad
    rsi=(1-float(1)/(rs+1))*100
    return rsi


def ema(df):
    step=[5,10,30,25,50,150,100,200,600]
    name=['PMA5_day','PMA10_day','PMA30_day','PMA5_week','PMA10_week',
          'PMA30_week','PMA5_month','PMA10_month','PMA30_month']
    for i in range(9):
        df[name[i]]=pd.ewma(df['adj_price'],  span= step[i])
    
    return 

def rsi(df):
    span=[6,12,24]
    name=['RSI6','RSI12','RSI24']
    for v in range(3):
        df[name[v]]=pd.rolling_apply(df['adj_price'], span[v], rsi_lambda)
    print df
    return


def bias(df):
    span=[6,12,24]
    name=['BIAS6','BIAS12','BIAS24']
    for v in range(3):
        ma=pd.rolling_mean(df['adj_price'],span[v])
        df[name[v]]=(df['adj_price']-ma)/ma*100
        
    print df
    return
        
        