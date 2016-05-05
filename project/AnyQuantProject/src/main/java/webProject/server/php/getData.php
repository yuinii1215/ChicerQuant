<?php
/**
 * Created by PhpStorm.
 * User: G
 * Date: 16/4/27
 * Time: 上午12:06
 */

header("Content-Type: text/html;charset=utf8");
include('db_login.php');
$connection = mysqli_connect($db_host, $db_username, $db_password,$db_database);
if($connection->connect_error){
    die("Could not connect to the database: <br/>".$connection->connect_error);
}



function getNameByID($id){
    global $connection;
    $query = "select name, id from names where id = ".$id;
    mysqli_query($connection, "SET NAMES utf8");
    $result = mysqli_query($connection,$query);
    if ($result === false){
        echo "query error <br/>".$connection->error;
    }
    while ($result_row = mysqli_fetch_array($result,MYSQLI_ASSOC)){
        return $result_row["name"];
    }
}


$name = getNameByID(2);
echo $name;
function getNameByAge($age){
    global $connection;
    $query = "select name, age from snois where age = ".$age;
    $result = mysqli_query($connection,$query);
    if ($result === false){
        echo "query error <br/>".$connection->error;
    }
    $names = array();
    while ($result_row = mysqli_fetch_array($result,MYSQLI_ASSOC)){
        array_push($names, $result_row);
    }
    return $names;
}


mysqli_close($connection);
?>