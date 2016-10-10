<?php
/**
 * Created by PhpStorm.
 * User: G
 * Date: 2016/10/9
 * Time: 下午8:06
 */
require_once('db_login.php');
header("Content-Type: text/json;charset=utf8");

try{
//        $connection = new PDO("mysql:host=".$db_host.";port = 15003;dbname=".$db_database."; charset=utf8", $db_username, $db_password, array(PDO::MYSQL_ATTR_INIT_COMMAND => "SET NAMES 'utf8'"));
    $connection = new PDO("mysql:host=114.55.37.133".";port=15003;dbname=chicer"."; charset=utf8", 'chicer', 'chicer2016', array(PDO::MYSQL_ATTR_INIT_COMMAND => "SET NAMES 'utf8'"));
    $connection->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

}catch (PDOException $e) {
    echo json_encode('Connection failed: ' . $e->getMessage());
}
// 设置 PDO 错误模式为异常
$connection->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
$connection->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);

$hand = fopen('/Users/G/Desktop/ChicerQuant/project/DBinitPY/industry.csv','r');
$row = 1;
if (($handle = fopen("/Users/G/Desktop/ChicerQuant/project/DBinitPY/industry.csv", "r")) !== FALSE) {
    while (($data = fgetcsv($handle, 4000, ",")) !== FALSE) {
        $num = count($data);
        $row++;
//        for ($c=0; $c < $num; $c++) {

            echo $data[1]." ".$data[2]." ".$data[3]."   \n";
            if (substr($data[1], 0, 1) == '6'){
                echo "sh".$data[1]." \n";
                $stmt =$connection->prepare("insert into industry_stock values (:stock_id,:stock_name,:industry)");
                $stmt->bindParam(":stock_id",$_stock_id);
                $stmt->bindParam(":stock_name",$_stock_name);
                $stmt->bindParam(":industry",$_industry);
                $_stock_id = "sh".$data[1];
                $_stock_name = $data[2];
                $_industry = $data[3];
                execOperation($connection,$stmt);
            } elseif (substr($data[1], 0, 1) == '0'){
                echo "sz".$data[1]." \n";
                $stmt =$connection->prepare("insert into industry_stock values (:stock_id,:stock_name,:industry)");
                $stmt->bindParam(":stock_id",$_stock_id);
                $stmt->bindParam(":stock_name",$_stock_name);
                $stmt->bindParam(":industry",$_industry);
                $_stock_id = "sz".$data[1];
                $_stock_name = $data[2];
                $_industry = $data[3];
                execOperation($connection,$stmt);
            }


            
//        }
    }
    fclose($handle);
}




?>