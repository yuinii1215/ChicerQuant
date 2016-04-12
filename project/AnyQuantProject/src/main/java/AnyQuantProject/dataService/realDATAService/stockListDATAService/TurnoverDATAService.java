package AnyQuantProject.dataService.realDATAService.stockListDATAService;

/**
 * Created by G on 16/4/4.
 */
public interface TurnoverDATAService {

    /**
     * 由股票的代号得到股票当天成交金额
     *
     * @param name
     * @return
     */
    public double getTurnoverValue(String name);

    /**
     * 由股票的代号得到股票总股本(最新)
     * @param name
     * @return
     */
    public double getTotalShares(String name);

    /**
     * 由股票的代号公司无限售流通股份合计(最新)
     * @param name
     * @return
     */
    public double getNonrestFloatShares(String name);
}
