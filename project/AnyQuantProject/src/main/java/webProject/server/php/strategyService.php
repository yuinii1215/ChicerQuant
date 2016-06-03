<?php
/**
 * Created by PhpStorm.
 * User: G
 * Date: 16/6/2
 * Time: 上午8:48
 */
require_once('strategy_user.php');
header("Access-Control-Allow-Origin: *");
header('Access-Control-Allow-Headers: X-Requested-With');
function saveCrossStrategyService($username,$strategyname,$crossstr)
{
     $jsonstring = saveCrossStrategy($username,$strategyname,$crossstr);
     echo $jsonstring;

}

function getCrossStrategyService($username)
{
     $jsonstring = getCrossStrategy($username);
     echo $jsonstring;
}

function saveCustomStrategyService($username,$strategyname,$type,$buypoint,$sellpoint)
{
    $jsonstring = saveCustomStrategy($username,$strategyname,$type,$buypoint,$sellpoint);
    echo $jsonstring;
}

function getCustomStrategyService($username)
{
    $jsonstring = getCustomStrategy($username);
    echo $jsonstring;
}


function delCrossStrategyService($username,$strategyname)
{
    $jsonstring = delCrossStrategy($username,$strategyname);
    echo $jsonstring;
}

function delCustomStrategyService($username,$strategyname)
{
    $jsonstring = delCustomStrategy($username,$strategyname);
    echo $jsonstring;
}

//saveCrossStrategyService("cx",'straname','str');
//getCrossStrategyService('cx');
//delCrossStrategy('cx','straname');
//getCrossStrategyService('cx');
 ?>