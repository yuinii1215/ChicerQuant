<?php
/**
 * Created by PhpStorm.
 * User: G
 * Date: 16/5/18
 * Time: 下午9:05
 */

//favorstocks(username,stock_id)

require_once('db_login.php');
header("Content-Type: text/json;charset=utf8");


function getMyFavor($username){
    $connection = getDBConnection();
    $stmt = $connection->prepare("select * from favorstocks where username = :username");
    $stmt->bindParam(":username",$_username);
    $_username = $username;
    return execQuery($connection,$stmt);
}

//echo getMyFavor("aha");
function cancelMyFavor($stock_id, $username){
    $connection = getDBConnection();
    $stmt = $connection->prepare("delete from favorstocks where username = :username and stock_id = :stock_id");
    $stmt->bindParam(":username",$_username);
    $stmt->bindParam(":stock_id",$_stock_id);
    $_username = $username;
    $_stock_id = $stock_id;
    return execOperation($connection,$stmt);
}
//echo cancelMyFavor("39d633dc9c","cx");
function addMyFavor($stock_id,$username){
    $connection = getDBConnection();
    $stmt = $connection->prepare("insert into favorstocks values( :username , :stock_id)");
    $stmt->bindParam(":username",$_username);
    $stmt->bindParam(":stock_id",$_stock_id);
    $_username = $username;
    $_stock_id = $stock_id;
    return execOperation($connection,$stmt);
}

function getFavorNumByStock($name){
    $connection = getDBConnection();
    $stmt = $connection->prepare("select count(*) as favornum from favorstocks where stock_id = :stock_id");
    $stmt->bindParam(":stock_id",$_stock_id);
    $_stock_id = $name;
    return execQuery($connection,$stmt);
}

//echo addMyFavor("sh600000","aha");
?>