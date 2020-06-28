// ==UserScript==
// @name         Anigame Claimer
// @namespace    http://tampermonkey.net/
// @version      0.1
// @description  Auto Claim and detect verification in Anigame
// @author       Somoy
// @match        *discord.com/channels/*
// @grant        none
// ==/UserScript==

//Global variables
var claimURL = "https://drive.google.com/uc?export=download&id=13kw9_YTU_7HEgYjGKdO3PVl_ZFY7p1Q-";
var verifyURL = "https://drive.google.com/uc?export=download&id=1yoI-nQ9dZJuzx2y6GbCmRojo2fjdRXgg";
var botFlag, interval = null;
//Create a button to Initialize Bot Code
function buttonCreate(){
    botFlag = false;
    var b = document.createElement('button');
    b.innerHTML = 'Start Bot';
    b.className = 'b1';
    b.id = 'b1';
    b.style = 'position: absolute; width: 10%; height: 10%; opacity: 0.9; z-index: 1;';
    var body = document.getElementsByTagName('body')[0];
    body.appendChild(b);
    var par,child;
    b.addEventListener("click",function(){
        if(!botFlag)alert('Bot Started');
        if(!botFlag){
            b.innerHTML = 'Stop Bot';
            botFlag = true;
            child = document.getElementById('b1');
            par = child.parentNode;
            par.removeChild(child);
            body.appendChild(b);
            searchLoop();
        }else{
            window.open(verifyURL,"_blank");
            b.innerHTML = 'Start Bot';
            botFlag = false;
            child = document.getElementById('b1');
            par = child.parentNode;
            par.removeChild(child);
            body.appendChild(b);
            stopSearchLoop();
        }
    });
}
buttonCreate();

function searchLoop(){
    interval = setInterval(function(){
        //console.log('gees');
        verifySearch();
        claimSearch2();
    },1);
}
function stopSearchLoop(){
    clearInterval(interval);
}
//Search for claim text
function claimSearch(){
    var content = document.body.textContent || document.body.innerText;
    var hasText = content.indexOf("A wild anime card appears!") !== -1;
    if(hasText){
        createClaimFile().then(()=>{
            setTimeout(function(){},2000);
            document.getElementById('b1').click();
        });
        //delay 3 seconds
        setTimeout(function(){},6000);
    }else{
        //delay 3 seconds;
        setTimeout(function(){},3000);
    }
}
//Search for claim text Better Ver.
function claimSearch2(){
    var flag = false;
    for(var i=44;i<=50; ++i){
        var idd = "messages-"+i;
        var elem = document.getElementById(idd);
        var child;
        if(elem !=null){
            child = elem.getElementsByTagName('em')[0];
        }
        //console.log(child+"\n");
        if(child != undefined && child != null){
            if(child.innerText=="A wild anime card appears!"){
                flag = true;
                break;
            }

        }
    }
    if(flag){
        createClaimFile();
        //delay 6 seconds
        setTimeout(function(){},6000);
    }else{
        setTimeout(function(){},3000);
    }
}

//Captcha Alert!
function verifySearch(){
    var content = document.body.textContent || document.body.innerText;
    var hasText = content.indexOf("Captcha Verification") !== -1;
    if(hasText){
        createVerifyFile().then(()=>{
            setTimeout(function(){},2000);
            document.getElementById('b1').click();
        });
    }else{
        setTimeout(function(){},3000);
    }
}

async function createClaimFile(){
    await window.location.replace(claimURL);
}
async function createVerifyFile(){
    await window.location.replace(verifyURL);
}

