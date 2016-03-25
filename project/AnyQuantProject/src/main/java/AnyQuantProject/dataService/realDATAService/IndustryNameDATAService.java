package AnyQuantProject.dataService.realDATAService;

import java.util.List;

/**
 * Created by G on 16/3/24.
 */
public interface IndustryNameDATAService {

    /**
     * 由单只股票的代号名得到对应的行业
     * @param name
     * @return
     */
    public String getIndustryName(String name);
    /**
     * 获得所有股票分类行业的名称
     * @return
     */
    public List<String> getAllIndustries();
    /**
     * 获得该分类下所有的股票代号
     * @param industry 行业名称
     * @return
     */
    public List<String> getStockByIndustry(String industry);
}
