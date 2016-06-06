import json
import urllib2
import sys
import scipy.stats as stats

import  numpy 
from numpy import log

IP_ADDRESS='http://115.159.97.98/php/serviceController.php';

def getData(id,start,end):
    postData={'name':id,'startdate':start,'enddate':end,'method':'getStockAmongDateService'};
    req=urllib2.Request(IP_ADDRESS);
    req.add_header('Content-type', 'application/json');
    data=json.dumps(postData);
    response=urllib2.urlopen(req,data);
    return json.loads(response.read());

def parseDataForER(data):
    array={};
    i=0;
    for k,v in data.items():
        if k!= 'retmsg':
            array[i]=float(v['adj_price']);
            i+=1;
    ans={};
    for num in range(i-1):
        te=(array[num+1]/array[num])-0.01;
        ans[num]=log(te);
    return ans;

