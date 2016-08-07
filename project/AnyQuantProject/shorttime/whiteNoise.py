from GetData import *;

def whiteNoise():
    t=sys.argv;
    # er=parseDataForER(getData(t[1],t[2],t[3]));
    data = parseDataForER(getData('sh600315', '2016-01-01', '2016-06-01'));
    array=numpy.array(data.values());
    res=stsmdsd.acorr_ljungbox(array,12);
    print (res[1]);
    return 0;

whiteNoise();