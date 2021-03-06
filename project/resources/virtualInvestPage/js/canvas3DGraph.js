// VERSION 0.5 beta
//
// This software is published under BSD licence.
//##############################################################################
//* Copyright (c) 2007, Bajcic Dragan [dragan.bajcic|at|gmail|dot|com]
//* All rights reserved.
//* Redistribution and use in source and binary forms, with or without
//* modification, are permitted provided that the following conditions are met:
//*
//*     * Redistributions of source code must retain the above copyright
//*       notice, this list of conditions and the following disclaimer.
//*     * Redistributions in binary form must reproduce the above copyright
//*       notice, this list of conditions and the following disclaimer in the
//*       documentation and/or other materials provided with the distribution.
//*     * Neither the name of the University of California, Berkeley nor the
//*       names of its contributors may be used to endorse or promote products
//*       derived from this software without specific prior written permission.
//*
//* THIS SOFTWARE IS PROVIDED BY COPYRIGHT HOLDERS ``AS IS'' AND ANY
//* EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
//* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
//* DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY
//* DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
//* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
//* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
//* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
//* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
//* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
//*
//##############################################################################

canvasGraph=function(elm){
    // initialise
    var canvas= document.getElementById(elm);
    this.ctx = canvas.getContext('2d');

    this.ctx.clearRect(0,0,400,400);
    //define some constants
    this.containerWidth=400; 	//default
    this.containerHeight=400;	//default
    this.padding=10;
    this.xMid=this.containerWidth/2;
    this.yMid=this.containerHeight/2;

    this.startX=this.xMid-60;
    this.startY=this.yMid+60;
    this.gray1="#c1c1c1";
    this.gray2="#f1f1f1	";
    this.gray3="#787878";
    this.stepX=this.xMid/10;

    // min - max range, defalut value

    this.xMin=0;
    this.xMax=1000;
    this.yMin=-500;
    this.yMax=4000;
    this.zMin=0;
    this.zMax=1000;

    this.factor=(this.stepX / 1.5);
    this.perspectiveFactor = 1.2;

    // Draw XYZ AXIS
    this.drawAxis();
    this.drawInfo();
}


canvasGraph.prototype.drawAxis=function(){

    this.ctx.fillStyle = this.gray1;
    this.ctx.strokeStyle= this.gray1;

    //draw Z-axis
    this.ctx.beginPath();
    this.ctx.moveTo((this.startX),(this.startY));
    this.ctx.lineTo(this.padding,this.containerHeight-this.padding);
    this.ctx.stroke();
    this.ctx.closePath();
    //draw Y-axis
    this.ctx.fillRect(this.startX,this.padding,1,(this.startY-this.padding));
    //draw X-axis
    this.ctx.fillRect(this.startX,this.startY,(this.startY-this.padding),1);


    this.yHeight = this.startY - (2 * this.stepX);

    sx=this.startX;
    markH=this.containerHeight/100;
    sy=this.startY;
    this.ctx.strokeStyle=this.ctx.fillStyle = this.gray3;
    xx=sx
    yy=sy;

    var perspectiveCompensation = 0;
    this.marginX=this.padding + this.startY - (10*this.stepX) - (this.padding);
    for(i=0;i<10;i++){

        sx=sx+this.stepX;
        sy=sy-(this.stepX);

        xx=xx-this.factor;
        yy=yy+this.factor;
        perspectiveCompensation = i * this.factor * this.perspectiveFactor + (this.perspectiveFactor * this.factor - i);
        this.ctx.strokeStyle=this.ctx.fillStyle = "rgba(200,200,200,0.5)";

        // Draw XY Grid lines
        this.ctx.fillRect(sx ,this.marginX,1,this.startY - this.marginX);
        this.ctx.fillRect(this.startX,sy, this.startY - this.marginX,1);

        // Draw XZ Grid lines

        this.ctx.fillRect(xx,yy, this.startY - this.marginX + perspectiveCompensation,1);

        this.ctx.beginPath();
        this.ctx.moveTo(sx,this.startY);
        this.ctx.lineTo(sx - ((10 * this.factor) - perspectiveCompensation) ,this.startY+((10)*this.factor));
        this.ctx.stroke();

        //Draw YZ Grid Lines
        this.ctx.fillRect(xx  ,yy,1, (-1 * this.yHeight - perspectiveCompensation) )

        this.ctx.beginPath();
        this.ctx.moveTo(this.startX,sy);
        this.ctx.lineTo(this.startX-(10*this.factor) ,sy + (10 * this.factor) - perspectiveCompensation);
        this.ctx.stroke();

        //Draw mark on Axis
        this.ctx.strokeStyle=this.ctx.fillStyle = this.gray3;

        this.ctx.fillRect(sx,this.startY-(markH/2),1,markH);
        this.ctx.fillRect(this.startX-(markH/2),sy,markH,1);

        this.ctx.beginPath();
        this.ctx.moveTo(xx-(markH/2),yy-(markH/2));
        this.ctx.lineTo(xx,yy);
        this.ctx.stroke();

    }
}


