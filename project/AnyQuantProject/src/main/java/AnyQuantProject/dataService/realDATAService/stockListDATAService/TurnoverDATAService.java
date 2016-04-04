package AnyQuantProject.dataService.realDATAService.stockListDATAService;

import AnyQuantProject.dataStructure.Stock;

import java.io.IOException;

/**
 * Created by G on 16/4/4.
 */
public interface TurnoverDATAService {

    /**
     * 由股票的代号得到股票成交量和成交金额
     *
     * @param name
     * @return
     */
    public Stock getTurnOverVolume(String name);
}
