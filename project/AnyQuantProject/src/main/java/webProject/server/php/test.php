<?php
/**
 * Created by PhpStorm.
 * User: G
 * Date: 2016/10/10
 * Time: 下午9:34
 */
     $cmd = "rqalpha run -f examples/28.py -o 28.pkl -s 2016-07-01 -e 2016-09-30 --no-plot";
     system($cmd);
     $cmd2 = "python pickle2json.py 28.pkl";
     system($cmd2,$ret);
     $ret = substr($ret,0,-1);
     $rankObj = json_decode($ret,true);
//     echo count($rankObj);


?>