function drawMarkers() {
    this.ctx.textAlign = "right";
    this.ctx.fillStyle = "#000";;

    this.ctx.textAlign = 'center';
    for (var i = 0; i < 3; i++) {
        // arrval = arrVisitors[i].split(",");
        // name = arrval[0];
        // markerXPos = cMarginSpace + bMargin
        //     + (i * (bWidth + bMargin)) + (bWidth/2);
        // markerYPos = cMarginHeight + 10;
        this.ctx.fillText('a', x_3d, 0, 10);
    }
    this.ctx.save();
    // Add Y Axis title
    // context.translate(cMargin + 10, cHeight / 2);
    // context.rotate(Math.PI * -90 / 180);
    // context.fillText('Visitors in Thousands', 0, 0);
    // context.restore();
    // // Add X Axis Title
    // context.fillText('Year Wise', cMarginSpace +
    //     (cWidth / 2), cMarginHeight + 30 );
}

canvasGraph.prototype.drawBar=function(x,y,z){

    x_min=this.xMin;
    x_max=this.xMax;

    y_min=this.yMin;
    y_max=this.yMax;

    z_min=this.zMin;
    z_max=this.zMax;

    graph_step_x=(x_max-x_min)/10;
    graph_step_y=(y_max-y_min)/10;
    graph_step_z=(z_max-z_min)/10;

    var pcyz = ((y/y_max*10) * this.factor * this.perspectiveFactor - (y/y_max*(10/this.perspectiveFactor)) * this.perspectiveFactor) * (z/z_max*1) ;
    var pcx = ((x/x_max*10) * this.factor * this.perspectiveFactor  - (x/x_max*(10/this.perspectiveFactor)) * this.perspectiveFactor) * (z/z_max *1)



    y_height_scaled=(y * this.stepX/graph_step_y) + pcyz;
    x_width_scaled=(x * this.stepX/graph_step_x) + pcx;
    z_len_scaled=(z * this.factor/graph_step_z);

    x_scaled=this.startX + x_width_scaled ;
    y_scaled=this.startY - y_height_scaled;

    //x_3d and y_3d are 2D representation of any  3D XYZ Coordinates within given range (xyz max - min )
    x_3d=x_scaled-z_len_scaled;
    y_3d=y_scaled+z_len_scaled;

    //white cap on top
    this.ctx.fillStyle=this.ctx.strokeStyle = "rgba(255,255,255,1)";
    this.ctx.beginPath();
    this.ctx.moveTo(x_3d - 3,y_3d);
    this.ctx.lineTo(x_3d + 3,y_3d);
    this.ctx.lineTo(x_3d + 7,(y_3d - 3));
    this.ctx.lineTo(x_3d ,(y_3d-3));
    this.ctx.lineTo(x_3d -2 ,y_3d);
    this.ctx.closePath();
    this.ctx.fill();

    //main color
    this.ctx.fillStyle = "rgba(189,189,243,0.7)";
    this.ctx.fillRect(x_3d-3,y_3d,7,y_height_scaled);



    //shadow
    this.ctx.fillStyle = "rgba(77,77,180,0.7)";
    this.ctx.fillRect(x_3d+4,y_3d-0,1,y_height_scaled);
    this.ctx.fillRect(x_3d+5,y_3d-1,1,y_height_scaled);
    this.ctx.fillRect(x_3d+6,y_3d-2,1,y_height_scaled);
    this.ctx.fillRect(x_3d+7,y_3d-3,1,y_height_scaled);

    //black outline

    this.ctx.fillStyle = "rgba(0,0,0,0.7)";
    this.ctx.fillRect(x_3d-3,y_3d,1,y_height_scaled);
    this.ctx.fillRect(x_3d+7,y_3d-3,1,y_height_scaled);
    this.ctx.fillRect(x_3d-2,(y_3d+y_height_scaled),7,1);
    this.ctx.fillRect(x_3d-3,(y_3d-1),1,1);
    this.ctx.fillRect(x_3d-2,(y_3d-2),1,1);
    this.ctx.fillRect(x_3d-1,(y_3d-3),1,1);
    this.ctx.fillRect(x_3d+5,(y_3d-1+y_height_scaled),1,1);
    this.ctx.fillRect(x_3d+6,(y_3d-2+y_height_scaled),1,1);
    this.ctx.fillRect(x_3d+7,(y_3d-3+y_height_scaled),1,1);
    this.ctx.fillRect(x_3d,(y_3d-3),7,1);



}
canvasGraph.prototype.drawGraph=function(gData){

    for(i=0;i<gData.length;i++){
        //dbgEl.innerHTML+='x: '+gData[i].x+' y:'+gData[i].y+' z:'+gData[i].z+' <br />';
        this.drawBar(gData[i].x,gData[i].y,gData[i].z);
    }
}

