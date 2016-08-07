<?php
/**
 * Created by PhpStorm.
 * User: G
 * Date: 16/6/2
 * Time: 上午8:46
 */
 require_once('db_login.php');
 header("Content-Type: text/json;charset=utf8");

 function saveCrossStrategy($username,$strategyname,$crossstr,$share)
 {
     $connection = getDBConnection();
     $dup = checkDuplicate($username,$strategyname,'crossstrategy');
     if($dup){
        $arr = ['retmsg'=>'success','duplicate'=>true];
        return json_encode($arr,JSON_UNESCAPED_UNICODE);
     }
     $stmt = $connection->prepare("insert into crossstrategy values( :username , :strategyname, :crossstr,:share)");
     $stmt->bindParam(":username",$_username);
     $stmt->bindParam(":strategyname",$_strategyname);
     $stmt->bindParam(":crossstr",$_crossstr);
     $stmt->bindParam(":share",$_share);
     $_username = $username;
     $_strategyname = $strategyname;
     $_crossstr = $crossstr;
     $_share = $share;
     $jsonstring = execOperation($connection,$stmt);
     $arr = json_decode($jsonstring,true);
     $arr['duplicate'] = false;
     return json_encode($arr,JSON_UNESCAPED_UNICODE);
 }

// echo saveCustomStrategy('cx','cus18','RSI','10','20');
//echo delCustomStrategy('cx','cus2');
// echo getCustomStrategy('cx');
 function getCrossStrategy($username)
 {
    $connection = getDBConnection();
    $stmt = $connection->prepare("select * from crossstrategy where username = :username");
    $stmt->bindParam(":username",$_username);
    $_username = $username;
    return execQuery($connection,$stmt);
 }

 function delCrossStrategy($username,$strategyname)
 {
    return delStrategy($username,$strategyname,'crossstrategy');
 }

 function delCustomStrategy($username,$strategyname)
 {
    return delStrategy($username,$strategyname,'customstrategy');
 }
 function delStrategy($username,$strategyname,$tablename)
 {
    $connection = getDBConnection();
    $stmt = $connection->prepare("delete from  ".$tablename."  WHERE username = :username and strategyname = :strategyname");
    $stmt->bindParam(':username', $_username);
    $stmt->bindParam(":strategyname",$_strategyname);
    $_username = $username;
    $_strategyname = $strategyname;
    return execOperation($connection,$stmt);
 }
 function saveCustomStrategy($username,$strategyname,$type,$buypoint,$sellpoint,$share)
 {
         $connection = getDBConnection();
         $dup = checkDuplicate($username,$strategyname,'customstrategy');
         if($dup){
            $arr = ['retmsg'=>'success','duplicate'=>true];
            return json_encode($arr,JSON_UNESCAPED_UNICODE);
         }
         $stmt = $connection->prepare("insert into customstrategy values( :username , :strategyname, :type,:buypoint,:sellpoint,:share)");
         $stmt->bindParam(":username",$_username);
         $stmt->bindParam(":strategyname",$_strategyname);
         $stmt->bindParam(":type",$_type);
         $stmt->bindParam(":buypoint",$_buypoint);
         $stmt->bindParam(":sellpoint",$_sellpoint);
         $stmt->bindParam(":share",$_share);
         $_username = $username;
         $_strategyname = $strategyname;
         $_type = $type;
         $_buypoint = $buypoint;
         $_sellpoint = $sellpoint;
         $_share = $share;
         $jsonstring = execOperation($connection,$stmt);
         $arr = json_decode($jsonstring,true);
         $arr['duplicate'] = false;
         return json_encode($arr,JSON_UNESCAPED_UNICODE);
 }

 function getCustomStrategy($username)
 {
        $connection = getDBConnection();
        $stmt = $connection->prepare("select * from customstrategy where username = :username");
        $stmt->bindParam(":username",$_username);
        $_username = $username;
        return execQuery($connection,$stmt);
 }


 function getShareStrategy()
 {
    $connection = getDBConnection();
    $stmt = $connection->prepare("select * from crossstrategy where share = 1");
    $jsonstr = execQuery($connection,$stmt);
    $stmt1 = $connection->prepare("select * from customstrategy where share = 1");
    $jsonstr1 = execQuery($connection,$stmt1);
    $arr = json_decode($jsonstr,true);
    $arr1 = json_decode($jsonstr1,true);
    $arr = array_merge($arr,$arr1);
    return json_encode($arr,JSON_UNESCAPED_UNICODE);
 }

 function checkDuplicate($username,$strategyname,$tablename)
 {
    $connection = getDBConnection();
    $stmt = $connection->prepare("select count(*) from ".$tablename." where username = :username and strategyname= :strategyname");
    $stmt->bindParam(":username",$_username);
    $stmt->bindParam(":strategyname",$_strategyname);
    $_username = $username;
    $_strategyname = $strategyname;
    $jsonstring = execQuery($connection,$stmt);
    $arr = json_decode($jsonstring,true);
    if ($arr[0]['count(*)'] == 1){
        return true;
    }else{
        return false;
    }
 }



?>