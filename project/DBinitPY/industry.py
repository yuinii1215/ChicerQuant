# -*- coding: UTF-8 -*-
from industData import getMap, getCompany_info
import datetime
import pandas as pd
import numpy as np
from AnyQuant import getSingleStock
from pdCal import updown

def apply_lambda(df):
    return df.sort_values(by='updown')[-1:]


def transTable(id):
    idr=str(id).zfill(6)
    if id>=600000:
        idr='sh'+idr
    else:
        idr='sz'+idr
    return idr

__company_info__=getCompany_info('../datasrc/')


def oneIndustry(industry,i_c):
    set=pd.DataFrame()
    for id in i_c.index:
        te=__company_info__.query('stock_id=='+str(id))
        set=set.append(te)
    pure=set['outstanding'].sum()*10000
    pure=int(pure)
    total=int((set['outstanding']*set['bvps']).sum()*10000)
    set['percent']=(set['outstanding']*set['bvps']*10000)/total
    count=len(set.index)
    #get src
    starttime=datetime.datetime(2016,1,1)
    endtime=datetime.datetime.now()
    use=[]
    count=0
    for id in i_c.index:
        te=getSingleStock(transTable(id),starttime,endtime)
        updown(te)
        te.reset_index(inplace=True)
        te['stock_id']=id
        resu=pd.merge(left=te[['stock_id','open','high','close','low','volume','updown','date']],right=set[['stock_id','stock_name','percent']],on='stock_id')
        resu['open']=resu['open']*resu['percent']
        resu['realclose']=resu['close']
        resu['close']=resu['close']*resu['percent']
        resu['high']=resu['high']*resu['percent']
        resu['low']=resu['low']*resu['percent']
        resu['volume']=resu['volume']*resu['percent']
        use.append(resu)
        count+=1
        if count>1:
            break
    data=use[0]
    for i in range(1,len(use)):
        data=data.append(use[i])
    data.fillna(0,inplace=True)
    df1=data[['open','high','low','close','volume']].groupby(data['date']).sum()
    df1.reset_index(level=0, inplace=True)
#     print df1
    df2=data[['stock_name','updown','stock_id','date','realclose']].groupby(data['date'],as_index=False).apply(apply_lambda)
    df=pd.merge(left=df1,right=df2,on='date')
    print df
    return
    
    


def init():
    all=getMap(dir='../datasrc/')
    ans=all.c_name.unique()
    oneIndustry(ans[0], all[all['c_name']==ans[0]])
#     for v in ans:
#         print v,'start'
#         oneIndustry(v,all[all['c_name']==v])
    return


init()
