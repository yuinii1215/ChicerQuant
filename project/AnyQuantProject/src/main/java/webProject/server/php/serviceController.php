<?php
/**
 * Created by PhpStorm.
 * User: G
 * Date: 16/5/7
 * Time: 下午8:08
 */
 header("Access-Control-Allow-Origin: *");
// header("Access-Control-Allow-Method: GET");
// header("Access-Control-Max-Age: 60");

header("Content-Type: text/plain");
require_once('ajaxService.php');
require_once('favorstockService.php');
require_once('loginService.php');
require_once('strategyService.php');
$data = file_get_contents("php://input");

$objData = json_decode($data);
$method = $objData->method;
//echo json_encode($method);


switch ($method)
{
    case "signUpService":
        signUpService($objData->username,$objData->password);
        break;
    case "removeUserService":
        removeUserService($objData->username);
        break;
    case "modifyPasswordService":
        modifyPasswordService($objData->username,$objData->newPassword);
        break;
    case "verifyPasswordService":
        verifyPasswordService($objData->username,$objData->password);
        break;
    case "getMyFavorService":
        getMyFavorService($objData->username);
        break;
    case "cancelMyFavorService":
        cancelMyFavorService($objData->name,$objData->username);
        break;
    case "addMyFavorService":
        addMyFavorService($objData->name,$objData->username);
        break;
    case "getStockByNameService":
        getStockByNameService($objData->username,$objData->name,$objData->date);
        break;
    case "getStockAmongDateService":
        getStockAmongDateService($objData->name,$objData->startdate,$objData->enddate);
        break;
    case "getAllStocksService":
        getAllStocksService();
        break;
    case "getAllStockNamesService":
        getAllStockNamesService();
        break;
    case "getBenchMarkByNameService":
        getBenchMarkByNameService($objData->name, $objData->date);
        break;
    case "getBenchMarkAmongDateService":
        getBenchMarkAmongDateService($objData->name, $objData->startdate, $objData->enddate);
        break;
    case "get5PMA_dayService":
        get5PMA_dayService($objData->name, $objData->date);
        break;
    case "get5PMA_weekService":
        get5PMA_weekService($objData->name, $objData->date);
        break;
    case "get5PMA_monthService":
        get5PMA_monthService($objData->name, $objData->date);
        break;
    case "get10PMA_dayService":
        get10PMA_dayService($objData->name, $objData->date);
        break;
    case "get10PMA_weekService":
        get10PMA_weekService($objData->name, $objData->date);
        break;
    case "get10PMA_monthService":
        get10PMA_monthService($objData->name, $objData->date);
        break;
    case "get30PMA_dayService":
        get30PMA_dayService($objData->name, $objData->date);
        break;
    case "get30PMA_weekService":
        get30PMA_weekService($objData->name, $objData->date);
        break;
    case "get30PMA_monthService":
        get30PMA_monthService($objData->name, $objData->date);
        break;
    case "get6RSIService":
        get6RSIService($objData->name, $objData->date);
        break;
    case "get12RSIService":
        get12RSIService($objData->name, $objData->date);
        break;
    case "get24RSIService":
        get24RSIService($objData->name, $objData->date);
        break;
    case "get6BIASService":
        get6BIASService($objData->name, $objData->date);
        break;
    case "get12BIASService":
        get12BIASService($objData->name, $objData->date);
        break;
    case "get24BIASService":
        get24BIASService($objData->name, $objData->date);
        break;
    case "getKService":
        getKService($objData->name, $objData->date);
        break;
    case "getDService":
        getDService($objData->name, $objData->date);
        break;
    case "getJService":
        getJService($objData->name, $objData->date);
        break;
    case "getDIFService":
        getDIFService($objData->name, $objData->date);
        break;
    case "getDEAService":
        getDEAService($objData->name, $objData->date);
        break;
    case "getMACDBarService":
        getMACDBarService($objData->name, $objData->date);
        break;
    case "getpolyService":
        getpolyService($objData->name, $objData->date);
        break;
    case "getAllIndustriesService":
        getAllIndustriesService();
        break;
    case "getStocksByIndustryService":
        getStocksByIndustryService($objData->industry_name);
        break;
    case "getIndustryService":
        getIndustryService($objData->industry_name,$objData->date);
        break;
    case "getDayLineService":
        getDayLineService($objData->name,$objData->startdate,$objData->enddate);
        break;
    case "getWeekLineService":
        getWeekLineService($objData->name,$objData->startdate,$objData->enddate);
        break;
    case "getMonthLineService":
        getMonthLineService($objData->name,$objData->startdate,$objData->enddate);
        break;
    case "getPolyAmongDateService":
        getPolyAmongDateService($objData->name,$objData->startdate,$objData->enddate);
        break;
    case "getRelativeDateService":
        getRelativeDateService($objData->date,$objData->offset);
        break;
    case "getMyStrategyDataService":
        getMyStrategyDataService($objData->name,$objData->startdate,$objData->enddate);
        break;
    case "saveCrossStrategyService":
        saveCrossStrategyService($objData->username,$objData->strategyname,$objData->crossstr);
        break;
    case "getCrossStrategyService":
        getCrossStrategyService($objData->username);
        break;
    case "saveCustomStrategyService":
        saveCustomStrategyService($objData->username,$objData->strategyname,$objData->type,$objData->buypoint,$objData->sellpoint);
        break;
    case "getCustomStrategyService":
        getCustomStrategyService($objData->username);
        break;
    case "delCrossStrategyService":
        delCrossStrategyService($objData->username,$objData->strategyname);
        break;
    case "delCustomStrategyService":
        delCustomStrategyService($objData->username,$objData->strategyname);
        break;
    default :
        echo json_encode("no such method");
}

?>