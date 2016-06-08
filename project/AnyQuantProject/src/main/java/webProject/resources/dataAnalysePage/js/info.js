jQuery(window).load(function() {


})

function initMACDData(){
    document.getElementById("text-content").style.backgroundColor="#e96656";
    document.getElementById("panel-body1").innerHTML="DIF由下向上突破 DEA,MACD 值由负变正；";
    document.getElementById("panel-body2").innerHTML="DIF由上向下突破 DEA,MACD 值由正变负；";
    document.getElementById("panel-body3").innerHTML="适用于中线趋势分析，建议与KDJ线一起使用。";

}
function initRSIData(){
    document.getElementById("text-content").style.backgroundColor="#2EBA82";
    document.getElementById("panel-body1").innerHTML="1.W形或头肩底 当RSI在低位或底部形成W形或头肩底形时，属最佳买入时期。"+"</br>"
        + "2.当RSI运行到20以下时为超卖区，易产生返弹。"+"</br>"
        + "3.黄金交叉:当短天期的RSI向上穿越长天期的RSI时为买入信号。"+"</br>"
        + "4.牛背离:当股指或股价一波比一波低，而RSI却一波比一波高，叫牛背离，此时股指或股价很容易反转上涨.";
    document.getElementById("panel-body2").innerHTML= "1.形态M形、头肩顶形 当RSI在高位或顶部形成M形或头肩顶形时，属最佳卖出时机。"+"</br>"
        + "2.当RSI运行到80以上时进入超买区，股价很容易下跌。"+"</br>"
        + "3.顶背离当股指或股价创新高时，而RSI却不创新高，叫顶背离，将是最佳卖出时机。"+"</br>"
        + "4.当短天期RSI下穿长天期RSI时，叫死亡交叉，为卖出信号。";
    document.getElementById("panel-body3").innerHTML="稳健型的投资可以使用交叉买入或卖出，SI短线自下而上穿越长期参数即RSI指标长线时，是一个买点，此时介入至少应当有10％左右的获利"+"</br>"
        + "空间；"+"</br>"+"反之则是卖出信号，损失空间也将有10％左右；"+"</br>"+"两条指标线产生"
        + "交叉后如果出现两次以上的迎合交叉，价格趋势运行按照原来方向加速"
        + "的可能性为70％；"+"</br>"+"对短线而言，长线将起到阻挡或支撑作用；"+"</br>"+"指标仍然"
        + "强调形态、背离等信号；"+"</br>"+"两条线同方向运行，向上或向下的运行力度会"
        + "很强；"+"</br>"+"对于灵敏的人来说，当短线远离长线时产生勾头，我们就作好操"
        + "作的准备。";
}
function initBIASData(){
    document.getElementById("text-content").style.backgroundColor="#3191BA";
    document.getElementById("panel-body1").innerHTML="一般来说，负乖离率涨降至某一百分比时，为买入信号"+"</br>"
        + "其他情况有：" +"</br>"
        +"1、当短期BIAS曲线开始在底部向上突破长期BIAS曲线时，说明股价的弱势整理格局可能被打破，股价短期将向上运动，投资者可以考虑少量长线建仓。"+"</br>"
        + "2、当短期BIAS曲线向上突破长期BIAS曲线并迅速向上运动，同时中期BIAS曲线也向上突破长期BIAS曲线，说明股价的中长期上涨行情已经开始，投资者可以加大买入股票的力度。"+"</br>"
        + "3、当短、中、长期BIAS曲线开始摆脱前期窄幅盘整的区间并同时向上快速运动时，说明股价已经进入短线强势拉升行情，投资者应坚决持股待涨。";
    document.getElementById("panel-body2").innerHTML="一般来说，正乖离率涨至某一百分比时为卖出信号。"+"</br>"
        + "其他情况有："+"</br>"
        + "1、当短期BIAS曲线经过一段快速向上运动的过程后开始在高位向下掉头时，说明股价短期上涨过快，将开始短线调整，投资者可以短线卖出股票。"+"</br>"
        + "2、当中期BIAS曲线也开始在高位向下掉头时，说明股价的短期上涨行情可能结束，投资者应中线卖出股票。"+"</br>"
        + "3、当长期BIAS曲线也开始在高位向下掉头时，说明股价的中短期上涨行情已经结束，投资者应全部清仓离场。"
    document.getElementById("panel-body3").innerHTML="在分析和预测股价走势时，可以结合乖离率与布林线或其他数据图表进行分析。"+"</br>"+"但如果仅单一适用乖离率作为研判依据，" +
        "有时会出现偏差，尤其是在极端行情中，乖离率所给出的逆势操作幸好可能会丢失机会或作出错误决策。"+"</br>"+
                    "所以，通过乖离率洞察股市冷暖，符合常规思路：涨多了要跌，跌多了要涨";
}
function initKDJData(){
    document.getElementById("text-content").style.backgroundColor="#E7AC44";
    document.getElementById("panel-body1").innerHTML="K、D、J这三值在20以下，K线向上突破D线";
    document.getElementById("panel-body2").innerHTML="K、D、J这三值在80以上，K线向下跌破D线";
    document.getElementById("panel-body3").innerHTML="适用于中短线趋势分析，建议与MACD线一起使用。"+"</br>"+"由RSV可知，其是当前股价在最近周期内股价所处位置的体现。"+"</br>"+"当K线向上突破D线时，"
        +"说明目前趋势是向上的；"+"</br>"+"当K线向下突破D线时，说明目前趋势是向下的,"+"</br>"+"但是股票持续稳定上涨或下跌都会出现钝化现象，KDJ线持平，此时可以综合MACD线来判断";
}