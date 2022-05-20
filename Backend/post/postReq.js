const express = require('express');
const User = require('../model/user');
const bodyParser = require('body-parser');
const postRequest = require('../model/request');

const postReq = express.Router();
postReq.use(bodyParser.json());

postReq.post('/',async (req,res)=>{
    const title = req.body.title;
    const location = req.body.location;
    // const lat = req.body.lat;
    // const long = req.body.long;
    const uuid = req.body.uuid;
    console.log(uuid);
    console.log("posting");
    try{
        const resp = await postRequest.create({
            uuid,
            title,
            location
        });
        console.log("Request added successfully by user ",uuid);
        res.json({status:"ok"})
    }catch(err){
        console.log("Error in creating a request");
        return res.json({status:'error',error:'Error in creating a request!'});
    }

});

module.exports = postReq;