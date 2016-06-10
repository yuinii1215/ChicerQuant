from GetData import *



def x2Test():
    t=sys.argv;
    # data = parseDataForER(getData('sh600000', '2016-05-20', '2016-06-01'));
    er=parseDataForER(getData(t[1],t[2],t[3]));
    # x = stats.norm.rvs(size=100)
    array=numpy.array(er.values());
    res=stsmdsd.kstest_normal(array);
    print(res[1]);

    return 0;
        
x2Test();
