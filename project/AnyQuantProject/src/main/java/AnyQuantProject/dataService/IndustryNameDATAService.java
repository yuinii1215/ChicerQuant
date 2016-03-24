package AnyQuantProject.dataService;

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
}
