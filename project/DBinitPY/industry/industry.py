# -*- coding: UTF-8 -*-
from industData import getMap, getCompany_info
import datetime
import pandas as pd
import numpy as np



def transTable(id):
    idr=str(id).zfill(6)
    if id>=600000:
        idr='sh'+idr
    else:
        idr='sz'+idr
    return idr

__company_info__=getCompany_info('../datasrc/')


def oneIndustry(industry,df):
    print df
    return
    
    


def init():
    all=getMap(dir='../datasrc/')
    ans=all.c_name.unique()
    for v in ans:
        print v,'start'
        oneIndustry(v,all[all['c_name']==v])
    return


init()
