# -*- coding: UTF-8 -*-
from industData import getMap
from sql import createIndustry
import datetime

def oneIndustry(industry,df):
#     createIndustry(industry)
    start=datetime.datetime(2005,1,1)
    
    pass
    
    


def init():
    all=getMap(dir='../datasrc/')
    ans=all.c_name.unique()
    for v in ans:
        print v,'start'
        oneIndustry(v,all[all['c_name']==v])
    return

init()