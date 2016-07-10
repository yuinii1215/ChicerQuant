# -*- coding: UTF-8 -*-
from industData import getMap, getCompany_info
import datetime
import pandas as pd
import numpy as np
from AnyQuant import getSingleStock



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
#     for id in i_c.index:
    te=getSingleStock(transTable(600051),starttime,endtime)
    te['stock_id']=600051
    resu=pd.merge(left=te,right=set[['stock_id',]],on='stock_id')
    print resu
        
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
