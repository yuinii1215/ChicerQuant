#! /usr/bin/env python
# -*- coding: utf-8 -*-
import requests
from time import sleep
classId=[72355,72360,72348]
url='http://jwas2.nju.edu.cn:8080/jiaowu/student/elective/courseList.do'
header={
        'Host': 'jwas2.nju.edu.cn:8080',
        'User-Agent': 'Mozilla/5.0 (X11; Fedora; Linux x86_64; rv:48.0) Gecko/20100101 Firefox/48.0',
        'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
        'Accept-Language': 'en-US,en;q=0.5',
        'Accept-Encoding': 'gzip, deflate',
        'Referer': 'http://jwas2.nju.edu.cn:8080/jiaowu/student/elective/courseList.do?method=discussRenewCourseList&campus=%E9%BC%93%E6%A5%BC%E6%A0%A1%E5%8C%BA',
        'Cookie': 'JSESSIONID=050CA99AFE27381E6401A346E80586CA; user_id="141250018 1472543118640"',
        'Connection': 'keep-alive',
        'Upgrade-Insecure-Requests': '1'
        };
def submitclass():
    for id in classId:
        r=requests.get(url=url,params={'method':'submitDiscussRenew','classId':str(id),'campus':'鼓楼校区'},headers=header);
        print r.url
if True:
    t=0
    while (True):
        t=t+1
        print t,'times try'
        submitclass()
        print 'here'
        sleep(1)        
         
         
         
         
         
         
         
         
         
         
         
         
         