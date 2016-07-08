# -*- coding: UTF-8 -*-
from datasrc.industData import getMap
from sql import createIndustry

def oneIndustry(industry):
    createIndustry(industry)
    
    


def init():
    all=getMap(dir='../datasrc/')
    ans=all.c_name.unique()
    for v in ans:
        print v,'start'
        oneIndustry(v)
    return

