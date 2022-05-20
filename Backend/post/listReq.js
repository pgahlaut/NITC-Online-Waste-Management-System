const express = require('express');
const User = require('../model/user');
const bodyParser = require('body-parser');
const postRequest = require('../model/request');

const listReq = express.Router();
listReq.use(bodyParser.json());

listReq.post('/',async (req,res)=>{
    const uuid = req.body.uuid;
    console.log('Trying to fetch requests for '+uuid);
    const posts = await postRequest.find({"uuid":uuid});
    if(!posts){
        return res.json({"status":"NA"});
    }

    res.json({status:"ok","post":posts});
});

module.exports = listReq;