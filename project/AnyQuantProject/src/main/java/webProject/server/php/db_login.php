<?php
/**
 * Created by PhpStorm.
 * User: G
 * Date: 16/5/1
 * Time: 下午7:25
 */
header("Content-Type: text/json;charset=utf8");
//$db_host = '10.66.115.75';
//$db_database = 'chicer';
//$db_username = 'chicer';
//$db_password = 'chicer2016';
$db_host = '10.66.171.146';
$db_database = 'chicer';
$db_username = 'chicer';
$db_password = 'chicer2016';

$mydb_host = '10.66.171.146';
$mydb_database = 'chicer';
$mydb_username = 'chicer';
$mydb_password = 'chicer2016';



function getMyDBConnection()
{
    global $mydb_host;
    global $mydb_username;
    global $mydb_password;
    global $mydb_database;

    try{
        $connection = new PDO("mysql:host=$mydb_host;dbname=$mydb_database; charset=utf8; unix_socket=/path/to/socket", $mydb_username, $mydb_password);
        $connection->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    }catch (PDOException $e) {
        echo json_encode('Connection failed: ' . $e->getMessage());
    }
    // 设置 PDO 错误模式为异常
    $connection->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $connection->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
    return $connection;
}




function execQuery($connection, $stmt)
{
    if(!$stmt) {
        $arr = array('retmsg'=>$connection->errorInfo());
        $json_string = json_encode($arr);
        return $json_string;
    }
//    echo json_encode($stmt);
    try{
    $result = $stmt -> execute();
    }catch(PDOException $e){
     return "A database problem has occurred:".$e->getMessage();
    }
    if ($result === false){
        $arr = array('retmsg'=>$connection->errorInfo());
        $json_string = json_encode($arr);
        return $json_string;
    }
    $arr = array('retmsg'=>'success');
    $json_string = json_encode($arr);
//    $json_string = json_encode(array());

    while ($result_row = $stmt->fetch(PDO::FETCH_OBJ, PDO::FETCH_ORI_NEXT)) {
        $arr[] = $result_row;
    }
    $connection = null;
    $stmt = null;
    $json_string = json_encode($arr,JSON_UNESCAPED_UNICODE);
    return $json_string;
}


function execOperation($connection, $stmt)
{
    if(!$stmt) {
        $arr = array('retmsg'=>$connection->errorInfo());
        $json_string = json_encode($arr);
        return $json_string;
    }
    try{
    $result = $stmt -> execute();
    }catch(PDOException $e){
        return json_encode(array("operation:  "=>"failed for: ".$e->getMessage()));
    }

    return json_encode(array("operation:  "=>"success:)"));
}



function getDBConnection()
{
    global $db_host;
    global $db_username;
    global $db_password;
    global $db_database;
    try{
        $connection = new PDO("mysql:host=".$db_host.";port = 3306;dbname=".$db_database."; charset=utf8", $db_username, $db_password, array(PDO::MYSQL_ATTR_INIT_COMMAND => "SET NAMES 'utf8'"));
        $connection->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    }catch (PDOException $e) {
        echo json_encode('Connection failed: ' . $e->getMessage());
    }
    // 设置 PDO 错误模式为异常
    $connection->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $connection->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
    return $connection;
}




?>