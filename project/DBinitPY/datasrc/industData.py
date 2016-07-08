import tushare as ts
import pandas as pd


def getMap(dir=''):
    df=pd.read_csv(dir+'industry.csv')
    return df


