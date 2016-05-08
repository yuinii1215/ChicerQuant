<?php
/**
 * Created by PhpStorm.
 * User: G
 * Date: 16/5/7
 * Time: 下午8:08
 */
include_once ('ajaxService.php');
$data = file_get_contents("php://input");

$objData = json_decode($data);
$method=$objData->method;

switch ($method)
{
    case "getMyFavorService":
        getMyFavorService($objData->username);
        break;
    case "cancelMyFavorService":
        cancelMyFavorService($objData->username,$objData->name);
        break;
    case "addMyFavorService":
        addMyFavorService($objData->username,$objData->name);
        break;
    case "getStockByNameService":
        getStockByNameService($objData->name,$objData->date);
        break;
    case "getStockAmongDateService":
        getStockAmongDateService($objData->name,$objData->startdate,$objData->enddate);
        break;
    case "getAllStocksService":
        getAllStocksService();
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
    default :
        echo "no such method!";
}

?>