canvasGraph.prototype.drawInfo=function(){

    this.infoElm=document.getElementById('gInfo');

    this.infoElm.innerHTML='<div id="y-label"><font color="white">盈利情况:4000元分割线</font></div>';
    this.infoElm.innerHTML+='<div id="x-label"><font color="white">黄金交叉</font></div>';
    this.infoElm.innerHTML+='<div id="z-label"><font color="white">死亡交叉</font></div>';



    this.infoElm.innerHTML+='<div id="t-001" class="gText"><font color="white">kdj</font></div>';
    this.infoElm.innerHTML+='<div id="t-002" class="gText"><font color="white">macd</font></div>';
    this.infoElm.innerHTML+='<div id="t-003" class="gText"><font color="white">rsi</font></div>';
    this.infoElm.innerHTML+='<div id="t-004" class="gText"><font color="white">bias</font></div>';
    this.infoElm.innerHTML+='<div id="t-005" class="gText"><font color="white">kdj</font></div>';
    this.infoElm.innerHTML+='<div id="t-006" class="gText"><font color="white">macd</font></div>';
    this.infoElm.innerHTML+='<div id="t-007" class="gText"><font color="white">rsi</font></div>';
    this.infoElm.innerHTML+='<div id="t-008" class="gText"><font color="white">bias</font></div>';

    this.infoElm=document.getElementById('t-001').style.top='270px';
    this.infoElm=document.getElementById('t-001').style.left='100px';

    this.infoElm=document.getElementById('t-002').style.top='300px';
    this.infoElm=document.getElementById('t-002').style.left='65px';

    this.infoElm=document.getElementById('t-003').style.top='320px';
    this.infoElm=document.getElementById('t-003').style.left='40px';

    this.infoElm=document.getElementById('t-004').style.top='340px';
    this.infoElm=document.getElementById('t-004').style.left='20px';

    this.infoElm=document.getElementById('t-005').style.top='240px';
    this.infoElm=document.getElementById('t-005').style.left='170px';

    this.infoElm=document.getElementById('t-006').style.top='240px';
    this.infoElm=document.getElementById('t-006').style.left='210px';

    this.infoElm=document.getElementById('t-007').style.top='240px';
    this.infoElm=document.getElementById('t-007').style.left='270px';

    this.infoElm=document.getElementById('t-008').style.top='240px';
    this.infoElm=document.getElementById('t-008').style.left='310px';
}



//edit this if you use some other range of numbers
function checkRange(param,min,max){
    if(param>=min && param <= max ){

        return true;

    }else{
        alert('Invalid value: '+param );
    }

}

//helper function
function sortNumByZ(a, b) {
    var x = a.z;
    var y = b.z;
    return ((x < y) ? -1 : ((x > y) ? 1 : 0));
}