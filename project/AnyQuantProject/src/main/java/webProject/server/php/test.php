<?php
/**
 * Created by PhpStorm.
 * User: G
 * Date: 2016/10/10
 * Time: 下午9:34
 */
 require_once('getData.php');
//     $cmd = "rqalpha run -f examples/28.py -o 28.pkl -s 2016-07-01 -e 2016-09-30 --no-plot";
//     exec($cmd);
//     $cmd2 = "python pickle2json.py 28.pkl";
//     exec($cmd2,$ret);
//     $ret = substr($ret,0,-1);
//     $rankarr = json_decode($ret,true);
//     echo count($rankarr);
// saveCode('cx','2016-01-03','2016-04-03','hello');
$str  = getCode('cx');
echo $str;


?>
