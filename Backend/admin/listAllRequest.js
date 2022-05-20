const express = require('express');
const User = require('../model/user');
const bodyParser = require('body-parser');
const postRequest = require('../model/request');

const listR = express.Router();
listR.use(bodyParser.json());

listR.post('/',async (req,res)=>{
    console.log('Trying to fetch requests');
    const email = req.body.email;
    const user = await User.findOne({email}).lean();
    console.log("Got user "+user.email);
    if(user.type!=0){
        return res.json({"status":"PERM_DENIED","error":"Permission denied for "+user.email});
    }
    const posts = await postRequest.find();
    if(!posts){
        return res.json({"status":"NA"});
    }

    res.json({status:"ok","post":posts});
});

module.exports = listR;