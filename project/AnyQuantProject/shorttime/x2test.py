from GetData import *



def x2Test():
    t=sys.argv;
    er=parseDataForER(getData(t[1], t[2], t[3]));
    array=er.values();
    
    res=stats.shapiro(array);
    print(res[1]);
    return 0;
        
x2Test();
