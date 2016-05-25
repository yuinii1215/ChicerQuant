<?php
/**
 * Created by PhpStorm.
 * User: G
 * Date: 16/5/21
 * Time: 上午12:37
 */

 require_once ('favorStocks.php');
  header("Access-Control-Allow-Origin: *");
  header('Access-Control-Allow-Headers: X-Requested-With');

   /**
       *
       * 模块一:  股票关注
       *
       */
      //输入: 用户名
      //输出: 操作是否成功的信息以及所有喜爱的股票名
      function getMyFavorService($username){
          $jsonstring = getMyFavor($username);
          echo $jsonstring;
      }
//      getMyFavorService("cx");
      //输入: 股票名
      //输出: 操作是否成功的信息
      function cancelMyFavorService($name,$username){
          $jsonstring = cancelMyFavor($name, $username);
          echo $jsonstring;
      }

      //输入: 股票名
      //输出: 操作是否成功的信息
      function addMyFavorService($name,$username){
          $jsonstring = addMyFavor($name, $username);
          echo $jsonstring;
      }
//        addMyFavorService("cx","sh600000");
 ?>