from GetData import *;

def whiteNoise():
    t=sys.argv;
    er=parseDataForER(getData(t[1],t[2],t[3]));
    # data = parseDataForER(getData('sh600315', '2015-06-01', '2016-06-01'));
    array=numpy.array(er.values());
    res=stsmdsd.acorr_ljungbox(array);
    print (res[1]);
    return 0;

whiteNoise();