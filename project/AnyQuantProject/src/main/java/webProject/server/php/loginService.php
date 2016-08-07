<?php
/**
 * Created by PhpStorm.
 * User: G
 * Date: 16/5/21
 * Time: 上午10:41
 */



 require_once ('userLogin.php');
  header("Access-Control-Allow-Origin: *");
  header('Access-Control-Allow-Headers: X-Requested-With');

 /**
     *
     * 模块一:  登录
     *
     */
     /**
      *
      * 注册一名用户
      * @param $username
      * @param $password
      * @return string 注册结果
      */
     function signUpService($username,$password){
        $jsonstring = insertUser($username,$password);
        echo $jsonstring;
     }

    /**
     *
     * 删除一名用户
     * @param $username
     * @return string 删除结果
     */
     function removeUserService($username){
        $jsonstring = deleteUser($username);
        echo $jsonstring;
     }
     /**
      * 修改用户密码
      * @param $username
      * @param $newPassword
      * @return string
      */
     function modifyPasswordService($username,$newPassword){
        $jsonstring = modifyPassword($username,$newPassword);
        echo $jsonstring;

     }


     /**
      * 密码验证
      * @param $username
      * @param $password
      * @return string
      */
     function verifyPasswordService($username,$password){
        $jsonstring = verifyPassword($username,$password);
        echo $jsonstring;
     }



 ?>