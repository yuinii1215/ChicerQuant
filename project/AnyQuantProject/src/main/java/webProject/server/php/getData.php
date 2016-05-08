<?php
/**
 * Created by PhpStorm.
 * User: G
 * Date: 16/5/4
 * Time: 上午12:58
 */
header("Content-Type: text/html;charset=utf8");

include('db_login.php');



function getMyFavor($username)
{
    $query = "select * from favorstocks where username = ".$username;
    return execQuery($query);
}

function cancelMyFavor($name, $username)
{
    $query = "delete from favorstocks where username = ".$username."and stock_id = ".$name;
    return execQuery($query);
}

function addMyFavor($name, $username)
{
    $query = "insert into favorstocks values (".$username.",".$name.")";
    return execQuery($query);
}

function getStockByName($name, $date)
{
    if ($date == date("Y-m-d")){
        $query = "select * from today where stock_id = ".$name;
    }else{
        $query = "select * from ".$name."where date = ".$date;
    }
    return execQuery($query);
}

function getStockAmongDate($name, $startdate, $enddate)
{
//    $datearr = getAmongDates($startdate, $enddate);
//    $string = "";
//    for ($i = 0; $i < sizeof($datearr); $i++){
//        $query = "select * from ".$name."where date = ".$datearr[$i];
//        $string .= execQuery($query);
//    }
//    return $string;

    $query = "select * from ".$name."where (date between ".$startdate." and ".$enddate.")";
    return execQuery($query);
}


function getAllStocks()
{
    $query = "select * from today";
    return execQuery($query);
}

function getBenchMarkByName($name, $date)
{
    $query = "select * from ".$name."where date = ".$date;
    return execQuery($query);
}

function getBenchMarkAmongDate($name, $startdate, $enddate)
{
    return getStockAmongDate($name,$startdate,$enddate);
}

function get5PMA_day($name, $date)
{
    return getCalcuValue("PMA5_day",$name,$date);
}

function get5PMA_week($name, $date)
{
    return getCalcuValue("PMA5_week",$name,$date);
}

function get5PMA_month($name, $date)
{
    return getCalcuValue("PMA5_month",$name,$date);
}

function get10PMA_day($name, $date)
{
    return getCalcuValue("PMA10_day",$name,$date);
}

function get10PMA_week($name, $date)
{
    return getCalcuValue("PMA10_week",$name,$date);
}

function get10PMA_month($name, $date)
{
    return getCalcuValue("PMA10_month",$name,$date);
}

function get30PMA_day($name, $date)
{
    return getCalcuValue("PMA30_day",$name,$date);
}

function get30PMA_week($name, $date)
{
    return getCalcuValue("PMA30_week",$name,$date);
}

function get30PMA_month($name, $date)
{
    return getCalcuValue("PMA30_month",$name,$date);
}

function get6RSI($name, $date)
{
    return getCalcuValue("RSI6",$name,$date);
}

function get12RSI($name, $date)
{
    return getCalcuValue("RSI12",$name,$date);
}

function get24RSI($name, $date)
{
    return getCalcuValue("RSI24",$name,$date);
}

function get6BIAS($name, $date)
{
    return getCalcuValue("BIAS6",$name,$date);
}


function get12BIAS($name, $date)
{
    return getCalcuValue("BIAS12",$name,$date);
}

function get24BIAS($name, $date)
{
    return getCalcuValue("BIAS24",$name,$date);
}

function getK($name, $date)
{
    return getCalcuValue("K",$name,$date);
}

function getD($name, $date)
{
    return getCalcuValue("D",$name,$date);
}

function getJ($name, $date)
{
    return getCalcuValue("J",$name,$date);
}

function getDIF($name, $date)
{
    return getCalcuValue("DIF",$name,$date);
}

function getDEA($name, $date)
{
    return getCalcuValue("DEA",$name,$date);
}

function getMACDBar($name, $date)
{
    return getCalcuValue("MACDBar",$name,$date);
}

function getpoly($name, $date)
{
    return getCalcuValue("poly",$name,$date);
}


function getAllIndustries()
{
    //TODO
    $query = "select industry from industry_stock";
    $json_string = execQuery($query);
    return $arr = json_decode($json_string,true);
}

$arr = getAllIndustries();
echo "--- ".$arr['industry'];


function getStocksByIndustry($industry_name)
{
    $query = "select stock_id, stock_name from industry_stock where industry = ".$industry_name;
    return execQuery($query);
}

function getIndustry($industry_name,$date)
{
    //TODO
}
function getCalcuValue($type, $name, $date)
{
    $query = "select ".$type." from ".$name."where date = ".$date;
    return execQuery($query);
}

function getAmongDates($startdate, $enddate)
{
    $arr = array();
    while ($startdate<=$enddate){
        $arr[] = date('Y-m-d',$startdate);
        $startdate = strtotime('+1 day',$startdate);
    }
    return $arr;
}


function execQuery($query)
{
    global $db_host;
    global $db_username;
    global $db_password;
    global $db_database;
    $connection = mysqli_connect($db_host, $db_username, $db_password,$db_database);
    if($connection->connect_error) {
        die("Could not connect to the database: <br/>" . $connection->connect_error);
    }
    mysqli_query($connection, "SET NAMES utf8");
    $result = mysqli_query($connection,$query);
    if ($result === false){
        $arr = array('retmsg'=>$connection->error);
        $json_string = json_encode($arr);
        return $json_string;
    }
    $arr = array('retmsg'=>'success');
    $json_string = json_encode($arr);
    while ($result_row = mysqli_fetch_array($result,MYSQLI_ASSOC)) {
        $json_string .= json_encode($result_row,JSON_UNESCAPED_UNICODE);
    }
    mysqli_close($connection);
    return $json_string;
}

//echo execQuery("select * from sh600216 where date = '2013-03-01'");
echo execQuery("show tables");

function getNameByID($name,$id){
    global $db_host;
    global $db_username;
    global $db_password;
    global $db_database;
    $connection = mysqli_connect($db_host, $db_username, $db_password,$db_database);
    if($connection->connect_error) {
        die("Could not connect to the database: <br/>" . $connection->connect_error);
    }
    $query = "select * from ".$name;
//    $query = "select * from  600216";
    mysqli_query($connection, "SET NAMES utf8");
    $result = mysqli_query($connection,$query);
    if ($result === false){
        $arr = array('retmsg'=>$connection->error);
        $json_string = json_encode($arr);
//        return "query error <br/>".$connection->error;
        return $json_string;
    }
    $arr = array('retmsg'=>'success');
    $json_string = json_encode($arr);
    while ($result_row = mysqli_fetch_array($result,MYSQLI_ASSOC)) {
        $json_string .= json_encode($result_row);
    }
    return $json_string;
}

//$result = getNameByID("sh600216","2006-02-01");
//echo $result;

//
//$name = getNameByID(2);
//echo $name;
//function getNameByAge($age){
//    global $connection;
//    $query = "select name, age from snois where age = ".$age;
//    $result = mysqli_query($connection,$query);
//    if ($result === false){
//        echo "query error <br/>".$connection->error;
//    }
//    $names = array();
//    while ($result_row = mysqli_fetch_array($result,MYSQLI_ASSOC)){
//        array_push($names, $result_row);
//    }
//    return $names;
//}

?>