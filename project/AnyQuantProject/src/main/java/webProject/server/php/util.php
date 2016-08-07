<?php
/**
 * Created by PhpStorm.
 * User: G
 * Date: 16/5/19
 * Time: 下午6:00
 */

/**
 * 得到给定日期前几天或后几天的日期
 * @param $date  指定的日期
 * @param $offset +2表示两天后; -3表示3天前
 * @return bool|string
 *
 */

function getRelativeDate($date,$offset)
{
    $stringtime = strtotime($date);
    return date('Y-m-d',$stringtime+3600*24*$offset);
}

//echo getRelativeDate(date('Y-m-d'),30);

?>