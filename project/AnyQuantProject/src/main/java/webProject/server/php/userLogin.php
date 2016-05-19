<?php
/**
 * Created by PhpStorm.
 * User: G
 * Date: 16/5/18
 * Time: 下午7:59
 */
/*
 * account:(username,password)
 */



require_once('db_login.php');
header("Content-Type: text/json;charset=utf8");

$tablename = "account";

function createAccountTable()
{
    $connection = getMyDBConnection();
    global $tablename;
    $stmt = $connection->prepare("create table ".$tablename." (username varchar(150)not null,".
                                 "password varchar(255) DEFAULT = ".getDefaultPassword().",".
                                 "primary key(username)".
                                 ")ENGINE=InnoDB DEFAULT CHARSET=utf8");
    return execQuery($connection,$stmt);
}


//echo createAccountTable();
/**
 *
 * 注册一名用户
 * @param $username
 * @param $password
 * @return string 注册结果
 */
function insertUser($username,$password)
{
    $connection = getMyDBConnection();
    global $tablename;
    $stmt = $connection->prepare("insert into ".$tablename." values(:username,:password)");
    $stmt->bindParam(':username', $_username);
    $stmt->bindParam(':password', $_password);
    $_username = $username;
    $_password = $password;
    return execQuery($connection,$stmt);
}

insertUser("cx","little potato");




/**
 *
 * 删除一名用户
 * @param $username
 * @return string 删除结果
 */
function deleteUser($username)
{
    $connection = getMyDBConnection();
    global $tablename;
    $stmt = $connection->prepare("delete from  ".$tablename."  WHERE username = :username");
    $stmt->bindParam(':username', $_username);
    $_username = $username;
    return execQuery($connection,$stmt);
}

//deleteUser("cx");
/**
 * 修改用户密码
 * @param $username
 * @param $newPassword
 * @return string
 */
function modifyPassword($username,$newPassword)
{
    $connection = getMyDBConnection();
    global $tablename;
    $stmt = $connection->prepare("update ".$tablename." set password = :newpw where username = :username");
    $stmt->bindParam(':username', $_username);
    $stmt->bindParam(':newpw', $_newpw);
    $_username = $username;
    $_newpw = $newPassword;
    return execQuery($connection,$stmt);
}

//modifyPassword("cx","i am potato!");

echo "here";
$connection = getMyDBConnection();
$stmt = $connection ->prepare("select * from account");
echo execQuery($connection, $stmt);
/**
 * 密码验证
 * @param $username
 * @param $password
 * @return string
 */
function verifyPassword($username,$password)
{
    $connection = getMyDBConnection();
    global $tablename;
    $stmt = $connection->prepare("select password from ".$tablename."  where username = :username");
    $stmt->bindParam(':username', $_username);
    $_username = $username;
    $jsonstring = execQuery($connection,$stmt);
//    if(password_verify ($password , $realpw)){
//        echo "right";
//    }else{echo "false";}
    return $jsonstring;
}



/**
 * @return bool|string 得到默认的hash密码
 */
function getDefaultPassword()
{
    return password_hash("chicer", PASSWORD_DEFAULT);
}

/**
 * @param $password
 * @return bool|string 得到hash密码
 */
function getHashPassword($password)
{
    return password_hash($password, PASSWORD_DEFAULT);
}

//getDefaultPassword();
//if(password_verify ("" , getDefaultPassword())){
//    echo "right";
//}else{echo "false";}

function execQuery($connection, $stmt)
{
    echo json_encode($stmt);
    if(!$stmt) {
        $arr = array('retmsg'=>$connection->errorInfo());
        $json_string = json_encode($arr);
        return $json_string;
    }
    $result = $stmt -> execute();

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



function getMyDBConnection()
{
     global $db_host;
     global $db_username;
     global $db_password;
     global $db_database;
     try{
         $connection = new PDO("mysql:host=".$db_host.";port = 3306;dbname=".$db_database."; charset=utf8", $db_username, $db_password);
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