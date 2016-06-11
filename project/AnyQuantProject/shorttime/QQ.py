from GetData import *;



def QQ(data):
    array=numpy.array(data.values());
    array.sort();
    le=len(array);

    array.sort();
    y=[0]*le;
    for num in range(0,le):
        y[num]=(num+0.5)/le;
    y=numpy.array(y);
    y=stats.norm.ppf(y);
    ans={};
    for num in range(0,le):
        ans[array[num]]=y[num];
    return ans;

def main():
    t=sys.argv;
    data=parseDataForER(getData(t[1],t[2],t[3]));
    ans=QQ(data);
    for k,v in ans.items():
        print k
        print v

if __name__ == '__main__':
    main();