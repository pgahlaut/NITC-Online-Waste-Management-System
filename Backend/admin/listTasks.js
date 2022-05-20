const express = require('express');
const User = require('../model/user');
const bodyParser = require('body-parser');
const taskReq = require('../model/task');

const listT = express.Router();
listT.use(bodyParser.json());

listT.post('/',async (req,res)=>{
    console.log('Trying to fetch requests');
    const email = req.body.email;
    const user = await User.findOne({email}).lean();
    console.log("Got user "+user.email);
    if(user.type==1){
        return res.json({"status":"PERM_DENIED","error":"Permission denied for "+user.email});
    }
    const posts = await taskReq.find();
    if(!posts){
        return res.json({"status":"NA"});
    }

    res.json({status:"ok","post":posts});
});

module.exports = listT;