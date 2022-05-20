const express = require('express');
const User = require('../model/user');
const bodyParser = require('body-parser');
const postRequest = require('../model/request');

const listS = express.Router();
listS.use(bodyParser.json());

listS.post('/',async (req,res)=>{
    console.log('Trying to fetch accounts');
    const email = req.body.email;
    const user = await User.findOne({email}).lean();
    console.log("Got user "+user.email);
    if(user.type!=0){
        return res.json({"status":"PERM_DENIED","error":"Permission denied for "+user.email});
    }
    const accounts = await User.find({"type":2});
    if(!accounts){
        return res.json({"status":"NA"});
    }

    res.json({status:"ok","post":accounts});
});

module.exports = listS;